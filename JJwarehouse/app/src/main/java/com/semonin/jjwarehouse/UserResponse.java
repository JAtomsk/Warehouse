/**
 * Class Name: UserResponse
 * Purpose:
 * - To parse and encapsulate the response data from the API when a user registers or completes an action that requires server interaction.
 * - Specifically designed to handle responses that include a message and a user ID, which aids the app in providing appropriate feedback and managing user identifiers.
 * Features:
 * - Message: Provides feedback or a status message from the API concerning the performed operation.
 * - UserId: Serves as a unique identifier for the user as assigned by the database.
 * Usage:
 * - Utilized in the network communication layer to manage data returned by server post user interactions such as registrations, logins, or other actions requiring server feedback.
 * - Allows the application to respond properly based on the server's feedback, such as navigating to a login screen post-registration or displaying an error message on failure.
 * Example:
 * - On user registration, the API might return a UserResponse object containing a message like "Registration Successful" and the new user's ID, which the app can then use for session management or appropriate user direction.
 * Author: Jared Semonin
 * Date: 04/21/2024
 */

package com.semonin.jjwarehouse;
 //used to parse the response from the API when a user registers.
public class UserResponse {
     private String message; // Message from the API indicating the outcome of the operation
     private int userId; // Unique identifier for the user assigned during the operation

     /**
      * Constructor for UserResponse.
      * @param message The message from the API indicating the outcome of the operation.
      * @param userId The unique identifier for the user assigned during the operation.
      */
    public UserResponse(String message, int userId){
        this.message=message;
        this.userId = userId;

    }

     /**
      * Gets the message provided by the API.
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

