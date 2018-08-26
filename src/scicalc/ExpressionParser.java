package scicalc;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuffer;

//Expressions with many parenthesis are evaluated such that subexpressions that are deepest (inside the most parentheses) are evaluated first.
//whenever the minus sign is used as a negative simply enclose it with parenthesis and add (0-1)* before it. The. Any non-operator plus signs can be deleted without consequence.
//if there is are more "(" than ")", and they are otherwise balanced, simply add the required amount of ")" to the end.
//insert abbr. mult symbols. cases: (4+4)(5+5), 1/2(4), 2log5,

class ExpressionParser
{
    private String infix;
    private String postfix;
    private List<Object> expression=new ArrayList<Object>();
    
    ExpressionParser (String input) throws SyntaxException
    {
        infix=input;
        detectImmediateErrors();
    }
    
    boolean isNumeric (char ch)
    {
        return Character.isDigit(ch) || ch == '.';
    }
    
    void tokenize ()
    {
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
                    expression.add(new Operand(ConstantDatabase.constantLookup.get(infix.substring(x,x+5))));
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
                            expression.add(new Operator(OperatorDatabase.ADDITION));
                        }
                    }
                    else if (temp.equals(OperatorDatabase.SUBTRACTION.getRepresentation()))
                    {
                        if (isBinaryContext())
                        {
                            expression.add(new Operator(OperatorDatabase.SUBTRACTION));
                        }
                        else
                        {
                            expression.add(new Operator(OperatorDatabase.NEGATION));
                        }
                    }
                    else //all other operators and functions
                    {
                        expression.add(new Operator(OperatorDatabase.operatorLookup.get(temp)));
                    }
                    x+=4;
                }
            }
        }
    }
    
    void printlist ()
    {
        for (int x=0;x<expression.size();x++)
        {
            System.out.println (expression.get(x).toString());
        }
    }
    
    
    private boolean isBinaryContext ()
    {
        if (expression.size()==0)
        {
            return false;
        }
        
        Object previous=expression.get(expression.size()-1);
        boolean condition1=previous instanceof Operand;
        boolean condition2=previous instanceof Symbol && ((Symbol) previous).getSymbolType()==Symbol.SymbolType.PARENTHESIS_CLOSE;
        boolean condition3=previous instanceof Operator && ((Operator) previous).getOperatorClass()== Operator.OperatorClass.UNARY_POST;
        
        return condition1 || condition2 || condition3;
    }
    
    /**
     * Detects easily identifiable input and syntax errors. Performed as an initial check.
     * Harder to find syntax errors are found while the expression is being parsed.
     */ 
    private void detectImmediateErrors () throws SyntaxException
    {
        if (infix.contains("()")) //Checks for any empty pair of parentheses.
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
    
    void insertAbbreviatedMultiplicationSigns ()
    {
        for (int x=expression.size()-1; x>0; x--)
        {
            Object current=expression.get(x);
            Object previous=expression.get(x-1);
    
            //System.out.println ("nice1");
            if (current instanceof Symbol && ((Symbol) current).getSymbolType()==Symbol.SymbolType.PARENTHESIS_OPEN)
            {
                if
                (previous instanceof Operand
                || (previous instanceof Operator && ((Operator) previous).getOperatorClass()== Operator.OperatorClass.UNARY_POST)
                || (previous instanceof Symbol && ((Symbol) previous).getSymbolType()==Symbol.SymbolType.PARENTHESIS_CLOSE))
                {
                    expression.add (x,new Operator(OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW));
                }
            }
            if (current instanceof Operator && ((Operator) current).getOperatorClass()==Operator.OperatorClass.FUNCTION)
            {
                if
                (previous instanceof Operand
                || (previous instanceof Symbol && ((Symbol) previous).getSymbolType()==Symbol.SymbolType.PARENTHESIS_CLOSE)
                || (previous instanceof Operator && ((Operator) previous).getOperatorClass()== Operator.OperatorClass.UNARY_POST))
                {
                    expression.add (x,new Operator(OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW));
                }
            }
            if (previous instanceof Symbol && ((Symbol) previous).getSymbolType()==Symbol.SymbolType.PARENTHESIS_CLOSE)
            {
                if (current instanceof Operand)
                {
                    expression.add (x,new Operator(OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW));
                }
            }
            
            if (current instanceof Operand && ((Operand)current).getIsConstant())
            {
                if
                (previous instanceof Operand
                || (previous instanceof Operator && ((Operator) previous).getOperatorClass()== Operator.OperatorClass.UNARY_POST))
                {
                    expression.add (x,new Operator(OperatorDatabase.MULTIPLICATION_ABBREVIATED_HIGH));
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