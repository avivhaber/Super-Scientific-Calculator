package scicalc;

import java.util.HashMap;

//each operator and function's string representation is a lowercase 3 character code enclosed between '&' and ';'. This consistent format must be kept for the parser to work.
public enum OperatorDatabase
{
    ADDITION (OperatorClass.BINARY_LEFT, "&add;", "+", 5),
    SUBTRACTION (OperatorClass.BINARY_LEFT, "&sub;", "-", 5),
    MULTIPLICATION (OperatorClass.BINARY_LEFT, "&mul;", "\u00d7", 10),
    DIVISION (OperatorClass.BINARY_LEFT, "&div;", "\u00f7", 10),
    MULTIPLICATION_ABBREVIATED_LOW (OperatorClass.BINARY_LEFT, "&aml;", "@", 15), //used before parentheses and functions. ex: 4sin30->4@sin30, 2(e)->2@(e)
    NEGATION (OperatorClass.UNARY_PRE,"&neg;", "-", 20),
    
    //functions
    SINE (OperatorClass.FUNCTION, "&sin;", "sin", 20),
    COSINE (OperatorClass.FUNCTION, "&cos;", "cos", 20),
    TANGENT (OperatorClass.FUNCTION, "&tan;", "tan", 20),
    ARC_SINE (OperatorClass.FUNCTION, "&asn;", "sin\u207b\u00b9", 20),
    ARC_COSINE (OperatorClass.FUNCTION, "&acs;", "cos\u207b\u00b9", 20),
    ARC_TANGENT (OperatorClass.FUNCTION, "&atn;", "tan\u207b\u00b9", 20),
    LOG_DECIMAL (OperatorClass.FUNCTION, "&lgd;", "log\u23e8", 20),
    LOG_NATURAL (OperatorClass.FUNCTION, "&lgn;", "ln", 20),
    SQUARE_ROOT (OperatorClass.FUNCTION, "&srt;", "\u221a", 20),
    CUBE_ROOT (OperatorClass.FUNCTION, "&crt;", "\u221b", 20),
    GAMMA (OperatorClass.FUNCTION, "&gam;", "\u0393", 20),
    ABSOLUTE (OperatorClass.FUNCTION, "&abs;", "abs", 20),
    
    MULTIPLICATION_ABBREVIATED_HIGH (OperatorClass.BINARY_LEFT, "&amh;", "#", 25), //used before constants and variables. ex: 4pi->4#pi
    EXPONENTIATION (OperatorClass.BINARY_RIGHT, "&exp;", "^", 30),
    FACTORIAL (OperatorClass.UNARY_POST, "&fac;", "!", 35),
    RECIPROCAL (OperatorClass.UNARY_POST, "&rcl;", "\u207b\u00b9", 35),
    SQUARE (OperatorClass.UNARY_POST, "&sqr;", "\u00b2", 35),
    CUBE (OperatorClass.UNARY_POST, "&cub;", "\u00b3", 35);
    
    private OperatorClass operatorClass;
    private String representation;
    private String outputString;
    private int precedence;
    
    enum OperatorClass {BINARY_LEFT,BINARY_RIGHT,UNARY_PRE,UNARY_POST, FUNCTION};
    
    public static HashMap<String,OperatorDatabase> operatorLookup=new HashMap<String,OperatorDatabase>();
    
    static
    {
        for (OperatorDatabase od : OperatorDatabase.values())
        {
            operatorLookup.put(od.representation, od);
        }
    }
    
    OperatorDatabase (OperatorClass operatorClass, String representation, String outputString, int precedence)
    {
        this.operatorClass=operatorClass;
        this.representation=representation;
        this.outputString=outputString;
        this.precedence=precedence;
    }
    
    OperatorClass getOperatorClass ()
    {
        return this.operatorClass;
    }
    
    public String getRepresentation ()
    {
        return this.representation;
    }
    
    public String toString ()
    {
        return this.outputString;
    }
    
    int getPrecedence ()
    {
        return this.precedence;
    }
}
