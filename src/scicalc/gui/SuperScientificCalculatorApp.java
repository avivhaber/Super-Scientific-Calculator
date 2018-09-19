package scicalc.gui;

import scicalc.ExpressionParser;
import scicalc.MathEvaluator;
import scicalc.SyntaxException;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Scanner;

class ScientificCalculatorApp
{
    public static void main (String [] args) throws SyntaxException
    {
//        //System.out.println('\u212f');//e
//        //System.out.println('\u221a');//sqrt
//        //System.out.println('\u03c0');//pi
//        Scanner sc=new Scanner (System.in);
//        ExpressionParser exp;
//        while (true)
//        {
//            exp = new ExpressionParser(sc.next(),false);
//            double meme=exp.getResult();
//            System.out.println(meme);
//            //System.out.println(MathEvaluator.round(meme,MathEvaluator.numDecimals(meme)-2, BigDecimal.ROUND_HALF_UP));
//            //System.out.println(StrictMath.sin(Math.PI));
//            //exp.printlist();
//        }
        
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }
                catch(Exception e){}
                new SuperScientificCalculatorFrame();
            }
        });
    }
}
    