package GroupProject;

import java.sql.*;

public class User {

	public String username;
	public String password;
	public String Usertype;
	public String BuildingName;
	
	/**
	 * Constructor
	 * @return
	 */
	
	public User(String username, String password, String Usertype, String BuildingName) {
		this.username = username;
		this.password = password;
		this.Usertype = Usertype;
		this.BuildingName = BuildingName;
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
		return Usertype;
		
	}
	
	
	public void setUserType(String Usertype) {
		this.Usertype = Usertype;
	}
	
	
	public String getBuildingName() {
		return BuildingName;
	}
	
	public void setBuildingName(String BuildingName) {
		this.BuildingName = BuildingName;
	}
}
