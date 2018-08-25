package scicalc;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuffer;

//Expressions with many parenthesis are evaluated such that subexpressions that are deepest (inside the most parentheses) are evaluated first.
//whenever the minus sign is used as a negative simply enclose it with parenthesis and add (0-1)* before it. The. Any non-operator plus signs can be deleted without consequence.
//if there is are more "(" than ")", and they are otherwise balanced, simply add the required amount of ")" to the end.
class ExpressionParser
{
    String infix;
    String postfix;
    List<Token> expression=new ArrayList<Token>();
    
    ExpressionParser (String input) throws SyntaxException
    {
        infix=input;
        detectErrors();
    }
    
    boolean isNumeric (char ch, boolean includeConstants)
    {
        if (includeConstants)
        {
            return Character.isDigit(ch) || ch == '\u03c0' || ch == '\u212f' || ch == '.';
        }
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
                if (isNumeric(current,false))
                {
                    x++;
                    StringBuffer num=new StringBuffer();
                    num.append(current);
                    current=infix.charAt(x);
                    while (x<infix.length() && isNumeric(current,false))
                    {
                        num.append (current);
                        x++;
                        current=infix.charAt(x);
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
                else if (current=='+')
                {
                    expression.add(new Operator(Operator.OperatorType.ADDITION));
                }
                else if (current=='-')
                {
                    if (expression.size()==0 || !(expression.get(expression.size()-1) instanceof Operand) || !expression.get(expression.size()-1).toString().equals(")"))
                    {
                        expression.add(new Operator(Operator.OperatorType.NEGATION));
                    }
                    else
                    {
                        expression.add(new Operator(Operator.OperatorType.SUBTRACTION));
                    }
                }
                else if (current=='*')
                {
                    expression.add(new Operator(Operator.OperatorType.MULTIPLICATION));
                }
                else if (current=='/')
                {
                    expression.add(new Operator(Operator.OperatorType.DIVISION));
                }
                else if (current=='^')
                {
                    expression.add(new Operator(Operator.OperatorType.EXPONENTATION));
                }
                else if (current=='!')
                {
                    expression.add(new Operator(Operator.OperatorType.FACTORIAL));
                }
                else if (current=='(')
                {
                    expression.add(new Symbol(Symbol.SymbolType.PARENTHESIS_LEFT));
                }
                else if (current==')')
                {
                    expression.add(new Symbol(Symbol.SymbolType.PARENTHESIS_RIGHT));
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
    
    /**
     * Detects easily identifiable input and syntax errors. Performed as an initial check.
     * Harder to find syntax errors are found while the expression is being parsed.
     */ 
    private void detectErrors () throws SyntaxException
    {
        if (!infix.matches(".*\\d+.*") && !infix.contains ("pi") && !infix.contains ("e"))//checks if the string actually contains any numbers or constants, using regex. I have no idea how this works, but it does. Don't question it.
        {
            throw new SyntaxException ("No operands detected");
        }
        else if (infix.contains("()")) //Checks for any empty pair of parentheses.
        {
            throw new SyntaxException ("Empty parentheses");
        }
        //find consecutive operators
    }
    
    static int countOccurrences(String s, String sub)
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