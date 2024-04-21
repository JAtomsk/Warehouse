package com.semonin.jjwarehouse;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("item_id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("quantity")
    private Integer quantity;

    // Constructor, getters, and setters
    public Item(Integer id, String name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}