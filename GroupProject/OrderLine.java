package GroupProject;

public class OrderLine {
	
	// Order item values
	private int orderID;
	private Item product;
	private int quantity;
	
	/**
	 * Constructor
	 * @param orderID
	 * @param product
	 * @param quantity
	 */
	public OrderLine(int orderID, Item product, int quantity) { 
		this.orderID = orderID;
		this.product = product;
		this.quantity = quantity;

		// PSEUDO - FROM SQL DATABASE
		// Adds new orderline to the SQL database
	}
	
	/**
	 * Constructor given an SQL query
	 */
	public OrderLine() {

		// PSEUDO - FROM SQL DATABASE
		// Collects data for this.fields from SQL database
		
	}
	
	
	//
	// GETTER METHODS
	//
	/**
	 * Getter for order id
	 * @return orderID
	 */
	public int getOrderID() {
		return this.orderID;
	}
	
	/**
	 * Getter for product
	 * @return product
	 */
	public Item product() {
		return this.product;
	}
	
	/**
	 * Getter for quantity
	 * @return quantity
	 */
	public int quantity() {
		return this.quantity;
	}
	
	
}