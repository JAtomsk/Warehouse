/**
 * InventoryAdapter
 *
 * Purpose:
 * This adapter is responsible for binding inventory item data to views that are displayed within a RecyclerView.
 * It manages a list of inventory items and facilitates interaction through an OnItemClickListener interface.
 *
 * Key Features:
 * - Efficiently displays inventory items using the ViewHolder pattern to minimize layout inflation and findViewById() calls.
 * - Supports click events on items, making it easy to handle user interactions with the inventory list.
 *
 * Usage:
 * This adapter is used in conjunction with RecyclerView in UIs where a list of inventory items needs to be displayed.
 * It requires a List of Item objects to display and an OnItemClickListener to handle user clicks on individual items.
 *
 * Author: [Jared Semonin]
 * Date: [03/31/2024]
 * Version: 1.0
 */
package com.semonin.jjwarehouse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private final List<Item> itemList;
    private final OnItemClickListener listener;

    // Interface defining an item click listener to enable interaction with each item
    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    // Constructor initializing the adapter with a list of items and an item click listener
    public InventoryAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    // Inflates the item layout and creates ViewHolder instances for items

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }

    // ALGO    // Binds inventory item data to each ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = itemList.get(position);
        holder.textViewItemName.setText(currentItem.getName());
        holder.textViewItemQuantity.setText(String.valueOf(currentItem.getQuantity()));
        holder.bind(currentItem, listener);
    }

    // Returns the total number of items in the data set held by the adapter

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class that holds the views for each inventory item

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemName, textViewItemQuantity;


        // Constructor binding the text views

        public ViewHolder(View itemView) {
            super(itemView);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemQuantity = itemView.findViewById(R.id.textViewItemQuantity);
        }

        // Method to bind each item with a click listener

        public void bind(final Item item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}