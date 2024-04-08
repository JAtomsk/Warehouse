/**
 * DataGridFragment.java - Version 2.0
 *
 * Purpose:
 * To display a grid of inventory items and allow users to dynamically filter through these items using a search functionality.
 *
 * Features:
 * - Displays inventory items in a RecyclerView.
 * - Allows for dynamic searching and filtering of inventory items.
 * - Navigation to item addition and item details view.
 *
 * Enhancements:
 * - Added a search field with real-time filtering capabilities.
 *
 * Course Outcomes:
 * - "Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts."
 * - "Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals."
 *
 * Meeting Course Outcome:
 * The integration of a dynamic search feature enhances user communication by providing a responsive and user-friendly interface. This directly contributes to developing professional-quality, technically sound applications that are adapted to user needs. The application of TextWatcher for real-time data filtering exemplifies the use of innovative techniques and tools in computing practices, showcasing a practical implementation of computer solutions that deliver significant value and improve user interaction with the application.
 *
 * Reflecting on the Enhancement:
 * The process of adding search functionality underscored the importance of responsive UI design and the application of algorithmic principles in enhancing user experience. It challenged us to think creatively about data manipulation and presentation in Android development, leading to a deeper understanding of dynamic data handling and UI updates based on user interactions. This enhancement not only improved the app's usability but also its adaptability to user needs, demonstrating a key aspect of delivering industry-specific goals through well-founded computing practices.
 *
 * Author: [Jared Semonin]
 * Date: [04/27/2024]
 * Version: 2.0
 */




package com.semonin.jjwarehouse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Version 2.0: Enhanced search functionality with real-time filtering
public class DataGridFragment extends Fragment {

    // Maintains adapter globally for easy access and manipulation across methods.

    private InventoryAdapter adapter; // Maintain adapter as a global variable for easy access

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = setupRecyclerView(view); // Assign returned RecyclerView
        setupSearch(view, recyclerView);

        // Simplified lambda expression for adding new item button click listener.

        ImageButton addDataButton = view.findViewById(R.id.addDataButton);
        addDataButton.setOnClickListener(v -> navigateToAddNewItem());
    }
    // Initializes RecyclerView and returns it
    private RecyclerView setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.dataGrid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetches items from the database.

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Item> items = db.getItems();

        // Adapter initialization moved here for clarity and to ensure global access.

        adapter = new InventoryAdapter(items, this::navigateToGridItemFragmentWithItemDetails); // Initialize adapter here
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    // New method for implementing search functionality.
    private void setupSearch(View view, RecyclerView recyclerView) {
        EditText searchField = view.findViewById(R.id.search_field);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterData(editable.toString()); // Filters data as the user types.
            }
        });
    }


    // Filters data based on the search query
    private void filterData(String query) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Item> filteredItems = db.getItemsFilteredBy(query);
        adapter.updateData(filteredItems); // Update the adapter with filtered data
    }


    // Navigates to the GridItem fragment to display or edit the details of an item
    private void navigateToGridItemFragmentWithItemDetails(Item item) {
        GridItem gridItemFragment = new GridItem();

        // Pass item details to GridItemFragment
        Bundle args = new Bundle();
        args.putInt("itemId", item.getId()); // Assuming Item class has getId()
        args.putString("itemName", item.getName());
        args.putInt("itemQuantity", item.getQuantity());
        gridItemFragment.setArguments(args);

        // Perform the fragment transaction
        if (isAdded()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, gridItemFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
    // Initiates the fragment transaction to add a new item

    private void navigateToAddNewItem() {
        GridItem gridItemFragment = new GridItem();
        // Perform the fragment transaction to add a new item
        if (isAdded()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, gridItemFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the RecyclerView to display any new or updated items

        setupRecyclerView(getView());
    }
}
