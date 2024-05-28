
package GroupProject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

/**
 * 
 * THis one will help to handle the various operations related to the user information, orders and building details
 */
public class InfoHandler {
    
    public final SQLDatabase myDatabase;
    
    // user values
    public final String username;
    public final String usertype;
    
    // product information
    private TreeMap<String, Double> products = new TreeMap<String, Double>();
    
    // order information
    private Order[] ordersInProgress = new Order[] {};
    
    // user information
    private User[] users = new User[] {};
    
    // this building values
    private String buildingPhone;
    private String state;
    private String city;
    private String streetAddr1;
    private String streetAddr2;
    
    // this user's order info
    private ArrayList<OrderLine> myWaitingOrder = new ArrayList<OrderLine>();
    private ArrayList<OrderLine> myCurrentOrder = new ArrayList<OrderLine>();
    private String myWaitingOrderNote = "";
    
    // this user's waiting order ID
    // -1 means no order currently in progress
    private int myOrderID = -1;
    
    // the order currently being viewed by the employee
    private Order currentlyViewedOrder;
    
    /**
     * Constructor for InfoHandler
     * @param myDatabase    the SQLDatabase instance
     * @param username      the username of the user
     * @param usertype      the type of the user (customer/employee)
     * @param buildingPhone the phone number of the building
     * @param state         the state where the building is located
     * @param city          the city where the building is located
     * @param streetAddr1   the first line of the street address
     * @param streetAddr2   the second line of the street address (if any)
     */
    public InfoHandler(
        SQLDatabase myDatabase,
        String username,
        String usertype,
        String buildingPhone,
        String state,
        String city,
        String streetAddr1,
        String streetAddr2) {
        this.myDatabase = myDatabase;
        this.username = username;
        this.usertype = usertype;
        this.buildingPhone = buildingPhone;
        this.state = state;
        this.city = city;
        this.streetAddr1 = streetAddr1;
        this.streetAddr2 = streetAddr2;
    }
    /**
     *Paginates an array of objects.
     * 
     * @param pageSource 	 the source array to paginate
     * @param pageNumber  	the page number to retrieve (1-based)
     * @param itemsPerPage 	the number of items per page
     * @param page       	 the output array to hold the paginated items
     */
    
    private void makePage(Object[] pageSource, int pageNumber, int itemsPerPage, Object[] page) {
        if (pageSource == null || pageSource.length == 0) {
            return;
        }
        
        pageNumber -= 1;
        for (
            int i = pageNumber * itemsPerPage;
            i < ((pageNumber * itemsPerPage) + itemsPerPage) && i < pageSource.length;
            i++) {
            page[i - (pageNumber * itemsPerPage)] = pageSource[i];
        }
    }
    
    /**
     * Gets the product to the entry set
     * @return a set of products entries
     */
    public Set<Map.Entry<String, Double>> getProductEntrySet() {
        return products.entrySet();
    }
    /**
     * Gets a paginated list of order lines.
     * 
     * @param orderLines   the array of order lines to paginate
     * @param pageNumber   the page number to retrieve (1-based)
     * @param itemsPerPage the number of items per page
     * @return an array of order lines for the specified page
     */
    
    public OrderLine[] getOrderLinePage(OrderLine[] orderLines, int pageNumber, int itemsPerPage) {
        OrderLine[] page = new OrderLine[itemsPerPage];
        makePage(orderLines, pageNumber, itemsPerPage, page);
        return page;
    }
    /**
     * Gets a paginated list of orders.
     * 
     * @param pageNumber   the page number to retrieve (1-based)
     * @param itemsPerPage the number of items per page
     * @return an array of orders for the specified page
     */
    
    public Order[] getOrderPage(int pageNumber, int itemsPerPage) {
        Order[] page = new Order[itemsPerPage];
        makePage(ordersInProgress, pageNumber, itemsPerPage, page);
        return page;
    }
    /**
     * Gets a paginated list of users.
     * 
     * @param pageNumber   the page number to retrieve (1-based)
     * @param itemsPerPage the number of items per page
     * @return an array of users for the specified page
     */
    
