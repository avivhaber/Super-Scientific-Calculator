package scicalc;

import java.util.HashMap;

//each constant's string representation is a lowercase 3 character code enclosed between '%' and ';'. This consistent format must be kept for the parser to work.
public enum ConstantDatabase
{
    VARIABLE_X ("%var;", "x", 1),
    ANSWER ("%ans;", "Ans", 0),
    PI ("%pie;", "\u03c0",Math.PI), //Pi, the ratio of a circle's circumference to its diameter.
    E ("%eul;", "\u212f", Math.E), //Euler's constant, the base of the natural logarithm
    LARGE_G ("%lgg;","G", 6.67408e-11); //Gravitational constant used on newtons equation (Fg = m1*m2 / r^2)
    
    private String representation;
    private String outputString;
    private double value;
    
    static HashMap<String,ConstantDatabase> constantLookup=new HashMap<String,ConstantDatabase>();
    static
    {
        for (ConstantDatabase cd : ConstantDatabase.values())
        {
            constantLookup.put(cd.representation, cd);
        }
    }
    
    ConstantDatabase(String representation, String outputString, double value)
    {
        this.representation=representation;
        this.outputString=outputString;
        this.value=value;
    }
    
    void setValue (double value)
    {
        this.value=value;
    }
    
    public String getRepresentation()
    {
        return this.representation;
    }
    
    public String toString()
    {
        return this.outputString;
    }
    
    double getValue ()
    {
        return this.value;
    }
}
