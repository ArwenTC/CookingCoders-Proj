
package GroupProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class RestaurantGUI extends JFrame {

	// Window Size
	final int SIZE_X = 600, SIZE_Y = 450;
	
	// items per pages
	public final int ORDERLINES_PER_PAGE = 5;
	public final int ORDERS_PER_PAGE = 5;
	public final int USERS_PER_PAGE = 5;
	public final int ORDERLINES_PAGE_TYPE = 0;
	public final int ORDERS_PAGE_TYPE = 1;
	public final int USERS_PAGE_TYPE = 2;
	
	// current page type
	private int pagetype = 0;
	
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
    private JButton buttonAddItem;

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
        scrollPaneMenu.setBounds(400, 30, 120, 130);
        createOrderP.add(scrollPaneMenu);
        
        lblMenuList = new JLabel("Menu");
        lblMenuList.setBounds(444, 15, 81, 14);
        createOrderP.add(lblMenuList);
        
        lblCustomerName = new JLabel("Name:");
        lblCustomerName.setBounds(381, 203, 89, 14);
        createOrderP.add(lblCustomerName);
        
        lblCustomerTotal = new JLabel("Total:");
        lblCustomerTotal.setBounds(381, 253, 89, 14);
        createOrderP.add(lblCustomerTotal);
        
        lblOrderID = new JLabel("Order ID:");
        lblOrderID.setBounds(381, 228, 89, 14);
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
        lblItem1.setVisible(false);
        createOrderP.add(lblItem1);
        
        lblItem2 = new JLabel("item:");
        lblItem2.setBounds(55, 59, 109, 14);
        lblItem2.setVisible(false);
        createOrderP.add(lblItem2);
        
        lblItem3 = new JLabel("item:");
        lblItem3.setBounds(55, 84, 109, 14);
        lblItem3.setVisible(false);
        createOrderP.add(lblItem3);
        
        lblItem4 = new JLabel("item:");
        lblItem4.setBounds(55, 108, 109, 14);
        lblItem4.setVisible(false);
        createOrderP.add(lblItem4);
        
        lblItem5 = new JLabel("item:");
        lblItem5.setBounds(55, 133, 109, 14);
        lblItem5.setVisible(false);
        createOrderP.add(lblItem5);
        
        btnClearItem1 = new JButton("X");
        btnClearItem1.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem1.setBounds(10, 32, 39, 18);
        btnClearItem1.setVisible(false);
        createOrderP.add(btnClearItem1);
        
        btnClearItem2 = new JButton("X");
        btnClearItem2.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem2.setBounds(10, 57, 39, 18);
        btnClearItem2.setVisible(false);
        createOrderP.add(btnClearItem2);
        
        btnClearItem3 = new JButton("X");
        btnClearItem3.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem3.setBounds(10, 82, 39, 18);
        btnClearItem3.setVisible(false);
        createOrderP.add(btnClearItem3);
        
        btnClearItem4 = new JButton("X");
        btnClearItem4.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem4.setBounds(10, 106, 39, 18);
        btnClearItem4.setVisible(false);
        createOrderP.add(btnClearItem4);
        
        btnClearItem5 = new JButton("X");
        btnClearItem5.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem5.setBounds(10, 131, 39, 18);
        btnClearItem5.setVisible(false);
        createOrderP.add(btnClearItem5);
        
        btnPageBack = new JButton("<");
        btnPageBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pageNumberTxt = lblPageNumber.getText();
                int slashIndex = pageNumberTxt.indexOf('/');
                String currentPageString = pageNumberTxt.substring(0, slashIndex);
                String totalPagesString = pageNumberTxt.substring(slashIndex + 1);
                
                int currentPage = Integer.valueOf(currentPageString);
                int totalPages = Integer.valueOf(totalPagesString);
                
                if (currentPage == 1) {
                    return;
                }
                
                currentPage -= 1;
                
                if (pagetype == ORDERLINES_PAGE_TYPE) {
                    OrderLine[] orderLines = infoHandler.getMyCurrentOrder().toArray(OrderLine[]::new);
                    drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentPage, ORDERLINES_PER_PAGE));
                } else if (pagetype == ORDERS_PAGE_TYPE) {
                    // placeholder
                } else if (pagetype == USERS_PAGE_TYPE) {
                    // placeholder
                }
                
                lblPageNumber.setText(currentPage + "/" + totalPages);
            }
        });
        btnPageBack.setBounds(10, 176, 47, 23);
        createOrderP.add(btnPageBack);
        
        lblPageNumber = new JLabel("1/1");
        lblPageNumber.setBounds(67, 180, 47, 14);
        createOrderP.add(lblPageNumber);
        
        btnPageForward = new JButton(">");
        btnPageForward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pageNumberTxt = lblPageNumber.getText();
                int slashIndex = pageNumberTxt.indexOf('/');
                String currentPageString = pageNumberTxt.substring(0, slashIndex);
                String totalPagesString = pageNumberTxt.substring(slashIndex + 1);
                
                int currentPage = Integer.valueOf(currentPageString);
                int totalPages = Integer.valueOf(totalPagesString);
                
                if (currentPage == totalPages) {
                    return;
                }
                
                currentPage += 1;
                
                if (pagetype == ORDERLINES_PAGE_TYPE) {
                    OrderLine[] orderLines = infoHandler.getMyCurrentOrder().toArray(OrderLine[]::new);
                    drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentPage, ORDERLINES_PER_PAGE));
                } else if (pagetype == ORDERS_PAGE_TYPE) {
                    // placeholder
                } else if (pagetype == USERS_PAGE_TYPE) {
                    // placeholder
                }
                
                lblPageNumber.setText(currentPage + "/" + totalPages);
            }
        });
        btnPageForward.setBounds(96, 176, 47, 23);
        createOrderP.add(btnPageForward);
        
        buttonAddItem = new JButton("Add");
        buttonAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedValue = menuList.getSelectedValue();
                
                if (selectedValue == null || selectedValue.isEmpty()) {
                    return;
                }
                
                String productName = selectedValue.substring(selectedValue.indexOf(':') + 2);
                
                ArrayList<OrderLine> currentOrderLines = infoHandler.getMyCurrentOrder();
                
                boolean itemCountIncremented = false;
                for (OrderLine orderLine : currentOrderLines) {
                    if (orderLine.getProductName().equals(productName)) {
                        orderLine.setQuantity(orderLine.getQuantity() + 1);
                        itemCountIncremented = true;
                        break;
                    }
                }
                if (!itemCountIncremented) {
                    currentOrderLines.add(new OrderLine(infoHandler.getUserOrderID(), productName, 1));
                }
                
                String pageNumberTxt = lblPageNumber.getText();
                String currentPageString = pageNumberTxt.substring(0, pageNumberTxt.indexOf('/'));
                
                int currentOrderLinePage = Integer.valueOf(currentPageString);
                
                OrderLine[] orderLines = infoHandler.getMyCurrentOrder().toArray(OrderLine[]::new);
                drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentOrderLinePage, ORDERLINES_PER_PAGE));
                
                int howManyPages = (currentOrderLines.size() / ORDERLINES_PER_PAGE);
                if (currentOrderLines.size() % ORDERLINES_PER_PAGE != 0 || currentOrderLines.size() == 0) {
                    howManyPages += 1;
                }
                lblPageNumber.setText(currentPageString + "/" + howManyPages);
                
                String totalString = lblCustomerTotal.getText();
                double total = Double.valueOf(totalString.substring(totalString.indexOf('$') + 1));
                total += infoHandler.getProductValue(productName);
                lblCustomerTotal.setText("Total: $" + String.format("%.2f", total));
            }
        });
        buttonAddItem.setBounds(421, 169, 83, 23);
        createOrderP.add(buttonAddItem);
   		
   		// Sets the menu bar created by the build
   		setJMenuBar(menu);
   		
   		// Size and display the window.
   		setSize(SIZE_X, SIZE_Y);
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
	
	
	public void drawOrderLinePage(OrderLine[] orderLinePage) {
	    JLabel[] itemLabels = {lblItem1, lblItem2, lblItem3, lblItem4, lblItem5};
	    JButton[] itemClearButtons = {btnClearItem1, btnClearItem2, btnClearItem3, btnClearItem4, btnClearItem5};
	    
	    for (int i = 0; i < itemLabels.length; i++) {
	        itemLabels[i].setVisible(false);
	        itemClearButtons[i].setVisible(false);
	    }
	    
	    for (int i = 0; i < orderLinePage.length; i++) {
	        if (orderLinePage[i] == null) {
	            break;
	        }
	        
	        itemClearButtons[i].setVisible(true);
	        
	        itemLabels[i].setText(orderLinePage[i].getProductName() + " x" + orderLinePage[i].getQuantity());
	        itemLabels[i].setVisible(true);
	    }
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
	    pagetype = ORDERLINES_PAGE_TYPE;
	    
        // Removes any panels currently in view
        removeAllPanelsFromContentPane();
        // Adds the panel selected by the user
        getContentPane().add(createOrderP);
        // Validates and repaints the changes
        
        lblCustomerName.setText("Name: " + infoHandler.username);
        lblOrderID.setText("Order ID: " + (infoHandler.getUserOrderID() != -1 ? infoHandler.getUserOrderID() : "null"));
        lblCustomerTotal.setText("Total: $" + String.format("%.2f", infoHandler.getMyTotalCharge()));
        
        ArrayList<String> itemStrings = new ArrayList<String>();
        
        for (Map.Entry<String, Double> productView : infoHandler.getProductEntrySet()) {
            String costString = "$" + String.format("%.2f", productView.getValue());
            itemStrings.add(costString + ": " + productView.getKey());
        }
        
        OrderLine[] currentOrder = infoHandler.getMyCurrentOrder().toArray(OrderLine[]::new);
        
        OrderLine[] orderLinePage = infoHandler.getOrderLinePage(
            currentOrder,
            1,
            ORDERLINES_PER_PAGE
        );
        
        drawOrderLinePage(orderLinePage);
        
        menuList = new JList<String>(itemStrings.toArray(String[]::new));
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneMenu.setViewportView(menuList);
        
        int howManyPages = (currentOrder.length / ORDERLINES_PER_PAGE);
        if (currentOrder.length % ORDERLINES_PER_PAGE != 0 || currentOrder.length == 0) {
            howManyPages += 1;
        }
        lblPageNumber.setText("1/" + howManyPages);
        
        validate();
        repaint();
        
    }
	
	
	/**
	 * Method that runs when the user presses "Manage"
	 */
    void manageAction() {
        
        User[] users = infoHandler.getUserPage(0, 5);
        
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
        
        for (User user : users) {
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
