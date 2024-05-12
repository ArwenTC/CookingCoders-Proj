
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

class User {
    String username;
    String usertype;
    String buildingName;
}

public class LoginWindow {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnReset;
	private JButton btnCancel;
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
				    LoginWindow window = new LoginWindow(new SQLDatabase("jdbc:mysql://localhost:3306/cs380restaurant", "root", "placeholder"));
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/
	
	public void setVisible(boolean makeVisible) {
	    frame.setVisible(makeVisible);
	}
	
	
	public boolean isVisible() {
	    return frame.isVisible();
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
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    
                    int addResult = myDatabase.addItem(
                        "USER",
                        new ArrayList<String>(Arrays.asList("Username", "Password", "Usertype")),
                        new ArrayList<Object>(
                            Arrays.asList(
                                username,
                                password,
                                "customer"
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
                    newUser.usertype = "customer";
                    newUser.buildingName = "";
                    
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
		
		btnCancel = new JButton("Exit");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnCancel.setBounds(467, 297, 89, 23);
		frame.getContentPane().add(btnCancel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(21, 277, 553, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 55, 553, 2);
		frame.getContentPane().add(separator_1);
		
		JLabel lblSigningUpMsg = new JLabel("If signing up:");
		lblSigningUpMsg.setBounds(96, 200, 71, 14);
		frame.getContentPane().add(lblSigningUpMsg);
		
		lblConfirm = new JLabel("Confirm Password");
		lblConfirm.setBounds(96, 228, 110, 14);
		frame.getContentPane().add(lblConfirm);
		
		txtConfirm = new JPasswordField();
		txtConfirm.setBounds(215, 225, 254, 20);
		frame.getContentPane().add(txtConfirm);
		
	}
}
