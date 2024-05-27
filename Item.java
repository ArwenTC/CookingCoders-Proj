package GroupProject;

public class Item {
	
	private String name;
	private double price;
	
	/**
	 * Constructor
	 * @param name
	 * @param price
	 */
	public Item(String name, double price) {
		this.name = name;
		this.price = price;

		// PSEUDO - FROM SQL DATABASE
		// Adds item to the database
		
	}
	
	public Item(String name) {
		this.name = name;

		// PSEUDO - FROM SQL DATABASE
		// Gets item price from the database
		
	}
	
	//
	// GETTER METHODS
	//
	
	/**
	 * Returns the item name
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the item price
	 * @return
	 */
	public double getPrice() {
		return this.price;
	}
}
