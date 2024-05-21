
package GroupProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RestaurantGUI extends JFrame {

	// Window Size
	final int SIZE_X = 600, SIZE_Y = 400;
	
	// Panels that will be swapped depending what the user selects
	JPanel masterP;
	JPanel viewOrderP;
	JPanel createOrderP;
	JPanel employeeViewOrderP;
	JPanel orderListP;
	JPanel manageUsersP;
	JPanel settingsP;
	
	// Menu bar that allows user to swap between each view
	JMenuBar menu;
	// Menu bar buttons
	JMenuItem viewOrderB;
	JMenuItem createOrderB;
	JMenuItem orderListB;
	JMenuItem manageB;
	
	LoginWindow loginWindow;
	
	SQLDatabase myDatabase;
    
    User loggedInUser;

    String chosenBuildingName;

    JLabel lblBuildingName;
    JTextField buildingNameField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField textField;
    private JScrollPane scrollPaneMenu;
    private JList<String> menuList;
    private JLabel lblCustomerTotal;
    private JLabel lblOrderID;
    private JScrollPane scrollPaneNote;
    private JLabel lblNote;
    private JTextArea textArea;

	/**
	 * Creates the RestaurantGUI
	 */
	public RestaurantGUI(SQLDatabase myDatabase_) {
	    
	    myDatabase = myDatabase_;
		
   		// Set the title.
   		setTitle("Order Manager");
   		
   		// Specify what happens when the close button is clicked.
   		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		
   		// Build and add the panel that contains the other components.
   		build();

        loginWindow.toggleVisibility();

        // wait for user login
        try {
            while (!loginWindow.userIsLoggedIn()) {
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Makes the login window invisible
        loginWindow.toggleVisibility();
		
		System.out.println(loginWindow.getProgramView());
   		
   		// Adds the master panel
   		getContentPane().add(masterP);
   		createOrderP.setLayout(null);
   		
   		
   		String[] foodMenuTestData = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        
        scrollPaneMenu = new JScrollPane();
        scrollPaneMenu.setBounds(376, 63, 100, 100);
        createOrderP.add(scrollPaneMenu);
        
        menuList = new JList<String>(foodMenuTestData);
        
        scrollPaneMenu.setViewportView(menuList);
        
        JLabel lblMenuList = new JLabel("Menu");
        lblMenuList.setBounds(385, 48, 46, 14);
        createOrderP.add(lblMenuList);
        
        JLabel lblCustomerName = new JLabel("Name:");
        lblCustomerName.setBounds(386, 174, 65, 14);
        createOrderP.add(lblCustomerName);
        
        lblCustomerTotal = new JLabel("Total:");
        lblCustomerTotal.setBounds(385, 199, 66, 14);
        createOrderP.add(lblCustomerTotal);
        
        lblOrderID = new JLabel("Order ID:");
        lblOrderID.setBounds(385, 224, 66, 14);
        createOrderP.add(lblOrderID);
        
        scrollPaneNote = new JScrollPane();
        scrollPaneNote.setBounds(37, 224, 200, 70);
        createOrderP.add(scrollPaneNote);
        
        textArea = new JTextArea();
        scrollPaneNote.setViewportView(textArea);
        
        lblNote = new JLabel("Note:");
        lblNote.setBounds(47, 209, 46, 14);
        createOrderP.add(lblNote);
        
        JButton btnSubmitOrder = new JButton("Submit Order");
        btnSubmitOrder.setBounds(330, 249, 188, 45);
        createOrderP.add(btnSubmitOrder);
   		
   		// Sets the menu bar created by the build
   		setJMenuBar(menu);
   		
   		// Size and display the window.
   		setSize(SIZE_X,SIZE_Y);
   		setVisible(true);

		//Sets the program view based on the login window input
		switch (loginWindow.getProgramView()) { 
			case 0: setCustomerView(); break;
			case 1: setEmployeeView(); break;
			case 2: setManagerView();  break;
		}
	}
	
	/**
	 * Builds the JFrame an sets values
	 */
	public void build() {
		
		// Creates Panels
		masterP = new RPanel();
		viewOrderP = new ViewOrderPanel();
		createOrderP = new RPanel();
		employeeViewOrderP = new RPanel();
		orderListP = new OrderListPanel();
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
		manageB = new RMenuItem("Manage");
		manageB.add(new PopupMenu("message"));
		
		// Adds mouse listeners
		viewOrderB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                viewOrderAction();
            }
        });
		createOrderB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                createOrderAction();
            }
        });
		orderListB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                orderListAction();
            }
        });
		manageB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                manageAction();
            }
        });
		
		// Creates a new login window
		loginWindow = new LoginWindow(myDatabase, "Testraunt");
		 
	}

	/**
	 * Sets the viewable buttons by the user as a customer
	 */
	public void setCustomerView() {
		// Removes all items from the menu
	    removeAllButtons();
		
		// Adds necessary items to the menu
		menu.add(viewOrderB);
		menu.add(createOrderB);
		//menu.add(orderListB);
		//menu.add(manageB);
	}

	/**
	 * Sets the viewable buttons by the user as an employee
	 */
	public void setEmployeeView() {
		// Removes all items from the menu
	    removeAllButtons();
		
		// Adds necessary items to the menu
		//menu.add(viewOrderB);
		menu.add(orderListB);
		menu.add(createOrderB);
		//menu.add(manageB);
	}

	/**
	 * Sets the viewable buttons by the user as a manager
	 */
	public void setManagerView() {
		// Removes all items from the menu
	    removeAllButtons();
		
		// Adds necessary items to the menu
		//menu.add(viewOrderB);
		menu.add(orderListB);
		menu.add(createOrderB);
		menu.add(manageB);
	}
	
	
	
	/**
	 * Method that runs when the user presses "View Order"
	 */
	void viewOrderAction() {
		// Sets the current panel to the page selected by the user
	    removeAllPanels();
		// Adds the panel selected by the user
		masterP.add(viewOrderP);
		// Validates and repaints the changes
		validate();
		repaint();
	}

	/**
	 * Method that runs when the user presses "Order List"
	 */
	void orderListAction() {
		// Removes any panels currently in view
	    removeAllPanels();
		// Adds the panel selected by the user
		masterP.add(orderListP);
		// Validates and repaints the changes
		validate();
		repaint();
		
	}
	
	/**
	 * Method that runs when the user presses "Create Order"
	 */
	void createOrderAction() {
		// Removes any panels currently in view
	    removeAllPanelsFromContentPane();
		// Adds the panel selected by the user
	    getContentPane().add(createOrderP);
		// Validates and repaints the changes
		validate();
		repaint();
		
	}
	
	/**
	 * Method that runs when the user presses "Manage"
	 */
	void manageAction () {
		// Removes any panels currently in view
	    removeAllPanels();
		// Adds the panel selected by the user
		masterP.add(manageUsersP);
		// Validates and repaints the changes
		validate();
		repaint();
		
	}
	
	
	void removeAllPanelsFromContentPane() {
	    getContentPane().remove(viewOrderP);
	    getContentPane().remove(createOrderP);
	    getContentPane().remove(employeeViewOrderP);
	    getContentPane().remove(orderListP);
	    getContentPane().remove(manageUsersP);
	    getContentPane().remove(masterP);
	}
	
	
	/**
	 * Removes all panels from the program
	 */
	void removeAllPanels() {
	    masterP.remove(viewOrderP);
        masterP.remove(createOrderP);
        masterP.remove(employeeViewOrderP);
        masterP.remove(orderListP);
        masterP.remove(manageUsersP);
	}
	
	/**
	 * Removes all buttons from the program
	 */
	void removeAllButtons() {
	    menu.remove(viewOrderB);
        menu.remove(createOrderB);
        menu.remove(orderListB);
        menu.remove(manageB);
	}
}