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
		RestaurantGUI rgui = new RestaurantGUI();
		
		// Pass error code
		return 0;
	}
}
