
package GroupProject;

// Imports
import javax.swing.*;
import java.awt.*;

public class OrderListPanel extends JPanel {
	
	// Creates button color
	Color color0 = new Color(143, 186, 167);
	Color color1 = new Color(38, 102, 55);
	Color color2 = new Color(255, 255, 255);

	Color boxColor0 = new Color(255, 213, 140);
	Color boxColor1 = new Color(163, 207, 187);
	Color highlightColor0 = new Color(255, 221, 161);
	Color highlightColor1 = new Color(175, 219, 199);
	
	// Constructor that uses the default colors
	public OrderListPanel() {
		// Super constructor
		super();
		// Sets background
		setBackground(this.color0);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		// Calls the paint component from the button class
		super.paintComponent(g);
		
		// Order Boxes
		g.setColor(boxColor1);
		g.drawRect(30, 20, 520, 275);
		g.fillRect(30, 20, 521, 276);

		// Creates a highlight to the order list box
		g.setColor(highlightColor1);
		g.drawRect(30, 20, 520, 18);
		g.fillRect(30, 20, 521, 18);
		g.drawRect(30, 277, 520, 28);
		g.fillRect(30, 277, 521, 28);
		
		// Normal panel drawings
		g.drawRect(0, 0, getWidth(), 10);
		g.setColor(color1);
		g.fillRect(0, 0, getWidth(), 11);
		g.drawRect(0, getHeight()-20, getWidth(), getHeight());
		g.setColor(color1);
		g.fillRect(0, getHeight()-20, getWidth(), getHeight()+1);
	}
}