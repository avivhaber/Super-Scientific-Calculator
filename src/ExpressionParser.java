class ExpressionParser
{
    String infix;
    String postfix;
    public ExpressionParser (String input) throws SyntaxException
    {
        infix=input;
        detectErrors();
    }
    
    /**
     * Detects easily identifiable input and syntax errors. Performed as an initial check.
     * Harder to find syntax errors are found while the expression is being parsed.
     */ 
    public void detectErrors () throws SyntaxException
    {
        if (countOccurrences(infix,"(")!= countOccurrences(infix,")"))//checks if the number of left brackets equals the number of right brackets
        {
            throw new SyntaxException ("Parenthesis Imbalance");
        }
        else if (!infix.matches(".*\\d+.*") && !infix.contains ("pi") && !infix.contains ("e"))//checks if the string contains any numbers or constants. uses regex. i have no idea how this works, but it does. dont question it.
        {
            throw new SyntaxException ("Syntax Error");
        }
        else if (infix.contains("()"))
        {
            throw new SyntaxException ("Syntax Error");
        }
        //find consecutive operators
    }
    
    public static int countOccurrences(String s, String sub)
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
    
    public int getPrecedence (String token)
    {
        if (token.contains("sin")||token.contains("cos")||token.contains("tan")||token.contains("sin")||token.contains("log")||token.contains("ln"))
        {
            return 0;
        }
        if (token.equals("^")) return 1;
        if (token.equals("#")) return 2;//# means abbreviated multiplication. ex. 1/2pi=1/2#pi
        if (token.equals("*")||token.equals("/")) return 3;
        if (token.equals("+")||token.equals("-")) return 4;
        return -1; //abnormal output, indicates error
    }
}