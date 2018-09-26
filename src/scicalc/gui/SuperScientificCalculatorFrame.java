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
    private JPanel primaryButtonPanel; //stores 0-9, and .
    private JPanel secondaryButtonPanel; //basic buttons and operators
    private JPanel tertiaryButtonPanel;
    
    private JButton[]numberButtons;
    private FunctionButton[]functionButtons;
    private JButton decimalPointButton;
    private JButton backspace;
    private JButton equalsButton;
    private JButton clear;
    
    private JButton openBracketButton;
    private JButton closeBracketButton;
    
    private FunctionButton mult;
    private FunctionButton div;
    private FunctionButton add;
    private FunctionButton sub;
    private JButton ans;
    
    private boolean pressedEquals;
    private double answer;
    
    //private JButton;
    
    private ArrayList<Object> contents;
    private StringBuffer effectiveString;
    
    SuperScientificCalculatorFrame ()
    {
        super("SuperSci");
        FlowLayout lay=new FlowLayout(FlowLayout.CENTER,20,100);
        setLayout(lay);
        
        pressedEquals=false;
        answer=0;
        
        printCode=new JButton("print");
        
        setSize(300, 400);
        setLocationRelativeTo(null); //centers frame
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage (new ImageIcon ("res/icon_128x128.png").getImage()); //image taken from https://freeiconshop.com/icon/calculator-icon-flat/);
        
        parser=new ExpressionParser(true);
        effectiveString= new StringBuffer();
        fieldFont=new Font(null,Font.PLAIN,14);
        contents=new ArrayList<Object>();
        
        field=new JTextField(22);
        bar=new JMenuBar();
        file=new JMenu("File");
        quit=new JMenuItem("Quit");
        about=new JMenuItem("About");
        
        
        primaryButtonPanel=new JPanel(new FlowLayout());
        secondaryButtonPanel=new JPanel(new FlowLayout());
        tertiaryButtonPanel=new JPanel(new FlowLayout());
        
        primaryButtonPanel.setPreferredSize(new Dimension(132, 200));
        secondaryButtonPanel.setPreferredSize(new Dimension(116,200));
        tertiaryButtonPanel.setPreferredSize(new Dimension(280,150));
        
        openBracketButton=new JButton("(");
        closeBracketButton=new JButton(")");
        backspace=new JButton("DEL");
        clear=new JButton("C");
        equalsButton=new JButton("=");
        ans=new JButton("Ans");
        numberButtons=new JButton[10];
        
        mult=new FunctionButton(OperatorDatabase.MULTIPLICATION.toString(),OperatorDatabase.MULTIPLICATION);
        div=new FunctionButton(OperatorDatabase.DIVISION.toString(),OperatorDatabase.DIVISION);
        add=new FunctionButton(OperatorDatabase.ADDITION.toString(),OperatorDatabase.ADDITION);
        sub=new FunctionButton(OperatorDatabase.SUBTRACTION.toString(),OperatorDatabase.SUBTRACTION);
        
        for (int x=0;x<numberButtons.length;x++)
        {
            numberButtons[x]=new JButton(Integer.toString(x));
            numberButtons[x].addActionListener(this);
        }
        decimalPointButton=new JButton(".");
        
        functionButtons=new FunctionButton[OperatorDatabase.values().length-7];
        int i=0;
        for (OperatorDatabase od : OperatorDatabase.values())
        {
            if (!od.equals(OperatorDatabase.MULTIPLICATION_ABBREVIATED_LOW) &&
                    !od.equals(OperatorDatabase.MULTIPLICATION_ABBREVIATED_HIGH) &&
                    !od.equals(OperatorDatabase.MULTIPLICATION) &&
                    !od.equals(OperatorDatabase.DIVISION) &&
                    !od.equals(OperatorDatabase.ADDITION) &&
                    !od.equals(OperatorDatabase.SUBTRACTION) &&
                    !od.equals(OperatorDatabase.NEGATION)
            )
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
        
        tertiaryButtonPanel.add(field);
        tertiaryButtonPanel.add(backspace);
        //tertiaryButtonPanel.add(printCode);
        tertiaryButtonPanel.add(openBracketButton);
        tertiaryButtonPanel.add(closeBracketButton);
        
        for (int x=numberButtons.length-1;x>=1;x-=3)
        {
            primaryButtonPanel.add(numberButtons[x-2]);
            primaryButtonPanel.add(numberButtons[x-1]);
            primaryButtonPanel.add(numberButtons[x]);
        }
        primaryButtonPanel.add(numberButtons[0]);
        primaryButtonPanel.add(decimalPointButton);
        
        secondaryButtonPanel.add(backspace);
        
        secondaryButtonPanel.add(clear);
        secondaryButtonPanel.add(mult);
        secondaryButtonPanel.add(div);
        secondaryButtonPanel.add(add);
        secondaryButtonPanel.add(sub);
        secondaryButtonPanel.add(ans);
        secondaryButtonPanel.add(equalsButton);
        
        
        for (int x=0;x<functionButtons.length;x++)
        {
            tertiaryButtonPanel.add(functionButtons[x]);
        }
        
        add (tertiaryButtonPanel);
        add(primaryButtonPanel);
        add(secondaryButtonPanel);
        
        quit.addActionListener(this);
        about.addActionListener(this);
        backspace.addActionListener(this);
        clear.addActionListener(this);
        decimalPointButton.addActionListener(this);
        equalsButton.addActionListener(this);
        printCode.addActionListener(this);
        openBracketButton.addActionListener(this);
        closeBracketButton.addActionListener(this);
        mult.addActionListener(this);
        div.addActionListener(this);
        add.addActionListener(this);
        sub.addActionListener(this);
        ans.addActionListener(this);
    
        
        setJMenuBar(bar);
        setVisible(true);
    
        clear.setPreferredSize(backspace.getSize());
        equalsButton.setPreferredSize(backspace.getSize());
        ans.setPreferredSize(backspace.getSize());
        mult.setPreferredSize(backspace.getSize());
        div.setPreferredSize(backspace.getSize());
        add.setPreferredSize(backspace.getSize());
        sub.setSize(backspace.getSize());
        sub.setPreferredSize(backspace.getSize());
        revalidate();
        repaint();
    }
    
    public void actionPerformed (ActionEvent e)
    {
        for (int x=0;x<numberButtons.length;x++)
        {
            if (e.getSource().equals(numberButtons[x]))
            {
                if (pressedEquals)
                {
                    clearField();
                    pressedEquals=false;
                }
                effectiveString.append(x);
                field.setText(field.getText()+x);
                field.getCaret().setVisible(true);
            }
        }
        
        if (e.getSource() instanceof FunctionButton)
        {
            if (pressedEquals)
            {
                clearField();
                pressedEquals=false;
            }
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
        else if (e.getSource().equals(clear))
        {
            clearField();
        }
        else if (e.getSource().equals(decimalPointButton))
        {
            if (pressedEquals)
            {
                clearField();
                pressedEquals=false;
            }
            //int temp=field.getCaretPosition();
            effectiveString.append('.');
            field.setText(field.getText()+'.');
            //field.setCaretPosition(temp+1);
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(openBracketButton))
        {
            if (pressedEquals)
            {
                clearField();
                pressedEquals=false;
            }
            effectiveString.append('(');
            field.setText(field.getText()+'(');
            //field.setCaretPosition(temp+1);
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(closeBracketButton))
        {
            if (pressedEquals)
            {
                clearField();
                pressedEquals=false;
            }
            effectiveString.append(')');
            field.setText(field.getText()+')');
            //field.setCaretPosition(temp+1);
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(printCode))
        {
            System.out.println(effectiveString.toString());
        }
        else if (e.getSource().equals(ans))
        {
            if (pressedEquals)
            {
                clearField();
                pressedEquals=false;
            }
            effectiveString.append("("+Double.toString(answer)+")");
            field.setText(field.getText()+"Ans");
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(equalsButton))
        {
            parser.setInfix(effectiveString.toString());
            pressedEquals=true;
            try
            {
                double d=parser.getResult();
                answer=d;
                field.setText(Double.toString(d));
                effectiveString.delete(0,effectiveString.length());
                effectiveString.append(d);
            }
            catch (Exception ex)
            {
                field.setText(ex.getMessage());
                answer=0;
            }
        }
    }
    
    private void delete ()
    {
        String text=effectiveString.toString();
        int pos=text.length();
        if (ExpressionParser.isNumeric(text.charAt(pos-1)) || text.charAt(pos-1)=='(' || text.charAt(pos-1)==')')
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
    
    private void clearField()
    {
        effectiveString.delete(0,effectiveString.length());
        field.setText("");
        field.getCaret().setVisible(true);
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
