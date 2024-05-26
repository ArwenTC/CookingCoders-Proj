
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
    private String buildingName;
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
    
    
    public InfoHandler(
        SQLDatabase myDatabase,
        String username,
        String usertype,
        String buildingName,
        String state,
        String city,
        String streetAddr1,
        String streetAddr2) {
        this.myDatabase = myDatabase;
        this.username = username;
        this.usertype = usertype;
        this.buildingName = buildingName;
        this.state = state;
        this.city = city;
        this.streetAddr1 = streetAddr1;
        this.streetAddr2 = streetAddr2;
    }
    
    
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
    
    
    public Set<Map.Entry<String, Double>> getProductEntrySet() {
        return products.entrySet();
    }
    
    
    public OrderLine[] getOrderLinePage(OrderLine[] orderLines, int pageNumber, int itemsPerPage) {
        OrderLine[] page = new OrderLine[itemsPerPage];
        makePage(orderLines, pageNumber, itemsPerPage, page);
        return page;
    }
    
    
    public Order[] getOrderPage(int pageNumber, int itemsPerPage) {
        Order[] page = new Order[itemsPerPage];
        makePage(ordersInProgress, pageNumber, itemsPerPage, page);
        return page;
    }
    
    
    public User[] getUserPage(int pageNumber, int itemsPerPage) {
        User[] page = new User[itemsPerPage];
        makePage(users, pageNumber, itemsPerPage, page);
        return page;
    }
    
    
    public ArrayList<OrderLine> getMyCurrentOrder() {
        return myCurrentOrder;
    }
    
    
    public ArrayList<OrderLine> getMyWaitingOrder() {
        return myWaitingOrder;
    }
    
    
    public Order[] getOrdersInProgress() {
        return ordersInProgress;
    }
    
    
    public int getMyOrderID() {
        return myOrderID;
    }
    
    
    public String getMyWaitingOrderNote() {
        return myWaitingOrderNote;
    }
    
    
    public double getProductValue(String productName) {
        return products.get(productName);
    }
    
    
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
    
    
    public String[] getBuildingInfo() {
    	return new String[] {buildingName, state, city, streetAddr1, streetAddr2};
    }
    
    
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
    
    
    public void setThisBuildingInfo(String newBuildingName) {
    	try {
    		
    		ResultSet rs = myDatabase.getDatabaseInfo("building", "buildingname = '" + newBuildingName + "'", null);
    		
    		if (rs == null || !rs.next()) {
				return;
			}
    		
    		this.buildingName = newBuildingName;
            this.state = rs.getString("state");
            this.city = rs.getString("city");
            this.streetAddr1 = rs.getString("streetaddr1");
            this.streetAddr2 = rs.getString("streetaddr2");
            
            String sqlString = "UPDATE user SET buildingname = ? WHERE username = ?;";
    		PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString);
    		
    		pst.setString(1, newBuildingName);
    		pst.setString(2, this.username);
    		
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Couldn't refresh buildings map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    
    public int addUserCurrentOrder(String note) {
        try {
            
            String sqlString = "INSERT INTO `order` (BuildingName, CustomerUsername, Completed, OrderDate, Note) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, buildingName);
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
            
            if (rs == null) {
                return -1;
            }
            
            rs.next();
            result = rs.getInt("completed");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't check if order was complete: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        
        return result;
    }
    
    
    public void markOrderCompleted(int orderID) {
        try {
            
            String sqlString = "UPDATE `order` SET Completed = TRUE WHERE OrderID = " + orderID + ";";
            PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString);
            
            pst.executeUpdate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't mark order as completed: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
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
    
    
    public void refreshOrdersInProgress() {
    	try {
			
			ResultSet rsOrders = myDatabase.getDatabaseInfo("order", "buildingname = '" + buildingName + "' AND completed = FALSE", "customerusername");
			
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
				
				if (rsOrderLines == null) {
					return;
				}
				
				ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
				
				while (rsOrderLines.next()) {
					String productName = rsOrderLines.getString("productname");
					int quantity = rsOrderLines.getInt("quantity");
					
					orderLines.add(new OrderLine(productName, quantity));
				}
				
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
    
    
    public void refreshUserInfo() {
    	try {
    		
    		ResultSet rs = myDatabase.getDatabaseInfo("user", "buildingname = '" + buildingName + "'", "username");
			
			if (rs == null || !rs.next()) {
			    users = new User[] {};
				return;
			}
			
			ArrayList<User> refreshedUserInfo = new ArrayList<User>();
			
			do {
		    	
			    refreshedUserInfo.add(new User(rs.getString("username"), rs.getString("usertype")));
		        
		    } while (rs.next());
			
			users = refreshedUserInfo.toArray(User[]::new);
    		
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Couldn't refresh users map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    
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
    
    
    public void setUsertype(String username, String newUsertype) {
        if (!newUsertype.equals("customer") || !newUsertype.equals("employee")) {
            JOptionPane.showMessageDialog(null, "invalid usertype: " + newUsertype, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        try {
        
        String sqlString = "UPDATE user SET usertype = ? WHERE username = ?;";
        PreparedStatement pst = myDatabase.getCon().prepareStatement(sqlString);
        
        pst.setString(1, newUsertype);
        pst.setString(2, username);
        
        pst.executeUpdate();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't set usertype: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
