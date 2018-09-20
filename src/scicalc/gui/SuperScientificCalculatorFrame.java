package scicalc.gui;

import scicalc.OperatorDatabase;
import scicalc.ExpressionParser;
import scicalc.Operand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class SuperScientificCalculatorFrame extends JFrame implements ActionListener, FocusListener
{
    private JButton printCode;
    
    private ExpressionParser parser;
    
    private Font fieldFont;
    
    private JMenuBar bar;
    private JMenu file;
    private JMenuItem quit;
    private JMenuItem about;
    
    private JTextField field;
    private JPanel primaryButtonPanel; //stores 0-9 as well as basic operators
    private JPanel secondaryButtonPanel; //stores buttons for additional functions
    
    private JButton[]numberButtons;
    private FunctionButton[]functionButtons;
    private JButton decimalPointButton;
    private JButton backspace;
    private JButton equalsButton;
    
    //private JButton;
    
    private ArrayList<Object> contents;
    private StringBuffer effectiveString;
    
    SuperScientificCalculatorFrame ()
    {
        super("Super Scientific Calculator");
        
        
        printCode=new JButton("print");
        
        setSize(500, 1000);
        setLocationRelativeTo(null); //centers frame
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage (new ImageIcon ("res/icon_128x128.png").getImage()); //image taken from https://freeiconshop.com/icon/calculator-icon-flat/);
        
        
        parser=new ExpressionParser(true);
        effectiveString= new StringBuffer();
        fieldFont=new Font(null,Font.PLAIN,14);
        contents=new ArrayList<Object>();
        
        field=new JTextField(20);
        bar=new JMenuBar();
        file=new JMenu("File");
        quit=new JMenuItem("Quit");
        about=new JMenuItem("About");
        
        backspace=new JButton("DEL");
        equalsButton=new JButton("=");
        numberButtons=new JButton[10];
        for (int x=0;x<numberButtons.length;x++)
        {
            numberButtons[x]=new JButton(Integer.toString(x));
            numberButtons[x].addActionListener(this);
        }
        decimalPointButton=new JButton(".");
        
        functionButtons=new FunctionButton[OperatorDatabase.values().length-2];
        int i=0;
        for (OperatorDatabase od : OperatorDatabase.values())
        {
            if (!od.equals(OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW) && !od.equals(OperatorDatabase.MULTIPLICATION_ABBREVIATED_HIGH))
            {
                functionButtons[i] = new FunctionButton(od.toString(), od);
                functionButtons[i].addActionListener(this);
                i++;
            }
        }
        
        field.setEditable(false);
        field.getCaret().setVisible(true);
        field.setFont(fieldFont);
        field.addFocusListener(this);
        
        
        file.add(quit);
        file.add(about);
        
        bar.add(file);
        
        add(field);
        add(backspace);
        add(decimalPointButton);
        add(equalsButton);
        add(printCode);
        for (int x=0;x<numberButtons.length;x++)
        {
            add(numberButtons[x]);
        }
        for (int x=0;x<functionButtons.length;x++)
        {
            add(functionButtons[x]);
        }
        
        
        quit.addActionListener(this);
        about.addActionListener(this);
        backspace.addActionListener(this);
        decimalPointButton.addActionListener(this);
        equalsButton.addActionListener(this);
        printCode.addActionListener(this);
        
    
        setJMenuBar(bar);
        setVisible(true);
    }
    
    public void actionPerformed (ActionEvent e)
    {
        for (int x=0;x<numberButtons.length;x++)
        {
            if (e.getSource().equals(numberButtons[x]))
            {
                //int temp=field.getCaretPosition();
                effectiveString.append(x);
                field.setText(field.getText()+x);
                //field.setCaretPosition(temp+1);
                field.getCaret().setVisible(true);
            }
        }
        
        if (e.getSource() instanceof FunctionButton)
        {
            //int temp=field.getCaretPosition();
            effectiveString.append(((FunctionButton) e.getSource()).function.getRepresentation());
            field.setText(field.getText()+((FunctionButton) e.getSource()).function.toString());
            field.getCaret().setVisible(true);
            
        }
        else if (e.getSource().equals(quit))
        {
            dispose();
        }
        else if (e.getSource().equals(about))
        {
            JOptionPane.showMessageDialog(this, "Written by Aviv Haber", "About", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource().equals(backspace) && field.getCaretPosition()>0 && effectiveString.length()>0)
        {
            delete();
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(decimalPointButton))
        {
            //int temp=field.getCaretPosition();
            effectiveString.append('.');
            field.setText(field.getText()+'.');
            //field.setCaretPosition(temp+1);
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(printCode))
        {
            System.out.println(effectiveString.toString());
        }
        else if (e.getSource().equals(equalsButton))
        {
            parser.setInfix(effectiveString.toString());
            try
            {
                double d=parser.getResult();
                field.setText(Double.toString(d));
                effectiveString.delete(0,effectiveString.length());
                effectiveString.append(d);
            }
            catch (Exception ex)
            {
                field.setText(ex.getMessage());
            }
        }
    }
    
    private void delete ()
    {
        String text=effectiveString.toString();
        int pos=text.length();
        if (ExpressionParser.isNumeric(text.charAt(pos-1)))
        {
            effectiveString.deleteCharAt(effectiveString.length()-1);
            field.setText(field.getText().substring(0,field.getText().length()-1));
        }
        else if (text.charAt(pos-1)==';')
        {
            effectiveString.delete(effectiveString.length()-5,effectiveString.length());
            System.out.println (effectiveString);
            field.setText(field.getText().substring(0,field.getText().length()-OperatorDatabase.operatorLookup.get(text.substring(text.length()-5)).toString().length()));//im so sorry about this line
        }
    }
    
    private static String insert (String str, String sub, int pos)
    {
        if (pos<0 || pos>str.length()) throw new IllegalArgumentException("invalid position for string insertion");
        if (pos==str.length()) return str+sub;
        return str.substring(0,pos)+sub+str.substring(pos);
    }
    
    public void focusLost(FocusEvent e)
    {
        return;
    }
    
    public void focusGained(FocusEvent e)
    {
        field.getCaret().setVisible(true);
    }
}
