package scicalc;

import java.util.HashMap;

//each constant's string representation is a lowercase 3 character code enclosed between '%' and ';'. This consistent format must be kept for the parser to work.
enum ConstantDatabase
{
    PI ("%pie;", Math.PI), //Pi, the ratio of a circle's circumference to its diameter.
    E ("%eul;", Math.E), //Euler's constant, the base of the natural logarithm
    LARGE_G ("%lgg;", 6.67408e-11); //Gravitational constant used on newtons equation (F= m1*m2 / r^2)
    
    private String representation;
    private double value;
    
    static HashMap<String,ConstantDatabase> constantLookup=new HashMap<String,ConstantDatabase>();
    static
    {
        for (ConstantDatabase cd : ConstantDatabase.values())
        {
            constantLookup.put(cd.representation, cd);
        }
    }
    
    ConstantDatabase(String representation, double value)
    {
        this.representation=representation;
        this.value=value;
    }
    
    String getRepresentation()
    {
        return this.representation;
    }
    
    double getValue ()
    {
        return this.value;
    }
}
