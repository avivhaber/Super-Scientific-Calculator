package scicalc;

class Operand
{
    private double value;
    
    Operand (double value)
    {
        this.value=value;
    }
    
    Operand ()
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
