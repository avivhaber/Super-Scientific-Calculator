package scicalc;

public class MathEvaluator
{
    private boolean useDegrees;
    private OperatorDatabase operator;
    
    MathEvaluator (boolean useDegrees)
    {
        this.operator=operator;
        this.useDegrees=useDegrees;
    }
    
    boolean getUseDegrees ()
    {
        return useDegrees;
    }
    
    void setUseDegrees (boolean useDegrees)
    {
        this.useDegrees=useDegrees;
    }
    
    private double factorial (double n)
    {
        if (n==0)
        {
            return 1;
        }
        return n*factorial(n-1);
    }
    
    //Taken from https://introcs.cs.princeton.edu/java/91float/Gamma.java.html by Roger Sedgewick.
    private double logGamma(double x)
    {
        double tmp = (x - 0.5) * StrictMath.log(x + 4.5) - (x + 4.5);
        double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
        return tmp + StrictMath.log(ser * StrictMath.sqrt(2 * StrictMath.PI));
    }
    
    private double gamma(double x)
    {
        return StrictMath.exp(logGamma(x));
    }
    
    double evaluate (OperatorDatabase operator, double arg1)
    {
        if (operator==OperatorDatabase.NEGATION)
        {
            return -arg1;
        }
        else if (operator==OperatorDatabase.SINE)
        {
            if (useDegrees)
            {
                return StrictMath.sin(StrictMath.toRadians(arg1));
            }
            else
            {
                return StrictMath.sin(arg1);
            }
        }
        else if (operator==OperatorDatabase.COSINE)
        {
            if (useDegrees)
            {
                return StrictMath.cos(StrictMath.toRadians(arg1));
            }
            else
            {
                return StrictMath.cos(arg1);
            }
        }
        else if (operator==OperatorDatabase.TANGENT)
        {
            if (useDegrees)
            {
                return StrictMath.tan(StrictMath.toRadians(arg1));
            }
            else
            {
                return StrictMath.tan(arg1);
            }
        }
        else if (operator==OperatorDatabase.ARC_SINE)
        {
            if (useDegrees)
            {
                return StrictMath.toDegrees(StrictMath.asin(arg1));
            }
            else
            {
                return StrictMath.asin(arg1);
            }
        }
        else if (operator==OperatorDatabase.ARC_COSINE)
        {
            if (useDegrees)
            {
                return StrictMath.toDegrees(StrictMath.acos(arg1));
            }
            else
            {
                return StrictMath.asin(arg1);
            }
        }
        else if (operator==OperatorDatabase.ARC_TANGENT)
        {
            if (useDegrees)
            {
                return StrictMath.toDegrees(StrictMath.atan(arg1));
            }
            else
            {
                return StrictMath.atan(arg1);
            }
        }
        else if (operator==OperatorDatabase.LOG_DECIMAL)
        {
            return StrictMath.log10(arg1);
        }
        else if (operator==OperatorDatabase.LOG_NATURAL)
        {
            return StrictMath.log(arg1);
        }
        else if (operator==OperatorDatabase.SQUARE_ROOT)
        {
            return StrictMath.sqrt(arg1);
        }
        else if (operator==OperatorDatabase.CUBE_ROOT)
        {
            return StrictMath.cbrt(arg1);
        }
        else if (operator==OperatorDatabase.GAMMA)
        {
            return gamma (arg1);
        }
        else if (operator==OperatorDatabase.ABSOLUTE)
        {
            return StrictMath.abs(arg1);
        }
        else if (operator==OperatorDatabase.FACTORIAL)
        {
            if (StrictMath.floor(arg1)!=arg1 || arg1<0)
            {
                throw new IllegalArgumentException("Factorial function can only operate on whole numbers.");
            }
            return factorial (arg1);
        }
        else if (operator==OperatorDatabase.RECIPROCAL)
        {
            return 1/arg1;
        }
        else if (operator==OperatorDatabase.SQUARE)
        {
            return arg1*arg1;
        }
        else if (operator==OperatorDatabase.CUBE)
        {
            return arg1*arg1*arg1;
        }
        else
        {
            throw new IllegalArgumentException("Function '" + operator + "' with 2 arguments not found.");
        }
    }
    
    double evaluate (OperatorDatabase operator, double arg1, double arg2)
    {
        if (operator==OperatorDatabase.ADDITION)
        {
            return arg1+arg2;
        }
        else if (operator==OperatorDatabase.SUBTRACTION)
        {
            return arg1-arg2;
        }
        else if (operator==OperatorDatabase.MULTIPLICATION || operator==OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW || operator==OperatorDatabase.MULTIPLICATION_ABBREVIATED_HIGH)
        {
            return arg1*arg2;
        }
        else if (operator==OperatorDatabase.DIVISION)
        {
            return arg1/arg2;
        }
        else if (operator==OperatorDatabase.EXPONENTIATION)
        {
            return Math.pow (arg1,arg2);
        }
        else
        {
            throw new IllegalArgumentException("Function '" + operator + "' with 1 argument not found.");
        }
    }
}
