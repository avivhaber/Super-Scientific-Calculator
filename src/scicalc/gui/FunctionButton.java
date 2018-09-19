package scicalc.gui;

import javax.swing.JButton;
import scicalc.OperatorDatabase;

//literally just a jbutton with a function associated with it. Makes storing groups of function buttons way easier
public class FunctionButton extends JButton
{
    OperatorDatabase function;
    
    FunctionButton (String text, OperatorDatabase function)
    {
        super (text);
        this.function=function;
    }
}
