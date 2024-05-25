package GroupProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import java.sql.*;

public class Order {

	private int orderID;
	private String buildingName;
	private ArrayList<OrderLine> items;
	private String customerUsername;
	private Boolean completed;
	private SQLDatabase database_;
	
	/**
	 * Constructor for creating an order from data in the SQL Database
	 * @param orderID
	 * @param buildingName
	 */
	public Order(int orderID, SQLDatabase database_) {
		this.orderID = orderID;
		this.buildingName = "";
		this.customerUsername = "";
		this.completed = false;
		this.database_ = database_;
		
		// Creates empty list to populate with OrderLines
		items = new ArrayList<OrderLine>();
		
		// PSEUDO - FROM SQL DATABASE
		// Gets all OrderLines associated with the orderID
		ResultSet results = database_.executeQuery("SELECT * FROM `order` WHERE OrderID = " + this.orderID);
		
		// Gets the next item in the result set.
		try {
			results.next();
			// Sets the values of self from the data in the result set
			this.buildingName = results.getString(2);
			this.customerUsername = results.getString(3);
			this.completed = results.getBoolean(4);
		} catch (Exception e) {
			System.out.println("Unable to initialize order");
		}
	}
	
	/**
	 * Constructor for creating a new order from scratch.
	 * Adds order to the database
	 * @param buildingName
	 * @param customerUserName
	 * @param database_
	 */
	public Order(String buildingName, String customerUsername, SQLDatabase database_) {
		this.buildingName = buildingName;
		this.customerUsername = customerUsername;
		this.completed = false;
		this.database_ = database_;
		
		// Creates empty list to populate with OrderLines
		items = new ArrayList<OrderLine>();
		
		// Creates a new orderID using a random number, tries until a unique id is created
		Random r = new Random();
		do {
			this.orderID = r.nextInt(1000000);
		} while (database_.valueExists("order","OrderID",Integer.toString(this.orderID)) != 0);
		
		// PSEUDO - FROM SQL DATABASE
		// Adds new order to the SQL Database
		database_.addItem(
            	// Table Name
                "ORDER",
                // Columns
                new ArrayList<String>(
                	Arrays.asList( "OrderID", "BuildingName", "CustomerUsername", "Completed" )),
                // Values
                new ArrayList<Object>(
                    Arrays.asList( this.orderID, this.buildingName , this.customerUsername, this.completed)
                )
		);
	}

	/**
	 * Adds an item to the order
	 * @param product
	 */
	public void addItem(String productName) {
		// Adds a new item to the order line
		items.add(new OrderLine(productName, 1));
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
	public String getCustomerUsername() {
		return customerUsername;
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
