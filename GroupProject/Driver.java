package GroupProject;

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
	    SQLDatabase myDatabase = new SQLDatabase("jdbc:mysql://localhost:3306/mydb", "root", "password");
	    
		RestaurantGUI rgui = new RestaurantGUI(myDatabase);
		
		// Pass error code
		return 0;
	}
}
