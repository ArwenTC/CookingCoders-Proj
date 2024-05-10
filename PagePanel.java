
// Imports
import javax.swing.*;
import java.awt.*;

public class PagePanel extends JPanel {
	
	// Creates button color
	Color color0 = new Color(50, 168, 82);
	Color color1 = new Color(38, 102, 55);
	Color color2 = new Color(255, 255, 255);
	
	// Constructor that sets all colors
	public PagePanel(Color color0, Color color1, Color color2) {
		// Super constructor
		super();
		
		// Sets the button color based on the constructor
		this.color0 = color0;
		this.color1 = color1;
		this.color2 = color2;
		
		// Sets filled content area flag
		setBackground(this.color0);
	}
	
	// Constructor that sets just button color and outline
	public PagePanel(Color color0) {
		// Super constructor
		super();
		
		// Sets the button color based on the constructor
		this.color0 = color0;
		
		// Sets background
		setBackground(this.color0);
	}

	// Constructor that uses the default colors
	public PagePanel() {
		// Super constructor
		super();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		// Calls the paint component from the button class
		super.paintComponent(g);
		g.drawRect(0, 0, getWidth(), 10);
		g.setColor(color1);
		g.fillRect(0, 0, getWidth(), 10);
		g.drawRect(0, getHeight()-30, getWidth(), getHeight());
		g.setColor(color1);
		g.fillRect(0, getHeight()-30, getWidth(), getHeight());
	}
}
