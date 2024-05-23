
package GroupProject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
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
    private TreeMap<Integer, ArrayList<OrderLine>> ordersInProgress = new TreeMap<Integer, ArrayList<OrderLine>>();
    
    // user information
    // TreeMap<userName, String[password, userType]>
    private TreeMap<String, String[]> userInfo = new TreeMap<String, String[]>();
    
    
    // this building values
    private String buildingName;
    private String state;
    private String city;
    private String streetAddr1;
    private String streetAddr2;
    
    
    // current order
    private int userOrderID = -1; // -1 means the user has no pending order
    
    
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
    
    
    public Set<Map.Entry<String, Double>> getProductMapView() {
    	return products.entrySet();
    }
    
    
    public Set<Map.Entry<Integer, ArrayList<OrderLine>>> getOrdersInProgressMapView() {
    	return ordersInProgress.entrySet();
    }
    
    
    public Set<Map.Entry<String, String[]>> getUserMapView() {
    	return userInfo.entrySet();
    }
    
    
    public String[] getBuildingInfo() {
    	return new String[] {buildingName, state, city, streetAddr1, streetAddr2};
    }
    
    
    public static TreeMap<String, String[]> getBuildingInfoMap(SQLDatabase myDatabase) {
    	try {
    		
    		ResultSet rs = myDatabase.getDatabaseInfo("building", null);
    		
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
    		JOptionPane.showMessageDialog(null, "Couldn't refresh buildings map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
    	}
    	
    	return null;
    }
    
    
    public void setThisBuildingInfo(String newBuildingName) {
    	try {
    		
    		ResultSet rs = myDatabase.getDatabaseInfo("building", "buildingname = '" + newBuildingName + "'");
    		
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
    
    
    public int addUserOrder(ArrayList<OrderLine> orderLines, String note) {
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
            userOrderID = aiKeyResultSet.getInt(1);
            
            for (int i = 0; i < orderLines.size(); i++) {
                sqlString = "INSERT INTO orderline (orderID, OrderLineNumber, ProductName, Quantity) VALUES (?, ?, ?, ?);";
                pst = myDatabase.getCon().prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
                
                OrderLine orderLine = orderLines.get(i);
                
                pst.setInt(1, userOrderID);
                pst.setInt(2, i + 1);
                pst.setString(3, orderLine.getProductName());
                pst.setInt(4, orderLine.getQuantity());
                
                pst.executeUpdate();
                
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't insert order: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            
            return -1;
        }
        
        return userOrderID;
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
            
            ResultSet rs = myDatabase.getDatabaseInfo("order", "orderid = " + orderID);
            
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
    
    
    public void markOrderAsCompleted(int orderID) {
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
    		
			ResultSet rs = myDatabase.getDatabaseInfo("product", null);
		    
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
			
			ResultSet rsOrders = myDatabase.getDatabaseInfo("order", "buildingname = '" + buildingName + "' AND completed = FALSE");
			
			if (rsOrders == null || !rsOrders.next()) { // don't clear the map unless the ResultSet works
				return;
			}
			
			ordersInProgress.clear();
			
			do {
				
				int orderID = rsOrders.getInt("orderID");
				
				ResultSet rsOrderLines = myDatabase.getDatabaseInfo("orderline", "orderID = " + orderID);
				
				if (rsOrderLines == null) {
					return;
				}
				
				ArrayList<OrderLine> orderLines = new ArrayList<OrderLine>();
				
				while (rsOrderLines.next()) {
					String productName = rsOrderLines.getString("productname");
					int quantity = rsOrderLines.getInt("quantity");
					
					orderLines.add(new OrderLine(orderID, productName, quantity));
				}
				
				ordersInProgress.put(orderID, orderLines);
				
			} while (rsOrders.next());
			
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Couldn't refresh orders map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    
    public void refreshUserInfo() {
    	try {
    		
    		ResultSet rs = myDatabase.getDatabaseInfo("user", "buildingname = '" + buildingName + "'");
			
			if (rs == null || !rs.next()) { // don't clear the map unless the ResultSet works
				return;
			}
			
			userInfo.clear();
			
			do {
		    	
				userInfo.put(
					rs.getString("username"),
					new String[] {rs.getString("password"), rs.getString("usertype")}
				);
		        
		    } while (rs.next());
    		
    	} catch (SQLException e) {
    		JOptionPane.showMessageDialog(null, "Couldn't refresh users map: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    
    public String getUsertype(String username) {
        try {
            
            ResultSet rs = myDatabase.getDatabaseInfo("user", "buildingname = '" + buildingName + "'");
            
            if (rs == null || !rs.next()) {
                return null;
            }
            
            return rs.getString("useretype");
            
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
	    
	    String[] oldInfo = userInfo.get(username);
	    userInfo.put(username, new String[] {oldInfo[0], oldInfo[1]});
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't set usertype: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
