package scicalc;

import java.util.HashMap;

//each operator and function's string representation is a lowercase 3 character code enclosed between '&' and ';'. This consistent format must be kept for the parser to work.
enum OperatorDatabase
{
    ADDITION (Operator.OperatorClass.BINARY_LEFT, "&add;", 5),
    SUBTRACTION (Operator.OperatorClass.BINARY_LEFT, "&sub;", 5),
    MULTIPLICATION (Operator.OperatorClass.BINARY_LEFT, "&mul;", 10),
    DIVISION (Operator.OperatorClass.BINARY_LEFT, "&div;", 10),
    MULTIPLICATION_ABBREVIATED_LOW (Operator.OperatorClass.BINARY_LEFT, "&aml;", 15), //used before parentheses and functions. ex: 4sin30->4@sin30, 2(e)->2@(e)
    NEGATION (Operator.OperatorClass.UNARY_PRE,"&neg;",20),
    
    //functions
    SINE (Operator.OperatorClass.FUNCTION, "&sin;", 20),
    COSINE (Operator.OperatorClass.FUNCTION, "&cos;", 20),
    
    MULTIPLICATION_ABBREVIATED_HIGH (Operator.OperatorClass.BINARY_LEFT, "&amh;", 25), //used before constants and variables. ex: 4pi->4#pi
    EXPONENTATION (Operator.OperatorClass.BINARY_RIGHT, "&exp;", 30),
    FACTORIAL (Operator.OperatorClass.UNARY_POST, "&fac;", 35);
    
    private Operator.OperatorClass operatorClass;
    private String representation;
    private int precedence;
    
    static HashMap<String,OperatorDatabase> operatorLookup=new HashMap<String,OperatorDatabase>();
    static
    {
        for (OperatorDatabase od : OperatorDatabase.values())
        {
            operatorLookup.put(od.representation, od);
        }
    }
    
    OperatorDatabase (Operator.OperatorClass operatorClass, String representation, int precedence)
    {
        this.operatorClass=operatorClass;
        this.representation=representation;
        this.precedence=precedence;
    }
    
    Operator.OperatorClass getOperatorClass ()
    {
        return this.operatorClass;
    }
    
    String getRepresentation ()
    {
        return this.representation;
    }
    
    int getPrecedence ()
    {
        return this.precedence;
    }
}
