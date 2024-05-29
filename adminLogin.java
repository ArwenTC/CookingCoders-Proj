package groupproject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminLogin {

    private JFrame frame;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnReset;
    private JButton btnExit;
    private JFrame frmLoginSystem;
    private JButton btnSignUp;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    adminLogin window = new adminLogin();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public adminLogin() {
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
        lblPassword.setBounds(96, 193, 71, 14);
        frame.getContentPane().add(lblPassword);
        
        txtUsername = new JTextField();
        txtUsername.setBounds(177, 120, 254, 20);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(177, 190, 254, 20);
        frame.getContentPane().add(txtPassword);
        
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = txtPassword.getText();
                String username = txtUsername.getText();
                
                if (password.contains("king") && username.contains("one")) {
                    txtPassword.setText(null);
                    txtUsername.setText(null);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid Login Details","Login Error", JOptionPane.ERROR_MESSAGE);
                    txtPassword.setText(null);
                    txtUsername.setText(null);
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
            }
        });
        btnReset.setBounds(177, 297, 89, 23);
        frame.getContentPane().add(btnReset);
        
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
        
        btnSignUp = new JButton("Sign Up");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnSignUp.setBounds(318, 297, 89, 23);
        frame.getContentPane().add(btnSignUp);
    }
}
