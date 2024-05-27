

package GroupProject;

// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * 
 * Custom the JMenuItem
 *
 */
public class RMenuItem extends JMenuItem {
	
	// Creates button color
	Color color0 = new Color(50, 168, 82);
	Color hoverColor = new Color(80, 198, 112);
	Color clickColor = new Color(20, 138, 52);
	Color color1 = new Color(255, 255, 255);
	
	 /**
     * Constructs an RMenuItem with the specified text and colors.
     *
     * @param text   The text to display on the menu item.
     * @param color0 The background color of the menu item.
     * @param color1 The color of the text on the menu item.
     */
	public RMenuItem(String text, Color color0, Color color1) {
		// Super constructor
		super(text);
		
		this.color0 = color0;
		this.color1 = color1;
		
		// Sets button colors
		setBackground(this.color0);
		setForeground(this.color1);
		
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
				setBackground(color0);
			}
		});
		
	}
	 /**
     * Constructs an RMenuItem with the specified text using default colors.
     *
     * @param text The text to display on the menu item.
     */
	public RMenuItem(String text) {
		// Super constructor
		super(text);
		
		// Sets button colors
		setBackground(this.color0);
		setForeground(this.color1);
		
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
				setBackground(color0);
			}
		});
	}
}