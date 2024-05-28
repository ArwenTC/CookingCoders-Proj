

package GroupProject;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

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
    
    // program view types
    private final int CUSTOMER_VIEW_TYPE = 0;
    private final int EMPLOYEE_VIEW_TYPE = 1;
    private final int ADMIN_VIEW_TYPE = 2;
    
    // current page type
    private int pagetype;
    
    // program view
    private int programView;
    
    // Panels that will be swapped depending what the user selects
    private JPanel masterP;
    private JPanel viewOrderP;
    private JPanel createOrderP;
    private JPanel employeeViewOrderP;
    private JPanel orderListP;
    private JPanel manageUsersP;
    private JPanel buildingP;
    
    // Menu bar that allows user to swap between each view
    private JMenuBar menu;
    // Menu bar buttons
    private JMenuItem viewOrderB;
    private JMenuItem createOrderB;
    private JMenuItem orderListB;
    private JMenuItem manageUsersB;
    private JMenuItem buildingB;
    
    private SQLDatabase myDatabase;
    
    private InfoHandler infoHandler;
    
    
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
    private RXButton btnClearItem1;
    private RXButton btnClearItem2;
    private RXButton btnClearItem3;
    private RXButton btnClearItem4;
    private RXButton btnClearItem5;
    private RActionButton btnPageBack;
    private JLabel lblPageNumber;
    private RActionButton btnPageForward;
    private JLabel lblMenuList;
    private JLabel lblCustomerName;
    private RActionButton btnSubmitOrder;
    private RActionButton buttonAddItem;
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
    private JComboBox<String> comboBoxUsertype1;
    private JComboBox<String> comboBoxUsertype2;
    private JComboBox<String> comboBoxUsertype3;
    private JComboBox<String> comboBoxUsertype4;
    private JComboBox<String> comboBoxUsertype5;
    private JButton btnEditUser1;
    private JButton btnEditUser2;
    private JButton btnEditUser3;
    private JButton btnEditUser4;
    private JButton btnEditUser5;
    private JButton btnDeleteUser1;
    private JButton btnDeleteUser2;
    private JButton btnDeleteUser3;
    private JButton btnDeleteUser4;
    private JButton btnDeleteUser5;
    private JLabel lblusername1;
    private JTextField txtUsername1;
    private JLabel lblPassword1;
    private JTextField txtPassword1;
    private JLabel lblusername2;
    private JTextField txtUsername2;
    private JLabel lblPassword2;
    private JTextField txtPassword2;
    private JLabel lblusername3;
    private JTextField txtUsername3;
    private JLabel lblPassword3;
    private JTextField txtPassword3;
    private JLabel lblusername4;
    private JTextField txtUsername4;
    private JLabel lblPassword4;
    private JTextField txtPassword4;
    private JLabel lblusername5;
    private JTextField txtUsername5;
    private JLabel lblPassword5;
    private JTextField txtPassword5;
    private JLabel lblBuilding;
    private JLabel lblBuildingPhone;
    private JLabel lblState;
    private JLabel lblCity;
    private JLabel lblStreetAddr1;
    private JLabel lblStreetAddr2;
    private JComboBox<String> comboBoxState;
    private JButton btnBuilding;
    private JTextField txtBuildingPhone;
    private JTextField txtCity;
    private JTextField txtAddrLine1;
    private JTextField txtAddrLine2;
    
    private JLabel[] itemLabels;
    private JButton[] itemClearButtons;
    private JButton[] viewOrderButtons;
    private JButton[] markCompletedButtons;
    private JLabel[] orderLabels;
    private JLabel[] nameLabels;
    private JButton[] editUserButtons;
    private JButton[] deleteUserButtons;
    private JLabel[] usernameLabels;
    private JLabel[] passwordLabels;
    private JTextField[] usernameTxtFields;
    private JTextField[] passwordTxtFields;
    private ArrayList<JComboBox<String>> usertypeComboBoxes;

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
            case ADMIN_VIEW_TYPE:    setAdminView();    break;
        }
        
        setVisible(true);
    }
    
    /**
     * Builds the JFrame an sets values
     */
    public void build() {
        
        // Creates Panels
        masterP = new RPanel();
        viewOrderP = new ViewOrderPanel();
        createOrderP = new ViewOrderPanel();
        employeeViewOrderP = new ViewOrderPanel();
        orderListP = new OrderListPanel();
        manageUsersP = new RPanel();
        buildingP = new RPanel();
        
        // Menu bar that allows user to swap between each view
        menu = new JMenuBar();
        menu.setLayout(new GridLayout(1, 0, 3, 0));
        menu.setBackground(new Color(38, 102, 55));
        menu.setBorderPainted(false);
        // Menu bar buttons
        viewOrderB = new RMenuItem("View Order");
        createOrderB = new RMenuItem("Create Order");
        orderListB = new RMenuItem("Order List");
        manageUsersB = new RMenuItem("Manage Users");
        buildingB = new RMenuItem("Building");
        manageUsersB.add(new PopupMenu("message"));
        
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
        manageUsersB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                manageUsersAction();
            }
        });
        buildingB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                buildingAction();
            }
        });
        
        btnPageBack = new RActionButton("<");
        btnPageBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pageBackAction();
            }
        });
        btnPageBack.setBounds(223, 280, 47, 23);
        
        btnPageForward = new RActionButton(">");
        btnPageForward.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pageForwardAction();
            }
        });
        btnPageForward.setBounds(309, 280, 47, 23);
        
        lblPageNumber = new JLabel("1/1");
        lblPageNumber.setBounds(266, 284, 47, 14);
        lblPageNumber.setHorizontalAlignment(SwingConstants.CENTER);
        
        createOrderP.setLayout(null);
        viewOrderP.setLayout(null);
        
        scrollPaneMenu = new RScrollPane();
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
        
        btnSubmitOrder = new RActionButton("Submit Order");
        btnSubmitOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitOrderAction();
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
        
        btnClearItem1 = new RXButton("X");
        btnClearItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLineAction(0);
            }
        });
        btnClearItem1.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem1.setBounds(10, 32, 39, 18);
        btnClearItem1.setVisible(false);
        createOrderP.add(btnClearItem1);
        
        btnClearItem2 = new RXButton("X");
        btnClearItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLineAction(1);
            }
        });
        btnClearItem2.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem2.setBounds(10, 57, 39, 18);
        btnClearItem2.setVisible(false);
        createOrderP.add(btnClearItem2);
        
        btnClearItem3 = new RXButton("X");
        btnClearItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLineAction(2);
            }
        });
        btnClearItem3.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem3.setBounds(10, 82, 39, 18);
        btnClearItem3.setVisible(false);
        createOrderP.add(btnClearItem3);
        
        btnClearItem4 = new RXButton("X");
        btnClearItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLineAction(3);
            }
        });
        btnClearItem4.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem4.setBounds(10, 106, 39, 18);
        btnClearItem4.setVisible(false);
        createOrderP.add(btnClearItem4);
   
        btnClearItem5 = new RXButton("X");
        btnClearItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePageOrderLineAction(4);
            }
        });
        btnClearItem5.setFont(new Font("Tahoma", Font.PLAIN, 8));
        btnClearItem5.setBounds(10, 131, 39, 18);
        btnClearItem5.setVisible(false);
        createOrderP.add(btnClearItem5);
        
        itemLabels = new JLabel[] {lblItem1, lblItem2, lblItem3, lblItem4, lblItem5};
        itemClearButtons = new JButton[] {btnClearItem1, btnClearItem2, btnClearItem3, btnClearItem4, btnClearItem5};
        
        buttonAddItem = new RActionButton("Add");
        buttonAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItemAction();
            }
        });
        buttonAddItem.setBounds(413, 169, 83, 23);
        createOrderP.add(buttonAddItem);
        
        // Styles the scroll pane
        scrollPaneNote = new RScrollPane();
        scrollPaneNote.setBounds(27, 240, 220, 100);
        createOrderP.add(scrollPaneNote);
        
        txtCurrentOrderNote = new RTextArea();
        txtCurrentOrderNote.setLineWrap(true);
        
        txtWaitingOrderNote = new RTextArea();
        txtWaitingOrderNote.setLineWrap(true);
        
        lblOrderInProgress = new JLabel("Order In Progress");
        lblOrderInProgress.setBounds(405, 300, 135, 14);
        createOrderP.add(lblOrderInProgress);
        
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
        
        btnViewOrder1 = new RActionButton("View Order");
        btnViewOrder1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrderAction(0);
            }
        });
        btnViewOrder1.setBounds(197, 46, 100, 23);
        orderListP.add(btnViewOrder1);
        
        btnViewOrder2 = new RActionButton("View Order");
        btnViewOrder2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrderAction(1);
            }
        });
        btnViewOrder2.setBounds(197, 96, 100, 23);
        orderListP.add(btnViewOrder2);
        
        btnViewOrder3 = new RActionButton("View Order");
        btnViewOrder3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrderAction(2);
            }
        });
        btnViewOrder3.setBounds(197, 146, 100, 23);
        orderListP.add(btnViewOrder3);
        
        btnViewOrder4 = new RActionButton("View Order");
        btnViewOrder4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrderAction(3);
            }
        });
        btnViewOrder4.setBounds(197, 196, 100, 23);
        orderListP.add(btnViewOrder4);
        
        btnViewOrder5 = new RActionButton("View Order");
        btnViewOrder5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewOrderAction(4);
            }
        });
        btnViewOrder5.setBounds(197, 246, 100, 23);
        orderListP.add(btnViewOrder5);
        
        btnMarkCompleted1 = new RActionButton("Set Completed");
        btnMarkCompleted1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompletedAction(0);
            }
        });
        btnMarkCompleted1.setBounds(67, 46, 120, 23);
        orderListP.add(btnMarkCompleted1);
        
        btnMarkCompleted2 = new RActionButton("Set Completed");
        btnMarkCompleted2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompletedAction(1);
            }
        });
        btnMarkCompleted2.setBounds(67, 96, 120, 23);
        orderListP.add(btnMarkCompleted2);
        
        btnMarkCompleted3 = new RActionButton("Set Completed");
        btnMarkCompleted3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompletedAction(2);
            }
        });
        btnMarkCompleted3.setBounds(67, 146, 120, 23);
        orderListP.add(btnMarkCompleted3);
        
        btnMarkCompleted4 = new RActionButton("Set Completed");
        btnMarkCompleted4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompletedAction(3);
            }
        });
        btnMarkCompleted4.setBounds(67, 196, 120, 23);
        orderListP.add(btnMarkCompleted4);
        
        btnMarkCompleted5 = new RActionButton("Set Completed");
        btnMarkCompleted5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markOrderCompletedAction(4);
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
        
        manageUsersP.setLayout(null);
        
        JLabel lblUsers = new JLabel("Users");
        lblUsers.setBounds(270, 15, 46, 14);
        manageUsersP.add(lblUsers);
        
        btnEditUser1 = new RXButton("Save Edits");
        btnEditUser1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUserAction(0);
            }
        });
        btnEditUser1.setBounds(124, 55, 97, 23);
        manageUsersP.add(btnEditUser1);
        
        btnEditUser2 = new RXButton("Save Edits");
        btnEditUser2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUserAction(1);
            }
        });
        btnEditUser2.setBounds(124, 110, 97, 23);
        manageUsersP.add(btnEditUser2);
        
        btnEditUser3 = new RXButton("Save Edits");
        btnEditUser3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUserAction(2);
            }
        });
        btnEditUser3.setBounds(124, 165, 97, 23);
        manageUsersP.add(btnEditUser3);
        
        btnEditUser4 = new RXButton("Save Edits");
        btnEditUser4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUserAction(3);
            }
        });
        btnEditUser4.setBounds(124, 220, 97, 23);
        manageUsersP.add(btnEditUser4);
        
        btnEditUser5 = new RXButton("Save Edits");
        btnEditUser5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editUserAction(4);
            }
        });
        btnEditUser5.setBounds(124, 275, 97, 23);
        manageUsersP.add(btnEditUser5);
        
        btnDeleteUser1 = new RXButton("Delete User");
        btnDeleteUser1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUserAction(0);
            }
        });
        btnDeleteUser1.setBounds(10, 55, 104, 23);
        manageUsersP.add(btnDeleteUser1);
        
        btnDeleteUser2 = new RXButton("Delete User");
        btnDeleteUser2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUserAction(1);
            }
        });
        btnDeleteUser2.setBounds(10, 110, 104, 23);
        manageUsersP.add(btnDeleteUser2);
        
        btnDeleteUser3 = new RXButton("Delete User");
        btnDeleteUser3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUserAction(2);
            }
        });
        btnDeleteUser3.setBounds(10, 165, 104, 23);
        manageUsersP.add(btnDeleteUser3);
        
        btnDeleteUser4 = new RXButton("Delete User");
        btnDeleteUser4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUserAction(3);
            }
        });
        btnDeleteUser4.setBounds(10, 220, 104, 23);
        manageUsersP.add(btnDeleteUser4);
        
        btnDeleteUser5 = new RXButton("Delete User");
        btnDeleteUser5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUserAction(4);
            }
        });
        btnDeleteUser5.setBounds(10, 275, 104, 23);
        manageUsersP.add(btnDeleteUser5);
        
        String[] comboBoxOptions = new String[] {"customer", "employee"};
        
        comboBoxUsertype1 = new JComboBox<String>(comboBoxOptions);
        comboBoxUsertype1.setBounds(231, 55, 85, 22);
        comboBoxUsertype1.setSelectedItem("customer");
        manageUsersP.add(comboBoxUsertype1);
        
        comboBoxUsertype2 = new JComboBox<String>(comboBoxOptions);
        comboBoxUsertype2.setBounds(231, 110, 85, 22);
        comboBoxUsertype2.setSelectedItem("customer");
        manageUsersP.add(comboBoxUsertype2);
        
        comboBoxUsertype3 = new JComboBox<String>(comboBoxOptions);
        comboBoxUsertype3.setBounds(231, 165, 85, 22);
        comboBoxUsertype3.setSelectedItem("customer");
        manageUsersP.add(comboBoxUsertype3);
        
        comboBoxUsertype4 = new JComboBox<String>(comboBoxOptions);
        comboBoxUsertype4.setBounds(231, 220, 85, 22);
        comboBoxUsertype4.setSelectedItem("customer");
        manageUsersP.add(comboBoxUsertype4);
        
        comboBoxUsertype5 = new JComboBox<String>(comboBoxOptions);
        comboBoxUsertype5.setBounds(231, 275, 85, 22);
        comboBoxUsertype5.setSelectedItem("customer");
        manageUsersP.add(comboBoxUsertype5);
        
        lblusername1 = new JLabel("Username:");
        lblusername1.setBounds(326, 49, 69, 14);
        manageUsersP.add(lblusername1);
        
        lblPassword1 = new JLabel("Password:");
        lblPassword1.setBounds(326, 71, 70, 14);
        manageUsersP.add(lblPassword1);
        
        txtUsername1 = new JTextField();
        txtUsername1.setBounds(390, 46, 184, 20);
        manageUsersP.add(txtUsername1);
        txtUsername1.setColumns(10);
        
        txtPassword1 = new JTextField();
        txtPassword1.setBounds(390, 68, 184, 20);
        manageUsersP.add(txtPassword1);
        txtPassword1.setColumns(10);
        
        lblusername2 = new JLabel("Username:");
        lblusername2.setBounds(326, 104, 69, 14);
        manageUsersP.add(lblusername2);
        
        txtUsername2 = new JTextField();
        txtUsername2.setColumns(10);
        txtUsername2.setBounds(390, 101, 184, 20);
        manageUsersP.add(txtUsername2);
        
        lblPassword2 = new JLabel("Password:");
        lblPassword2.setBounds(326, 126, 70, 14);
        manageUsersP.add(lblPassword2);
        
        txtPassword2 = new JTextField();
        txtPassword2.setColumns(10);
        txtPassword2.setBounds(390, 123, 184, 20);
        manageUsersP.add(txtPassword2);
        
        lblusername3 = new JLabel("Username:");
        lblusername3.setBounds(326, 159, 69, 14);
        manageUsersP.add(lblusername3);
        
        txtUsername3 = new JTextField();
        txtUsername3.setColumns(10);
        txtUsername3.setBounds(390, 156, 184, 20);
        manageUsersP.add(txtUsername3);
        
        lblPassword3 = new JLabel("Password:");
        lblPassword3.setBounds(326, 181, 70, 14);
        manageUsersP.add(lblPassword3);
        
        txtPassword3 = new JTextField();
        txtPassword3.setColumns(10);
        txtPassword3.setBounds(390, 178, 184, 20);
        manageUsersP.add(txtPassword3);
        
        lblusername4 = new JLabel("Username:");
        lblusername4.setBounds(326, 214, 69, 14);
        manageUsersP.add(lblusername4);
        
        txtUsername4 = new JTextField();
        txtUsername4.setColumns(10);
        txtUsername4.setBounds(390, 211, 184, 20);
        manageUsersP.add(txtUsername4);
        
        lblPassword4 = new JLabel("Password:");
        lblPassword4.setBounds(326, 236, 70, 14);
        manageUsersP.add(lblPassword4);
        
        txtPassword4 = new JTextField();
        txtPassword4.setColumns(10);
        txtPassword4.setBounds(390, 233, 184, 20);
        manageUsersP.add(txtPassword4);
        
        lblusername5 = new JLabel("Username:");
        lblusername5.setBounds(326, 269, 69, 14);
        manageUsersP.add(lblusername5);
        
        txtUsername5 = new JTextField();
        txtUsername5.setColumns(10);
        txtUsername5.setBounds(390, 266, 184, 20);
        manageUsersP.add(txtUsername5);
        
        lblPassword5 = new JLabel("Password:");
        lblPassword5.setBounds(326, 291, 70, 14);
        manageUsersP.add(lblPassword5);
        
        txtPassword5 = new JTextField();
        txtPassword5.setColumns(10);
        txtPassword5.setBounds(390, 288, 184, 20);
        manageUsersP.add(txtPassword5);
        
        usernameLabels = new JLabel[] {lblusername1, lblusername2, lblusername3, lblusername4, lblusername5};
        passwordLabels = new JLabel[] {lblPassword1, lblPassword2, lblPassword3, lblPassword4, lblPassword5};
        usernameTxtFields = new JTextField[] {txtUsername1, txtUsername2, txtUsername3, txtUsername4, txtUsername5};
        passwordTxtFields = new JTextField[] {txtPassword1, txtPassword2, txtPassword3, txtPassword4, txtPassword5};
        editUserButtons = new JButton[] {btnEditUser1, btnEditUser2, btnEditUser3, btnEditUser4, btnEditUser5};
        deleteUserButtons = new JButton[] {btnDeleteUser1, btnDeleteUser2, btnDeleteUser3, btnDeleteUser4, btnDeleteUser5};
        usertypeComboBoxes = new ArrayList<JComboBox<String>>();
        usertypeComboBoxes.add(comboBoxUsertype1);
        usertypeComboBoxes.add(comboBoxUsertype2);
        usertypeComboBoxes.add(comboBoxUsertype3);
        usertypeComboBoxes.add(comboBoxUsertype4);
        usertypeComboBoxes.add(comboBoxUsertype5);
        
        buildingP.setLayout(null);
        
        lblBuilding = new JLabel("Building");
        lblBuilding.setBounds(270, 20, 46, 14);
        buildingP.add(lblBuilding);
        
        lblBuildingPhone = new JLabel("Building Phone:");
        lblBuildingPhone.setBounds(130, 50, 100, 14);
        buildingP.add(lblBuildingPhone);
        
        lblState = new JLabel("State:");
        lblState.setBounds(130, 100, 100, 14);
        buildingP.add(lblState);
        
        lblCity = new JLabel("City:");
        lblCity.setBounds(130, 150, 100, 14);
        buildingP.add(lblCity);
        
        lblStreetAddr1 = new JLabel("Address Line 1:");
        lblStreetAddr1.setBounds(130, 200, 100, 14);
        buildingP.add(lblStreetAddr1);
        
        lblStreetAddr2 = new JLabel("Address Line 2:");
        lblStreetAddr2.setBounds(130, 250, 100, 14);
        buildingP.add(lblStreetAddr2);
        
        txtBuildingPhone = new JTextField();
        txtBuildingPhone.setBounds(240, 47, 150, 20);
        buildingP.add(txtBuildingPhone);
        txtBuildingPhone.setColumns(10);
        
        txtCity = new JTextField();
        txtCity.setBounds(240, 147, 150, 20);
        buildingP.add(txtCity);
        txtCity.setColumns(10);
        
        txtAddrLine1 = new JTextField();
        txtAddrLine1.setBounds(240, 197, 150, 20);
        buildingP.add(txtAddrLine1);
        txtAddrLine1.setColumns(10);
        
        txtAddrLine2 = new JTextField();
        txtAddrLine2.setBounds(240, 247, 150, 20);
        buildingP.add(txtAddrLine2);
        txtAddrLine2.setColumns(10);
        
        String[] states = {
            "ak", "al", "ar", "az", "ca", "co", "ct", "de", "fl", "ga",
            "hi", "ia", "id", "il", "in", "ks", "ky", "la", "ma", "md",
            "me", "mi", "mn", "mo", "ms", "mt", "nc", "nd", "ne", "nh",
            "nj", "nm", "nv", "ny", "oh", "ok", "or", "pa", "ri", "sc",
            "sd", "tn", "tx", "ut", "va", "vt", "wa", "wi", "wv", "wy"
        };
        comboBoxState = new JComboBox<String>(states);
        comboBoxState.setBounds(240, 96, 50, 22);
        buildingP.add(comboBoxState);
        
        btnBuilding = new JButton("Save Changes");
        btnBuilding.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBuildingChangesAction();
            }
        });
        btnBuilding.setBounds(239, 300, 120, 23);
        buildingP.add(btnBuilding);
    }
    
    /**
     * Draws the order line page with the provided array of OrderLine objects.
     * 
     * @param orderLinePage An array of OrderLine objects representing the order lines to be displayed on the page.
     */
    public void drawOrderLinePage(OrderLine[] orderLinePage) {
        for (int i = 0; i < ORDERLINES_PER_PAGE; i++) {
            itemLabels[i].setVisible(false);
            itemClearButtons[i].setVisible(false);
        }
        
        for (int i = 0; i < orderLinePage.length && orderLinePage[i] != null; i++) {
            itemClearButtons[i].setVisible(true);
            
            itemLabels[i].setText(orderLinePage[i].getProductName() + " x" + orderLinePage[i].getQuantity());
            itemLabels[i].setVisible(true);
        }
    }
    
    /**
     * Draws the order page with the provided array of Order objects.
     * 
     * @param orderPage An array of Order objects representing the orders to be displayed on the page.
     */
    public void drawOrderPage(Order[] orderPage) {
        for (int i = 0; i < ORDERS_PER_PAGE; i++) {
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
    
    /**
     * Draws the user page with the provided array of User objects.
     * 
     * @param userPage An array of User objects representing the users to be displayed on the page.
     */
    public void drawUserPage(User[] userPage) {
        for (int i = 0; i < USERS_PER_PAGE; i++) {
            usernameLabels[i].setVisible(false);
            passwordLabels[i].setVisible(false);
            usertypeComboBoxes.get(i).setVisible(false);
            usernameTxtFields[i].setVisible(false);
            passwordTxtFields[i].setVisible(false);
            editUserButtons[i].setVisible(false);
            deleteUserButtons[i].setVisible(false);
        }
        
        for (int i = 0; i < userPage.length && userPage[i] != null; i++) {
            usernameLabels[i].setVisible(true);
            passwordLabels[i].setVisible(true);
            editUserButtons[i].setVisible(true);
            deleteUserButtons[i].setVisible(true);
            
            usertypeComboBoxes.get(i).setSelectedItem(userPage[i].getUsertype());
            usernameTxtFields[i].setText(userPage[i].getUsername());
            passwordTxtFields[i].setText(userPage[i].getPassword());
            usertypeComboBoxes.get(i).setVisible(true);
            usernameTxtFields[i].setVisible(true);
            passwordTxtFields[i].setVisible(true);
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
     * Sets the viewable buttons by the user as an admin
     */
    public void setAdminView() {
        // Removes all items from the menu
        removeAllButtons();
        
        // Adds necessary items to the menu
        //menu.add(viewOrderB);
        menu.add(orderListB);
        menu.add(createOrderB);
        menu.add(manageUsersB);
        menu.add(buildingB);
    }
    
    /**
     * Sets up the view for viewing a specific order, including its details and order lines.
     * 
     * @param orderToView The list of order lines representing the order to be viewed.
     * @param orderID The ID of the order to be viewed.
     * @param username The username associated with the order.
     * @param orderNote The note associated with the order.
     */
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
     * Action performed when navigating back to the previous page.
     * This method retrieves the current page number and total pages from the page number label,
     * decrements the current page number if it's not already the first page,
     * based on the page type, updates the displayed content accordingly by calling appropriate methods,
     * updates the page number label with the new current page number.
     */
    public void pageBackAction() {
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
            } else if (programView == CUSTOMER_VIEW_TYPE) {
                myOrder = infoHandler.getMyWaitingOrder();
            } else {
                myOrder = infoHandler.getCurrentlyViewedOrder().getItems();
            }
            
            OrderLine[] orderLines = myOrder.toArray(OrderLine[]::new);
            drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentPage, ORDERLINES_PER_PAGE));
        } else if (pagetype == ORDERS_PAGE_TYPE) {
            drawOrderPage(infoHandler.getOrderPage(currentPage, ORDERS_PER_PAGE));
        } else if (pagetype == USERS_PAGE_TYPE) {
            drawUserPage(infoHandler.getUserPage(currentPage, USERS_PER_PAGE));
        }
        
        lblPageNumber.setText(currentPage + "/" + totalPages);
    }
    
    /**
     * Action performed when navigating forward to the next page.
     * This method retrieves the current page number and total pages from the page number label,
     * increments the current page number if it's not already the last page,
     * based on the page type, updates the displayed content accordingly by calling appropriate methods,
     * updates the page number label with the new current page number.
     */
    public void pageForwardAction() {
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
            } else if (programView == CUSTOMER_VIEW_TYPE) {
                myOrder = infoHandler.getMyWaitingOrder();
            } else {
                myOrder = infoHandler.getCurrentlyViewedOrder().getItems();
            }
            
            OrderLine[] orderLines = myOrder.toArray(OrderLine[]::new);
            drawOrderLinePage(infoHandler.getOrderLinePage(orderLines, currentPage, ORDERLINES_PER_PAGE));
        } else if (pagetype == ORDERS_PAGE_TYPE) {
            drawOrderPage(infoHandler.getOrderPage(currentPage, ORDERS_PER_PAGE));
        } else if (pagetype == USERS_PAGE_TYPE) {
            drawUserPage(infoHandler.getUserPage(currentPage, USERS_PER_PAGE));
        }
        
        lblPageNumber.setText(currentPage + "/" + totalPages);
    }
    
    /**
     * Action performed when submitting the current order.
     * This method checks if the total charge of the current order is zero,
     * if the user note exceeds 255 characters,
     * displays an error message and returns if either condition is met,
     * prompts the user to confirm the order submission,
     * adds the user's current order with the provided note to the database,
     * updates GUI components such as the current order note, pending order ID, total charge, and page number label,
     * makes the "Order In Progress" label visible and the "Submit Order" button invisible,
     * updates the order line page with the modified order lines.
     */
    public void submitOrderAction() {
        if (infoHandler.getTotalCharge(infoHandler.getMyCurrentOrder()) == 0.0) {
            return;
        }
        
        if (txtCurrentOrderNote.getText().length() >= 255) {
            JOptionPane.showMessageDialog(null, "user note must be less than 255 characters", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JFrame confirmSubmit = new JFrame();
        if (JOptionPane.showConfirmDialog(confirmSubmit, "Confirm Order Submission", "Confirm Order", JOptionPane.YES_NO_OPTION) 
                != JOptionPane.YES_OPTION) {
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
    
    /**
     * Action performed when adding an item to the current order.
     * This method retrieves the selected item from the menu list,
     * checks if an item is selected and not empty,
     * extracts the product name from the selected value,
     * retrieves the current order lines from the infoHandler,
     * checks if the quantity of the selected product is already incremented,
     * increments the quantity if the product is already in the order lines,
     * otherwise, adds a new order line with quantity 1 for the selected product,
     * updates the order line page with the modified order lines,
     * calculates the total number of pages and updates the page number label accordingly,
     * recalculates the total charge of the current order and updates the total charge label.
     */
    public void addItemAction() {
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
    
    /**
     * Action performed when saving changes made to building information.
     * This method prompts the user to confirm the changes through a dialog window.
     * If the user confirms the changes, the method retrieves the updated building information,
     * including phone number, state, city, and address lines,
     * validates the phone number to ensure it has exactly ten characters,
     * displays an error message if the phone number is invalid,
     * otherwise, updates the building information using the infoHandler,
     * which updates the building information in the database.
     */
    public void saveBuildingChangesAction() {
        JFrame confirmSubmit = new JFrame();
        if (JOptionPane.showConfirmDialog(confirmSubmit, "Confirm Change To Building", "Confirm Building Edit", JOptionPane.YES_NO_OPTION) 
                != JOptionPane.YES_OPTION) {
            return;
        }
        
        String newPhone = txtBuildingPhone.getText();
        String state = (String)comboBoxState.getSelectedItem();
        String city = txtCity.getText();
        String addr1 = txtAddrLine1.getText();
        String addr2 = txtAddrLine2.getText();
        if (addr2.isEmpty()) {
            addr2 = null;
        }
        
        if (newPhone.length() != 10) {
            JOptionPane.showMessageDialog(null, "enter ten phone characters", "Building Edit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        infoHandler.setBuildingInfo(newPhone, state, city, addr1, addr2);
    }
    
    /**
     * Action performed when marking an order as completed.
     * This method prompts the user to confirm order completion through a dialog window.
     * If the user confirms completion, the method retrieves the current page number,
     * calculates the index of the order to mark as completed based on the page number and index passed as parameters,
     * retrieves the order ID of the order to mark as completed, marks the order as completed in the database,
     * refreshes orders in progress, recalculates the total number of pages, adjusts the current page if necessary,
     * updates the page number label, and redraws the order page.
     *
     * @param idx The index of the order to mark as completed within the current page.
     */
    public void viewOrderAction(int idx) {
        String pageNumberTxt = lblPageNumber.getText();
        int slashIndex = pageNumberTxt.indexOf('/');
        String currentPageString = pageNumberTxt.substring(0, slashIndex);
        
        int currentPage = Integer.valueOf(currentPageString);
        
        Order orderToView = infoHandler.getOrdersInProgress()[((currentPage - 1) * ORDERS_PER_PAGE) + idx];
        
        infoHandler.setCurrentlyViewedOrder(orderToView);
        
        setupOrderViewPage(
            orderToView.getItems(),
            orderToView.getOrderID(),
            orderToView.getCustomerUsername(),
            orderToView.getNote()
        );
    }
    
    
    /**
     * Marks an order as completed and removes it from the order list.
     * 
     * @param idx The index of the order line to delete within the current page.
     */
    public void markOrderCompletedAction(int idx) {
        JFrame confirmCompleted = new JFrame();
        if (JOptionPane.showConfirmDialog(confirmCompleted, "Confirm Order Completion", "Confirm Completion", JOptionPane.YES_NO_OPTION) 
                != JOptionPane.YES_OPTION) {
            return;
        }
        
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
    
    /**
     * Action performed when deleting an order line from the current page.
     * This method retrieves the current page number, calculates the index of the order line to delete based on the index passed as a parameter,
     * retrieves the name of the item to remove from the order line, retrieves the current order lines,
     * removes the order line corresponding to the item to remove from the current order lines,
     * recalculates the total number of pages and adjusts the current page if necessary,
     * updates the page number label, redraws the order line page with the updated order lines,
     * calculates the total charge of the current order, and updates the total charge label.
     *
     * @param idx The index of the order line to delete within the current page.
     */
    public void deletePageOrderLineAction(int idx) {
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
     * Action performed when editing a user's information.
     * This method retrieves the current page number, calculates the index of the user to edit based on the page number and index passed as parameters,
     * retrieves the user to edit, extracts the old username of the user, retrieves the new username, password, and user type entered by the user,
     * updates the user information with the new values, refreshes user information, displays a confirmation message,
     * recalculates the total number of pages and adjusts the current page if necessary,
     * updates the page number label, and redraws the user page.
     *
     * @param idx The index of the user to edit within the current page.
     */
    
    public void editUserAction(int idx) {
        String pageNumberTxt = lblPageNumber.getText();
        int slashIndex = pageNumberTxt.indexOf('/');
        String currentPageString = pageNumberTxt.substring(0, slashIndex);
        
        int currentPage = Integer.valueOf(currentPageString);
        
        User userToEdit = infoHandler.getUsers()[((currentPage - 1) * USERS_PER_PAGE) + idx];
        
        String oldUsername = userToEdit.getUsername();

        String newUsername = usernameTxtFields[idx].getText();
        String newPassword = passwordTxtFields[idx].getText();
        String newUsertype = (String)usertypeComboBoxes.get(idx).getSelectedItem();
        
        infoHandler.setUserInfo(oldUsername, newUsername, newPassword, newUsertype);
        
        infoHandler.refreshUserInfo();
        
        JOptionPane.showMessageDialog(null, "User Information Edited", "User Edited", JOptionPane.INFORMATION_MESSAGE);
        
        // do this in case orders were deleted since last refresh
        int howManyUsers = infoHandler.getUsers().length;
        int totalPages = (howManyUsers / USERS_PER_PAGE);
        if (howManyUsers % USERS_PER_PAGE != 0 || howManyUsers == 0) {
            totalPages += 1;
        }
        if (currentPage > totalPages) {
            currentPage -= 1;
        }
        lblPageNumber.setText(currentPage + "/" + totalPages);
        drawUserPage(infoHandler.getUserPage(currentPage, USERS_PER_PAGE));
    }
    
    /**
     * Action performed when deleting a user.
     * This method prompts the user to confirm user deletion through a dialog window.
     * If the user confirms deletion, the method retrieves the current page number,
     * calculates the index of the user to delete based on the page number and index passed as parameters,
     * deletes the user from the database, refreshes user information,
     * recalculates the total number of pages, adjusts the current page if necessary,
     * updates the page number label, and redraws the user page.
     *
     * @param idx The index of the user to delete within the current page.
     */
    public void deleteUserAction(int idx) {
        JFrame confirmCompleted = new JFrame();
        if (JOptionPane.showConfirmDialog(confirmCompleted, "Confirm User Deletion", "Confirm Delete User", JOptionPane.YES_NO_OPTION) 
                != JOptionPane.YES_OPTION) {
            return;
        }
        
        String pageNumberTxt = lblPageNumber.getText();
        int slashIndex = pageNumberTxt.indexOf('/');
        String currentPageString = pageNumberTxt.substring(0, slashIndex);
        
        int currentPage = Integer.valueOf(currentPageString);
        
        User userToDelete = infoHandler.getUsers()[((currentPage - 1) * USERS_PER_PAGE) + idx];
        
        String username = userToDelete.getUsername();
        
        if (myDatabase.removeItem("user", "username", username) != 0) {
            return;
        }
        
        infoHandler.refreshUserInfo();
        
        int howManyUsers = infoHandler.getUsers().length;
        int totalPages = (howManyUsers / USERS_PER_PAGE);
        if (howManyUsers % USERS_PER_PAGE != 0 || howManyUsers == 0) {
            totalPages += 1;
        }
        if (currentPage > totalPages) {
            currentPage -= 1;
        }
        lblPageNumber.setText(currentPage + "/" + totalPages);
        drawUserPage(infoHandler.getUserPage(currentPage, USERS_PER_PAGE));
    }
    

    /**
     * Action performed when the "Order List" menu button is clicked.
     * This method sets up the interface to display a list of orders in progress.
     * It sets the page type to orders, resets the menu button text,
     * removes any panels currently in view, adds the order list panel to the content pane,
     * refreshes orders in progress, sets up pagination controls, retrieves and displays the first page of orders,
     * calculates the total number of pages, updates the page number label,
     * and validates and repaints the changes made.
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
        int howManyPages = (howManyWaitingOrders / ORDERS_PER_PAGE);
        if (howManyWaitingOrders % ORDERS_PER_PAGE != 0 || howManyWaitingOrders == 0) {
            howManyPages += 1;
        }
        lblPageNumber.setText("1/" + howManyPages);
        
        // Validates and repaints the changes
        validate();
        repaint();
        
    }
    
    
    /**
     * Action performed when the "View Order" menu button is clicked.
     * This method refreshes the order status, sets up the interface for viewing the user's waiting order,
     * including displaying order details such as order ID, customer name, and order note,
     * resets the "View Order" menu button text to "refresh",
     * and validates the changes made.
     */
    void viewOrderAction() {
    	// Refresh order status to ensure the latest data is retrieved
        infoHandler.refreshOrderStatus();
     // Set up the order view page with information about the user's waiting order
        setupOrderViewPage(
            infoHandler.getMyWaitingOrder(),
            infoHandler.getMyOrderID(),
            infoHandler.getUsername(),
            infoHandler.getMyWaitingOrderNote()
        );
        // Update the text of the "View Order" menu button to "refresh"
        viewOrderB.setText("refresh");
    }
    
    
    /**
     * Action performed when the "Create Order" menu button is clicked.
     * This method sets up the interface for creating a new order.
     * It sets the page type to create order, adds shared graphics components for order line pages to the create order panel,
     * sets up scroll panes for order notes and menu items, resets the menu button text, removes any panels currently in view,
     * adds the create order panel to the content pane, refreshes order status, sets up pagination controls,
     * retrieves and displays the first page of order lines, populates customer information and pending order ID,
     * populates the menu list with available items, and updates the page number label.
     * Additionally, it hides or displays the "Order In Progress" label and "Submit Order" button based on the existence of a pending order,
     * and validates and repaints the changes.
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
        
        lblCustomerName.setText("Name: " + infoHandler.getUsername());
        lblPendingOrderID.setText("Pending Order ID: " + (infoHandler.getMyOrderID() != -1 ? infoHandler.getMyOrderID() : "null"));
        lblCustomerTotal.setText("Total: $" + String.format("%.2f", infoHandler.getTotalCharge(infoHandler.getMyCurrentOrder())));
        
        ArrayList<String> itemStrings = new ArrayList<String>();
        
        for (Map.Entry<String, Double> productView : infoHandler.getProductEntrySet()) {
            String costString = "$" + String.format("%.2f", productView.getValue());
            itemStrings.add(costString + ": " + productView.getKey());
        }
        
        menuList = new RItemList<String>(itemStrings.toArray(String[]::new));
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
     * Action performed when the "Manage Users" menu button is clicked.
     * This method sets up the interface to manage user information.
     * It sets the page type to users, resets the menu button text, removes any panels currently in view,
     * adds the manage users panel to the content pane, refreshes user information, sets up pagination controls,
     * retrieves and displays the first page of users, calculates the total number of pages,
     * updates the page number label, and validates and repaints the changes.
     */
    void manageUsersAction() {
        pagetype = USERS_PAGE_TYPE;
        
        // resets the menu button text
        resetMenuButtonText();
        // Removes any panels currently in view
        removeAllPanelsFromContentPane();
        // Adds the panel selected by the user
        getContentPane().add(manageUsersP);
        
        infoHandler.refreshUserInfo();
        
        manageUsersB.setText("refresh");
        
        lblPageNumber.setLocation(266, 324);
        btnPageBack.setLocation(223, 320);
        btnPageForward.setLocation(309, 320);
        manageUsersP.add(lblPageNumber);
        manageUsersP.add(btnPageBack);
        manageUsersP.add(btnPageForward);
        
        User[] userPage = infoHandler.getUserPage(1, USERS_PER_PAGE);
        drawUserPage(userPage);
        
        int howManyUsers = infoHandler.getUsers().length;
        int howManyPages = (howManyUsers / USERS_PER_PAGE);
        if (howManyUsers % USERS_PER_PAGE != 0 || howManyUsers == 0) {
            howManyPages += 1;
        }
        lblPageNumber.setText("1/" + howManyPages);
        
        // Validates and repaints the changes
        validate();
        repaint();
        
    }
    
    /**
     * Action performed when the "Building" menu button is clicked.
     * This method sets up the interface to display building information.
     * It resets the menu button text, removes any panels currently in view,
     * adds the building panel to the content pane, refreshes user information,
     * populates the building information fields, and validates and repaints the changes.
     */
    void buildingAction() {
        
        // resets the menu button text
        resetMenuButtonText();
        // Removes any panels currently in view
        removeAllPanelsFromContentPane();
        // Adds the panel selected by the user
        getContentPane().add(buildingP);
        
        infoHandler.refreshUserInfo();
        
        String[] buildingInfo = infoHandler.getBuildingInfo();
        
        txtBuildingPhone.setText(buildingInfo[0]);
        comboBoxState.setSelectedItem(buildingInfo[1]);
        txtCity.setText(buildingInfo[2]);
        txtAddrLine1.setText(buildingInfo[3]);
        txtAddrLine2.setText(buildingInfo[4]);
        
        // Validates and repaints the changes
        validate();
        repaint();
    }
    
    /**
     * Adds shared graphics components for order line pages to a panel.
     * This method adds common graphical components used in order line pages
     * to the specified panel, such as labels, scroll panes, and buttons.
     * It is useful for maintaining consistency in the appearance and functionality
     * of order line pages across the application.
     *
     * @param panel The panel to which the shared graphics components will be added.
     */
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
     * Removes all panels from the content pane of the JFrame.
     * This method removes all panels from the content pane of the JFrame,
     * including the main master panel and all its child panels.
     * It is useful for clearing the content pane before adding new panels or refreshing the interface.
     */
    void removeAllPanelsFromContentPane() {
        getContentPane().remove(viewOrderP);
        getContentPane().remove(createOrderP);
        getContentPane().remove(employeeViewOrderP);
        getContentPane().remove(orderListP);
        getContentPane().remove(manageUsersP);
        getContentPane().remove(buildingP);
        getContentPane().remove(masterP);
    }
    
    
    /**
     * Removes all panels from the master panel.
     * This method removes all panels from the master panel,
     * which is the main panel containing all other panels.
     * It is useful for clearing the master panel before adding new panels or refreshing the interface.
     */
    void removeAllPanels() {
        masterP.remove(viewOrderP);
        masterP.remove(createOrderP);
        masterP.remove(employeeViewOrderP);
        masterP.remove(orderListP);
        masterP.remove(manageUsersP);
        masterP.remove(buildingP);
    }
    
    
    /**
     * Removes all buttons from the menu bar.
     * This method removes all menu items (buttons) from the menu bar.
     * It is useful for clearing the menu bar before adding new buttons or refreshing the menu.
     */
    void removeAllButtons() {
        menu.remove(viewOrderB);
        menu.remove(createOrderB);
        menu.remove(orderListB);
        menu.remove(manageUsersB);
        menu.remove(buildingB);
    }
    
    
    /**
     * Resets the text of menu buttons to their default values.
     * This method sets the text of each menu button to its default value.
     * It is useful for resetting the text of menu buttons after modifications.
     */
    void resetMenuButtonText() {
        viewOrderB.setText("View Order");
        createOrderB.setText("Create Order");
        orderListB.setText("Order List");
        manageUsersB.setText("Manage Users");
        buildingB.setText("Building");
    }

}
