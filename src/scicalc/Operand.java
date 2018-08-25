package scicalc;

class Operand implements Token
{
    double value;
    
    Operand (double value)
    {
        this.value=value;
    }
    
    public String toString ()
    {
        return Double.toString (value);
    }
}
