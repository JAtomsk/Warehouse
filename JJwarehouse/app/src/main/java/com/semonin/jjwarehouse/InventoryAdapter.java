/**
 * InventoryAdapter.java - Version 2.0
 *
 * Purpose:
 * This class acts as a bridge between the RecyclerView in DataGridFragment and the underlying data for inventory items. It dynamically updates the list of items based on search queries, allowing for efficient data presentation and interaction.
 *
 * Features:
 * - Adapter for RecyclerView to display inventory items.
 * - Dynamic updating of data based on search filters.
 * - Facilitates item click handling for further interaction.
 *
 * Enhancements:
 * - Implemented the updateData method to refresh the adapter's data set based on search results.
 *
 * Course Outcomes:
 * - "Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts."
 * - "Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals."
 *
 * Meeting Course Outcome:
 * By adapting the adapter to handle dynamic searches and filter results, we've enhanced the application's ability to communicate effectively with the user, ensuring the interface is coherent, responsive, and tailored to user needs. The introduction of a method to update data based on search inputs illustrates the innovative application of programming skills and tools in practice, aligning with industry standards for developing intuitive and user-centric software solutions.
 *
 * Reflecting on the Enhancement:
 * The enhancement of the InventoryAdapter class has been a critical step towards making the application more interactive and user-friendly. It allowed us to delve deeper into the principles of efficient data handling and real-time UI updates. This process highlighted the significance of adaptable UI components in software engineering, underscoring the necessity of continuous learning and application of best practices in the dynamic field of computing.
 *
 * Author: [Jared Semonin]
 * Date: [04/06/2024]
 * Version: 2.0
 */

package com.semonin.jjwarehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Version 2.0: Improved dynamic data handling and search functionality integration


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private List<Item> itemList; // Holds the data to display
    private final OnItemClickListener listener; // Click listener for each item

    // Interface for handling clicks on items
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    // Constructor
    public InventoryAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the name and quantity for each item
        Item currentItem = itemList.get(position);
        holder.textViewItemName.setText(currentItem.getName());
        holder.textViewItemQuantity.setText(String.valueOf(currentItem.getQuantity()));

        // Simplified click listener using lambda

        holder.itemView.setOnClickListener(v -> listener.onItemClick(currentItem)); // Handle item clicks
    }

    @Override
    public int getItemCount() {
        return itemList.size(); // Return the size of the dataset
    }

    // Added method for dynamic data updating
    public void updateData(List<Item> newItems) {
        this.itemList = newItems; // Update the list with filtered or unfiltered data
        notifyDataSetChanged(); // Notify any registered observers that the data set has changed
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemQuantity = itemView.findViewById(R.id.textViewItemQuantity);
        }
    }
}