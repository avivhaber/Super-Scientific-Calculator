package scicalc;

//implement abbreviated multiplication
class Operator
{
    enum OperatorClass {BINARY_LEFT,BINARY_RIGHT,UNARY_PRE,UNARY_POST, FUNCTION};
    private OperatorClass operatorClass;
    private String representation;
    private int precedence; //higher precedence means it is carried out first
    
    Operator (OperatorClass operatorClass, String representation, int precedence)
    {
        this.operatorClass=operatorClass;
        this.representation=representation;
        this.precedence=precedence;
    }
    
    Operator (OperatorDatabase operatorType)
    {
        this.operatorClass=operatorType.getOperatorClass();
        this.representation=operatorType.getRepresentation();
        this.precedence=operatorType.getPrecedence();
    }
    
    OperatorClass getOperatorClass()
    {
        return this.operatorClass;
    }
    
    public String toString()
    {
        return representation;
    }
}
