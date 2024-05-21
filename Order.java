package GroupProject;

import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

public class Order {

	private int orderID;
	private String buildingName;
	private ArrayList<OrderLine> items;
	private String customerUserName;
	private boolean completed;
	
	/**
	 * Constructor for creating an order from data in the SQL Database
	 * @param orderID
	 * @param buildingName
	 */
	public Order(int orderID, String buildingName, String customerUserName, boolean completed, SQLDatabase database_) {
		this.orderID = orderID;
		this.buildingName = buildingName;
		this.customerUserName = customerUserName;
		this.completed = completed;
		
		// Creates empty list to populate with OrderLines
		items = new ArrayList<OrderLine>();
		
		// PSEUDO - FROM SQL DATABASE
		// Gets all OrderLines associated with the orderID
	}
	
	/**
	 * Constructor for creating a new order from scratch
	 */
	public Order(String buildingName, String customerUserName, SQLDatabase database_) {
		this.buildingName = buildingName;
		this.customerUserName = customerUserName;
		this.completed = false;
		
		// Creates empty list to populate with OrderLines
		items = new ArrayList<OrderLine>();
		
		// Creates a new orderID using a random number, tries until a unique id is created
		Random r = new Random();
		do {
			this.orderID = r.nextInt(1000000);
		} while (database_.valueExists("order","OrderID",Integer.toString(this.orderID)) != 0);
		
		// PSEUDO - FROM SQL DATABASE
		// Adds new order to the SQL Database
		
	}

	/**
	 * Adds an item to the order
	 * @param product
	 */
	public void addItem(Item product) {
		// Adds a new item to the order line
		items.add(new OrderLine(this.orderID,product,1));
	}
	
	/**
	 * Removes an item from the order
	 * @param item
	 */
	public void removeItem(OrderLine item) {
		items.remove(item);
	}
	
	//
	// GETTER / SETTER METHODS
	//
	/**
	 * Getter method for items
	 * @return
	 */
	public ArrayList<OrderLine> getItems() {
		return items;
	}
	
	/**
	 * Setter method for items
	 * @param items
	 */
	public void setItems(ArrayList<OrderLine> items) {
		this.items = items;
	}
	/**
	 * Setter method for completed
	 * @param completed
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * Getter for completed
	 * @return completed
	 */
	public boolean isCompleted() {
		return completed;
	}
	/**
	 * Getter for customer user name
	 * @return customerUserName
	 */
	public String getCustomerUserName() {
		return customerUserName;
	}
	/**
	 * Getter for building name
	 * @return buildingName
	 */
	public String getBuildingName() {
		return buildingName;
	}
	/**
	 * Getter for orderID
	 * @return orderID
	 */
	public int getOrderID() {
		return orderID;
	}
	
	
}
