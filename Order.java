package GroupProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import java.sql.*;

public class Order{

	private int orderID;
	private ArrayList<OrderLine> items;
	private String customerUsername;
	private String note;
	
	
	public Order(int orderID, ArrayList<OrderLine> items, String customerUsername, String note) {
	    this.orderID = orderID;
	    this.items = items;
	    this.customerUsername = customerUsername;
	    this.note = note;
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
	 * Getter for customer user name
	 * @return customerUserName
	 */
	public String getCustomerUsername() {
		return customerUsername;
	}
	/**
	 * Getter for orderID
	 * @return orderID
	 */
	public int getOrderID() {
		return orderID;
	}
	
	
	public String getNote() {
	    return note;
	}
	
}
