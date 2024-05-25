package GroupProject;

public class OrderLine {
	
	// Order item values
	private Integer orderID;
	private String productName;
	private int quantity;
	
	/**
	 * Constructor
	 * @param orderID The order's ID, or null if no order ID has been assigned yet
	 * @param product
	 * @param quantity
	 */
	public OrderLine(Integer orderID, String productName, int quantity) { 
		this.orderID = orderID;
		this.productName = productName;
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
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * Getter for quantity
	 * @return quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	
	/**
	 * Setter for quantity
	 * @param newQuantity
	 */
	public void setQuantity(int newQuantity) {
	    this.quantity = newQuantity;
	}
	
}
