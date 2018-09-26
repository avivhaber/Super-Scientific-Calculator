package scicalc.gui;

import javax.swing.JButton;
import scicalc.ConstantDatabase;
import scicalc.ConstantDatabase;

//literally just a jbutton with a constant
public class ConstantButton extends JButton
{
    ConstantDatabase constant;
    
    ConstantButton (String text, ConstantDatabase constant)
    {
        super (text);
        this.constant=constant;
    }
}
