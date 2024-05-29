package groupproject;
import java.sql.*;
public class Building {
    
    // the variable for the building ID
    public int buildingID;
    
    /**
     * Constructor for building
     * @param buildingID the ID for the building
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
     * @param buildingID the ID to set
     */
    public void setBuildingID(int buildingID) {
         this.buildingID= buildingID;
    }
}