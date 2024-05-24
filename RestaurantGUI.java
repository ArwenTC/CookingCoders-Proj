
package GroupProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

public class RestaurantGUI extends JFrame {

	// Window Size
	final int SIZE_X = 600, SIZE_Y = 450;
	
	// page number trackers
	private int createOrderItemsPage = 0;
	
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
	
	SQLDatabase myDatabase;
	
	InfoHandler infoHandler;
    
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
    private JLabel lblNote;
    private JTextArea txtNote;
    private JLabel lblItem1;
    private JLabel lblItem2;
    private JLabel lblItem3;
    private JLabel lblItem4;
    private JLabel lblItem5;
    private JButton btnClearItem1;
    private JButton btnClearItem2;
    private JButton btnClearItem3;
    private JButton btnClearItem4;
    private JButton btnClearItem5;
    private JButton btnPageBack;
    private JLabel lblPageNumber;
    private JButton btnPageForward;
    private JLabel lblMenuList;
    private JLabel lblCustomerName;
    private JButton btnSubmitOrder;

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
   		
   		LoginWindow loginWindow = new LoginWindow(myDatabase, "Testraunt");

   		// Toggles the visibility of the login window
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
        
        infoHandler = loginWindow.makeInfoHandler();
        
        // Makes the login window invisible
        loginWindow.toggleVisibility();
   		
   		// Adds the master panel
   		getContentPane().add(masterP);
   		createOrderP.setLayout(null);
   		
   		scrollPaneMenu = new JScrollPane();
        scrollPaneMenu.setBounds(442, 50, 83, 130);
        createOrderP.add(scrollPaneMenu);
        
        
        scrollPaneMenu.setViewportView(menuList);
        
        lblMenuList = new JLabel("Menu");
        lblMenuList.setBounds(444, 34, 81, 14);
        createOrderP.add(lblMenuList);
        
        lblCustomerName = new JLabel("Name:");
        lblCustomerName.setBounds(381, 190, 89, 14);
        createOrderP.add(lblCustomerName);
        
        lblCustomerTotal = new JLabel("Total:");
        lblCustomerTotal.setBounds(381, 240, 89, 14);
        createOrderP.add(lblCustomerTotal);
        
        lblOrderID = new JLabel("Order ID:");
        lblOrderID.setBounds(381, 215, 89, 14);
        createOrderP.add(lblOrderID);
        
        lblNote = new JLabel("Note:");
        lblNote.setBounds(31, 222, 69, 14);
        createOrderP.add(lblNote);
        
        btnSubmitOrder = new JButton("Submit Order");
        btnSubmitOrder.setBounds(403, 278, 122, 58);
        createOrderP.add(btnSubmitOrder);
        
        txtNote = new JTextArea();
        txtNote.setBounds(31, 247, 220, 101);
        createOrderP.add(txtNote);
        
        lblItem1 = new JLabel("item:");
        lblItem1.setBounds(55, 34, 109, 14);
        createOrderP.add(lblItem1);
        
        lblItem2 = new JLabel("item:");
        lblItem2.setBounds(55, 59, 109, 14);
        createOrderP.add(lblItem2);
        
        lblItem3 = new JLabel("item:");
        lblItem3.setBounds(55, 84, 109, 14);
        createOrderP.add(lblItem3);
        
        lblItem4 = new JLabel("item:");
        lblItem4.setBounds(55, 108, 109, 14);
        createOrderP.add(lblItem4);
        
        lblItem5 = new JLabel("item:");
        lblItem5.setBounds(55, 133, 109, 14);
        createOrderP.add(lblItem5);
        
        btnClearItem1 = new JButton("X");
        btnClearItem1.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem1.setBounds(10, 32, 39, 18);
        createOrderP.add(btnClearItem1);
        
        btnClearItem2 = new JButton("X");
        btnClearItem2.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem2.setBounds(10, 57, 39, 18);
        createOrderP.add(btnClearItem2);
        
