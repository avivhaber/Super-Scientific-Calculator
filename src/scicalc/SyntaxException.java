package scicalc;

public class SyntaxException extends Exception
{
    boolean syntax=false;
    
    public SyntaxException (String msg)
    {
        super (msg);
    }
}