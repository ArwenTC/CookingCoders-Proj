
package GroupProject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;

/**
 * Represents a login window frame for the application.
 * Allows users to log in, sign up, reset their credentials, or exit the program.
 * Extends the JFrame class.
 */

public class LoginWindow extends JFrame {
	
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnReset;
	private JButton btnExit;
	private JFrame frmLoginSystem;
	private JButton btnSignUp;
	private JLabel lblConfirm;
    private JPasswordField txtConfirm;
    
    
    // Returns a view of the program
    private int programView;
    
    private User loggedInUser = null;
	
    // Creates a database myDatabase
	private SQLDatabase myDatabase;
	
	private JTextField txtBuilding;
	
	 /**
     * Constructor for the LoginWindow class.
     * Initializes the login window with the given database instance.
     * @param myDatabase_ The SQLDatabase instance used for authentication and data storage.
     */
	public LoginWindow(SQLDatabase myDatabase_) {
	    myDatabase = myDatabase_;
	    
	    // Initializes the frame
		initialize();
	}
	
	  /**
     * Creates an InfoHandler instance based on the logged-in user's data.
     * @return An InfoHandler instance containing user and building information.
     */
	public InfoHandler makeInfoHandler() {
	    try {
	        
	        // getting the building name
    	    ResultSet rs = myDatabase.getDatabaseInfo("user", "username = '" + loggedInUser.getUsername() + "'", null);
    	    
    	    if (rs == null || !rs.next()) {
                return null;
            }
    	    
    	    String buildingName = rs.getString("BuildingName");
    	    
    	    // getting the building information
    	    rs = myDatabase.getDatabaseInfo("building", "buildingname = '" + buildingName + "'", null);
    	    
    	    if (rs == null || !rs.next()) {
                return null;
            }
    	    
    	    String buildingState = rs.getString("state");
    	    String buildingCity = rs.getString("city");
    	    String buildingStreetAddr1 = rs.getString("streetaddr1");
    	    String buildingStreetAddr2 = rs.getString("streetaddr2");
            
            InfoHandler infoHandler = new InfoHandler(
                myDatabase,
                loggedInUser.getUsername(),
                loggedInUser.getUsertype(),
                buildingName,
                buildingState,
                buildingCity,
                buildingStreetAddr1,
                buildingStreetAddr2
            );
            
            infoHandler.refreshProductMap();
            
            if (infoHandler.usertype != "customer") {
                infoHandler.refreshOrdersInProgress();
                infoHandler.refreshUserInfo();
            }
            
            return infoHandler;
    	    
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error when getting info for InfoHandler creation" + e, "SQL Error", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    return null;
	}
	
	
	/**
     * Toggles the visibility of the login window frame.
     * @return True if the frame is now visible, false otherwise.
     */
	public void toggleVisibility() {
	    setVisible(!isVisible());
	}
	
	/**
     * Checks if a user is currently logged in.
     * @return True if a user is logged in, false otherwise.
     */
	public boolean userIsLoggedIn() {
	    return loggedInUser != null;
	}


	/**
	 * Initialize the contents of the frame.
	 */

	
	
	/**
     * Retrieves the current view of the program.
     * @return The current view of the program.
     */
    public int getProgramView() {
        return programView;
    }
    
    /**
     * Handles the action when the user attempts to log in.
     * @return True if the login attempt was successful, false otherwise.
     */
    public void loginAction() {
        
        User loggedInUser_ = null;
        
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        
        if (myDatabase.verifyLogin(username, password)) {
            
            String usertype = myDatabase.getUsertype(username);
            
            if (usertype == null) {
                return;
            }
            
            loggedInUser_ = new User(username, password, usertype);
            
            if ("customer".equals(usertype)) {
                programView = 0;
            } else if ("employee".equals(usertype)) {
                programView = 1;
            } else if ("admin".equals(usertype)) {
                programView = 2;
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "unrecognized usertype: " + loggedInUser.getUsertype(),
                    "Sign Up Error",
                    JOptionPane.ERROR_MESSAGE
                );
                loggedInUser_ = null;
            }
            
            JOptionPane.showMessageDialog(null, "Login successful!");
            
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username or password");
        }
        
        loggedInUser = loggedInUser_;
    }
    
    /**
     * Handles the action when the user attempts to reset their credentials.
     * @return True if the reset attempt was successful, false otherwise.
     */
    public void resetAction() {
        txtUsername.setText(null);
        txtPassword.setText(null);
        txtBuilding.setText(null);
        txtConfirm.setText(null);
    }
    