        btnClearItem3 = new JButton("X");
        btnClearItem3.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem3.setBounds(10, 82, 39, 18);
        createOrderP.add(btnClearItem3);
        
        btnClearItem4 = new JButton("X");
        btnClearItem4.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem4.setBounds(10, 106, 39, 18);
        createOrderP.add(btnClearItem4);
        
        btnClearItem5 = new JButton("X");
        btnClearItem5.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem5.setBounds(10, 131, 39, 18);
        createOrderP.add(btnClearItem5);
        
        btnPageBack = new JButton("<");
        btnPageBack.setBounds(10, 176, 47, 23);
        createOrderP.add(btnPageBack);
        
        lblPageNumber = new JLabel("1/1");
        lblPageNumber.setBounds(67, 180, 47, 14);
        createOrderP.add(lblPageNumber);
        
        btnPageForward = new JButton(">");
        btnPageForward.setBounds(96, 176, 47, 23);
        createOrderP.add(btnPageForward);
   		
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
		orderListP = new OrderListPanel(this.myDatabase);
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
        
        lblCustomerName.setText("Name: " + infoHandler.username);
        lblOrderID.setText("Order ID: " + infoHandler.getMyOrderID());
        lblCustomerTotal.setText("Total: " + infoHandler.getMyTotalCharge());
        
        validate();
        repaint();
        
    }
	
	
	/**
	 * Method that runs when the user presses "Manage"
	 */
    void manageAction() {
        
        Set<Map.Entry<String, User>> userMapView = infoHandler.getUserMapView();
        User[] userList = new User[userMapView.size()];
        
        int userListIdx = 0;
        for (Map.Entry<String, User> mapEntry : userMapView) {
            userList[userListIdx] = mapEntry.getValue();
            userListIdx += 1;
            System.out.println(mapEntry.getValue().getUsername() + ": " + mapEntry.getValue().getUsertype());
        }
        
        // Removes any panels currently in view
        removeAllPanels();
        manageUsersP.setLayout(new BoxLayout(manageUsersP,BoxLayout.Y_AXIS));
        manageUsersP.setBackground(new Color(255, 255, 224));
        
        JPanel headerPanel = new JPanel();
        //headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setLayout(new GridLayout(1,3,10,10));
        headerPanel.setBackground(new Color(255, 255, 224));
        
        JLabel usernameHeader = new JLabel("Username");
        usernameHeader.setFont(new Font("Arial", Font.BOLD,12));
     
        headerPanel.add(usernameHeader);
        JSeparator sep1 = new JSeparator(SwingConstants.VERTICAL);
        manageUsersP.add(sep1);
        
       
        
        
        JLabel userTypeHeader = new JLabel("Usertype");
        userTypeHeader.setFont(new Font("Arial", Font.BOLD,12));
        
        headerPanel.add(userTypeHeader);
        manageUsersP.add(headerPanel);
        
      
        
        //manageUsersP.add(removeUser);
        
        for (User user : userList) {
            JPanel  userPanel = new JPanel();
            userPanel.setLayout(new GridLayout(1,2,10,10));
            userPanel.setBackground(new Color(144, 238, 144));
            JLabel UsernameLabel = new JLabel(user.getUsername());
            userPanel.add(UsernameLabel);
            
            JLabel UsertypeLabel = new JLabel( user.getUsertype());
            userPanel.add(UsertypeLabel);
            String[] userTypeOptions = {"Customer", "Employee"};
            JComboBox<String> userTypeComboBox = new JComboBox<>(userTypeOptions);
            userTypeComboBox.setSelectedItem(user.getUsertype()); // Set selected item based on user's current type
            userPanel.add(userTypeComboBox);
            
              userPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
              manageUsersP.add(userPanel);
              
              JSeparator now = new JSeparator(SwingConstants.HORIZONTAL);
              manageUsersP.add(now);
              
        }
        
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
