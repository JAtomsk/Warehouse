
package com.semonin.jjwarehouse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class Name: ItemResponse
 * Purpose:
 * - To encapsulate the response data from the API concerning a specific item operation like fetching, adding, or updating an item.
 * - It includes details such as success status, a message from the server, and the item details if applicable.
 * Features:
 * - Success: Indicates whether the API operation was successful.
 * - Message: Provides detailed feedback from the server regarding the operation.
 * - Item: Contains the item details when a single item operation is performed.
 * Usage:
 * - Used across the application wherever an API call is made that concerns individual item operations to interpret and utilize the response data effectively.
 * - Helps in managing the item details after operations such as add, update, or fetch.
 * Author: Jared Semonin
 * Date: 04/21/2024
 */

public class ItemResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("item")
    private Item item;  // Include full item details

    /**
     * Constructor for ItemResponse.
     * @param success Indicates if the operation was successful.
     * @param message Provides a message about the operation's outcome.
     * @param item The item details involved in the operation.
     */
    public ItemResponse(boolean success, String message, Item item) {
        this.success = success;
        this.message = message;
        this.item = item;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

/**
 * Class Name: ItemsResponse
 * Purpose:
 * - To encapsulate the response data from the API when multiple items are fetched or involved in the operation.
 * - It includes a list of items along with the success status and a message from the server.
 * Features:
 * - Success: Indicates the outcome of the operation.
 * - Message: Feedback or detailed information regarding the operation.
 * - Items: A list of items related to the operation.
 * Usage:
 * - Utilized in scenarios where multiple items are being managed or retrieved from the API,
 * - providing a structured way to handle and display these items within the application.
 * Author: Jared Semonin
 * Date: 04/21/2024
 */

 class ItemsResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("items")
    private List<Item> items; // List of items

    // Constructor, getters, and setters
    public ItemsResponse(boolean success, String message, List<Item> items) {
        this.success = success;
        this.message = message;
        this.items = items;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

