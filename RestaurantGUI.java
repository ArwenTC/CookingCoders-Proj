
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
	JButton viewOrderB;
	JButton createOrderB;
	JButton orderListB;
	JButton logInB;
	JButton logOutB;
	JButton usersB;
	JButton settingsB;
	
	
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
	
	public void build() {
		
		// Creates Panels
		masterP = new JPanel();
		viewOrderP = new JPanel();
		createOrderP = new JPanel();
		EmployeeViewOrderP = new JPanel();
		orderListP = new JPanel();
		createAccountP = new JPanel();
		logInP = new JPanel();
		manageUsersP = new JPanel();
		settingsP = new JPanel();
		
		// Menu bar that allows user to swap between each view
		menu = new JMenuBar();
		// Menu bar buttons
		viewOrderB = new JButton("View Order");
		createOrderB = new JButton("Create Order");
		orderListB = new JButton("Order List");
		logInB = new JButton("Log In");
		logOutB = new JButton("Log Out");
		usersB = new JButton("Users");
		settingsB = new JButton("Settings");
		
		// Adds all buttons to the menu bar
		menu.add(viewOrderB);
		menu.add(createOrderB);
		menu.add(orderListB);
		menu.add(logInB);
		menu.add(logOutB);
		menu.add(usersB);
		menu.add(settingsB);
	}
	
	
	
}