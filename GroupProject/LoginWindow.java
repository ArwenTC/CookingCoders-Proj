package GroupProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;



public class LoginWindow {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnReset;
	private JButton btnExit;
	private JFrame frmLoginSystem;
	private JButton btnSignUp;
	private JLabel lblConfirm;
    private JPasswordField txtConfirm;
    
    private User loggedInUser = null;
	
	private SQLDatabase myDatabase;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	public void toggleVisibility() {
	    frame.setVisible(!frame.isVisible());
	}
	
	
	public boolean userIsLoggedIn() {
	    return loggedInUser != null;
	}
	
	/**
	 * Create the application.
	 */
	public LoginWindow(SQLDatabase myDatabase_) {
	    myDatabase = myDatabase_;
	    
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 595, 394);
		frame.setTitle("Admin Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbLogin = new JLabel("Login Systems");
		lbLogin.setBounds(238, 30, 98, 14);
		frame.getContentPane().add(lbLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(96, 123, 71, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(96, 154, 71, 14);
		frame.getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(215, 120, 254, 20);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(215, 151, 254, 20);
		frame.getContentPane().add(txtPassword);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUsername.getText();
				char [] pass = txtPassword.getPassword();
				String password = new String(pass);
				
				if(username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter both username and password" , "Login Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					String query = "SELECT * FROM user WHERE Username = ? AND Password = ?";
					PreparedStatement pst = myDatabase.con.prepareStatement(query);
					pst.setString(1, username);
					pst.setString(2, password);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()) {
						JOptionPane.showMessageDialog(null, "Login successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
						String retrievedUsername = rs.getString("Username");
						String Usertype = rs.getString("UserType");
						String BuidingName = rs.getString("BuildingName");
						
						loggedInUser = new User(retrievedUsername, password, Usertype, BuidingName);
						Arrays.fill(pass, '\0');
						
						//Optional For mat when you choose to anther login
						//frame.setVisible(false);
						//new MainWindow().setVisible(true);
						
						
					}else {
						//Login failed
						JOptionPane.showMessageDialog(null, "Invalid username or password", "Login error", JOptionPane.ERROR_MESSAGE);
						
					}
				}catch (SQLException ex) {
					//myDatabase.logError("SQL error during login: " + ex);
					JOptionPane.showMessageDialog(null, "An error occured while trying to log in. Please try again later.", "Login Error", JOptionPane.ERROR_MESSAGE);
					
					
				}
				
				
				
				
			}
		});
		btnLogin.setBounds(31, 297, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText(null);
				txtPassword.setText(null);
				txtConfirm.setText(null);
			}
		});
		btnReset.setBounds(177, 297, 89, 23);
		frame.getContentPane().add(btnReset);
		
		btnSignUp = new JButton("Sign Up");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] password = {};
                char[] confirmedPassword = {};
                
                try {
                    String username = txtUsername.getText();
                    password = txtPassword.getPassword();
                    confirmedPassword = txtConfirm.getPassword();
                    
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
                    
                    int usernameAvailable = myDatabase.valueExists("USER", "Username", "'" + username + "'");
                    
                    if (usernameAvailable == 1) {
                        JOptionPane.showMessageDialog(null, "username not available", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (usernameAvailable == 2) {
                        JOptionPane.showMessageDialog(null, "SQL error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    /*
                     * Define the available type option between the customer and the employee
                     */
                    String [] user_types = {"customer", "employee", "admin"};
                    String selectedUserType =(String) JOptionPane.showInputDialog(null, "Select User Type", "Usertype", JOptionPane.QUESTION_MESSAGE, null, user_types, user_types[0]) ;
                    // if the user cancel the selection they will return from the method
                    if (selectedUserType == null) {
                    	return;
                    }
                    
                    
                    String BuildingName = JOptionPane.showInputDialog("Enter Building Name: ");
                    //Check if the buildingName is empty or not
                    if(BuildingName == null || BuildingName.trim().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "Building name is required", "Sign Up Error", JOptionPane.ERROR_MESSAGE );
                    }
                    
                    String[] state_types = {
                    	    "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", 
                    	    "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", 
                    	    "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", 
                    	    "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
                    	};
                    String state = (String) JOptionPane.showInputDialog(null, "Select State", "State", JOptionPane.QUESTION_MESSAGE, null, state_types, state_types[0]) ;
                    if(state == null|| state.trim().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "State is required", "Sign Up Error", JOptionPane.ERROR_MESSAGE );
                    }
                    
                    String city = JOptionPane.showInputDialog("Enter City: ");
                    if(city == null|| city.trim().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "City is required", "Sign Up Error", JOptionPane.ERROR_MESSAGE );
                    }
                    
                    String streetAddress = JOptionPane.showInputDialog("Enter streetAdress: ");
                    if(streetAddress == null|| streetAddress.trim().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "State is required", "Sign Up Error", JOptionPane.ERROR_MESSAGE );
                    }
                    
                    
                    
                    if(!myDatabase.buildingExists(BuildingName)) {
                    	int result = myDatabase.addBuilding(BuildingName, state, city, streetAddress);
                    	if (result == 0) {
                    		JOptionPane.showMessageDialog(null, "Building does not exist", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        	return;
                    	}
                    	
                    	
                    }
                    /**
                     * Display a dialog to prompt the user to select a user type
                     */
                    int addResult = myDatabase.addItem(
                        "USER",
                        new ArrayList<String>(Arrays.asList("Username", "Password", "Usertype","BuildingName")),
                        new ArrayList<Object>(
                            Arrays.asList(
                                username,
                                password,
                                selectedUserType.toLowerCase(), 
                                BuildingName
                            )
                        )
                    );
                    
                    if (addResult == 1) {
                        JOptionPane.showMessageDialog(null, "Java error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (addResult == 2) {
                        JOptionPane.showMessageDialog(null, "SQL error", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
            
                    JOptionPane.showMessageDialog(null, "user added", "Sign Up Succeeded", JOptionPane.INFORMATION_MESSAGE);
                    
                    User newUser = new User(username, new String(password),  selectedUserType.toLowerCase(), BuildingName );
                    

                    loggedInUser = newUser;
                
                } finally {
                    // do this so the password doesn't stay in memory
                    Arrays.fill(password, '\0');
                    Arrays.fill(confirmedPassword, '\0');
                }
            }
        });
        btnSignUp.setBounds(318, 297, 89, 23);
        frame.getContentPane().add(btnSignUp);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem, "Confirm if you want to exit", "Login Systems", JOptionPane.YES_NO_OPTION ) 
						==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
				
			}
		});
		btnExit.setBounds(467, 297, 89, 23);
		frame.getContentPane().add(btnExit);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(21, 277, 553, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(21, 75, 553, 2);
		frame.getContentPane().add(separator_1);
		
		JLabel lblSigningUpMsg = new JLabel("If signing up:");
		lblSigningUpMsg.setBounds(96, 200, 110, 14);
		frame.getContentPane().add(lblSigningUpMsg);
		
		lblConfirm = new JLabel("Confirm Password");
		lblConfirm.setBounds(96, 228, 128, 14);
		frame.getContentPane().add(lblConfirm);
		
		txtConfirm = new JPasswordField();
		txtConfirm.setBounds(215, 225, 254, 20);
		frame.getContentPane().add(txtConfirm);
		
	}
}