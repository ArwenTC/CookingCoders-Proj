package GroupProject;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class RScrollPane extends JScrollPane {
	
	public RScrollPane() {
		super();
		
		setBorder(null);
	    getVerticalScrollBar().setBackground(new Color(222, 185, 126));
	    getVerticalScrollBar().setUI(new BasicScrollBarUI() {
	    	@Override
	        protected JButton createDecreaseButton(int orientation) {
	    		// Creates a button with no size
	    		JButton down = new JButton();
	    		down.setPreferredSize(new Dimension(0,0));
	    		down.setMaximumSize(new Dimension(0,0));
	    		return down;
	    	}
	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
	        	// Creates a button with no size
	    		JButton up = new JButton();
	    		up.setPreferredSize(new Dimension(0,0));
	    		up.setMaximumSize(new Dimension(0,0));
	    		return up;
	        }
	        @Override
	        protected void configureScrollBarColors() {
	            this.thumbColor = new Color(255, 227, 179);
	        }
	    });
	}
}
