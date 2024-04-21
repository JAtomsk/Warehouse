package com.semonin.jjwarehouse;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Callback;

public class SharedPreferenceManager {
    private static final String PREF_NAME ="UserDetails";
    private static final String KEY_JWT_TOKEN = "JWT_TOKEN";
    private static final String KEY_USER_ROLE = "USER_ROLE";  // Key for the user role

    public static void saveToken (Context context, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_JWT_TOKEN, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_JWT_TOKEN, null);
    }

    // Saves the user's role to SharedPreferences
    public static void saveRole(Context context, String role) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ROLE, role);
        editor.apply();
    }

    // Retrieves the user's role from SharedPreferences
    public static String getRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ROLE, null);
    }

}
