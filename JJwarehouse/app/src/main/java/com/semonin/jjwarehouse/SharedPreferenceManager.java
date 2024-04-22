/**
 * Class Name: SharedPreferenceManager
 * Purpose:
 *  - To manage the application's shared preferences, providing a central mechanism to store and retrieve user-specific data like JWT tokens and user roles.
 *  - Facilitates persistence of essential user data across the application's sessions and restarts.
 * Features:
 *  - Token Storage: Securely store and retrieve the JWT token used for session management and API authentication.
 *  - Role Management: Store and access the user's role to enforce role-based functionalities and permissions within the app.
 * Usage:
 *  - This class is used throughout the app to save and retrieve authentication tokens and user roles which are critical after successful login.
 *  - Provides a standardized approach to accessing shared preferences, ensuring that data handling is consistent and reliable.
 * Author: Jared Semonin
 * Date: 04/21/2024
 */

package com.semonin.jjwarehouse;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Callback;

public class SharedPreferenceManager {
    private static final String PREF_NAME ="UserDetails"; // The name of the preference file
    private static final String KEY_JWT_TOKEN = "JWT_TOKEN"; // Key for storing the JWT token
    private static final String KEY_USER_ROLE = "USER_ROLE";  // Key for the user role

    /**
     * Saves the JWT token to SharedPreferences.
     * @param context The context of the caller.
     * @param token The JWT token to be saved.
     */
    public static void saveToken (Context context, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_JWT_TOKEN, token);
        editor.apply();
    }

    /**
     * Retrieves the JWT token from SharedPreferences.
     * @param context The context of the caller.
     * @return The JWT token if it exists, otherwise null.
     */
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_JWT_TOKEN, null);
    }

    /*
    * The user role being stored is not currently in use. I have added methods to capture the role for a future inclusion of adding permissions
    * to different users, as well as adding a loginAdmin logic that will allow an admin to login and perform CRUD functions on user accounts
    */

    /**
     * Saves the user's role to SharedPreferences.
     * @param context The context of the caller.
     * @param role The user's role to be saved.
     */
    public static void saveRole(Context context, String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ROLE, role);
        editor.apply();
    }

    /**
     * Retrieves the user's role from SharedPreferences.
     * @param context The context of the caller.
     * @return The user's role if it exists, otherwise null.
     */
    public static String getRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ROLE, null);
    }

}
