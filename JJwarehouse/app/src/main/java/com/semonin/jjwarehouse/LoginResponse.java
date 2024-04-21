

/**
 * LoginResponse Class
 *
 * Purpose:
 * This class is designed to encapsulate the response data received from the server after a login request.
 * It holds a token and a message, which are used to communicate the outcome of the login attempt to the user
 * and manage session control within the application.
 *
 * Features:
 * - Token: A JWT (JSON Web Token) or similar token that can be used to maintain and verify user sessions across the application.
 * - Message: Provides feedback to the user about the login process, such as success or failure reasons.
 *
 * Usage:
 * This class is primarily used within the network communication layer of the app, where it's instantiated
 * with the data received from the login API call. The token is then used for subsequent authenticated requests,
 * and the message is displayed to the user.
 *
 * Example:
 * On successful login, the server might return a token and a message "Login Successful", which this class will encapsulate.
 * The token is then stored in the app for session management, and the message is displayed to the user as a Toast or similar.
 *
 * Author: Jared Semonin
 * Date: 04/14/2024
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
     * Constructor for LoginResponse
     * @param success Indicates if the login was successful.
     * @param token The token issued by the server for session management.
     * @param message A message from the server, usually indicating the status of the login attempt.
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