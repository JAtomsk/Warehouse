/**
 * GridItem Fragment
 *
 * Purpose:
 * Serves as a detailed view for editing and viewing individual inventory items.
 * It allows for adjusting item quantities, editing details, and deleting the item.
 *
 * Features:
 * - Dynamic Item Editing: Users can update item names and quantities.
 * - Immediate Feedback: Changes in item quantity are immediately reflected in the UI.
 * - Load and display individual item details.
 * - Update item details.
 * - Delete an item from the inventory.
 * - Adjust the quantity of an item directly from the interface.
 * - Intuitive user interaction through buttons for increasing/decreasing quantity, saving changes, or deleting the item.
 *
 * Usage:
 *  - This fragment is used within the main activity where item details need to be presented and manipulated.
 *  - It interacts with the backend through API calls to fetch, update, and delete items.
 * - Utilizes shared preferences for managing session tokens and user authentication for API calls.
 * Author: Jared Semonin
 * Date: 04/21/2024
 * Version:3.0
 *  */



package com.semonin.jjwarehouse;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridItem extends Fragment {

    private EditText itemNameInput, itemQtyInput; // Text fields for item name and quantity.
    private int itemId = -1; // Holds the ID of the current item. '-1' indicates no item is loaded.
    private ApiInterface apiInterface; // Interface to make API calls.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment and initialize the API interface.
        View view = inflater.inflate(R.layout.fragment_grid_item, container, false);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // Initialize UI components from the layout.
        itemNameInput = view.findViewById(R.id.item_name_input);
        itemQtyInput = view.findViewById(R.id.item_qty_input);
        ImageView decreaseQtyButton = view.findViewById(R.id.item_qty_decrease);
        ImageView increaseQtyButton = view.findViewById(R.id.item_qty_increase);
        ImageButton removeItemButton = view.findViewById(R.id.remove_item_link);
        ImageButton saveItemButton = view.findViewById(R.id.save_item);
        ImageButton backButton = view.findViewById(R.id.backButton);

        // Retrieve item details from bundle if present.
        Bundle args = getArguments();
        if (args != null && args.containsKey("itemId")) {
            itemId = args.getInt("itemId", -1);  // Retrieve the itemId, defaulting to -1 if not found.
            if (itemId != -1) {
                loadItemDetails(itemId); // Load item details if a valid ID is present.
            }
        }
        // Set up button listeners for item quantity adjustments and item operations.
        decreaseQtyButton.setOnClickListener(v -> adjustItemQuantity(-1));
        increaseQtyButton.setOnClickListener(v -> adjustItemQuantity(1));
        removeItemButton.setOnClickListener(v -> deleteItem());
        saveItemButton.setOnClickListener(v -> saveItem());
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    /**
     * Load item details from the server using the item ID.
     */
    private void loadItemDetails(int itemId) {
        String token = SharedPreferenceManager.getToken(getContext()); // Retrieve the stored JWT token.
        apiInterface.getItemById("Bearer " + token, itemId).enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getItem() != null) {
                    Item item = response.body().getItem();
                    itemNameInput.setText(item.getName());// Set item name in input field.
                    itemQtyInput.setText(String.valueOf(item.getQuantity()));// Set item quantity in input field.
                } else {
                    Toast.makeText(getContext(), "Failed to load item details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Save the current item details. Determines whether to add or update an item based on itemId.
     */
    private void saveItem() {
        String token = SharedPreferenceManager.getToken(getContext());
        String name = itemNameInput.getText().toString();
        int quantity;
        try {
            quantity = Integer.parseInt(itemQtyInput.getText().toString().isEmpty() ? "0" : itemQtyInput.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        Item item = new Item(itemId, name, quantity);
        Call<ItemResponse> call;

        if (itemId == -1) { // If itemId is -1, it means this is a new item that needs to be added
            call = apiInterface.addItem("Bearer " + token, item);
        } else { // Otherwise, it's an existing item that needs to be updated
            call = apiInterface.updateItem("Bearer " + token, item);
        }

        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), itemId == -1 ? "Item added successfully" : "Item updated successfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Deletes the current item based on itemId.
     */
    private void deleteItem() {
        if (itemId != -1) {
            String token = SharedPreferenceManager.getToken(getContext());
            apiInterface.deleteItem("Bearer " + token, itemId).enqueue(new Callback<ItemResponse>() {
                @Override
                public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Toast.makeText(getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {

                        Log.d("DeleteItem", "Response: " + new Gson().toJson(response.body()));
                        Toast.makeText(getContext(), "Failed to delete item: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ItemResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Invalid item ID", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Adjusts the item quantity by a specific amount and updates the UI to reflect this change.
     */
    private void adjustItemQuantity(int adjustment) {
        try {
            int currentQty = Integer.parseInt(itemQtyInput.getText().toString());
            int newQty = currentQty + adjustment;
            itemQtyInput.setText(String.valueOf(newQty > 0 ? newQty : 0));
        } catch (NumberFormatException e) {
            itemQtyInput.setText(String.valueOf(0));
        }
    }
}



