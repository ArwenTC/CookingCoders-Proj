
package groupproject;

// Imports
import javax.swing.*;
import java.awt.*;

public class ViewOrderPanel extends JPanel {
    
    boolean isCustomer = false;
    
    // Creates button color
    Color color0 = new Color(143, 186, 167);
    Color color1 = new Color(38, 102, 55);
    Color color2 = new Color(255, 255, 255);

    Color boxColor0 = new Color(255, 213, 140);
    Color boxColor1 = new Color(163, 207, 187);
    Color highlightColor0 = new Color(255, 221, 161);
    Color highlightColor1 = new Color(175, 219, 199);
    
    /**
     * Constructor that uses default colors
     */
    public ViewOrderPanel() {
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
        
        // Order Boxes
        g.setColor(boxColor1);
        g.drawRect(5, 20, 360, 140);
        g.fillRect(5, 20, 361, 141);

        g.setColor(highlightColor1);
        g.drawRect(5, 20, 360, 5);
        g.fillRect(5, 20, 360, 5);
        g.drawRect(5, 155, 360, 5);
        g.fillRect(5, 155, 360, 5);
        
        if (this.isCustomer) {
            // Creates box that does not store order status
            g.setColor(boxColor0);
            g.drawRect(370, 200, 170, 125);
            g.fillRect(370, 200, 171, 126);
        } else {
            // Creates box to contain order status
            g.setColor(boxColor0);
            g.drawRect(370, 200, 170, 125);
            g.fillRect(370, 200, 171, 126);    
        }

        g.setColor(highlightColor0);
        g.drawRect(370, 200, 170, 20);
        g.fillRect(370, 200, 171, 20);
        
        
        
        // Normal panel drawings
        g.drawRect(0, 0, getWidth(), 10);
        g.setColor(color1);
        g.fillRect(0, 0, getWidth(), 11);
        g.drawRect(0, getHeight()-20, getWidth(), getHeight());
        g.setColor(color1);
        g.fillRect(0, getHeight()-20, getWidth(), getHeight()+1);
    }
}