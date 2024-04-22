/**
 * Class Name: ApiInterface
 *
 * Purpose:
 * This interface defines the API endpoints for the application using Retrofit annotations to encode details about the network
 * requests. It specifies the methods for registering and logging in users, as well as CRUD operations for items, corresponding to
 * the server-side routes that handle these operations. Retrofit uses this interface to generate the necessary code for making HTTP requests.
 *
 * Features:
 * - Registration Endpoint: Allows new users to register by sending their username and password to the server.
 * - Login Endpoint: Allows users to log in by validating their credentials against those stored in the server's database.
 * - CRUD Operations for Items: Provides endpoints for adding, retrieving, updating, and deleting items, leveraging JWT for authorization.
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
 * Date: 04/21/2024
 * Version: 2.0
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
     * Registers a new user by sending a POST request to the server with user details.
     * @param user A User object containing the username and password.
     * @return A Call object encapsulating a UserResponse.
     */
    @POST("register")
    Call<UserResponse> registerUser(@Body User user);

    /**
     * Logs in a user by sending a POST request with user credentials.
     * @param user A User object containing the username and password.
     * @return A Call object encapsulating a LoginResponse.
     */
    @POST("login")
    Call<LoginResponse> loginUser(@Body User user);

    /**
     * Adds a new item by sending a POST request with item details and authorization token.
     * @param authToken A string representing the JWT authorization token.
     * @param item An Item object containing item details.
     * @return A Call object encapsulating an ItemResponse.
     */
    @POST("addItem")
    Call<ItemResponse> addItem(@Header("Authorization") String authToken, @Body Item item);

    /**
     * Retrieves all items by sending a GET request with an authorization token.
     * @param authToken A string representing the JWT authorization token.
     * @return A Call object encapsulating an ItemsResponse with a list of items.
     */
    @GET("getItems")
    Call<ItemsResponse> getItems(@Header("Authorization") String authToken);

    /**
     * Retrieves a single item by its ID.
     * @param authToken A string representing the JWT authorization token.
     * @param itemId An integer representing the item's unique identifier.
     * @return A Call object encapsulating an ItemResponse.
     */
    @GET("getItem/{id}")
    Call<ItemResponse> getItemById(@Header("Authorization") String authToken, @Path("id") int itemId);

    /**
     * Retrieves filtered list of items based on a search query.
     * @param authToken A string representing the JWT authorization token.
     * @param searchQuery A string containing the search query.
     * @return A Call object encapsulating an ItemsResponse with filtered items.
     */
    @GET("getItems")
    Call<ItemsResponse> getFilteredItems(@Header("Authorization") String authToken, @Query("search") String searchQuery);

    /**
     * Updates an item's details by sending a PUT request with the item details and authorization token.
     * @param authToken A string representing the JWT authorization token.
     * @param item An Item object to be updated.
     * @return A Call object encapsulating an ItemResponse.
     */
    @PUT("updateItem")
    Call<ItemResponse> updateItem(@Header("Authorization") String authToken, @Body Item item);

    /**
     * Deletes an item by its ID by sending a DELETE request with the item ID and authorization token.
     * @param authToken A string representing the JWT authorization token.
     * @param itemId An integer representing the item's unique identifier.
     * @return A Call object encapsulating an ItemResponse.
     */
    @DELETE("deleteItem/{id}")
    Call<ItemResponse> deleteItem(@Header("Authorization") String authToken, @Path("id") int itemId);
}


