/**
 * Class Name: User
 * Purpose:
 * - Represents a user's credentials, specifically their username and password, used during registration and login processes.
 * - This class is designed to encapsulate the user information required by the API to authenticate users or register new accounts.
 * Features:
 * - Username: The user's unique identifier.
 * - Password: The user's password, which should be handled securely throughout the application's lifecycle, especially when being transmitted.
 * Usage:
 * - Objects of this class are created and used whenever user credentials need to be passed to the server for authentication tasks,
 *   such as logging in or registering a new user. The username and password are encapsulated in this object to streamline
 *   data management and enhance security practices.
 * - This model helps maintain clean architecture by separating the user data model from the business logic and view layers.
 * Author: Jared Semonin
 * Date: 04/21/2024
 */
package com.semonin.jjwarehouse;

public class User {
    private String username;
    private String password;

    /**
     * Constructor for User class.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the user's username.
     * @return A string representing the user's username.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets the user's username.
     * @param username A string containing the new username.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Gets the user's password.
     * @return A string representing the user's password.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets the user's password.
     * @param password A string containing the new password.
     */
    public void setPassword(String password){
        this.password = password;
    }
}
