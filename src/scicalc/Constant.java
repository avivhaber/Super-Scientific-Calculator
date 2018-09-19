package scicalc;

//Essentially just a wrapper class for ConstantDatabase. Was made because enumerations cannot act as subclasses.
public class Constant extends Operand
{
    ConstantDatabase constant;
    
    Constant (ConstantDatabase constant)
    {
        this.constant=constant;
    }
    
    @Override
    double getValue()
    {
        return constant.getValue();
    }
    
    @Override
    void setValue(double value)
    {
        constant.setValue(value);
    }
    
    String getRepresentation ()
    {
        return constant.getRepresentation();
    }
    
    public String toString()
    {
        return constant.toString();
    }
}
