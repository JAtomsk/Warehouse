/**
 * InventoryAdapter.java
 *
 * Purpose:
 * This class acts as a bridge between the RecyclerView in DataGridFragment and the underlying data for inventory items. It dynamically updates the list of items based on search queries, allowing for efficient data presentation and interaction.
 *
 * Features:
 * - Adapter for RecyclerView to display inventory items.
 * - Dynamic updating of data based on search filters.
 * - Facilitates item click handling for further interaction.
 * - Modular design allowing for reusable, maintainable code.
 *
 * Usage:
 * - This adapter is utilized in RecyclerViews where inventory items are displayed.
 * - It interacts with data sources to fetch and display items, and provides callbacks for user interactions.
 *
 * Enhancements:
 * - Implemented the updateData method to refresh the adapter's data set based on search results.
 *
 *
 *  *Course Outcome 4:
 *  "Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals (software engineering/design/database)."
 *  Enhancing the InventoryAdapter class to support live data filtering exemplifies the application of innovative computing techniques to solve practical challenges in inventory management.
 *  This change leverages the RecyclerView's flexibility and the adapter pattern to provide a dynamic, responsive user interface that reacts to user input in real-time.
 *  It demonstrates a deep understanding of Android UI patterns and  data handling, important skills in mobile software engineering.
 * These improvements offer value to the end-user by making inventory searches more intuitive and efficient, contributing to the goal of creating purposeful and impactful computing solutions.
 *
 * Meeting Course Outcome:
 * By adapting the adapter to handle dynamic searches and filter results, we've enhanced the application's ability to communicate effectively with the user, ensuring the interface is coherent, responsive, and tailored to user needs. The introduction of a method to update data based on search inputs illustrates the innovative application of programming skills and tools in practice, aligning with industry standards for developing intuitive and user-centric software solutions.
 *
 * Reflecting on the Enhancement:
 * The enhancement of the InventoryAdapter class has been a critical step towards making the application more interactive and user-friendly. It allowed us to delve deeper into the principles of efficient data handling and real-time UI updates. This process highlighted the significance of adaptable UI components in software engineering, underscoring the necessity of continuous learning and application of best practices in the dynamic field of computing.
 *
 * Author: [Jared Semonin]
 * Date: [04/21/2024]
 * Version: 3.0
 */

package com.semonin.jjwarehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private List<Item> itemList; // Holds the list of inventory items to display.

    /**
     * Interface for handling item clicks, allows modular interaction with RecyclerView items.
     */
    private final OnItemClickListener listener; // Click listener for each item


    // Interface defining an item click listener to enable interaction with each item
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    /**
     * Constructs the adapter with a list of items and a click listener.
     * @param itemList List of items to be displayed.
     * @param listener Listener for handling item clicks.
     */
        public InventoryAdapter(List<Item> itemList, OnItemClickListener listener) {
        // Initialize with an empty list if null is received
        this.itemList = (itemList != null) ? itemList : new ArrayList<>();
        this.listener = listener;
    }

    /**
     * Inflates the layout for each item, optimizing ViewHolder creation for better performance.
     * @param parent The ViewGroup into which the new view will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return ViewHolder that holds the View for each item.
     */
     @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the item data to the ViewHolder, setting up the displayed name and quantity for each item.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the name and quantity for each item
        Item currentItem = itemList.get(position);
        holder.textViewItemName.setText(currentItem.getName());
        holder.textViewItemQuantity.setText(String.valueOf(currentItem.getQuantity()));

        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentItem)); // Handle item clicks
    }

    /**
     * Returns the total number of items in the dataset held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return itemList.size(); // Now this won't cause a NullPointerException
    }


    /**
     * Updates the data in the adapter, replacing old data with new items.
     * @param newItems New list of items to replace the current set.
     */
    public void updateData(List<Item> newItems) {
        this.itemList = (newItems != null) ? newItems : new ArrayList<>();
        notifyDataSetChanged();
    }


    /**
     * ViewHolder class holds the views for each inventory item.
     * Encapsulates item view handling within a static class to enhance performance and reduce memory overhead.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemQuantity; // TextViews for item name and quantity.

        /**
         * Constructs the ViewHolder and binds the text views.
         * @param itemView The View that you inflated in onCreateViewHolder().
         */
        public ViewHolder(View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemQuantity = itemView.findViewById(R.id.textViewItemQuantity);
        }
    }
}

