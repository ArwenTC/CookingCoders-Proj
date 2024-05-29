package groupproject;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RTextField extends JTextField {

    /**
     * Constructor method
     */
    public RTextField() {
        super();

        // Creates colors
        Color defaultColor = new Color(255, 213, 140);
        
        // Sets the text color to white
        setForeground(new Color(23, 14, 7));
        
        // Sets the default color
        setBackground(defaultColor);
        
        // Removes the goofy outline
        setBorder(null);
    }
    

    /**
     * Paints rectangles on the object
     * @param g graphics component
     */
    @Override
    protected void paintComponent(Graphics g) {
        
        // Calls the paint component from the button class
        super.paintComponent(g);
        g.setColor(new Color(201, 148, 91));
        g.drawRect(0, getHeight()-3, getWidth(), getHeight());
        g.fillRect(0, getHeight()-3, getWidth(), getHeight());
        g.drawRect(0, 0, getWidth(), 2);
        g.fillRect(0, 0, getWidth(), 2);
    }
}
