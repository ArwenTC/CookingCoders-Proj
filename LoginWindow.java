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
    private String building;
    
    // Returns a view of the program
    private int programView;
    
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

	/**
	 * Create the application.
	 */
	public LoginWindow(SQLDatabase myDatabase_, String buildingName) {
	    myDatabase = myDatabase_;
	    this.building = buildingName;
	    
		initialize();
	}
	
	public void toggleVisibility() {
	    frame.setVisible(!frame.isVisible());
	}
	
	
	public boolean userIsLoggedIn() {
	    return loggedInUser != null;
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
                    String [] userTypes = {"customer", "employee", "admin"};
                    String selectedUserType =(String) JOptionPane.showInputDialog(null, "Select User Type", "User Type", JOptionPane.QUESTION_MESSAGE, null, userTypes, userTypes[0]) ;
                    
                    // Sets customer view based on the selection by the user
                    if (selectedUserType.equals("customer")) {
                    	programView = 0;
                    } else if (selectedUserType.equals("employee")) { 
                    	programView = 1;
                    } else if (selectedUserType.equals("admin")) {
                    	programView = 2;
                    } else {
                    	// Returns the function in case the user exits the prompt
                    	return;
                    }

                	System.out.println(selectedUserType + ": " + programView);
                    
                    /**
                     * Display a dialog to prompt the user to select a user type
                     */
                    int addResult = myDatabase.addItem(
                        "USER",
                        new ArrayList<String>(Arrays.asList("Username", "Password", "Usertype", "BuildingName")),
                        new ArrayList<Object>(
                            Arrays.asList(
                                username,
                                password,
                                selectedUserType.toLowerCase(),
                                building
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
                    
                    User newUser = new User();
                    newUser.username = username;
                    newUser.usertype = selectedUserType.toLowerCase();

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
	
	/**
	 * Getter for program view
	 * @return
	 */
	public int getProgramView() {
		return programView;
	}
	
}
