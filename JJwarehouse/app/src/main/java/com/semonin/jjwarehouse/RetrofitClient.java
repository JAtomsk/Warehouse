
/*
 * RetrofitClient Class
 *
 * Purpose:
 * This class serves as a singleton factory for creating a Retrofit instance configured to interact with the application's backend API.
 * Retrofit is used here to manage network requests efficiently, utilizing Gson for JSON serialization and deserialization.
 *
 * Features:
 * - Singleton Pattern: Ensures that only one instance of the Retrofit client is created throughout the app lifecycle, which helps in
 *   managing resources efficiently and maintaining a consistent state across network calls.
 * - Gson Converter: Automatically converts JSON responses from the server into Java objects and Java objects into JSON requests,
 *   simplifying the process of data handling in network operations.
 *
 * Usage:
 * This class is accessed whenever the app needs to make a network request. By calling `getRetrofitInstance`, other classes can use
 * this single instance to create service interfaces that define API calls.
 *
 * Example:
 * ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
 * This line of code fetches the Retrofit instance and prepares it to make API calls as defined in the ApiInterface class.
 *
 * Author: Jared Semonin
 * Date: 04/21/2024
 */
package com.semonin.jjwarehouse;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {

    // Base URL for the backend server. Note: Replace "http://10.0.2.2:3000" with the actual server URL when deployed.
    private static final String Base_URL = "http://10.0.2.2:3000";

    // Singleton instance of Retrofit to avoid multiple instance creation
    private static Retrofit retrofit = null;

    /**
     * This method ensures that only one Retrofit instance is created that configures and returns it.
     * The method synchronizes access to ensure thread safety in creating the instance.
     * @return The singleton Retrofit instance configured with the BASE_URL and Gson converter.
     */
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){

            // Double-check pattern for safe multi-threaded access
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_URL) // Set the base URL for HTTP requests
                    .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter factory for JSON serialization/deserialization
                    .build();
        }
        return retrofit;
    }
}
