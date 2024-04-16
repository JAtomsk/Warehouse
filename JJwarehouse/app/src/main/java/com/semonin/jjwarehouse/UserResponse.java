/**
 * UserResponse Class
 *
 * Purpose:
 * This class is designed to parse and encapsulate the response data from the API when a user registers or completes an action
 * that requires a server response. It specifically handles responses that include a message and a user ID, allowing the app
 * to display feedback to the user and manage user identifiers within the system.
 *
 * Features:
 * - Message: A string containing feedback or a status message from the API about the operation performed.
 * - UserId: An integer representing the unique identifier for the user assigned by the database.
 *
 * Usage:
 * This class is used in the network communication layer of the app to handle the data returned by the server after a user
 * registers, logs in, or performs other actions that require a response. It allows the application to react appropriately
 * based on the server's response, such as directing the user to the login screen after successful registration or displaying
 * an error message if something goes wrong.
 *
 * Example:
 * When a user registers, the API might respond with a UserResponse object containing a success message and the user's new ID.
 * The application can then use this ID for session management or direct the user accordingly based on the provided message.
 *
 * Author: Jared Semonin
 * Date: 04/14/2024
 */




package com.semonin.jjwarehouse;
 //used to parse the response from the API when a user registers.
public class UserResponse {
     // Field to store the message from the API

     private String message;
     // Field to store the user ID assigned by the database

     private int userId;


     /**
      * Constructor for creating a UserResponse object with a message and user ID.
      * @param message The message from the API indicating the outcome of the operation.
      * @param userId The unique identifier for the user assigned during the operation.
      */
    public UserResponse(String message, int userId){
        this.message=message;
        this.userId = userId;

    }

     /**
      * Gets the message from the API.
      * @return A string representing the message from the API.
      */
     public String getMessage() {
         return message;
     }


     /**
      * Sets a new message from the API.
      * @param message A string containing the new message.
      */
     public void setMessage(String message) {
         this.message = message;
     }


     /**
      * Gets the user ID.
      * @return An integer representing the user ID.
      */
     public int getUserId() {
         return userId;
     }


     /**
      * Sets a new user ID.
      * @param userId An integer containing the new user ID.
      */
     public void setUserId(int userId) {
         this.userId = userId;
     }
 }

