package scicalc;

//implement abbreviated multiplication
class Operator implements Token
{
    enum OperatorClass {BINARY_LEFT,BINARY_RIGHT,UNARY_PRE,UNARY_POST};
    enum OperatorType {ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, EXPONENTATION, FACTORIAL, NEGATION};
    OperatorClass operatorClass;
    char representation;
    int precedence; //higher precedence means it is carried out first
    
    Operator (OperatorClass operatorClass, char representation, int precedence)
    {
        this.operatorClass=operatorClass;
        this.representation=representation;
        this.precedence=precedence;
    }
    
    Operator (OperatorType operatorType)
    {
        if (operatorType==OperatorType.ADDITION)
        {
            operatorClass=OperatorClass.BINARY_LEFT;
            representation='+';
            precedence=5;
        }
        else if (operatorType==OperatorType.SUBTRACTION)
        {
            operatorClass=OperatorClass.BINARY_LEFT;
            representation='-';
            precedence=5;
        }
        else if (operatorType==OperatorType.MULTIPLICATION)
        {
            operatorClass=OperatorClass.BINARY_LEFT;
            representation='*';
            //representation='\u00d7'
            precedence=10;
        }
        else if (operatorType==OperatorType.DIVISION)
        {
            operatorClass=OperatorClass.BINARY_LEFT;
            representation='/';
            //representation='\u00f7';
            precedence=10;
        }
        else if (operatorType==OperatorType.EXPONENTATION)
        {
            operatorClass=OperatorClass.BINARY_RIGHT;
            representation='^';
            precedence=15;
        }
        else if (operatorType==OperatorType.FACTORIAL)
        {
            operatorClass=OperatorClass.UNARY_POST;
            representation='!';
            precedence=25;
        }
        else if (operatorType==OperatorType.NEGATION)
        {
            operatorClass=OperatorClass.UNARY_PRE;
            representation='-';
            precedence=20;
        }
    }
    
    public String toString()
    {
        return Character.toString(representation);
    }
}
