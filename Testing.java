package groupproject;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/*
 * JUnit testing class
 */
public class Testing {
    
    private static SQLDatabase database;
    private static InfoHandler infoHandler;
    
    private static int orderIdToRemove = -1;
    
    /*
     * testing setup
     */
    @BeforeClass
    public static void setUp() {
        String databaseURL = "jdbc:mysql://localhost:3306/cs380restaurant";
        String username = "root";
        String password = "Kl51abe7!-4567";
        database = new SQLDatabase(databaseURL, username, password);

        infoHandler = new InfoHandler(database, "testuser", "customer", "1234567890", "State", "City", "StreetAddr1", "StreetAddr2");
        
        if (database.getCon() == null) {
            throw new RuntimeException("Tests cancelled because connection to MySQL couldn't be established.");
        }
    }
    
    /*
     * removing inserted test data from the database
     */
    @AfterClass
    public static void cleanUp() {
        if (database.getCon() == null || orderIdToRemove == -1) {
            return;
        }
        
        database.execute("DELETE FROM orderline WHERE orderid = " + orderIdToRemove + ";");
        database.execute("DELETE FROM `order` WHERE orderid = " + orderIdToRemove + ";");
    }
    
    /*
     * tests the database connection
     */
    @Test
    public void testConnection() {
        assertNotNull("Database connection should be established.", database.getCon());
    }
    
    /*
     * tests the addItem method of SQLDatabase
     */
    @Test
    public void testAddItem() {
        ArrayList<String> elements = new ArrayList<>(Arrays.asList("Username", "Password", "Usertype", "BuildingName"));
        ArrayList<Object> values = new ArrayList<>(Arrays.asList("test_user", "test_pass", "customer", "1234567890"));

        int result = database.addItem("user", elements, values);
        assertEquals("Item should be added successfully.", 0, result);

        database.removeItem("user", "Username", "test_user");
    }
    
    /*
     * tests the removeItem method of SQLDatabase
     */
    @Test
    public void testRemoveItem() {
        ArrayList<String> elements = new ArrayList<>(Arrays.asList("Username", "Password", "Usertype", "BuildingName"));
        ArrayList<Object> values = new ArrayList<>(Arrays.asList("test_user", "test_pass", "customer", "1234567890"));
        database.addItem("user", elements, values);
        int result = database.removeItem("user", "Username", "test_user");
        assertEquals("Item should be remove successfully.", 0, result);
    }
    
    /*
     * tests the execute method of SQLDatabase
     */
    @Test
    public void testExecute() {
        int result = database.execute("Delete FROM user WHERE Username = 'test_user'");
        assertEquals("Command should be executed", 0, result);
    }
    
    /*
     * tests the verifyLogin method of SQLDatabase
     */
    @Test
    public void testVerifyLogin() {
        assertTrue("Login should be succesful.", database.verifyLogin("idk", "123"));
        assertFalse("Login failed", database.verifyLogin("aaaa", "1234"));
    }
    
    /*
     * tests the executeQuery method of SQLDatabase
     */
    @Test
    public void testExecuteQuery() {
        ResultSet rs = database.executeQuery("SELECT * FROM user");
        assertNotNull("ResultSet should not be null", rs);
        try {
            assertTrue("ResultSet should have at least one row", rs.next());
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }
    
    /*
     * tests the getUsertype method of SQLDatabase
     */
    @Test
    public void testGetUserType() {
        String usertype = database.getUsertype("testuser");
        assertEquals("Usertype should be 'customer'.", "customer", usertype);
        assertNull("Usertype should be null for non-existing user", database.getUsertype("non_existent_user"));
    }
    
    /*
     * tests InfoHandler methods
     */
    @Test
    public void testInfoHandler() throws SQLException {
        
        // addUserCurrentOrder test
        infoHandler.getMyCurrentOrder().add(new OrderLine("burger", 1));
        orderIdToRemove = infoHandler.addUserCurrentOrder("Test order note");
        assertNotEquals("Order ID should not be -1", -1, orderIdToRemove);
        
        
        // getOrderStatus test
        assertEquals("Order status should be 0", 0, infoHandler.getOrderStatus(orderIdToRemove));
        
        
        // editOrder test
        infoHandler.getMyCurrentOrder().clear();
        infoHandler.getMyCurrentOrder().add(new OrderLine("pizza", 1));
        Order editedOrder = new Order(orderIdToRemove, infoHandler.getMyCurrentOrder(), "testuser", "");
        infoHandler.editOrder(editedOrder, "test note");
        
        ResultSet rs = database.executeQuery("SELECT productname FROM orderline WHERE orderid = " + orderIdToRemove + ";");
        rs.next();
        
        assertEquals("orderline product should be pizza", "pizza", rs.getString(1));
        
        
        // markOrderCompleted test
        infoHandler.markOrderCompleted(orderIdToRemove);
        assertEquals("Order status should be 0", 1, infoHandler.getOrderStatus(orderIdToRemove));
        
    }
    
}
