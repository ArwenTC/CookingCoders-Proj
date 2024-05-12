package GroupProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RestaurantGUI extends JFrame {

    // Window Size
    final int SIZE_X = 500, SIZE_Y = 500;


    LoginWindow loginWindow;
    
    User loggedInUser;

    String chosenBuildingName;

    SQLDatabase myDatabase;

    // Panels that will be swapped depending what the user selects
    JPanel masterP;
    JPanel viewOrderP;
    JPanel createOrderP;
    JPanel employeeViewOrderP;
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

    JLabel lblBuildingName;
    JTextField buildingNameField;

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

        // Adds the master panel
        getContentPane().add(masterP);
        masterP.setLayout(null);
        
        // Sets the menu bar created by the build
        setJMenuBar(menu);

        // Size and display the window.
        setSize(SIZE_X, SIZE_Y);
        setVisible(true);

        // Sets view of the program
        //setCustomerView();
        //setEmployeeView();
        //setManagerView();

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
        logInB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                logInAction();
            }
        });
        logOutB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                logOutAction();
            }
        });
        manageB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent m) {
                manageAction();
            }
        });
        
        lblBuildingName = new JLabel("enter building name");
        lblBuildingName.setHorizontalAlignment(SwingConstants.CENTER);
        lblBuildingName.setBounds(147, 138, 182, 14);
        masterP.add(lblBuildingName);

        buildingNameField = new JTextField();
        buildingNameField.setBounds(91, 163, 295, 20);
        masterP.add(buildingNameField);
        buildingNameField.setColumns(10);

        JButton btnSelectName = new JButton("select name");
        btnSelectName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String buildingName = buildingNameField.getText();

                int buildingNameExists = myDatabase.valueExists("BUILDING", "BuildingName", "'" + buildingName + "'");

                if (buildingNameExists == 0) {
                    JOptionPane.showMessageDialog(
                        null,
                        "building named " + buildingName + " doesn't exist",
                        "building selection error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    
                    return;
                } else if (buildingNameExists != 1) {
                    JOptionPane.showMessageDialog(
                        null,
                        "SQL error",
                        "SQL error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    
                    return;
                }
                
                chosenBuildingName = buildingName;
                
                JOptionPane.showMessageDialog(
                    null,
                    buildingName + " chosen",
                    "building selection succeeded",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                lblBuildingName.setVisible(false);
                buildingNameField.setVisible(false);
                btnSelectName.setVisible(false);
                masterP.remove(lblBuildingName);
                masterP.remove(buildingNameField);
                masterP.remove(btnSelectName);
                
                setCustomerView();
            }
        });
        btnSelectName.setBounds(174, 194, 135, 23);
        masterP.add(btnSelectName);

        loginWindow = new LoginWindow(myDatabase);

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

        // Logs user out
        logOut();
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

        // Logs user out
        logOut();
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

        // Logs user out
        logOut();
    }

    /**
     * Sets logOut to be visible
     * Will log the user in
     */
    public void logIn() {

        // Sets the tabs viewed
        menu.remove(logInB);
        menu.add(logOutB);
    }

    /**
     * Sets logIn to be visible
     * Will log the user out
     */
    public void logOut() {

        // Sets the tabs viewed
        menu.remove(logOutB);
        menu.add(logInB);
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
        removeAllPanels();
        // Adds the panel selected by the user
        masterP.add(createOrderP);
        // Validates and repaints the changes
        validate();
        repaint();

    }

    /**
     * Method that runs when the user presses "Manage"
     */
    void manageAction() {
        // Removes any panels currently in view
        removeAllPanels();
        // Adds the panel selected by the user
        masterP.add(manageUsersP);
        // Validates and repaints the changes
        validate();
        repaint();

    }

    /**
     * Method that runs when the user presses "Log In"
     */
    void logInAction() {
        // Removes any panels currently in view
        removeAllPanels();
        // Adds the panel selected by the user
        masterP.add(logInP);
        // Validates and repaints the changes
        validate();
        repaint();
        
        loginWindow.setVisible(true);
        
        try {
            while (!loginWindow.userIsLoggedIn() && loginWindow.isVisible()) {
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        loginWindow.setVisible(false);

    }

    /**
     * Method that runs when the user presses "Log Out"
     */
    void logOutAction() {
        // Sets the current panel to the page selected by the user
        removeAllPanels();
        // Adds the panel selected by the user
        masterP.add(logInP);
        // Validates and repaints the changes
        validate();
        repaint();

    }


    void removeAllPanels() {
        masterP.remove(viewOrderP);
        masterP.remove(createOrderP);
        masterP.remove(employeeViewOrderP);
        masterP.remove(orderListP);
        masterP.remove(createAccountP);
        masterP.remove(logInP);
        masterP.remove(manageUsersP);
    }


    void removeAllButtons() {
        menu.remove(viewOrderB);
        menu.remove(createOrderB);
        menu.remove(orderListB);
        menu.remove(manageB);
    }
}
