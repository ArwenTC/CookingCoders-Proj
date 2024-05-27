
package GroupProject;

// Imports
import javax.swing.*;
import java.awt.*;

public class ViewOrderPanel extends RPanel {

	// Items in the view order panel
	RButton tempButton;
	
	/**
     * Constructs a ViewOrderPanel object.
     */
	public ViewOrderPanel() {
		super(new Color(0, 0, 0));
		
		setLayout(new GridLayout(1,1));
		build();
	}
	
	/**
	 * Adds items to the panel
	 */
	public void build() {
		// Adds created items to the viewOrder Panel
		tempButton = new RButton("View Order Panel");
		
		// Adds example to the panel
		add(tempButton);
	}

}