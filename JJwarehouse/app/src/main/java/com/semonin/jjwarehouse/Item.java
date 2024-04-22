/**
 * Class Name: Item
 * Purpose:
 * - Represents the structure of an item within the inventory management system.
 * - Used to encapsulate the attributes of items being managed, facilitating easy data manipulation and API interaction.
 *
 * Features:
 * - ID: A unique identifier for each item, typically used for database indexing and retrieval.
 * - Name: The descriptive name of the item, which identifies it within the inventory.
 * - Quantity: Represents the amount of the item available in the inventory.
 *
 * Usage:
 * - This class is central to the inventory operations, being used in various parts of the application where items
 *   need to be created, updated, or displayed.
 * - It is used in conjunction with API calls to fetch, update, and manipulate item data.
 *
 * Example:
 * Item newItem = new Item(1, "Laptop", 10);
 * This example creates an instance of Item, setting its ID, name, and quantity.
 *
 * Author: Jared Semonin
 * Date: 04/21/2024
 */

package com.semonin.jjwarehouse;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("item_id")
    private Integer id; // Unique identifier for the item

    @SerializedName("name")
    private String name; // Name of the item

    @SerializedName("quantity")
    private Integer quantity; // Quantity of the item available in

    /**
     * Constructor for creating an Item object with specified details.
     * @param id Unique identifier for the item.
     * @param name Descriptive name of the item.
     * @param quantity Amount of the item available.
     */
    public Item(Integer id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Gets the item's unique identifier.
     * @return An integer representing the item's ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the item's unique identifier.
     * @param id An integer representing the new ID for the item.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the item.
     * @return A string representing the item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the item.
     * @param name A string containing the new name for the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the current quantity of the item in inventory.
     * @return An integer representing the quantity of the item.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets a new quantity for the item.
     * @param quantity An integer representing the new quantity for the item.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}