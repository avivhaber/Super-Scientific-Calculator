package scicalc;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuffer;

//whenever the minus sign is used as a negative simply enclose it with parenthesis and add (0-1)* before it. The. Any non-operator plus signs can be deleted without consequence.
//if there is are more "(" than ")", and they are otherwise balanced, simply add the required amount of ")" to the end.
//insert abbr. mult symbols. cases: (4+4)(5+5), 1/2(4), 2log5,
//get rid of useless operator class

public class ExpressionParser
{
    private String infix;
    private List<Object> expression;
    private List<Object> postfix;
    private boolean useDegrees;
    
    public ExpressionParser (String infix, boolean useDegrees)
    {
        this.infix=infix;
        this.useDegrees=useDegrees;
        expression=new ArrayList<Object>();
        postfix=new ArrayList<Object>();
    }
    
    public ExpressionParser (boolean useDegrees)
    {
        this.useDegrees=useDegrees;
        expression=new ArrayList<Object>();
        postfix=new ArrayList<Object>();
    }
    
    public void setInfix (String infix)
    {
        this.infix=infix;
    }
    
    public String getInfix()
    {
        return infix;
    }
    
    public boolean getUseDegrees()
    {
        return useDegrees;
    }
    
    void setUseDegrees (boolean useDegrees)
    {
        this.useDegrees=useDegrees;
    }
    
    public double getResult () throws SyntaxException
    {
        detectImmediateErrors();
        tokenize();
        insertAbbreviatedMultiplicationSigns();
        convertToPostfix();
        return evaluatePostfix();
    }
    
    public static boolean isNumeric (char ch)
    {
        return Character.isDigit(ch) || ch == '.';
    }
    
    private void tokenize ()
    {
        expression.clear();
        int x;
        for (x=0;x<infix.length();x++)
        {
            char current=infix.charAt(x);
            if (!Character.isWhitespace(current))
            {
                if (isNumeric(current))
                {
                    x++;
                    StringBuffer num=new StringBuffer();
                    num.append(current);
                    while (x<infix.length() && isNumeric(infix.charAt(x)))
                    {
                        num.append (infix.charAt(x));
                        x++;
                    }
                    x--;
                    
                    try
                    {
                        expression.add(new Operand(Double.parseDouble(num.toString())));
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("not a number idiot");
                    }
                }
                
                else if (current=='%')//Checks if the next token is a constant, since all constants are in the form "%abc;"
                {
                    expression.add(new Constant(ConstantDatabase.constantLookup.get(infix.substring(x,x+5))));
                    x+=4;
                }

                else if (current=='(')
                {
                    expression.add(new Symbol(Symbol.SymbolType.PARENTHESIS_OPEN));
                }
                else if (current==')')
                {
                    expression.add(new Symbol(Symbol.SymbolType.PARENTHESIS_CLOSE));
                }
                
                else if (current=='&')//Checks if the next token is an operator, since all operators are in the form "&abc;"
                {
                    String temp=infix.substring(x,x+5);
                    
                    //special checks must be performed for + and - since they can be used in both a binary and unary context.
                    if (temp.equals(OperatorDatabase.ADDITION.getRepresentation()))
                    {
                        if (isBinaryContext())
                        {
                            expression.add(OperatorDatabase.ADDITION);
                        }
                    }
                    else if (temp.equals(OperatorDatabase.NEGATION.getRepresentation()))
                    {
                        if (isBinaryContext())
                        {
                            expression.add(OperatorDatabase.SUBTRACTION);
                        }
                        else
                        {
                            expression.add(OperatorDatabase.NEGATION);
                        }
                    }
                    else //all other operators and functions
                    {
                        expression.add(OperatorDatabase.operatorLookup.get(temp));
                    }
                    x+=4;
                }
            }
        }
    }
   
    //Uses an adapted shunting-yard algorithm to convert the infix expression to postfix.
    private void convertToPostfix () throws SyntaxException
    {
        postfix.clear();
        Stack<Object> operatorStack=new Stack<Object>();
        
        for (int x=0;x<expression.size();x++)
        {
            Object current=expression.get(x);
            
            if (current instanceof Operand || isUnaryPost(current))
            {
                postfix.add(current);
            }
            else if (isFunction(current) || isUnaryPre(current))
            {
                operatorStack.push(current);
            }
            else if (isBinaryLeft(current))
            {
                while (!operatorStack.isEmpty() && getPrecedence(operatorStack.peek())>=getPrecedence(current))
                {
                    postfix.add(operatorStack.pop());
                }
                operatorStack.push(current);
            }
            else if (isBinaryRight(current))
            {
                while (!operatorStack.isEmpty() && getPrecedence(operatorStack.peek())>getPrecedence(current))
                {
                    postfix.add(operatorStack.pop());
                }
                operatorStack.push(current);
            }
            else if (isOpenParenthesis(current))
            {
                operatorStack.push(current);
            }
            else if (isCloseParenthesis(current))
            {
                while (!isOpenParenthesis(operatorStack.peek()))
                {
                    postfix.add(operatorStack.pop());
                    if (operatorStack.isEmpty())
                    {
                        throw new SyntaxException("Parentheses mismatch");
                    }
                }
                operatorStack.pop();
                if (!operatorStack.isEmpty() && isFunction(operatorStack.peek()))
                {
                    postfix.add(operatorStack.pop());
                }
            }
        }
        while (!operatorStack.isEmpty())
        {
            if (isOpenParenthesis(operatorStack.peek()))
            {
                throw new SyntaxException("Parentheses mismatch");
            }
            postfix.add(operatorStack.pop());
        }
    }
    
