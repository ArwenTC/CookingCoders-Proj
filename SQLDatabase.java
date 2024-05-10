import java.sql.*;
import java.util.ArrayList;

public class SQLDatabase {
	
	// Database info to be used when connecting / getting data
	private String databaseURL;
	private String tableName;
	private String username;
	private String password;
	
	// SQL Connection
	private Connection con;
	
	/**
	 * Constructor method
	 * @param databaseURL - name of the database
	 * @param username - login details
	 * @param password - login details
	 */
	public SQLDatabase (String databaseURL, String username, String password) {
		
		this.databaseURL = databaseURL;
		this.username = username;
		this.password = password;
		
		// Sets up the database
		try {
			// Attempts to connect to the database
			this.con = DriverManager.getConnection(this.databaseURL, this.username, this.password);
			System.out.println("Successfully connected database.");
		} catch (Exception e) {
			// Prints out if the database could not connect
			System.out.println("Error connecting to database: " + e);
		}
		
	}
	
	/**
	 * Gets the data from the sql
	 * @return ResultSet containing the database information
	 */
	public ResultSet getDatabaseInfo(String table) {
		
		try {
			// Creates selection statement
			String sqlCommand = "SELECT * FROM " + table;
			Statement statement = con.createStatement();
			
			// Returns the set of results
			return statement.executeQuery(sqlCommand);
		}
		catch (SQLException e) {
			System.out.println("Error accessing database: " + e);
			return null;
		}
		
	}
	
	/**
	 * Adds an item to the table
	 * @param studentID
	 * @param firstName
	 * @param lastName
	 * @param midtermGrade
	 * @return - the success value of the program to be printed in the pop-up window
	 */
	public int addItem(String table, String keyName, String keyValue, ArrayList<String> elements, ArrayList<Object> values) {

		// String builder used to piece together command
		StringBuilder elementsSB = new StringBuilder("");
		StringBuilder valuesSB = new StringBuilder("");
		
		try {
			
			// Appends first value to string builder
			elementsSB.append(elements.get(0));
			// If there are more values, appends them using a loop
			if (elements.size() > 1) {
				for (int i = 1; i < elements.size(); i++) {
						elementsSB.append(", " + elements.get(i));
					
				}
			}

			
			// Adds first value
			// Adds a value with " if string and without if not
			if (values.get(0) instanceof String) {
				valuesSB.append("\"" + values.get(0) + "\"");
			} else {
				valuesSB.append(values.get(0));
			}
			
			// If there are more values, appends them using a loop
			if (values.size() > 1) {
				for (int i = 1; i < values.size(); i++) {
					
					// Adds a value with " if string and without if not
					if (values.get(i) instanceof String) {
						valuesSB.append(", \"" + values.get(i) + "\"");
					} else {
						valuesSB.append(", " + values.get(i));
					}
					
				}
			}
			
			
		} catch (Exception e) {
			// Array accessing errors
			return 1;
		}
		
		// Tries to remove an item from the database using a query
		try {
			// Creates selection statement
			String sqlCommand = "INSERT INTO " + tableName + " ( " + elementsSB.toString()  + " ) VALUES ( " + valuesSB.toString() + " )";
			Statement statement = con.createStatement();
			
			// Executes the deletion
			statement.execute(sqlCommand);
			
		} catch (SQLException e) {
			// Error upon failure
			return 2;
		}

		// Returns the success value
		return 0;
	}
	
	
	public int removeItem(String table, String keyName, String keyValue) {
		
		// Tries to remove an item from the database using a query
		try {
			// Creates selection statement
			String sqlCommand = "DELETE FROM " + table + " WHERE " + keyName + " = " + keyValue;
			Statement statement = con.createStatement();
			
			// Executes the deletion
			statement.execute(sqlCommand);
			
		} catch (SQLException e) {
			// Error upon failure
			return 1;
		}
		
		// Returns the success value
		return 0;
	}
	
	
	public int updateItem(String table, String keyName, String keyValue, ArrayList<String> elements, ArrayList<Object> values) {
		
		// String builder used to piece together command
		StringBuilder elementsSB = new StringBuilder("");
		
		try {
			// Appends first value to string builder
			
			// If the value being set is a string, adds a string value to the command
			if (values.get(0) instanceof String) {
				elementsSB.append(elements.get(0) + " = \"" + values.get(0) + "\"");
			// Else, adds a normal element to the string value
			} else {
				elementsSB.append(elements.get(0) + " = " + values.get(0));
			}
			
			// If there are more values, appends them using a loop
			if (elements.size() > 1) {
				for (int i = 1; i < elements.size(); i++) {
					
					// If the value being set is a string, adds a string value to the command
					if (values.get(i) instanceof String) {
						elementsSB.append("," + elements.get(i) + " = \"" + values.get(i) + "\"");
					// Else, adds a normal element to the string value
					} else {
						elementsSB.append("," + elements.get(i) + " = " + values.get(i));
					}
					
				}
			}
		} catch (Exception e) {
			// Array accessing errors
			return 1;
		}
		
		// Tries to remove an item from the database using a query
		try {
			// Creates selection statement
			String sqlCommand = "UPDATE " + tableName + " SET " + elementsSB.toString()  + " WHERE " + keyName + " = " + keyValue;
			Statement statement = con.createStatement();
			
			// Executes the deletion
			statement.execute(sqlCommand);
			
		} catch (SQLException e) {
			// Error upon failure
			return 2;
		}

		// Returns the success value
		return 0;
	}
	
}
