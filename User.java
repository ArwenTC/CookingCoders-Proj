package GroupProject;

import java.sql.*;

public class User {

	public String username;
	public String usertype;
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getUserType() {
		return usertype;
	}
	
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
