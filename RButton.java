
// Imports
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.GridLayout;

public class RButton extends JButton {
	
	// Creates button color
	Color buttonColor = new Color(50, 168, 82);
	Color outlineColor = new Color(38, 102, 55);
	Color textColor = new Color(255, 255, 255);
	
	// Constructor that sets all colors
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
	
	// Constructor that sets just button color and outline
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

	// Constructor that uses the default colors
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
