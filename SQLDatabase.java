
package GroupProject;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

// TODO: use char arrays instead of strings so the SQL queries can be overwritten.
// The above TODO probably isn't necessary since this isn't a security class.

public class SQLDatabase {
	
	// Database info to be used when connecting / getting data
	private String databaseURL;
	private String username;
	private String password;
	
	// SQL Connection
	private Connection con;
	
	 /**
     * Constructs a SQLDatabase object with the specified database URL, username, and password.
     *
     * @param databaseURL The URL of the database.
     * @param username The username for database authentication.
     * @param password The password for database authentication.
     */
	public SQLDatabase(String databaseURL, String username, String password) {
		
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
     * Gets the database connection.
     *
     * @return The database connection.
     */
	public Connection getCon() {
	    return con;
	}
	
	
	/**
     * Returns the data set inside a given table.
     *
     * @param table The name of the table.
     * @param condition The condition to filter the data. If null or empty, no condition is applied.
     * @param orderBy The order by clause for sorting the data. If null or empty, no sorting is applied.
     * @return ResultSet containing the data from the table, or null if an error occurs.
     */
	public ResultSet getDatabaseInfo(String table, String condition, String orderBy) {
	    if (condition == null || condition.isEmpty()) {
	        condition = "TRUE";
	    }
	    
	    if (orderBy == null || orderBy.isEmpty()) {
	        orderBy = "";
	    } else {
	        orderBy = " ORDER BY " + orderBy;
	    }
		
		try {
			// Creates selection statement
			String sqlCommand = "SELECT * FROM `" + table + "`" + " WHERE " + condition + orderBy + ";";
			Statement statement = con.createStatement();
			
			// Returns the set of results
			return statement.executeQuery(sqlCommand);
		}
		catch (SQLException e) {
		    JOptionPane.showMessageDialog(null, "Error accessing database: " + e, "SQL Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
	}
	
	/**
     * Adds a new row of data to a table.
     *
     * @param table The name of the table.
     * @param elements The list of column names to insert data into.
     * @param values The list of values to insert into corresponding columns.
     * @return 0 if the operation is successful, >0 if an error occurs.
     */
	public int addItem(String table, ArrayList<String> elements, ArrayList<Object> values) {

		// String builder used to piece together command
		StringBuilder elementsSB = new StringBuilder("");
		StringBuilder valuesSB = new StringBuilder("");
		
		try {
			
			// Appends first element to string builder
			elementsSB.append(elements.get(0));
			// If there are more values, appends them using a loop
			if (elements.size() > 1) {
				for (int i = 1; i < elements.size(); i++) {
					elementsSB.append(", " + elements.get(i));
					
				}
			}

			// Adds first value to string builder
			// Adds a value with " if string or char[] and without if not
			if (values.get(0) instanceof String) {
				valuesSB.append("\"" + values.get(0) + "\"");
			} else if (values.get(0) instanceof char[]) {
			    valuesSB.append("\"");
			    char[] firstValue = (char[])values.get(0);
			    for (char ch : firstValue) {
			        valuesSB.append(ch);
			    }
			    valuesSB.append("\"");
			} else {
				valuesSB.append(values.get(0).toString());
			}
			
			// If there are more values, appends them using a loop
			if (values.size() > 1) {
				for (int i = 1; i < values.size(); i++) {
					
					// Adds a value with " if string or char[] and without if not
					if (values.get(i) instanceof String) {
						valuesSB.append(", \"" + values.get(i) + "\"");
					} else if (values.get(i) instanceof char[]) {
					    valuesSB.append(", \"");
					    char[] ithValue = (char[])values.get(i);
		                for (char ch : ithValue) {
		                    valuesSB.append(ch);
		                }
		                valuesSB.append("\"");
		            } else {
						valuesSB.append(", " + values.get(i).toString());
					}
					
				}
			}
			
		} catch (Exception e) {
		    System.out.println(e.getMessage());
			// Array accessing errors
			return 1;
		}
		
		// Tries to remove an item from the database using a query
		try {
			// Creates selection statement
			String sqlCommand = "INSERT INTO `" + table + "` ( " + elementsSB.toString()  + " ) VALUES ( " + valuesSB.toString() + " )";
			Statement statement = con.createStatement();
			
			// Executes the deletion
			System.out.println(sqlCommand);
			statement.execute(sqlCommand);
			
		} catch (SQLException e) {
		    System.out.println(e.getMessage());
			// Error upon failure
			return 2;
		}

		// Returns the success value
		return 0;
	}
	
	 /**
     * Removes a row of data from a table based on the specified key.
     *
     * @param table The name of the table.
     * @param keyName The name of the column to identify the row to delete.
     * @param keyValue The value of the key to identify the row to delete.
     * @return 0 if the operation is successful, >0 if an error occurs.
     */
	public int removeItem(String table, String keyName, Object keyValue) {
		
		// Tries to remove an item from the database using a query
		try {
			// Creates selection statement (either using string or non-string as key)
			String sqlCommand;
			if (keyValue instanceof String) {
				sqlCommand = "DELETE FROM `" + table + "` WHERE " + keyName + " = \"" + keyValue + "\"";
			} else {
				sqlCommand = "DELETE FROM `" + table + "` WHERE " + keyName + " = " + keyValue.toString();
			}
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
	
	 /**
     * Updates a row of data in a table.
     *
     * @param table The name of the table.
     * @param keyName The name of the column to identify the row to update.
     * @param keyValue The value of the key to identify the row to update.
     * @param elements The list of column names to update.
     * @param values The list of new values to update into corresponding columns.
     * @return 0 if the operation is successful, >0 if an error occurs.
     */
	public int updateItem(String table, String keyName, Object keyValue, ArrayList<String> elements, ArrayList<Object> values) {
		
		// String builder used to piece together command
		StringBuilder elementsSB = new StringBuilder("");
		
		try {
			// Appends first value to string builder
			
			// If the value being set is a string, adds a string value to the command
			if (values.get(0) instanceof String) {
				elementsSB.append(elements.get(0) + " = \"" + values.get(0) + "\"");
			// Else, adds a normal element to the string value
			} else {
				elementsSB.append(elements.get(0) + " = " + values.get(0).toString());
			}
			
			// If there are more values, appends them using a loop
			if (elements.size() > 1) {
				for (int i = 1; i < elements.size(); i++) {
					
					// If the value being set is a string, adds a string value to the command
					if (values.get(i) instanceof String) {
						elementsSB.append("," + elements.get(i) + " = \"" + values.get(i) + "\"");
					// Else, adds a normal element to the string value
					} else {
						elementsSB.append("," + elements.get(i) + " = " + values.get(i).toString());
					}
					
				}
			}
		} catch (Exception e) {
			// Array accessing errors
			return 1;
		}
		
		// Tries to remove an item from the database using a query
		try {
			// Creates selection statement (either using string or non-string as key)
			String sqlCommand;
			if (keyValue instanceof String) {
				sqlCommand = "UPDATE `" + table + "` SET " + elementsSB.toString()  + " WHERE " + keyName + " = \"" + keyValue + "\"";
			} else {
				sqlCommand = "UPDATE `" + table + "` SET " + elementsSB.toString()  + " WHERE " + keyName + " = " + keyValue.toString();
			}
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
	
	 /**
     * Checks if a value exists in a table given a column and value.
     *
     * @param table The name of the table.
     * @param column The name of the column to search.
     * @param value The value to search for in the specified column.
     * @return 0 if the value does not exist, 1 if it exists, >1 if an error occurs.
     */
	public int valueExists(String table, String column, String value) {
	    String sqlString = "SELECT 1 FROM `" + table + "` WHERE " + column + " = " + value + ";";
        
        int exists = 0;
        
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = con.prepareStatement(sqlString);
            rs = st.executeQuery();
            
            exists = rs.next() ? 1 : 0;
        } catch (SQLException e) {
            return 2;
        }
        
        return exists;
	}
	
	/**
     * Executes a SQL command.
     *
     * @param command The SQL command to execute.
     * @return 0 if the operation is successful, >0 if an error occurs.
     */
	public int execute(String command) {
		try {
			// Creates a statement and executes
			Statement statement = con.createStatement();
			statement.execute(command);
		} catch (SQLException e) {
			// SQL Error
			return 1;
		}
		// Pass
		return 0;
	}
	
	 /**
     * Executes a SQL query and returns the result set.
     *
     * @param command The SQL query to execute.
     * @return The result set containing the data from the query, or null if an error occurs.
     */
	public ResultSet executeQuery(String command) {
		try {
			// Creates a statement and executes
			Statement statement = con.createStatement();
			// Returns the set of results
			return statement.executeQuery(command);
		}
		catch (SQLException e) {
			System.out.println("Error accessing database: " + e);
			return null;
		}
	}
	 /**
     * Verifies the login credentials of a user.
     *
     * @param Username The username of the user.
     * @param Password The password of the user.
     * @return true if the login is successful, false otherwise.
     */
	public boolean verifyLogin(String Username, char[] Password) {
        String query = "SELECT password FROM USER WHERE Username = ? AND Password = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, Username);
            pst.setString(2, new String(Password));
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error verifying user password: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return false;
    }
	/**
     * Retrieves the type of user based on the username.
     *
     * @param Username The username of the user.
     * @return The type of user, or null if an error occurs.
     */
	public String getUserType(String Username) {
		String query = "SELECT Usertype FROM USER WHERE Username = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, Username);
			try( ResultSet rs = pst.executeQuery()) {
				if(rs.next()) {
					return rs.getString("Usertype");
				}
			}
		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(null, "Error retrieving user type: "  + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
}