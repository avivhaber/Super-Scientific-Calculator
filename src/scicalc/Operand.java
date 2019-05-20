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
    
    public double getValue ()
    {
        return value;
    }
    
    public void setValue (double value)
    {
        this.value=value;
    }
    
    public String toString ()
    {
        return Double.toString(value);
    }
}
