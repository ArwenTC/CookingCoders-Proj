package groupproject;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComboBox;

public class RComboBox<String> extends JComboBox<String> {
    
    /**
     * Constructor
     * @param info
     */
    public RComboBox(String[] info) {
        super(info);
        
        // Creates colors
        Color defaultColor = new Color(50, 168, 82);
        Color hoverColor = new Color(80, 186, 96);
        Color clickColor = new Color(38, 102, 55);
        
        setForeground(Color.WHITE);
        
        setBackground(defaultColor);
        
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
        g.setColor(new Color(38, 102, 55));
        g.drawRect(0, 0, getWidth(), 2);
        g.fillRect(0, 0, getWidth()+1, 2);
        g.drawRect(0, getHeight()-2, getWidth(), getHeight());
        g.fillRect(0, getHeight()-2, getWidth()+1, getHeight()+1);

        g.setColor(new Color(50, 168, 82));
        g.drawRect(0, 3, 2, getHeight()-6);
        g.fillRect(0, 3, 2, getHeight()-6);
        g.drawRect(63, 3, 3, getHeight()-6);
        g.fillRect(63, 3, 3, getHeight()-6);
    }
    
}
