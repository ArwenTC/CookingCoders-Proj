
package GroupProject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JOptionPane;


public class InfoHandler {
    
    public final SQLDatabase myDatabase;
    
    // product information
    public final TreeMap<String, Double> products;
    
    // user values
    public final String username;
    public final String usertype;
    
    // building values
    public final String buildingName;
    public final String state;
    public final String city;
    public final String streetAddr1;
    public final String streetAddr2;
    
    
    // current order
    private int userOrderID = -1; // -1 means the user has no pending order
    
    
    public InfoHandler(
        SQLDatabase myDatabase,
        TreeMap<String, Double> products,
        String username,
        String usertype,
        String buildingName,
        String state,
        String city,
        String streetAddr1,
        String streetAddr2) {
        this.myDatabase = myDatabase;
        this.products = products;
        this.username = username;
        this.usertype = usertype;
        this.buildingName = buildingName;
        this.state = state;
        this.city = city;
        this.streetAddr1 = streetAddr1;
        this.streetAddr2 = streetAddr2;
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
                pst.setString(3, orderLine.getProduct().getName());
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
            JOptionPane.showMessageDialog(null, "Couldn't delete order", "SQL Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Couldn't check if order was complete", "SQL Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Couldn't mark order as completed", "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
