
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
	public final int CREATE_ORDER_PAGE_TYPE = 0;
	public final int VIEW_ORDER_PAGE_TYPE = 1;
	public final int ORDERS_PAGE_TYPE = 2;
	public final int USERS_PAGE_TYPE = 3;
	
	// current page type
	private int pagetype;
	
	// program view
	private int programView;
	
	// program view types
	private final int CUSTOMER_VIEW_TYPE = 0;
	private final int EMPLOYEE_VIEW_TYPE = 1;
	private final int MANAGER_VIEW_TYPE = 2;
	
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
    private JLabel lblPendingOrderID;
    private JLabel lblNote;
    private JTextArea txtCurrentOrderNote;
    private JTextArea txtWaitingOrderNote;
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
    private JScrollPane scrollPaneNote;
    private JLabel lblOrderInProgress;
    private JLabel lblWaitingOrders;
    private JButton btnViewOrder1;
    private JButton btnViewOrder2;
    private JButton btnViewOrder3;
    private JButton btnViewOrder4;
    private JButton btnViewOrder5;
    private JButton btnMarkCompleted1;
    private JButton btnMarkCompleted2;
    private JButton btnMarkCompleted3;
    private JButton btnMarkCompleted4;
    private JButton btnMarkCompleted5;
    private JLabel lblName1;
    private JLabel lblName2;
    private JLabel lblName3;
    private JLabel lblName4;
    private JLabel lblName5;
    private JLabel lblOrder1;
    private JLabel lblOrder2;
    private JLabel lblOrder3;
    private JLabel lblOrder4;
    private JLabel lblOrder5;
    
    private JLabel[] itemLabels;
    private JButton[] itemClearButtons;
    private JButton[] viewOrderButtons;
    private JButton[] markCompletedButtons;
    private JLabel[] orderLabels;
    private JLabel[] nameLabels;

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
   		
   		LoginWindow loginWindow = new LoginWindow(myDatabase);

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
   		
   	    // Sets the menu bar created by the build
        setJMenuBar(menu);
        
        // Size and display the window.
        setSize(SIZE_X, SIZE_Y);

		//Sets the program view based on the login window input
   		programView = loginWindow.getProgramView();
   		
		switch (programView) { 
			case CUSTOMER_VIEW_TYPE: setCustomerView(); break;
			case EMPLOYEE_VIEW_TYPE: setEmployeeView(); break;
			case MANAGER_VIEW_TYPE:  setManagerView();  break;
		}
		
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
		employeeViewOrderP = new RPanel();
		orderListP = new RPanel();
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
		
		createOrderP.setLayout(null);
        viewOrderP.setLayout(null);
        
        scrollPaneMenu = new JScrollPane();
        scrollPaneMenu.setBounds(381, 30, 139, 130);
        createOrderP.add(scrollPaneMenu);
        
        lblMenuList = new JLabel("Menu");
        lblMenuList.setBounds(439, 11, 81, 14);
        createOrderP.add(lblMenuList);
        
        lblCustomerName = new JLabel("Name:");
        lblCustomerName.setBounds(381, 203, 139, 14);
        createOrderP.add(lblCustomerName);
        
        lblCustomerTotal = new JLabel("Total:");
        lblCustomerTotal.setBounds(381, 253, 139, 14);
        createOrderP.add(lblCustomerTotal);
        
        lblPendingOrderID = new JLabel("Pending Order ID:");
        lblPendingOrderID.setBounds(381, 228, 139, 14);
        createOrderP.add(lblPendingOrderID);
        
        lblNote = new JLabel("Note:");
        lblNote.setBounds(31, 222, 69, 14);
        createOrderP.add(lblNote);
        
        btnSubmitOrder = new JButton("Submit Order");
        btnSubmitOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (infoHandler.getTotalCharge(infoHandler.getMyCurrentOrder()) == 0.0) {
                    return;
                }
                
                if (txtCurrentOrderNote.getText().length() >= 255) {
                    JOptionPane.showMessageDialog(null, "user note must be less than 255 characters", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int orderID = infoHandler.addUserCurrentOrder(txtCurrentOrderNote.getText());
                
                txtCurrentOrderNote.setText("");
                lblPendingOrderID.setText("Pending Order ID: " + orderID);
                lblCustomerTotal.setText("$0.00");
                lblPageNumber.setText("1/1");
                
                lblOrderInProgress.setVisible(true);
                btnSubmitOrder.setVisible(false);
                
                OrderLine[] orderLines = infoHandler.getMyCurrentOrder().toArray(OrderLine[]::new);
                drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, 1, ORDERLINES_PER_PAGE));
            }
        });
        btnSubmitOrder.setBounds(403, 278, 122, 58);
        createOrderP.add(btnSubmitOrder);
        
        lblItem1 = new JLabel("");
        lblItem1.setBounds(55, 34, 109, 14);
        lblItem1.setVisible(false);
        createOrderP.add(lblItem1);
        
        lblItem2 = new JLabel("");
        lblItem2.setBounds(55, 59, 109, 14);
        lblItem2.setVisible(false);
        createOrderP.add(lblItem2);
        
        lblItem3 = new JLabel("");
        lblItem3.setBounds(55, 84, 109, 14);
        lblItem3.setVisible(false);
        createOrderP.add(lblItem3);
        
        lblItem4 = new JLabel("");
        lblItem4.setBounds(55, 108, 109, 14);
        lblItem4.setVisible(false);
        createOrderP.add(lblItem4);
        
        lblItem5 = new JLabel("");
        lblItem5.setBounds(55, 133, 109, 14);
        lblItem5.setVisible(false);
        createOrderP.add(lblItem5);
        
        btnClearItem1 = new JButton("X");
        btnClearItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLine(0);
            }
        });
        btnClearItem1.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem1.setBounds(10, 32, 39, 18);
        btnClearItem1.setVisible(false);
        createOrderP.add(btnClearItem1);
        
        btnClearItem2 = new JButton("X");
        btnClearItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLine(1);
            }
        });
        btnClearItem2.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem2.setBounds(10, 57, 39, 18);
        btnClearItem2.setVisible(false);
        createOrderP.add(btnClearItem2);
        
        btnClearItem3 = new JButton("X");
        btnClearItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLine(2);
            }
        });
        btnClearItem3.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem3.setBounds(10, 82, 39, 18);
        btnClearItem3.setVisible(false);
        createOrderP.add(btnClearItem3);
        
        btnClearItem4 = new JButton("X");
        btnClearItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLine(3);
            }
        });
        btnClearItem4.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem4.setBounds(10, 106, 39, 18);
        btnClearItem4.setVisible(false);
        createOrderP.add(btnClearItem4);
        
        btnClearItem5 = new JButton("X");
        btnClearItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLine(4);
            }
        });
        btnClearItem5.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem5.setBounds(10, 131, 39, 18);
        btnClearItem5.setVisible(false);
        createOrderP.add(btnClearItem5);
        
        itemLabels = new JLabel[] {lblItem1, lblItem2, lblItem3, lblItem4, lblItem5};
        itemClearButtons = new JButton[] {btnClearItem1, btnClearItem2, btnClearItem3, btnClearItem4, btnClearItem5};
        
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
                    currentOrderLines.add(new OrderLine(productName, 1));
                }
                
                String pageNumberTxt = lblPageNumber.getText();
                String currentPageString = pageNumberTxt.substring(0, pageNumberTxt.indexOf('/'));
                
                int currentOrderLinePage = Integer.valueOf(currentPageString);
                
                OrderLine[] orderLines = infoHandler.getMyCurrentOrder().toArray(OrderLine[]::new);
                drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentOrderLinePage, ORDERLINES_PER_PAGE));
                
                int totalPages = (currentOrderLines.size() / ORDERLINES_PER_PAGE);
                if (currentOrderLines.size() % ORDERLINES_PER_PAGE != 0 || currentOrderLines.size() == 0) {
                    totalPages += 1;
                }
                lblPageNumber.setText(currentPageString + "/" + totalPages);
                
                double total = infoHandler.getTotalCharge(infoHandler.getMyCurrentOrder());
                lblCustomerTotal.setText("Total: $" + String.format("%.2f", total));
            }
        });
        buttonAddItem.setBounds(413, 169, 83, 23);
        createOrderP.add(buttonAddItem);
        
        scrollPaneNote = new JScrollPane();
        scrollPaneNote.setBounds(27, 240, 220, 100);
        createOrderP.add(scrollPaneNote);
        
        txtCurrentOrderNote = new JTextArea();
        txtCurrentOrderNote.setLineWrap(true);
        
        txtWaitingOrderNote = new JTextArea();
        txtWaitingOrderNote.setLineWrap(true);
        
        lblOrderInProgress = new JLabel("Order In Progress");
        lblOrderInProgress.setBounds(405, 300, 135, 14);
        createOrderP.add(lblOrderInProgress);
        
		
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
                
                if (pagetype == CREATE_ORDER_PAGE_TYPE || pagetype == VIEW_ORDER_PAGE_TYPE) {
                    ArrayList<OrderLine> myOrder;
                    
                    if (pagetype == CREATE_ORDER_PAGE_TYPE) {
                        myOrder = infoHandler.getMyCurrentOrder();
                    } else {
                        myOrder = infoHandler.getMyWaitingOrder();
                    }
                    
                    OrderLine[] orderLines = myOrder.toArray(OrderLine[]::new);
                    drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentPage, ORDERLINES_PER_PAGE));
                } else if (pagetype == ORDERS_PAGE_TYPE) {
                    // placeholder
                } else if (pagetype == USERS_PAGE_TYPE) {
                    // placeholder
                }
                
                lblPageNumber.setText(currentPage + "/" + totalPages);
            }
        });
        btnPageBack.setBounds(223, 280, 47, 23);
        
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
                
                if (pagetype == CREATE_ORDER_PAGE_TYPE || pagetype == VIEW_ORDER_PAGE_TYPE) {
                    ArrayList<OrderLine> myOrder;
                    
                    if (pagetype == CREATE_ORDER_PAGE_TYPE) {
                        myOrder = infoHandler.getMyCurrentOrder();
                    } else {
                        myOrder = infoHandler.getMyWaitingOrder();
                    }
                    
                    OrderLine[] orderLines = myOrder.toArray(OrderLine[]::new);
                    drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentPage, ORDERLINES_PER_PAGE));
                } else if (pagetype == ORDERS_PAGE_TYPE) {
                    drawOrderPage(infoHandler.getOrderPage(currentPage, ORDERS_PER_PAGE));
                } else if (pagetype == USERS_PAGE_TYPE) {
                    // placeholder
                }
                
                lblPageNumber.setText(currentPage + "/" + totalPages);
            }
        });
        btnPageForward.setBounds(309, 280, 47, 23);
        
        lblPageNumber = new JLabel("1/1");
        lblPageNumber.setBounds(266, 284, 47, 14);
        lblPageNumber.setHorizontalAlignment(SwingConstants.CENTER);
		
        orderListP.setLayout(null);
        lblWaitingOrders = new JLabel("Waiting Orders");
        lblWaitingOrders.setBounds(256, 20, 100, 14);
        orderListP.add(lblWaitingOrders);
        
        lblOrder1 = new JLabel("Order: ");
        lblOrder1.setBounds(307, 40, 100, 14);
        orderListP.add(lblOrder1);
        
        lblOrder2 = new JLabel("Order: ");
        lblOrder2.setBounds(307, 90, 100, 14);
        orderListP.add(lblOrder2);
        
        lblOrder3 = new JLabel("Order: ");
        lblOrder3.setBounds(307, 140, 100, 14);
        orderListP.add(lblOrder3);
        
        lblOrder4 = new JLabel("Order: ");
        lblOrder4.setBounds(307, 190, 100, 14);
        orderListP.add(lblOrder4);
        
        lblOrder5 = new JLabel("Order: ");
        lblOrder5.setBounds(307, 240, 170, 14);
        orderListP.add(lblOrder5);
        
        btnViewOrder1 = new JButton("View Order");
        btnViewOrder1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrder(0);
            }
        });
        btnViewOrder1.setBounds(197, 46, 100, 23);
        orderListP.add(btnViewOrder1);
        
        btnViewOrder2 = new JButton("View Order");
        btnViewOrder2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrder(1);
            }
        });
        btnViewOrder2.setBounds(197, 96, 100, 23);
        orderListP.add(btnViewOrder2);
        
        btnViewOrder3 = new JButton("View Order");
        btnViewOrder3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrder(2);
            }
        });
        btnViewOrder3.setBounds(197, 146, 100, 23);
        orderListP.add(btnViewOrder3);
        
        btnViewOrder4 = new JButton("View Order");
        btnViewOrder4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrder(3);
            }
        });
        btnViewOrder4.setBounds(197, 196, 100, 23);
        orderListP.add(btnViewOrder4);
        
        btnViewOrder5 = new JButton("View Order");
        btnViewOrder5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrder(4);
            }
        });
        btnViewOrder5.setBounds(197, 246, 100, 23);
        orderListP.add(btnViewOrder5);
        
        btnMarkCompleted1 = new JButton("Set Completed");
        btnMarkCompleted1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompleted(0);
            }
        });
        btnMarkCompleted1.setBounds(67, 46, 120, 23);
        orderListP.add(btnMarkCompleted1);
        
        btnMarkCompleted2 = new JButton("Set Completed");
        btnMarkCompleted2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompleted(1);
            }
        });
        btnMarkCompleted2.setBounds(67, 96, 120, 23);
        orderListP.add(btnMarkCompleted2);
        
        btnMarkCompleted3 = new JButton("Set Completed");
        btnMarkCompleted3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompleted(2);
            }
        });
        btnMarkCompleted3.setBounds(67, 146, 120, 23);
        orderListP.add(btnMarkCompleted3);
        
        btnMarkCompleted4 = new JButton("Set Completed");
        btnMarkCompleted4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompleted(3);
            }
        });
        btnMarkCompleted4.setBounds(67, 196, 120, 23);
        orderListP.add(btnMarkCompleted4);
        
        btnMarkCompleted5 = new JButton("Set Completed");
        btnMarkCompleted5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompleted(4);
            }
        });
        btnMarkCompleted5.setBounds(67, 246, 120, 23);
        orderListP.add(btnMarkCompleted5);
        
        lblName1 = new JLabel("Name: ");
        lblName1.setBounds(307, 60, 170, 14);
        orderListP.add(lblName1);
        
        lblName2 = new JLabel("Name: ");
        lblName2.setBounds(307, 110, 170, 14);
        orderListP.add(lblName2);
        
        lblName3 = new JLabel("Name: ");
        lblName3.setBounds(307, 160, 170, 14);
        orderListP.add(lblName3);
        
        lblName4 = new JLabel("Name: ");
        lblName4.setBounds(307, 210, 170, 14);
        orderListP.add(lblName4);
        
        lblName5 = new JLabel("Name: ");
        lblName5.setBounds(307, 260, 170, 14);
        orderListP.add(lblName5);
        
        viewOrderButtons = new JButton[] {btnViewOrder1, btnViewOrder2, btnViewOrder3, btnViewOrder4, btnViewOrder5};
        markCompletedButtons = new JButton[] {btnMarkCompleted1, btnMarkCompleted2, btnMarkCompleted3, btnMarkCompleted4, btnMarkCompleted5};
        orderLabels = new JLabel[] {lblOrder1, lblOrder2, lblOrder3, lblOrder4, lblOrder5};
        nameLabels = new JLabel[] {lblName1, lblName2, lblName3, lblName4, lblName5};
        
	}
	
	
	public void drawOrderLinePage(OrderLine[] orderLinePage) {
	    for (int i = 0; i < itemLabels.length; i++) {
	        itemLabels[i].setVisible(false);
	        itemClearButtons[i].setVisible(false);
	    }
	    
	    for (int i = 0; i < orderLinePage.length && orderLinePage[i] != null; i++) {
	        itemClearButtons[i].setVisible(true);
	        
	        itemLabels[i].setText(orderLinePage[i].getProductName() + " x" + orderLinePage[i].getQuantity());
	        itemLabels[i].setVisible(true);
	    }
	}
	
	
	public void drawOrderPage(Order[] orderPage) {
	    for (int i = 0; i < orderLabels.length; i++) {
	        viewOrderButtons[i].setVisible(false);
            markCompletedButtons[i].setVisible(false);
	        
	        orderLabels[i].setVisible(false);
            nameLabels[i].setVisible(false);
        }
	    
	    for (int i = 0; i < orderPage.length && orderPage[i] != null; i++) {
	        viewOrderButtons[i].setVisible(true);
            markCompletedButtons[i].setVisible(true);
            
            orderLabels[i].setText("Order: " + orderPage[i].getOrderID());
            nameLabels[i].setText("name: " + orderPage[i].getCustomerUsername());
            orderLabels[i].setVisible(true);
            nameLabels[i].setVisible(true);
        }
	}
	
	
	public void viewOrder(int idx) {
	    String pageNumberTxt = lblPageNumber.getText();
        int slashIndex = pageNumberTxt.indexOf('/');
        String currentPageString = pageNumberTxt.substring(0, slashIndex);
        
        int currentPage = Integer.valueOf(currentPageString);
        
        Order orderToView = infoHandler.getOrdersInProgress()[((currentPage - 1) * ORDERS_PER_PAGE) + idx];
        
        setupOrderViewPage(
            orderToView.getItems(),
            orderToView.getOrderID(),
            orderToView.getCustomerUsername(),
            orderToView.getNote()
        );
	}
	
	
	public void markOrderCompleted(int idx) {
	    String pageNumberTxt = lblPageNumber.getText();
        int slashIndex = pageNumberTxt.indexOf('/');
        String currentPageString = pageNumberTxt.substring(0, slashIndex);
        
        int currentPage = Integer.valueOf(currentPageString);
	    
	    int orderID = infoHandler.getOrdersInProgress()[((currentPage - 1) * ORDERS_PER_PAGE) + idx].getOrderID();
	    
	    infoHandler.markOrderCompleted(orderID);
	    
	    infoHandler.refreshOrdersInProgress();
	    
	    int howManyWaitingOrders = infoHandler.getOrdersInProgress().length;
	    int totalPages = (howManyWaitingOrders / ORDERS_PER_PAGE);
        if (howManyWaitingOrders % ORDERS_PER_PAGE != 0 || howManyWaitingOrders == 0) {
            totalPages += 1;
        }
        if (currentPage > totalPages) {
            currentPage -= 1;
        }
        lblPageNumber.setText(currentPage + "/" + totalPages);
        drawOrderPage(infoHandler.getOrderPage(currentPage, ORDERS_PER_PAGE));
	}
	
	
	public void deletePageOrderLine(int idx) {
	    String pageNumberTxt = lblPageNumber.getText();
        int slashIndex = pageNumberTxt.indexOf('/');
        String currentPageString = pageNumberTxt.substring(0, slashIndex);
        
        int currentPage = Integer.valueOf(currentPageString);
        
        String itemLabelText = itemLabels[idx].getText();
        int xIndex = itemLabelText.lastIndexOf('x');
        String itemToRemove = itemLabelText.substring(0, xIndex - 1);
        
        ArrayList<OrderLine> currentOrderLines = infoHandler.getMyCurrentOrder();
        
        for (int i = 0; i < currentOrderLines.size(); i++) {
            if (currentOrderLines.get(i).getProductName().equals(itemToRemove)) {
                currentOrderLines.remove(i);
                break;
            }
        }
        
        int totalPages = (currentOrderLines.size() / ORDERLINES_PER_PAGE);
        if (currentOrderLines.size() % ORDERLINES_PER_PAGE != 0 || currentOrderLines.size() == 0) {
            totalPages += 1;
        }
        if (currentPage > totalPages) {
            currentPage -= 1;
        }
        lblPageNumber.setText(currentPage + "/" + totalPages);
        drawOrderLinePage(infoHandler.getOrderLinePage(currentOrderLines.toArray(OrderLine[]::new), currentPage, ORDERLINES_PER_PAGE));
        
        double total = infoHandler.getTotalCharge(infoHandler.getMyCurrentOrder());
        lblCustomerTotal.setText("Total: $" + String.format("%.2f", total));
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
	
	
    void setupOrderViewPage(ArrayList<OrderLine> orderToView, int orderID, String username, String orderNote) {
        pagetype = VIEW_ORDER_PAGE_TYPE;
        
        addOrderLinePageSharedGraphics(viewOrderP);
        createOrderP.add(txtWaitingOrderNote);
        scrollPaneNote.setViewportView(txtWaitingOrderNote);
        
        // resets the menu button text
        resetMenuButtonText();
        // Removes any panels currently in view
        removeAllPanelsFromContentPane();
        // Adds the panel selected by the user
        getContentPane().add(viewOrderP);
        
        lblPageNumber.setLocation(53, 180);
        btnPageBack.setLocation(10, 176);
        btnPageForward.setLocation(96, 176);
        viewOrderP.add(lblPageNumber);
        viewOrderP.add(btnPageBack);
        viewOrderP.add(btnPageForward);
        
        lblCustomerName.setText("Name: " + username);
        lblPendingOrderID.setText("Pending Order ID: " + (orderID != -1 ? orderID : "null"));
        lblCustomerTotal.setText("Total: $" + String.format("%.2f", infoHandler.getTotalCharge(orderToView)));
        
        OrderLine[] waitingOrderArray = orderToView.toArray(OrderLine[]::new);
        OrderLine[] orderLinePage = infoHandler.getOrderLinePage(waitingOrderArray, 1, ORDERLINES_PER_PAGE);
        
        drawOrderLinePage(orderLinePage);
        
        int howManyPages = (waitingOrderArray.length / ORDERLINES_PER_PAGE);
        if (waitingOrderArray.length % ORDERLINES_PER_PAGE != 0 || waitingOrderArray.length == 0) {
            howManyPages += 1;
        }
        lblPageNumber.setText("1/" + howManyPages);
        
        lblOrderInProgress.setText(orderID == -1 ? "No Order In Progress" : "Order In Progress");
        lblOrderInProgress.setVisible(true);
        
        txtWaitingOrderNote.setText(orderNote);
        
        // Validates and repaints the changes
        validate();
        repaint();
        
    }
	

	/**
	 * Method that runs when the user presses "Order List"
	 */
	void orderListAction() {
	    pagetype = ORDERS_PAGE_TYPE;
	    
	    // resets the menu button text
        resetMenuButtonText();
        // Removes any panels currently in view
        removeAllPanelsFromContentPane();
        // Adds the panel selected by the user
        getContentPane().add(orderListP);
        
        infoHandler.refreshOrdersInProgress();
        
        orderListB.setText("refresh");
        
        lblPageNumber.setLocation(266, 284);
        btnPageBack.setLocation(223, 280);
        btnPageForward.setLocation(309, 280);
        orderListP.add(lblPageNumber);
        orderListP.add(btnPageBack);
        orderListP.add(btnPageForward);
        
        Order[] orderPage = infoHandler.getOrderPage(1, ORDERS_PER_PAGE);
        drawOrderPage(orderPage);
        
        int howManyWaitingOrders = infoHandler.getOrdersInProgress().length;
        int howManyPages = (howManyWaitingOrders / ORDERLINES_PER_PAGE);
        if (howManyWaitingOrders % ORDERLINES_PER_PAGE != 0 || howManyWaitingOrders == 0) {
            howManyPages += 1;
        }
        lblPageNumber.setText("1/" + howManyPages);
	    
		// Validates and repaints the changes
		validate();
		repaint();
		
	}
	
	
	/**
     * Method that runs when the user presses "View Order"
     */
    void viewOrderAction() {
        infoHandler.refreshOrderStatus();
        
        setupOrderViewPage(
            infoHandler.getMyWaitingOrder(),
            infoHandler.getMyOrderID(),
            infoHandler.username,
            infoHandler.getMyWaitingOrderNote()
        );
        
        viewOrderB.setText("refresh");
    }
	
	
	/**
	 * Method that runs when the user presses "Create Order"
	 */
	void createOrderAction() {
	    pagetype = CREATE_ORDER_PAGE_TYPE;
	    
	    addOrderLinePageSharedGraphics(createOrderP);
	    createOrderP.add(txtCurrentOrderNote);
	    scrollPaneNote.setViewportView(txtCurrentOrderNote);
	    
	    // resets the menu button text
	    resetMenuButtonText();
        // Removes any panels currently in view
        removeAllPanelsFromContentPane();
        // Adds the panel selected by the user
        getContentPane().add(createOrderP);
        
        infoHandler.refreshOrderStatus();
        
        createOrderB.setText("refresh");
        
        lblPageNumber.setLocation(53, 180);
        btnPageBack.setLocation(10, 176);
        btnPageForward.setLocation(96, 176);
        createOrderP.add(lblPageNumber);
        createOrderP.add(btnPageBack);
        createOrderP.add(btnPageForward);
        
        lblCustomerName.setText("Name: " + infoHandler.username);
        lblPendingOrderID.setText("Pending Order ID: " + (infoHandler.getMyOrderID() != -1 ? infoHandler.getMyOrderID() : "null"));
        lblCustomerTotal.setText("Total: $" + String.format("%.2f", infoHandler.getTotalCharge(infoHandler.getMyCurrentOrder())));
        
        ArrayList<String> itemStrings = new ArrayList<String>();
        
        for (Map.Entry<String, Double> productView : infoHandler.getProductEntrySet()) {
            String costString = "$" + String.format("%.2f", productView.getValue());
            itemStrings.add(costString + ": " + productView.getKey());
        }
        
        menuList = new JList<String>(itemStrings.toArray(String[]::new));
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneMenu.setViewportView(menuList);
        
        OrderLine[] currentOrder = infoHandler.getMyCurrentOrder().toArray(OrderLine[]::new);
        OrderLine[] orderLinePage = infoHandler.getOrderLinePage(currentOrder, 1, ORDERLINES_PER_PAGE);
        
        drawOrderLinePage(orderLinePage);
        
        int howManyPages = (currentOrder.length / ORDERLINES_PER_PAGE);
        if (currentOrder.length % ORDERLINES_PER_PAGE != 0 || currentOrder.length == 0) {
            howManyPages += 1;
        }
        lblPageNumber.setText("1/" + howManyPages);
        
        lblOrderInProgress.setText("Order In Progress");
        if (infoHandler.getMyOrderID() == -1) {
            lblOrderInProgress.setVisible(false);
            btnSubmitOrder.setVisible(true);
        } else {
            lblOrderInProgress.setVisible(true);
            btnSubmitOrder.setVisible(false);
        }
        
        // Validates and repaints the changes
        validate();
        repaint();
        
    }
	
	
	/**
	 * Method that runs when the user presses "Manage"
	 */
    void manageAction() {
        
        pagetype = USERS_PER_PAGE;
        
        User[] users = infoHandler.getUserPage(0, 5);
        
        // Removes any panels currently in view
        removeAllPanels();
        manageUsersP.setLayout(new BoxLayout(manageUsersP, BoxLayout.Y_AXIS));
        manageUsersP.setBackground(new Color(255, 255, 224));
        
        JPanel headerPanel = new JPanel();
        //headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setLayout(new GridLayout(1, 3, 10, 10));
        headerPanel.setBackground(new Color(255, 255, 224));
        
        JLabel usernameHeader = new JLabel("Username");
        usernameHeader.setFont(new Font("Arial", Font.BOLD, 12));
     
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
    
    
    void addOrderLinePageSharedGraphics(JPanel panel) {
        panel.add(lblCustomerName);
        panel.add(lblPendingOrderID);
        panel.add(lblCustomerTotal);
        panel.add(lblNote);
        panel.add(scrollPaneNote);
        panel.add(lblItem1);
        panel.add(lblItem2);
        panel.add(lblItem3);
        panel.add(lblItem4);
        panel.add(lblItem5);
        panel.add(btnPageBack);
        panel.add(btnPageForward);
        panel.add(lblPageNumber);
        panel.add(lblOrderInProgress);
    }
    
    
    /**
     * Removes all panels from the content pane
     */
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
	
	
	/**
	 * resets the menu button text
	 */
	void resetMenuButtonText() {
	    viewOrderB.setText("View Order");
	    createOrderB.setText("Create Order");
	    orderListB.setText("Order List");
	    manageB.setText("Manage");
	}
}
