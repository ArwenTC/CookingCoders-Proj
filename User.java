package GroupProject;

import java.sql.*;


public class User {

	// User fields
	private String username;
	private String password;
	private String usertype;
	
	/**
	 * Constructor
	 * @param username
	 * @param usertype
	 */
	public User(String userName, String password, String usertype) {
		this.username = userName;
		this.password = password;
		this.usertype = usertype;
	}
	
	/**
	 * Getter for userName
	 * @return username
	 */
	public String getUsername(){
		return username;
	}
	/**
	 * Setter for userName
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
	    return password;
	}
	
	public void setPassword(String password) {
	    this.password = password;
	}
	
	/**
	 * Getter for user type
	 * @return usertype
	 */
	public String getUsertype() {
		return usertype;
	}
	/**
	 * Setter for user type
	 * @param usertype
	 */
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