    public User[] getUserPage(int pageNumber, int itemsPerPage) {
        User[] page = new User[itemsPerPage];
        makePage(users, pageNumber, itemsPerPage, page);
        return page;
    }
    
    /**
     * Gets the current user's order.
     * 
     * @return an ArrayList of current order lines
     */
    public ArrayList<OrderLine> getMyCurrentOrder() {
        return myCurrentOrder;
    }
    
    /**
     * Gets the current user's waiting order.
     * 
     * @return an ArrayList of waiting order lines
     */
    public ArrayList<OrderLine> getMyWaitingOrder() {
        return myWaitingOrder;
    }
    
    /**
     * Gets the orders in progress.
     * 
     * @return an array of orders in progress
     */
    public Order[] getOrdersInProgress() {
        return ordersInProgress;
    }
    
    /**
     * Gets the list of users.
     * 
     * @return an array of users
     */
    public User[] getUsers() {
        return users;
    }
    
    /**
     * Gets the current user's order ID.
     * 
     * @return the current order ID
     */
    public int getMyOrderID() {
        return myOrderID;
    }
    /**
     * Gets the current user's waiting order note.
     * 
     * @return the waiting order note
     */
    
    public String getMyWaitingOrderNote() {
        return myWaitingOrderNote;
    }
    
    /**
     * Gets the price of a product.
     * 
     * @param productName the name of the product
     * @return the price of the product
     */
    public double getProductValue(String productName) {
        return products.get(productName);
    }
    /**
     * Calculates the total charge of an order.
     * 
     * @param chargeSource the source of the order lines to calculate the total charge from
     * @return the total charge
     */
    
    public double getTotalCharge(ArrayList<OrderLine> chargeSource) {
        if (myCurrentOrder == null) {
            return 0.0;
        }
        
        double totalCharge = 0.0;
        
        for (OrderLine orderLine : chargeSource) {
            totalCharge += products.get(orderLine.getProductName()) * orderLine.getQuantity();
        }
        
        return totalCharge;
    }
    
    /**
     * Gets the order currently being viewed by the employee.
     * 
     * @return The currently viewed order
     */
    public Order getCurrentlyViewedOrder() {
        return currentlyViewedOrder;
    }
    
    /**
     * Gets the building information.
     * 
     * @return an array of strings containing the building information
     */
    public String[] getBuildingInfo() {
        return new String[] {buildingPhone, state, city, streetAddr1, streetAddr2};
    }
    
    /**
     * Retrieves all building information from the database.
     * 
     * @param myDatabase the SQLDatabase instance
     * @return a TreeMap of building names to their information arrays
     */
    
