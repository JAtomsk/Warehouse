/**
 * GridItem Fragment
 *
 * Purpose:
 * This fragment is used for adding a new inventory item or editing an existing one within the application.
 * It provides a user interface where users can input item details such as name and quantity.
 * This fragment supports both creating a new item (default) and editing an existing item (if provided with an item ID).
 *
 * Key Features:
 * - Dynamic input fields for item name and quantity.
 * - Buttons for increasing/decreasing quantity, saving item details, and removing an item.
 * - Utilizes the DatabaseHelper class for all database interactions, showcasing effective CRUD operations.
 * - Implements user-friendly UI adjustments such as adjusting view size dynamically based on the container.
 *
 * Author: [Jared Semonin]
 * Date: [03/31/2024]
 * Version: 1.0
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

    private EditText itemNameInput, itemQtyInput;
    private int itemId = -1; // Default to -1, indicating a new item

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Dynamically adjust fragment size for better UX

        View view = inflater.inflate(R.layout.fragment_grid_item, container, false);


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

        decreaseQtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustItemQuantity(-1);
            }
        });

        increaseQtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustItemQuantity(1);
            }
        });

        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });


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




    private void adjustItemQuantity(int adjustment) {
        try {
            int currentQty = Integer.parseInt(itemQtyInput.getText().toString());
            int newQty = currentQty + adjustment;
            itemQtyInput.setText(String.valueOf(newQty > 0 ? newQty : 0));
        } catch (NumberFormatException e) {
            itemQtyInput.setText(String.valueOf(0));
        }
    }

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