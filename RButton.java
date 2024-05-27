
package GroupProject;

// Imports
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.GridLayout;
/**
 * Custom JButton with customizable appearance.
 */
public class RButton extends JButton {
	
	// Creates button color
	Color buttonColor = new Color(50, 168, 82);
	Color outlineColor = new Color(38, 102, 55);
	Color textColor = new Color(255, 255, 255);
	/**
     * Constructs an RButton with the specified text and colors.
     *
     * @param text         The text to display on the button.
     * @param buttonColor  The background color of the button.
     * @param outlineColor The color of the button outline.
     * @param textColor    The color of the text on the button.
     */
	
	public RButton(String text, Color buttonColor, Color outlineColor, Color textColor) {
		// Super constructor
		super(text);
		
		// Sets the button color based on the constructor
		this.buttonColor = buttonColor;
		this.outlineColor = outlineColor;
		this.textColor = textColor;
		
		// Sets filled content area flag
		setContentAreaFilled(false);
		// Sets the text color to white
		setForeground(textColor);
	}
	
	  /**
     * Constructs an RButton with the specified text, button color, and outline color.
     *
     * @param text         The text to display on the button.
     * @param buttonColor  The background color of the button.
     * @param outlineColor The color of the button outline.
     */
	public RButton(String text, Color buttonColor, Color outlineColor) {
		// Super constructor
		super(text);
		
		// Sets the button color based on the constructor
		this.buttonColor = buttonColor;
		this.outlineColor = outlineColor;
		
		// Sets filled content area flag
		setContentAreaFilled(false);
		// Sets the text color to white
		setForeground(textColor);
	}

	/**
     * Constructs an RButton with the specified text using default colors.
     *
     * @param name The text to display on the button.
     */
	public RButton(String name) {
		// Super constructor
		super(name);
		
		// Sets filled content area flag
		setContentAreaFilled(false);
		// Sets the text color to white
		setForeground(textColor);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		// Gets a new graphic
		Graphics2D style = (Graphics2D) g.create();
		Graphics2D style2 = (Graphics2D) g.create();
		// Sets rendering hint
		style.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//style2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Sets the color of the graphic to green
		style.setColor(this.buttonColor);
		//style2.setColor(this.outlineColor);

		style.fillRect(0, 0, getWidth(), getHeight());;
		//style.fillRect(3, 0, getWidth()-6, getHeight());;
		
		
		// Releases any memory used to modify the style
		style.dispose();
		
		// Calls the paint component from the button class
		super.paintComponent(g);
	}
}