    public static TreeMap<String, String[]> getAllBuildingInfo(SQLDatabase myDatabase) {
        try {
            
            ResultSet rs = myDatabase.getDatabaseInfo("building", null, "buildingname");
            
            if (rs == null || !rs.next()) {
                return null;
            }
            
            TreeMap<String, String[]> buildingInfo = new TreeMap<String, String[]>();
            
            do {
                
                buildingInfo.put(
                    rs.getString("buildingname"),
                    new String[] {rs.getString("state"), rs.getString("city"), rs.getString("streetaddr1"), rs.getString("streetaddr2")}
                );
                
            } while (rs.next());
            
            return buildingInfo;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't get building info: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Adds the current order for the user to the database.
     *
     * @param note The note to add to the order.
     * @return The order ID if successful, -1 if there is an error.
     */
    
    public int addUserCurrentOrder(String note) {
        try {
            
            String sqlString = "INSERT INTO `order` (BuildingName, CustomerUsername, Completed, OrderDate, Note) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, buildingPhone);
            pst.setString(2, username);
            pst.setBoolean(3, false);
            pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pst.setString(5, note);
            
            pst.executeUpdate();
            
            // getting the orderID auto incremented primary key
            ResultSet aiKeyResultSet = pst.getGeneratedKeys();
            aiKeyResultSet.next();
            
            myOrderID = aiKeyResultSet.getInt(1);
            
            for (int i = 0; i < myCurrentOrder.size(); i++) {
                sqlString = "INSERT INTO orderline (orderID, OrderLineNumber, ProductName, Quantity) VALUES (?, ?, ?, ?);";
                pst = myDatabase.getCon().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
                
                OrderLine orderLine = myCurrentOrder.get(i);
                
                pst.setInt(1, myOrderID);
                pst.setInt(2, i + 1);
                pst.setString(3, orderLine.getProductName());
                pst.setInt(4, orderLine.getQuantity());
                
                pst.executeUpdate();
                
            }
            
            myWaitingOrder = new ArrayList<OrderLine>(myCurrentOrder);
            myCurrentOrder.clear();
            myWaitingOrderNote = note;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't insert order: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            
            return -1;
        }
        
        return myOrderID;
    }
    
    /**
     * Removes an order from the database.
     *
     * @param orderID The ID of the order to be removed.
     */
    public void removeOrder(int orderID) {
        try {
            
            String sqlString = "DELETE FROM `order` WHERE orderID = " + orderID + ";";
            PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString);
            
            pst.executeUpdate();
            
            sqlString = "DELETE FROM orderline WHERE orderID = " + orderID + ";";
            pst = myDatabase.getCon().prepareStatement(sqlString);
            
            pst.executeUpdate();
            
            orderID = -1;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't delete order: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * gets an order's completion status
     * @param orderID The ID of the order being checked
     * @return -1 if error, 0 if not complete, 1 if complete
     */
    public int getOrderStatus(int orderID) {
        int result = -1;
        
        try {
            
            ResultSet rs = myDatabase.getDatabaseInfo("order", "orderid = " + orderID, null);
            
            if (rs == null || !rs.next()) {
                return -1;
            }
            
            result = rs.getInt("completed");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't check if order was complete: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        
        return result;
    }
    
    /**
     * Marks an order as completed in the database.
     *
     * @param orderID The ID of the order to be marked as completed.
     */
    public void markOrderCompleted(int orderID) {
        try {
            
            String sqlString = "UPDATE `order` SET Completed = TRUE WHERE OrderID = " + orderID + ";";
            PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString);
            
            pst.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't mark order as completed: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refreshes the product map with the latest product information from the database.
     */
    public void refreshProductMap() {
        try {
            
            ResultSet rs = myDatabase.getDatabaseInfo("product", null, "name");
            
            if (rs == null || !rs.next()) { // don't clear the map unless the ResultSet works
                return;
            }
            
            products.clear();
            
            do {
                
                products.put(rs.getString("name"), rs.getDouble("price"));
                
            } while (rs.next());
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't refresh product map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refreshes the list of orders in progress for the current building from the database.
     */
    public void refreshOrdersInProgress() {
        try {
            
            ResultSet rsOrders = myDatabase.getDatabaseInfo("order", "buildingname = '" + buildingPhone + "' AND completed = FALSE", "customerusername");
            
            if (rsOrders == null || !rsOrders.next()) {
                ordersInProgress = new Order[] {};
                return;
            }
            
            ArrayList<Order> refreshedOrdersInProgress = new ArrayList<Order>();
            
            do {
                
                int orderID = rsOrders.getInt("orderID");
                String customerUsername = rsOrders.getString("customerusername");
                String note = rsOrders.getString("note");
                
                ResultSet rsOrderLines = myDatabase.getDatabaseInfo("orderline", "orderID = " + orderID, "orderlinenumber");
                
                if (rsOrderLines == null || !rsOrderLines.next()) {
                    continue;
                }
                
                ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
                
                do {
                    
                    String productName = rsOrderLines.getString("productname");
                    int quantity = rsOrderLines.getInt("quantity");
                    
                    orderLines.add(new OrderLine(productName, quantity));
                    
                } while (rsOrderLines.next());
                
                refreshedOrdersInProgress.add(new Order(orderID, orderLines, customerUsername, note));
                
                if (customerUsername.equals(this.username)) {
                    myWaitingOrder = orderLines;
                    myOrderID = orderID;
                    myWaitingOrderNote = note;
                }
                
            } while (rsOrders.next());
            
            ordersInProgress = refreshedOrdersInProgress.toArray(Order[]::new);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't refresh orders map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refreshes the user information for the current building from the database.
     */
    public void refreshUserInfo() {
        try {
            
            ResultSet rs = myDatabase.getDatabaseInfo("user", "buildingname = '" + buildingPhone + "' AND usertype != 'admin'", "username");
            
            if (rs == null || !rs.next()) {
                users = new User[] {};
                return;
            }
            
            ArrayList<User> refreshedUserInfo = new ArrayList<User>();
            
            do {
                
                refreshedUserInfo.add(new User(rs.getString("username"), rs.getString("password"), rs.getString("usertype")));
                
            } while (rs.next());
            
            users = refreshedUserInfo.toArray(User[]::new);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't refresh users map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refreshes the status of the current user's order from the database.
     */
    public void refreshOrderStatus() {
        try {
            
            ResultSet rs = myDatabase.getDatabaseInfo("order", "customerusername = '" + username + "' AND completed = FALSE", null);
            
            if (rs == null) {
                return;
            } else if (!rs.next()) {
                myOrderID = -1;
                myWaitingOrderNote = "";
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't refresh order status: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Retrieves the user type of a specific user.
     *
     * @param username The username of the user whose type is being retrieved.
     * @return The user type of the specified user, or null if there is an error.
     */
    public String getUsertype(String username) {
        try {
            
            ResultSet rs = myDatabase.getDatabaseInfo("user", "username = '" + username + "'", null);
            
            if (rs == null || !rs.next()) {
                return null;
            }
            
            return rs.getString("usertype");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't get usertype: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    /**
     * Sets the order currently being viewed by the employee.
     * 
     * @param orderBeingViewed The order to be viewed
     */
    public void setCurrentlyViewedOrder(Order orderBeingViewed) {
        this.currentlyViewedOrder = orderBeingViewed;
    }
    
    /**
     * Updates the information of a specific user in the database.
     *
     * @param username The current username of the user.
     * @param newUsername The new username of the user.
     * @param newPassword The new password of the user.
     * @param newUsertype The new user type of the user (must be either 'customer' or 'employee').
     */
    public void setUserInfo(String username, String newUsername, String newPassword, String newUsertype) {
        if (!newUsertype.equals("customer") && !newUsertype.equals("employee")) {
            System.out.println(newUsertype + ": " + newUsertype.length());
            JOptionPane.showMessageDialog(null, "invalid usertype: " + newUsertype, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            
            String sqlString = "UPDATE user SET usertype = ?, password = ?, username = ? WHERE username = ?;";
            PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString);
            
            pst.setString(1, newUsertype);
            pst.setString(2, newPassword);
            pst.setString(3, newUsername);
            pst.setString(4, username);
            
            pst.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't set usertype: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Updates the information of a building in the database.
     *
     * @param buildingPhone The new building phone number.
     * @param state The new state of the building.
     * @param city The new city of the building.
     * @param addr1 The new first line of the building's address.
     * @param addr2 The new second line of the building's address.
     */
    public void setBuildingInfo(String buildingPhone, String state, String city, String addr1, String addr2) {
        try {
            
            String sqlString = (
                "UPDATE BUILDING "
                + "SET buildingname = ?, state = ?, city = ?, streetaddr1 = ?, streetaddr2 = ? "
                + "WHERE buildingname = ?"
            );
            PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString);
            
            pst.setString(1, buildingPhone);
            pst.setString(2, state);
            pst.setString(3, city);
            pst.setString(4, addr1);
            pst.setString(5, addr2);
            pst.setString(6, this.buildingPhone);
            
            pst.executeUpdate();
            
            this.buildingPhone = buildingPhone;
            this.state = state;
            this.city = city;
            this.streetAddr1 = addr1;
            this.streetAddr2 = addr2;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't update building: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
