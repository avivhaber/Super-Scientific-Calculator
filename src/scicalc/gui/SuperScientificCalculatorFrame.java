package scicalc.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class SuperScientificCalculatorFrame extends JFrame implements ActionListener, FocusListener
{
    
    Font fieldFont;
    
    JMenuBar bar;
    JMenu file;
    JMenuItem quit;
    JMenuItem about;
    
    JTextField field;
    JPanel primaryButtonPanel; //stores 0-9 as well as basic operators
    JPanel secondaryButtonPanel; //stores buttons for additional functions
    
    JButton addMeme;
    JButton caretLeft;
    JButton caretRight;
    JButton[]numberButtons;
    JButton backspace;
    
    SuperScientificCalculatorFrame ()
    {
        super("Super Scientific Calculator");
        setSize(500, 500);
        setLocationRelativeTo(null); //centers frame
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage (new ImageIcon ("res/icon_128x128.png").getImage()); //image taken from https://freeiconshop.com/icon/calculator-icon-flat/);
    
        fieldFont=new Font("Comic Sans MS",Font.PLAIN,14);
        
        field=new JTextField(20);
        bar=new JMenuBar();
        file=new JMenu("File");
        quit=new JMenuItem("Quit");
        about=new JMenuItem("About");
        
        addMeme=new JButton("Add Meme");
        caretLeft=new JButton("Left");
        caretRight=new JButton("Right");
        backspace=new JButton("DEL");
        numberButtons=new JButton[10];
        for (int x=0;x<numberButtons.length;x++)
        {
            numberButtons[x]=new JButton(Integer.toString(x));
            numberButtons[x].addActionListener(this);
        }
        
        field.setEditable(false);
        field.getCaret().setVisible(true);
        field.setFont(fieldFont);
        field.addFocusListener(this);
    
        addMeme.setHorizontalTextPosition(JButton.CENTER);
        addMeme.setVerticalTextPosition(JButton.CENTER);
        
        file.add(quit);
        file.add(about);
        
        bar.add(file);
        
        add(field);
        add(addMeme);
        add(caretLeft);
        add(caretRight);
        add(backspace);
        for (int x=0;x<numberButtons.length;x++)
        {
            add(numberButtons[x]);
        }
        
        
        quit.addActionListener(this);
        about.addActionListener(this);
        caretLeft.addActionListener(this);
        caretRight.addActionListener(this);
        backspace.addActionListener(this);
        
        addMeme.addActionListener(this);
    
        setJMenuBar(bar);
        setVisible(true);
    }
    
    public void actionPerformed (ActionEvent e)
    {
        for (int x=0;x<numberButtons.length;x++)
        {
            if (e.getSource().equals(numberButtons[x]))
            {
                int temp=field.getCaretPosition();
                field.setText(insert(field.getText(),Integer.toString(x),field.getCaretPosition()));
                field.setCaretPosition(temp+1);
                field.getCaret().setVisible(true);
            }
        }
        
        if (e.getSource().equals(quit))
        {
            dispose();
        }
        else if (e.getSource().equals(about))
        {
            JOptionPane.showMessageDialog(this, "Written by Aviv Haber", "About", JOptionPane.PLAIN_MESSAGE);
        }
        else if (e.getSource().equals(caretLeft) && field.getCaretPosition()>0)
        {
            field.setCaretPosition(field.getCaretPosition()-1);
        }
        else if (e.getSource().equals(caretRight) && field.getCaretPosition()<field.getText().length())
        {
            field.setCaretPosition(field.getCaretPosition()+1);
        }
        else if (e.getSource().equals(addMeme))
        {
            field.setText(insert(field.getText(),"Meme",field.getCaretPosition()));
            field.getCaret().setVisible(true);
        }
        else if (e.getSource().equals(backspace) && field.getCaretPosition()>0)
        {
            field.setText(field.getText().substring(0,field.getCaretPosition()-1)+field.getText().substring(field.getCaretPosition()));
            field.getCaret().setVisible(true);
        }
    }
    
    static String insert (String str, String sub, int pos)
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
