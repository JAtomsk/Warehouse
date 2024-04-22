

/**
 * Class Name: LoginResponse
 * Purpose:
 *  - To encapsulate the response data received from the server after a login attempt.
 *  - It holds a token and a message, along with the role, to communicate the outcome of the login
 *    attempt to the user and manage session control within the application.
 * Features:
 *  - Token: A JWT (JSON Web Token) used to authenticate and maintain user sessions across the application.
 *  - Message: Communicates the result of the login attempt to the user, such as success or failure reasons.
 *  - Role: Identifies the user's role within the application, which can be used for role-based access control.
 * Usage:
 *  - This class is used within the network communication layer of the app, instantiated with the data
 *    received from the login API call.
 *  - The token is used for subsequent authenticated requests to the server.
 *  - The message is displayed to the user as feedback regarding the login attempt.
 *  - The role is used to determine the user's permissions and access within the app.
 * Example:
 *  - On successful login, the server might return a JSON object that includes a token, a success message,
 *    and the user's role. This class encapsulates that data, allowing the app to manage user sessions
 *    and display appropriate messages.
 * Author: Jared Semonin
 * Date: 04/21/2024
 */




package com.semonin.jjwarehouse;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String role;
    @SerializedName("success")
    private boolean success;

    @SerializedName("token")
    private String token;

    @SerializedName("message")
    private String message;

    /**
     * Constructor for LoginResponse.
     * @param success Indicates if the login was successful.
     * @param token The token issued by the server for session management.
     * @param message A message from the server, usually indicating the status of the login attempt.
     * @param role The role of the user as defined by the server.
     */
    public LoginResponse(boolean success, String token, String message, String role) {
        this.success = success;
        this.token = token;
        this.role = role;
        this.message = message;
    }

    /**
     * Checks if the login was successful.
     * @return true if successful, otherwise false.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the login.
     * @param success true if login was successful, otherwise false.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the current token.
     * @return A string representing the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets a new token.
     * @param token A string containing the new token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the current message.
     * @return A string representing the message from the server.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets a new message.
     * @param message A string containing the new message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    // Getters and setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}