    /**
     * Handles the action when the user attempts to sign up.
     * @return True if the sign-up attempt was successful, false otherwise.
     */
    public void signUpAction() {
        // Collects user input from the text fields
        String username = txtUsername.getText();
        String buildingPhone = txtBuilding.getText();
        String password = new String(txtPassword.getPassword());
        String confirmedPassword = new String(txtConfirm.getPassword());
        
        
        // Cases in which userName or passwords are invalid entries
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "no username entered", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (username.length() > 31) {
            JOptionPane.showMessageDialog(null, "username too long", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmedPassword)) {
            JOptionPane.showMessageDialog(null, "passwords didn't match", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.length() > 31) {
            JOptionPane.showMessageDialog(null, "password too long", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (buildingPhone.length() != 10) {
            JOptionPane.showMessageDialog(null, "enter ten phone characters", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (myDatabase.valueExists("building", "buildingname", "'" + buildingPhone + "'") != 1) {
            JOptionPane.showMessageDialog(null, "building \"" + buildingPhone + "\" not found", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        // Checks if the userName is available in the database
        // Switches based on the error value
        switch(myDatabase.valueExists("USER", "Username", "'" + username + "'")) {
            case 0:
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "username not available", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
            case 2:
                JOptionPane.showMessageDialog(null, "SQL error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
        }
        
        
        
        // Display a dialog to prompt the user to select a user type
        switch (myDatabase.addItem(
            // Table Name
            "USER",
            // Columns
            new ArrayList<String>(
                Arrays.asList( "Username", "Password", "Usertype", "BuildingName")),
            // Values
            new ArrayList<Object>(
                Arrays.asList(username, password, "customer", buildingPhone))
        )) {
            // Gives an error depending on the result
            case 0:
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Java error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
            case 2:
                JOptionPane.showMessageDialog(null, "SQL error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                return;
        }
        
        
        // Gives a message 
        JOptionPane.showMessageDialog(null, "user added", "Sign Up Succeeded", JOptionPane.INFORMATION_MESSAGE);
        
        // Creates a new user using the selected fields
        loggedInUser = new User(username, password, "customer");
    }
    
    /**
     * Handles the action when the user attempts to exit the program.
     * @return True if the exit attempt was successful, false otherwise.
     */
    public void exitAction() {
        // Creates new frame for exit
        frmLoginSystem = new JFrame();
        // Confirms if user want to exit and exits if so
        if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm exit", "Login System", JOptionPane.YES_NO_OPTION) 
            == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

	
    /**
     * Initializes the contents of the login window frame.
     */

	private void initialize() {
		
		// Sets the bounds of the frame
		setBounds(100, 100, 595, 394);
		setTitle("Admin Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		// Sets the login label
		JLabel lbLogin = new JLabel("Login Page");
		lbLogin.setBounds(260, 30, 98, 14);
		getContentPane().add(lbLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(96, 88, 71, 14);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(96, 113, 71, 14);
		getContentPane().add(lblPassword);
		
		
		// Text Field Button
		txtUsername = new JTextField();
		txtUsername.setBounds(215, 85, 254, 20);
		txtUsername.setColumns(10);
		
		// Password Button
		txtPassword = new JPasswordField();
		txtPassword.setBounds(215, 110, 254, 20);
		
		// Login Button
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        
		        User loggedInUser_ = null;
		        
		        String username = txtUsername.getText();
		        String password = new String(txtPassword.getPassword());
		        
		        if (myDatabase.verifyLogin(username, password)) {
		            
		            String usertype = myDatabase.getUsertype(username);
		            
		            if (usertype == null) {
		                return;
		            }
		            
		            loggedInUser_ = new User(username, password, usertype);
		            
		            if ("customer".equals(usertype)) {
		                programView = 0;
		            } else if ("employee".equals(usertype)) {
		                programView = 1;
		            } else if ("admin".equals(usertype)) {
		                programView = 2;
		            } else {
		                JOptionPane.showMessageDialog(
		                    null,
		                    "unrecognized usertype: " + loggedInUser.getUsertype(),
		                    "Sign Up Error",
		                    JOptionPane.ERROR_MESSAGE
		                );
		                loggedInUser_ = null;
		            }
		            
		            JOptionPane.showMessageDialog(null, "Login successful!");
		            
		        } else {
		            JOptionPane.showMessageDialog(null, "Invalid Username or password");
		        }
		        
		        loggedInUser = loggedInUser_;
			}
		});
		btnLogin.setBounds(31, 297, 89, 23);
		
		
		// Reset Button
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText(null);
				txtPassword.setText(null);
				txtBuilding.setText(null);
				txtConfirm.setText(null);
			}
		});
		btnReset.setBounds(177, 297, 89, 23);
		
		// Sign Up Button
		btnSignUp = new JButton("Sign Up");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // Creates password and confirmed password strings
                char[] password = {};
                char[] confirmedPassword = {};
                
                try {
                    
                    // Collects user input from the text fields
                    String username = txtUsername.getText();
                    String buildingPhone = txtBuilding.getText();
                    password = txtPassword.getPassword();
                    confirmedPassword = txtConfirm.getPassword();
                    
                    
                    // Cases in which userName or passwords are invalid entries
                    if (username.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "no username entered", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (username.length() > 31) {
                        JOptionPane.showMessageDialog(null, "username too long", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!Arrays.equals(password, confirmedPassword)) {
                        JOptionPane.showMessageDialog(null, "passwords didn't match", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (password.length > 31) {
                        JOptionPane.showMessageDialog(null, "password too long", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (buildingPhone.length() != 10) {
                        JOptionPane.showMessageDialog(null, "enter ten phone characters", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (myDatabase.valueExists("building", "buildingname", "'" + buildingPhone + "'") != 1) {
                        JOptionPane.showMessageDialog(null, "building \"" + buildingPhone + "\" not found", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    
                    // Checks if the userName is available in the database
                    // Switches based on the error value
                    switch(myDatabase.valueExists("USER", "Username", "'" + username + "'")) {
                        case 0:
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, "username not available", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        case 2:
                            JOptionPane.showMessageDialog(null, "SQL error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                    
                    
                    
                    // Display a dialog to prompt the user to select a user type
                    switch (myDatabase.addItem(
                        // Table Name
                        "USER",
                        // Columns
                        new ArrayList<String>(
                            Arrays.asList( "Username", "Password", "Usertype", "BuildingName")),
                        // Values
                        new ArrayList<Object>(
                            Arrays.asList(username, password, "customer", buildingPhone))
                    )) {
                        // Gives an error depending on the result
                        case 0:
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, "Java error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        case 2:
                            JOptionPane.showMessageDialog(null, "SQL error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                    
                    
                    // Gives a message 
                    JOptionPane.showMessageDialog(null, "user added", "Sign Up Succeeded", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Creates a new user using the selected fields
                    loggedInUser = new User(username, new String(password), "customer");
                
                } finally {
                    // do this so the password doesn't stay in memory
                    Arrays.fill(password, '\0');
                    Arrays.fill(confirmedPassword, '\0');
                }
            }
        });
        
        // Creates a sign up button
        btnSignUp.setBounds(318, 297, 89, 23);
		
        // Creates an exit button
        btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Creates new frame for exit
				frmLoginSystem = new JFrame();
				// Confirms if user want to exit and exits if so
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm exit", "Login System", JOptionPane.YES_NO_OPTION) 
				    == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
		});
		btnExit.setBounds(467, 297, 89, 23);
		
		
		// Separators
		JSeparator separator = new JSeparator();
		separator.setBounds(21, 277, 553, 2);
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(21, 75, 553, 2);
		
		
		JLabel lblSigningUpMsg = new JLabel("If signing up:");
		lblSigningUpMsg.setBounds(96, 160, 110, 14);
		
		lblConfirm = new JLabel("Confirm Password");
		lblConfirm.setBounds(96, 225, 128, 14);
		
		txtConfirm = new JPasswordField();
		txtConfirm.setBounds(215, 222, 254, 20);
		
		
		
		// Adds all created objects to the contentPane
		getContentPane().add(btnReset);
		getContentPane().add(txtUsername);
		getContentPane().add(txtPassword);
		getContentPane().add(btnLogin);
		getContentPane().add(btnSignUp);
        getContentPane().add(btnExit);
		getContentPane().add(separator);
		getContentPane().add(separator_1);
		getContentPane().add(lblSigningUpMsg);
		getContentPane().add(lblConfirm);
		getContentPane().add(txtConfirm);
		
		JLabel lblBuilding = new JLabel("Building Phone");
		lblBuilding.setBounds(96, 200, 110, 14);
		getContentPane().add(lblBuilding);
		
		txtBuilding = new JTextField();
		txtBuilding.setBounds(215, 197, 254, 20);
		getContentPane().add(txtBuilding);
		txtBuilding.setColumns(10);
		
		
	}
	
	
}