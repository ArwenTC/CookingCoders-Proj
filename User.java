package GroupProject;

import java.sql.*;


public class User {

	// User fields
	private String username;
	private String usertype;
	
	/**
	 * Constructor
	 * @param username
	 * @param usertype
	 */
	public User(String userName, String usertype) {
		this.username = userName;
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
