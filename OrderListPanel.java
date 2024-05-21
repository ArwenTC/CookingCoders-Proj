package GroupProject;

import java.awt.Color;
import java.awt.Dimension;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OrderListPanel extends RPanel {
	
	ArrayList<OPanel> orderPanels;
	
	JScrollPane orderScroll;
	
	SQLDatabase database_;
	
	/**
	 * Constructor
	 */
	public OrderListPanel(SQLDatabase database_) {
		// Calls super constructor
		super();
		
		this.database_ = database_;
		
		orderPanels = new ArrayList<OPanel>();
		
		// Initializes 
		orderScroll = new JScrollPane();
		
		ResultSet orders = database_.getDatabaseInfo("order");
		try {
			int index = 0;
			// Iterates through the result set and adds items to the order panel
			while (orders.next()) {
				orderPanels.add(new OPanel(new Order(orders.getInt(1), database_)));
				orderScroll.add(orderPanels.get(index));
				// Ups the index
				index++;
			}
		} catch (SQLException e) {
			// Catch exception
		}
		
		add(orderScroll);
		
		// Repaints the changes
		revalidate();
		repaint();
	}
	
	/**
	 * Panel used to display an order
	 * @author Arwen
	 */
	private class OPanel extends JPanel {
		
		// Creates a new button
		private JButton remove;
		
		/**
		 * Constructor
		 * @param order
		 */
		public OPanel(Order order) {
			super();
			
			remove = new JButton("X");
			
			// New preferred size
			setPreferredSize(new Dimension(400, 75));
			
			// Sets background color
			setBackground(new Color(252, 223, 141));
			
			// Adds the button to the panel
			add(remove);
		}
	}
	
	
}
