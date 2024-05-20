package GroupProject;

import java.sql.*;

public class User {

	public String username;
	public String password;
	public String user_type;
	
	/**
	 * Constructor
	 * @return
	 */
	
	public User(String username, String password, String user_type) {
		this.username = username;
		this.password = password;
		this.user_type = user_type;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassWord(String password) {
		this.password = password;
	}
	
	
	
	public String getUserType() {
		return user_type;
		
	}
	
	
	public void setUserType(String user_type) {
		this.user_type = user_type;
	}
}
