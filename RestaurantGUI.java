
import javax.swing.*;
import java.awt.*;

public class RestaurantGUI extends JFrame {

	// Window Size
	final int SIZE_X = 500, SIZE_Y = 500;
	
	// Panels that will be swapped depending what the user selects
	JPanel masterP;
	JPanel viewOrderP;
	JPanel createOrderP;
	JPanel EmployeeViewOrderP;
	JPanel orderListP;
	JPanel createAccountP;
	JPanel logInP;
	JPanel manageUsersP;
	JPanel settingsP;
	
	// Menu bar that allows user to swap between each view
	JMenuBar menu;
	// Menu bar buttons
	JMenuItem viewOrderB;
	JMenuItem createOrderB;
	JMenuItem orderListB;
	JMenuItem logInB;
	JMenuItem logOutB;
	JMenuItem manageB;
	
	/**
	 * Creates the RestaurantGUI
	 */
	public RestaurantGUI() {
		
   		// Set the title.
   		setTitle("Order Manager");
   		
   		// Specify what happens when the close button is clicked.
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		
   		// Build and add the panel that contains the other components.
   		build();
   		
   		// Adds the master panel
   		add(masterP);
   		// Sets the menu bar created by the build
   		setJMenuBar(menu);
   		
   		// Size and display the window.
   		setSize(SIZE_X,SIZE_Y);
   		setVisible(true);
   		
	}
	
	/**
	 * Builds the JFrame an sets values
	 */
	public void build() {
		
		// Creates Panels
		masterP = new RPanel();
		viewOrderP = new RPanel();
		createOrderP = new RPanel();
		EmployeeViewOrderP = new RPanel();
		orderListP = new RPanel();
		createAccountP = new RPanel();
		logInP = new RPanel();
		manageUsersP = new RPanel();
		settingsP = new RPanel();
		
		// Menu bar that allows user to swap between each view
		menu = new JMenuBar();
		menu.setLayout(new GridLayout(1, 0, 3, 0));
		menu.setBackground(new Color(38, 102, 55));
		menu.setBorderPainted(false);
		// Menu bar buttons
		viewOrderB = new RMenuItem("View Order");
		createOrderB = new RMenuItem("Create Order");
		orderListB = new RMenuItem("Order List");
		logInB = new RMenuItem("Log In");
		logOutB = new RMenuItem("Log Out");
		manageB = new RMenuItem("Manage");
		manageB.add(new PopupMenu("message"));
		
		//setCustomerView();
		//setEmployeeView();
		setManagerView();
		
	}
	
	public void setCustomerView() {
		// Removes all items from the menu
		menu.remove(viewOrderB);
		menu.remove(createOrderB);
		menu.remove(orderListB);
		menu.remove(manageB);
		
		// Adds necessary items to the menu
		menu.add(viewOrderB);
		menu.add(createOrderB);
		//menu.add(orderListB);
		//menu.add(manageB);
		
		// Logs user out
		logOut();
	}
	public void setEmployeeView() {
		// Removes all items from the menu
		menu.remove(viewOrderB);
		menu.remove(createOrderB);
		menu.remove(orderListB);
		menu.remove(manageB);
		
		// Adds necessary items to the menu
		//menu.add(viewOrderB);
		menu.add(orderListB);
		menu.add(createOrderB);
		//menu.add(manageB);
		
		// Logs user out
		logOut();
	}
	public void setManagerView() {
		// Removes all items from the menu
		menu.remove(viewOrderB);
		menu.remove(orderListB);
		menu.remove(createOrderB);
		menu.remove(manageB);
		
		// Adds necessary items to the menu
		//menu.add(viewOrderB);
		menu.add(orderListB);
		menu.add(createOrderB);
		menu.add(manageB);
		
		// Logs user out
		logOut();
	}
	
	public void logIn() {
		
		// Sets the tabs viewed
		menu.remove(logInB);
		menu.add(logOutB);
	}
	
	public void logOut() {
		
		// Sets the tabs viewed
		menu.remove(logOutB);
		menu.add(logInB);
	}
}