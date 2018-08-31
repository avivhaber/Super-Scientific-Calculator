package scicalc;

import java.util.Scanner;

class ScientificCalculatorApp
{
    public static void main (String [] args) throws SyntaxException
    {
        //System.out.println('\u212f');//e
        //System.out.println('\u221a');//sqrt
        //System.out.println('\u03c0');//pi
        Scanner sc=new Scanner (System.in);
        ExpressionParser exp;
        while (true)
        {
            exp = new ExpressionParser(sc.next(),true);
            System.out.println(exp.getResult());
            //exp.printlist();
        }
    }
}
    