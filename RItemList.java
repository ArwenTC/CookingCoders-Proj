package groupproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListModel;

public class RItemList<String> extends JList<String> {

    /**
     * Constructor method
     * @param array
     */
    public RItemList(String[] array) {
        super(array);

        // Creates colors
        Color defaultColor = new Color(255, 213, 140);
        Color hoverColor = new Color(255, 227, 179);
        Color clickColor = new Color(201, 148, 91);
        
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
