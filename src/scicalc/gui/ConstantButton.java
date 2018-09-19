package scicalc.gui;

import javax.swing.JButton;
import scicalc.Constant;

//literally just a jbutton with a constant
public class ConstantButton extends JButton
{
    Constant constant;
    
    ConstantButton (String text, Constant constant)
    {
        super (text);
        this.constant=constant;
    }
}
