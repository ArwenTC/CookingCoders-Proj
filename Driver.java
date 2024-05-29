

package groupproject;


import javax.swing.JOptionPane;

public class Driver {
    
    /**
     * Driver function
     * @param args
     */
    public static void main(String[] args) {
        // Non-static context run
        new Driver().Run();
    }
    
    /**
     * Non-static driver method
     * @return pass/fail 0 = pass, >0 = error code
     */
    public int Run() {
        
        // Runs the gui
        SQLDatabase myDatabase = new SQLDatabase("jdbc:mysql://localhost:3306/cs380restaurant", "root", "Kl51abe7!-4567");
        
        
        if (myDatabase.getCon() != null) {
            new RestaurantGUI(myDatabase);
        } else {
            JOptionPane.showMessageDialog(null, "Couldn't connect to database", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
        
        // Pass error code
        return 0;
    }
}