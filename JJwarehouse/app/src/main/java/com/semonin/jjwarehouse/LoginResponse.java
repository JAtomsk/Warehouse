

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


public class LoginResponse {
    // Field to store the authentication token

    private String token;

    // Field to store messages from the server, typically regarding the success or failure of the login attempt

    private String message;


    /**
     * Constructor for LoginResponse
     * @param token The token issued by the server for session management.
     * @param message A message from the server, usually indicating the status of the login attempt.
     */
    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
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
}