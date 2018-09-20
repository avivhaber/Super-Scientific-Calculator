package scicalc;

public class Operand
{
    private double value;
    
    public Operand (double value)
    {
        this.value=value;
    }
    
    public Operand ()
    {
        this.value=Double.NaN;
    }
    
    double getValue ()
    {
        return value;
    }
    
    void setValue (double value)
    {
        this.value=value;
    }
    
    public String toString ()
    {
        return Double.toString(value);
    }
}
