/**
 * ApiInterface Class
 *
 * Purpose:
 * This interface defines the API endpoints for the application using Retrofit annotations to encode details about the network
 * requests. It specifies the methods for registering and logging in users, corresponding to the server-side routes that handle
 * these operations. Retrofit uses this interface to generate the necessary code for making HTTP requests.
 *
 * Features:
 * - Registration Endpoint: Allows new users to register by sending their username and password to the server.
 * - Login Endpoint: Allows users to log in by validating their credentials against those stored in the server's database.
 *
 * Usage:
 * This interface is utilized by creating an instance of Retrofit and calling these methods with required parameters.
 * The Retrofit client automatically handles the request execution and response parsing based on the definitions provided here.
 *
 * Example:
 * ApiInterface api = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
 * Call<UserResponse> call = api.registerUser(new User("username", "password"));
 * call.enqueue(new Callback<UserResponse>() {...});
 *
 * This setup enables clear separation of network operations from the rest of the application's code, making it easier to manage.
 *
 * Author: Jared Semonin
 * Date: 04/14/2024
 */




package com.semonin.jjwarehouse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    /**
     * Defines a POST method for registering a new user on the server.
     * The server endpoint "register" expects a user object in the request body.
     * @param user A User object containing the username and password.
     * @return A Call object encapsulating a UserResponse, which includes server feedback such as success or failure messages.
     */
    @POST("register")
    Call<UserResponse> registerUser(@Body User user);
    /**
     * Defines a POST method for logging in a user.
     * The server endpoint "login" expects a user object in the request body for authentication.
     * @param user A User object containing the username and password.
     * @return A Call object encapsulating a LoginResponse, which includes the authentication token and message if successful.
     */
    @POST("login")
    Call<LoginResponse> loginUser(@Body User user);

    @POST("addItem")
    Call<ItemResponse> addItem(@Header("Authorization") String authToken, @Body Item item);

    @GET("getItems")
    Call<ItemsResponse> getItems(@Header("Authorization") String authToken);

    @GET("getItem/{id}")
    Call<ItemResponse> getItemById(@Header("Authorization") String authToken, @Path("id") int itemId);

    @GET("getItems")
    Call<ItemsResponse> getFilteredItems(@Header("Authorization") String authToken, @Query("search") String searchQuery);

    @PUT("updateItem")
    Call<ItemResponse> updateItem(@Header("Authorization") String authToken, @Body Item item);

    @DELETE("deleteItem/{id}")
    Call<ItemResponse> deleteItem(@Header("Authorization") String authToken, @Path("id") int itemId);
}


