package GroupProject;

import java.sql.*;

public class User {

	// User fields
	private String userName;
	private String userType;
	
	/**
	 * Constructor
	 * @param userName
	 * @param userType
	 */
	public User(String userName, String userType) {
		this.userName = userName;
		this.userType = userType;
	}
	
	/**
	 * Getter for userName
	 * @return userName
	 */
	public String getUsername(){
		return userName;
	}
	/**
	 * Setter for userName
	 * @param username
	 */
	public void setUsername(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Getter for user type
	 * @return userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * Setter for user type
	 * @param usertype
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
