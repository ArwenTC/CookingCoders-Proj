package groupproject;

import java.sql.*;


public class User {

    // User fields
    private String username;
    private String password;
    private String usertype;
    
    /**
     * Constructs a User object with the specified username, password, and user type.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param usertype The type of the user.
     */
    public User(String userName, String password, String usertype) {
        this.username = userName;
        this.password = password;
        this.usertype = usertype;
    }
    
     /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername(){
        return username;
    }
       /**
     * Sets the username of the user.
     *
     * @param username The new username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password of the user.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Retrieves the type of the user.
     *
     * @return The type of the user.
     */
    public String getUsertype() {
        return usertype;
    }
    /**
     * Sets the type of the user.
     *
     * @param usertype The new type to set.
     */
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
    
}