    private double evaluatePostfix () throws SyntaxException
    {
        Stack<Double> postfixStack = new Stack<Double>();
        MathEvaluator evaluator = new MathEvaluator(useDegrees);
        
        for (int x=0; x<postfix.size(); x++)
        {
            Object current = postfix.get(x);
            if (current instanceof Operand)
            {
                postfixStack.push(((Operand)current).getValue());
            }
            else if (current instanceof OperatorDatabase)
            {
                if (isUnaryPre(current) || isUnaryPost(current) || isFunction(current))
                {
                    if (postfixStack.isEmpty())
                    {
                        throw new SyntaxException("No operands left to perform operation");
                    }
                    else
                    {
                        postfixStack.push(evaluator.evaluate((OperatorDatabase)current,postfixStack.pop()));
                    }
                }
                else if (isBinaryLeft(current) || isBinaryRight(current))
                {
                    if (postfixStack.size()<2)
                    {
                        throw new SyntaxException("No operands left to perform operation");
                    }
                    else
                    {
                        double temp = postfixStack.pop();
                        postfixStack.push(evaluator.evaluate((OperatorDatabase) current,postfixStack.pop(),temp));
                    }
                }
                else
                {
                    throw new SyntaxException("Unexpected operator class in postfix list");
                }
            }
            else
            {
                throw new SyntaxException("Unexpected object object in postfix list:" + current.toString());
            }
        }
        
        if (postfixStack.size()>1)
        {
            System.out.println(postfixStack.pop()+" "+postfixStack.pop());
            throw new SyntaxException("More than one object remaining on stack:" + postfixStack.peek().toString());
        }
        
        return postfixStack.pop();
    }
    
    void printlist ()
    {
        for (int x=0;x<expression.size();x++)
        {
            System.out.println (expression.get(x).toString());
        }
        System.out.println ();
        for (int x=0;x<postfix.size();x++)
        {
            System.out.println (postfix.get(x).toString());
        }
    }
    
    private boolean isBinaryLeft (Object o)
    {
        return (o instanceof OperatorDatabase && ((OperatorDatabase) o).getOperatorClass()== OperatorDatabase.OperatorClass.BINARY_LEFT);
    }
    
    private boolean isBinaryRight (Object o)
    {
        return (o instanceof OperatorDatabase && ((OperatorDatabase) o).getOperatorClass()== OperatorDatabase.OperatorClass.BINARY_RIGHT);
    }
    
    private boolean isUnaryPre (Object o)
    {
        return (o instanceof OperatorDatabase && ((OperatorDatabase) o).getOperatorClass()== OperatorDatabase.OperatorClass.UNARY_PRE);
    }
    
    private boolean isUnaryPost (Object o)
    {
        return (o instanceof OperatorDatabase && ((OperatorDatabase) o).getOperatorClass()== OperatorDatabase.OperatorClass.UNARY_POST);
    }
    
    private boolean isFunction(Object o)
    {
        return (o instanceof OperatorDatabase && ((OperatorDatabase) o).getOperatorClass()== OperatorDatabase.OperatorClass.FUNCTION);
    }
    
    private boolean isOpenParenthesis (Object o)
    {
        return (o instanceof Symbol && ((Symbol) o).getSymbolType()== Symbol.SymbolType.PARENTHESIS_OPEN);
    }
    
    private boolean isCloseParenthesis (Object o)
    {
        return (o instanceof Symbol && ((Symbol) o).getSymbolType()== Symbol.SymbolType.PARENTHESIS_CLOSE);
    }
    
    private boolean isConstant (Object o)
    {
        return o instanceof Constant;
    }
    
    private boolean isNumber (Object o)
    {
        return o instanceof Operand;
    }
    
    private int getPrecedence (Object o)
    {
        if (o instanceof OperatorDatabase)
        {
            return ((OperatorDatabase) o).getPrecedence();
        }
        return -1;
    }
    
    private boolean isBinaryContext ()
    {
        if (expression.size()==0)
        {
            return false;
        }
        
        Object previous=expression.get(expression.size()-1);
        
        return previous instanceof Operand || isCloseParenthesis(previous) || isUnaryPost(previous);
    }
    
    /**
     * Detects easily identifiable input and syntax errors. Performed as an initial check.
     * Harder to find syntax errors are found while the expression is being parsed.
     */ 
    private void detectImmediateErrors () throws SyntaxException
    {
        if (infix.contains("()") || infix.charAt(infix.length()-1)=='(') //Checks for any empty pair of parentheses.
        {
            throw new SyntaxException ("Empty parentheses");
        }
        int difference=countOccurrences(infix,"(")-countOccurrences(infix,")");
        if (difference<0)
        {
            throw new SyntaxException ("Empty parentheses");
        }
        else
        {
            for (int x=0;x<difference;x++)
            {
                infix+=")";
            }
        }
    }
    
    private void insertAbbreviatedMultiplicationSigns ()
    {
        for (int x=expression.size()-1; x>0; x--)
        {
            Object current=expression.get(x);
            Object previous=expression.get(x-1);
            
            if (isOpenParenthesis(current) || isFunction(current))
            {
                if (previous instanceof Operand || isUnaryPost(previous) || isCloseParenthesis(previous))
                {
                    expression.add (x,OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW);
                }
            }
            if (isCloseParenthesis(previous))
            {
                if (current instanceof Operand)
                {
                    expression.add (x,OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW);
                }
            }
            
            if (isConstant(current))
            {
                if (previous instanceof Operand || isUnaryPost(previous))
                {
                    expression.add (x,OperatorDatabase.MULTIPLICATION_ABBREVIATED_HIGH);
                }
            }
        }
    }
    
    private static int countOccurrences(String s, String sub)
    {
        int count=0;
        int index=s.indexOf(sub);
        
        while (index!=-1)
        {
            count++;
            index=s.indexOf(sub,index+1);
        }
        return count;
    }
}