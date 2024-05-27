package GroupProject;
import java.sql.*;
public class Building {
	
	// Create
	public int buildingID;
	
	/**
	 * Constructor for building
	 */
	public Building(int buildingID) {
		this.buildingID = buildingID;
	}
	
	/**
	 * Getter for building ID
	 * @return building id
	 */
	public int getBuildingID() {
		return buildingID;
	}
	/**
	 * Setter for building ID
	 * @param buildingID
	 */
	public void setBuildingID(int buildingID) {
		 this.buildingID= buildingID;
	}
}
