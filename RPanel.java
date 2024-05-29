
package groupproject;

// Imports
import javax.swing.*;
import java.awt.*;

public class RPanel extends JPanel {
    
    // Creates button color
    Color color0 = new Color(143, 186, 167);
    Color color1 = new Color(38, 102, 55);
    Color color2 = new Color(255, 255, 255);
    
      /**
     * Constructs an RPanel with the specified colors.
     *
     * @param color0 The background color of the panel.
     * @param color1 The color for the outlines of the panel.
     * @param color2 The color for additional elements.
     */
    public RPanel(Color color0, Color color1, Color color2) {
        // Super constructor
        super();
        
        // Sets the button color based on the constructor
        this.color0 = color0;
        this.color1 = color1;
        this.color2 = color2;
        
        // Sets filled content area flag
        setBackground(this.color0);
    }
    
     /**
     * Constructs an RPanel with the specified background color.
     *
     * @param color0 The background color of the panel.
     */
    public RPanel(Color color0) {
        // Super constructor
        super();
        
        // Sets the button color based on the constructor
        this.color0 = color0;
        
        // Sets background
        setBackground(this.color0);
    }

    // Constructor that uses the default colors
    public RPanel() {
        // Super constructor
        super();
        
        // Sets background
        setBackground(this.color0);
    }

    /**
     * Paints rectangles on the object
     * @param g graphics component
     */
    @Override
    protected void paintComponent(Graphics g) {
        
        // Calls the paint component from the button class
        super.paintComponent(g);
        g.drawRect(0, 0, getWidth(), 10);
        g.setColor(color1);
        g.fillRect(0, 0, getWidth(), 11);
        g.drawRect(0, getHeight()-20, getWidth(), getHeight());
        g.setColor(color1);
        g.fillRect(0, getHeight()-20, getWidth(), getHeight()+1);
    }
}