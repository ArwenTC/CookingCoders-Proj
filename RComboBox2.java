package groupproject;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComboBox;

public class RComboBox2<String> extends JComboBox<String> {
    
    /**
     * Constructor
     * @param info
     */
    public RComboBox2(String[] info) {
        super(info);
        
        // Creates colors
        Color defaultColor = new Color(255, 213, 140);
        Color hoverColor = new Color(255, 227, 179);
        Color clickColor = new Color(201, 148, 91);
        
        setForeground(Color.BLACK);
        
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
        g.setColor(new Color(201, 148, 91));
        g.drawRect(0, 0, getWidth(), 2);
        g.fillRect(0, 0, getWidth()+1, 2);
        g.drawRect(0, getHeight()-2, getWidth(), getHeight());
        g.fillRect(0, getHeight()-2, getWidth()+1, getHeight()+1);

        g.setColor(new Color(255, 213, 140));
        g.drawRect(0, 3, 2, getHeight()-6);
        g.fillRect(0, 3, 2, getHeight()-6);
        g.drawRect(26, 3, 3, getHeight()-6);
        g.fillRect(26, 3, 3, getHeight()-6);
    }
    
}
