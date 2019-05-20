package scicalc;

public class Fraction extends Operand
{
    public static final int MAX_DECIMAL_PLACES=7;
    int num;
    int den;
    
    @Override
    public double getValue()
    {
        return (double)num / (double)den;
    }
    
    
    public Fraction (int num, int den) throws ArithmeticException
    {
        if (den==0)
        {
            throw new ArithmeticException("Cannot have zero denominator.");
        }
        
        int m1=1,m2=1;
        if (num<0 && den<0)
        {
            m1=-1;
            m2=-1;
        }
        else if ((den<0) && !(num<0))
        {
            m1=-1;
            m2=-1;
        }
        
        this.num=m1*num;
        this.den=m2*den;
        this.reduce();
    }
    
    public static Fraction toFraction (double in) throws SyntaxException
    {
        String s=Double.toString(in);
        int zeros=s.length()-s.indexOf('.')-1;
        String numString=s.replace(".","");
        
        if (Math.max(numString.length(),zeros+1)>MAX_DECIMAL_PLACES)
        {
            throw new SyntaxException("Number too large to convert to fraction.");
        }
        
        int n=Integer.parseInt(numString);
        String denString="1";
        for (int x=0; x<zeros; x++) denString+="0";
        int d=Integer.parseInt(denString);
        return new Fraction(n,d);
    }
    
    public void add (Fraction f)
    {
        if (num==0)
        {
            num=f.num;
            den=f.den;
            return;
        }
        else if (f.num==0) return;
        
        double prod=den*f.den;
        
        num*=(prod/den);
        den=(int)prod;
        num+=f.num*(prod/f.den);
        reduce();
    }
    
    public void subtract (Fraction f)
    {
        f.num*=-1;
        add(f);
    }
    
    public void multiply (Fraction f)
    {
        num*=f.num;
        den*=f.den;
        reduce();
    }
    
    public void divide (Fraction f)
    {
        if (f.num==0) throw new ArithmeticException("Cannot divide by zero fraction.");
        num*=f.den;
        den*=f.num;
        reduce();
    }
    
    public void reduce ()
    {
        int g=Math.abs(gcd(num,den));
        if (g!=0)
        {
            num/=g;
            den/=g;
        }
    }
    
    
    private static int gcd(int p, int q)
    {
        while (q != 0) {
            int temp = q;
            q = p % q;
            p = temp;
        }
        return p;
    }
    
    @Override
    public String toString()
    {
        if (num==0)
        {
            return "0";
        }
        else if (den==1)
        {
            return Integer.toString(num);
        }
        return String.format("%d\u00f7%d",num,den);
    }
    
    public static void main (String [] args)
    {
        try{
            Fraction f = toFraction(-0.456);
            System.out.println(f);
            f.divide(new Fraction(1,1));
            System.out.println(f.getValue());
        }catch(Exception e){System.out.println(e);}
    }
}
