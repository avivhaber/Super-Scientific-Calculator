package scicalc.gui;

import scicalc.ConstantDatabase;
import scicalc.OperatorDatabase;
import scicalc.ExpressionParser;
import scicalc.Constant;
import scicalc.ConstantDatabase;
import scicalc.Operand;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
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
    
    private ConstantButton pi;
    private ConstantButton euler;
    
    private boolean pressedEquals;
    private Operand answer;
    
    //private JButton;
    
    private ArrayList<Object> contents;
    private StringBuffer effectiveString;
    
    SuperScientificCalculatorFrame ()
    {
        super("SuperSci");
        FlowLayout lay=new FlowLayout(FlowLayout.LEFT,20,100);
        setLayout(lay);
        
        pressedEquals=false;
        answer=new Operand(0);
        
        printCode=new JButton("print");
        
        setSize(300, 400);
        setLocationRelativeTo(null); //centers frame
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try
        {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("res/icon_128x128.png");
            setIconImage(ImageIO.read(is));
        }
        catch (IOException e)
        {
            System.out.print ("icon loading failed");
        }
        
        parser=new ExpressionParser(true);
        effectiveString= new StringBuffer();
        fieldFont=new Font(null,Font.PLAIN,14);
        contents=new ArrayList<Object>();
        
        field=new JTextField(24);
        field.setBackground(Color.WHITE);
        bar=new JMenuBar();
        file=new JMenu("File");
        quit=new JMenuItem("Quit");
        about=new JMenuItem("About");
        
        
        primaryButtonPanel=new JPanel(new FlowLayout());
        secondaryButtonPanel=new JPanel(new FlowLayout());
        tertiaryButtonPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        
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
        
        pi=new ConstantButton(ConstantDatabase.PI.toString(),ConstantDatabase.PI);
        euler=new ConstantButton(ConstantDatabase.E.toString(),ConstantDatabase.E);
        
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
                functionButtons[i].setPreferredSize(new Dimension(63,25));
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
        //tertiaryButtonPanel.add(backspace);
        //tertiaryButtonPanel.add(printCode);
        
        for (int x=numberButtons.length-1;x>=1;x-=3)
        {
            primaryButtonPanel.add(numberButtons[x-2]);
            primaryButtonPanel.add(numberButtons[x-1]);
            primaryButtonPanel.add(numberButtons[x]);
        }
        primaryButtonPanel.add(numberButtons[0]);
        primaryButtonPanel.add(decimalPointButton);
        
        secondaryButtonPanel.add(backspace);
    
        clear.setPreferredSize(new Dimension(53,25));
        equalsButton.setPreferredSize(new Dimension(53,25));
        ans.setPreferredSize(new Dimension(53,25));
        mult.setPreferredSize(new Dimension(53,25));
        div.setPreferredSize(new Dimension(53,25));
        add.setPreferredSize(new Dimension(53,25));
        sub.setSize(new Dimension(53,25));
        sub.setPreferredSize(new Dimension(53,25));
        
        openBracketButton.setPreferredSize(new Dimension(46,25));
        closeBracketButton.setPreferredSize(new Dimension(46,25));
        pi.setPreferredSize(new Dimension(46,25));
        euler.setPreferredSize(new Dimension(46, 25));
        
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
        tertiaryButtonPanel.add(openBracketButton);
        tertiaryButtonPanel.add(closeBracketButton);
        tertiaryButtonPanel.add(pi);
        tertiaryButtonPanel.add(euler);
        
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
        pi.addActionListener(this);
        euler.addActionListener(this);
    
        
        setJMenuBar(bar);
        setVisible(true);
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
            String temp=((FunctionButton) e.getSource()).function.toString();
            temp=temp.replaceAll("x","");//terrible solution, please fix
            field.setText(field.getText()+temp);
            field.getCaret().setVisible(true);
            
        }
        if (e.getSource() instanceof ConstantButton)
        {
            if (pressedEquals)
            {
                clearField();
                pressedEquals=false;
            }
            //int temp=field.getCaretPosition();
            effectiveString.append(((ConstantButton) e.getSource()).constant.getRepresentation());
            String temp=((ConstantButton) e.getSource()).constant.toString();
            field.setText(field.getText()+temp);
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
            effectiveString.append("("+answer.toString()+")");
            field.setText(field.getText()+"Ans");
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(equalsButton))
        {
            parser.setInfix(effectiveString.toString());
            pressedEquals=true;
            try
            {
                Operand d=parser.getResult();
                if (d.getValue() !=Double.NaN)
                {
                    answer = d;
                    field.setText(d.toString());
                    effectiveString.delete(0, effectiveString.length());
                    effectiveString.append(d);
                }
                else
                {
                    answer=new Operand(0);
                    field.setText("Math Error");
                    //doesnt work
                }
            }
            catch (Exception ex)
            {
                field.setText("Syntax Error");
                answer=new Operand(0);
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
