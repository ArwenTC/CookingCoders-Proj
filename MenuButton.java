
// Imports
import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {
	
	// Creates button color
	Color buttonColor = new Color(57, 130, 69);
	
	public MenuButton(String name) {
		// Creates a button using the name field
		super(name);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		// Gets a new graphic
		Graphics2D style = (Graphics2D) g.create();
		// Sets the color of the graphic to green
		style.setColor(new Color(57, 130, 69));
		
		// Releases any memory used to modify the style
		style.dispose();
		
		// Calls the paint component from the button class
		super.paintComponent(g);
	}
}
