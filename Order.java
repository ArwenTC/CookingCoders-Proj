package groupproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import java.sql.*;
/**
 * 
 * This class has an order containing multiple order lines, associated with a customer
 *
 */
public class Order{

    private int orderID;
    private ArrayList<OrderLine> items;
    private String customerUsername;
    private String note;
    
    /**
        Constructs an Order with the specified details.
     *
     * @param orderID The ID of the order.
     * @param items A list of order lines associated with the order.
     * @param customerUsername The username of the customer placing the order.
     * @param note Additional notes for the order.
     */
    public Order(int orderID, ArrayList<OrderLine> items, String customerUsername, String note) {
        this.orderID = orderID;
        this.items = items;
        this.customerUsername = customerUsername;
        this.note = note;
    }
    
    
    /**
     * Adds an item to the order
     * @param productName the name of the product to be added
     */
    public void addItem(String productName) {
        // Adds a new item to the order line
        items.add(new OrderLine(productName, 1));
    }
    
    /**
     * Removes an item from the order
     * @param item the order line item to be removed
     */
    public void removeItem(OrderLine item) {
        items.remove(item);
    }
    
    //
    // GETTER / SETTER METHODS
    //
    
     /**
     * Sets the list of order line items in the order.
     *
     * @param items A list of order line items to be set.
     */
    public void setItems(ArrayList<OrderLine> items) {
        this.items = items;
    }
    
    
    /**
     * Sets the order ID
     * 
     * @param orderID The new ID
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    
    /**
     * 
     * Returns the list of order line items in the order
     * 
     * @return A list of order line items 
     */
    public ArrayList<OrderLine> getItems() {
        return items;
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
    
    /**
     * Returns any additional notes associated with the order.
     *
     * @return The order note.
     */
    public String getNote() {
        return note;
    }
    
}