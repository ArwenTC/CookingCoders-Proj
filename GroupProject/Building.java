package GroupProject;
import java.sql.*;
public class Building {
	public String BuildingName;
	public char State;
	public String City;
	public String StreetAddress;
	/**
	 * 
	 * @param BuildingName
	 * @param State
	 * @param city
	 * @param StreetAddress
	 */
	public Building (String BuildingName, char State, String City, String StreetAddress) {
		this.BuildingName = BuildingName;
		this.State = State;
		this.City = City;
		this.StreetAddress = StreetAddress;
	}
	/**
	 * 
	 * @return
	 */
	public String getBuildingID() {
		return BuildingName;
	}
	/**
	 * 
	 * @param BuildingName
	 */
	public void setBuildingID(String BuildingName) {
		 this.BuildingName= BuildingName;
	}
	
	public char getState() {
		return State;
	}
	
	public void setState(char State) {
		this.State = State;
	}
	
	public String getCity() {
		return City;
	}
	
	public void setCity(String City) {
		this.City = City;
	}
	
	public String getStreetAddress() {
		return StreetAddress;
	}
	
	public void setStreetAddress(String StreetAddress) {
		this.StreetAddress = StreetAddress;
	}
	
	
	
	
	
}
