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
 * - Deletion Capability: Users can delete items directly from this detail view.
 *
 * Usage:
 * This fragment is utilized whenever an item in the inventory list is selected for more detailed viewing
 * or editing, providing a focused interface for these interactions.
 *
 *
 * Added more comments to make it easier for any programmer to understand and modify the code effectively.
 *
 * Author: Jared Semonin
 * Date: 04/11/2024
 * Version: 2.0
 */




package com.semonin.jjwarehouse;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GridItem extends Fragment {

    private EditText itemNameInput, itemQtyInput; // Text fields for item name and quantity.
    private int itemId = -1; // Default to -1, indicating a new item

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Dynamically adjust fragment size for better UX
        // Inflate the layout for this fragment, adjusting size dynamically for better user experience.

        View view = inflater.inflate(R.layout.fragment_grid_item, container, false);

        // Initialize UI components for item interaction.

        itemNameInput = view.findViewById(R.id.item_name_input);
        itemQtyInput = view.findViewById(R.id.item_qty_input);

        ImageView decreaseQtyButton = view.findViewById(R.id.item_qty_decrease);
        ImageView increaseQtyButton = view.findViewById(R.id.item_qty_increase);
        ImageButton removeItemButton = view.findViewById(R.id.remove_item_link);
        ImageButton saveItemButton = view.findViewById(R.id.save_item);

        // Check if editing an existing item
        if (getArguments() != null && getArguments().containsKey("itemId")) {
            itemId = getArguments().getInt("itemId", -1); // Default value as -1
            if (itemId != -1) {
                loadItemDetails(itemId);
            }
        }


        /*
         * Decreases the quantity of the item by one unless it is already at zero.
         *
         * @param view The view that was clicked.
         */
        decreaseQtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustItemQuantity(-1);
            }
        });

        /**
         * increased quantity of item by 1
         */
        increaseQtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustItemQuantity(1);
            }
        });
        /**
        * Saves the current details of the item to the database, either updating an existing item
        * or adding a new one.
        */
        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        /**
         * Saves the current details of the item to the database, either updating an existing item
         * or adding a new one.
         */
        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });

        /**
         * Navigates back to the previous fragment using the FragmentManager.
         */
        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Check if the fragment is added to an activity
            if (isAdded()) {
                // Use FragmentManager to pop the current fragment from the stack
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    /**
     * Loads item details if available, such as when editing an existing item.
     */
    private void loadItemDetails(int itemId) {
        // Load item details from the database and set them to the views
        // Implement this based on how  data is retrieved from the database

        DatabaseHelper db = new DatabaseHelper(getContext());
        Item item = db.getItemById(itemId);

        if (item != null) {
            itemNameInput.setText(item.getName());
            itemQtyInput.setText(String.valueOf(item.getQuantity()));
        }
    }


    /**
     * Deletes the current item and navigates back to the previous fragment.
     *
     *  view The view that was clicked.
     */
    private void deleteItem() {
        if (itemId != -1) {
            DatabaseHelper db = new DatabaseHelper(getContext());
            db.deleteItem(itemId);

            // Navigate back to DataGridFragment or update UI
            if (isAdded()) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }



    /**
     * Adjusts the item quantity by a specific amount and updates the UI to reflect this change.
     *
     * @param adjustment The amount to adjust the quantity by, can be negative or positive.
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


    /**
     * Saves the current details of the item to the database, either updating an existing item
     * or adding a new one.
     */

    private void saveItem() {
        String itemName = itemNameInput.getText().toString();
        int itemQuantity = Integer.parseInt(itemQtyInput.getText().toString());
        DatabaseHelper db = new DatabaseHelper(getContext());

        if (itemId != -1) {
            // Update existing item
            db.updateItem(itemId, itemName, itemQuantity);
        } else {
            // Add new item
            db.addItem(itemName, itemQuantity);
        }

        if (isAdded()) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    // Other methods...
}