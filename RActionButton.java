package groupproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class RActionButton extends JButton {
    
    /**
     * Constructor method
     */
    public RActionButton(String text) {
        super(text);

        // Creates colors
        Color defaultColor = new Color(50, 168, 82);
        Color hoverColor = new Color(80, 186, 96);
        Color clickColor = new Color(38, 102, 55);
        
        // Sets the text color to white
        setForeground(Color.WHITE);
        
        // Sets the default color
        setBackground(defaultColor);
        
        // Removes the goofy outline
        setBorderPainted(false);
        setBorder(null);
        
        // Adds mouse listener methods
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                setBackground(clickColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                setBackground(hoverColor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                setBackground(defaultColor);
            }
        });
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
        g.drawRect(getWidth()-4, 0, getWidth(), getHeight());
        g.fillRect(getWidth()-4, 0, getWidth(), getHeight());
        g.drawRect(0, 0, 4, getHeight());
        g.fillRect(0, 0, 4, getHeight());
    }
    
    
    
    
}
