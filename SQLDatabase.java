
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
	 * Constructor method
	 * @param databaseURL - name of the database
	 * @param username - login details
	 * @param password - login details
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
	
	
	public Connection getCon() {
	    return con;
	}
	
	
	/**
	 * Returns the data set inside a given table.
	 * @param table
	 * @param condition
	 * @return Result set containing info
	 * @return null upon error
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
	 * Adds a value to a table, allowing different sizes of inputs.
	 * @param table
	 * @param elements
	 * @param values
	 * @return int 0 = pass >0 = error code
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
	 * Removes an item from a given table, provided a key
	 * @param table
	 * @param keyName
	 * @param keyValue
	 * @return int 0 = pass >0 = error code
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
	 * Modifies a set of data in a given table.
	 * @param table
	 * @param keyName
	 * @param keyValue
	 * @param elements
	 * @param values
	 * @return int 0 = pass >0 = error code
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
	 * Checks if a value exists in a table given a column and value
	 * @param table
	 * @param column
	 * @param value
	 * @return int 0 = doesn't exist 1 = exists >1 = error code
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
	 * Calls and returns an error value
	 * @return 0 = pass, 1+ = error code
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
	 * Calls an returns a result set
	 * @return 0 = pass, 1+ = error code
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