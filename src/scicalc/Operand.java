package scicalc;

class Operand
{
    private double value;
    private boolean isConstant; //Stores whether the operand is a calculator supported constant, like pi and e.
    private String representation;
    
    Operand (double value)
    {
        this.value=value;
        this.representation=Double.toString(value);
        this.isConstant=false;
    }
    
    Operand (ConstantDatabase constant)
    {
        this.representation=constant.getRepresentation();
        isConstant=true;
    }
    
    boolean getIsConstant ()
    {
        return isConstant;
    }
    
    public String toString()
    {
        return representation;
    }
}
