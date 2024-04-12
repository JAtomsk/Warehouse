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
 *  -Global Adapter Variable: Made adapter a global variable for ease of access across methods, allowing for dynamic data updates, such as when filtering search results.
 * -Simplified Click Listeners: Utilized lambda expressions to simplify click listeners, making the code more readable and concise.
 * - Search Functionality: Added a setupSearch method that implements a TextWatcher on the search field. This allows for real-time filtering of the inventory items based on the user's input, significantly enhancing the app's usability.
 * - Filter Method Enhancement: The filterData method was updated to fetch filtered data from the DatabaseHelper and then update the RecyclerView's adapter with this data. This change makes the search functionality dynamic and responsive to user input.
 *  - Efficient RecyclerView Setup: Refactored RecyclerView setup into its method for clarity and to avoid repetition, particularly with setting up the adapter and layout manager.
 *
 *   Course Outcome 2:
 *   "Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent,
 *   technically sound, and appropriately adapted to specific audiences and contexts."
 *
 *   In enhancing the DataGridFragment class to include search functionality,
 *   I've  improved the application's interactivity and responsiveness, ensuring that users can effectively navigate and access inventory data.
 *   By integrating a search bar with real-time filtering capabilities, this enhancement directly contributes to delivering a more coherent and technically sound user interface.
 *   The changes made, including the addition of a TextWatcher for dynamic search queries and updating the RecyclerView adapter in real-time, demonstrate my approach to developing user-centric features.
 *   These enhancements not only make the application more intuitive and accessible but also demonstrate a commitment to professional-quality communication through visual elements.
 *
 *
 *   Course Outcome 4:
 *   "Demonstrate an ability to use well-founded and innovative techniques, skills,
 *   and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals (software engineering/design/database)."
 *
 *   The implementation of dynamic search and filtering within the DataGridFragment exemplifies the use of innovative computing techniques to enhance the application's functionality.
 *   By employing a TextWatcher to listen for changes in the search field and dynamically updating the displayed inventory items,
 *   I've leveraged fundamental computing practices to solve a key user need: the ability to quickly find specific items.
 *   This feature directly addresses industry-specific goals by improving inventory management efficiency.
 *   Also, removing redundant bindView calls in the adapter and optimizing database queries for performance are examples of how even minor enhancements contribute to a more efficient, effective computing solution.
 *   every change reflects a the application of computing principles aimed at delivering value through improved user experience and application performance.
 *
 *
 *
 *
 * Meeting Course Outcome:
 * The integration of a dynamic search feature enhances user communication by providing a responsive and user-friendly interface. This directly contributes to developing professional-quality, technically sound applications that are adapted to user needs. The application of TextWatcher for real-time data filtering exemplifies the use of innovative techniques and tools in computing practices, showcasing a practical implementation of computer solutions that deliver significant value and improve user interaction with the application.
 *
 * Reflecting on the Enhancement:
 * The process of adding search functionality underscored the importance of responsive UI design and the application of algorithmic principles in enhancing user experience. It challenged us to think creatively about data manipulation and presentation in Android development, leading to a deeper understanding of dynamic data handling and UI updates based on user interactions. This enhancement not only improved the app's usability but also its adaptability to user needs, demonstrating a key aspect of delivering industry-specific goals through well-founded computing practices.
 *
 * Author: [Jared Semonin]
 * Date: [04/11/2024]
 * Version: 2.1
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

/**
 * Version 2.1: Enhanced search functionality with real-time filtering.
 * Demonstrates optimization, time complexity, and efficiency of algorithmic logic in managing RecyclerView and database interactions.
 * Reflects enhancements that meet the course outcomes related to algorithmic principles and effective communication of technical solutions.
 */public class DataGridFragment extends Fragment {

    // Global adapter for managing RecyclerView content; allows for efficient data manipulation across different methods.

    private InventoryAdapter adapter; // Maintain adapter as a global variable for easy access

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // RecyclerView setup is encapsulated in a method for better readability and separation of concerns.

        RecyclerView recyclerView = setupRecyclerView(view); // Assign returned RecyclerView
        setupSearch(view, recyclerView);

        // Setup add data button with a lambda expression for cleaner event handling.

        ImageButton addDataButton = view.findViewById(R.id.addDataButton);
        addDataButton.setOnClickListener(v -> navigateToAddNewItem());
    }


    /**
     * Sets up RecyclerView with optimized LinearLayoutManager and custom adapter.
     * Efficient data retrieval and UI rendering with LinearLayoutManager to manage layout.
     */    private RecyclerView setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.dataGrid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Efficient database access and handling to fetch items.

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Item> items = db.getItems();

        // Adapter initialization for better encapsulation and global access.

        adapter = new InventoryAdapter(items, this::navigateToGridItemFragmentWithItemDetails); // Initialize adapter here
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    /**
     * Sets up search functionality with a TextWatcher to filter data in real-time.
     * Utilizes efficient string manipulation and database querying for optimal performance.
     */    private void setupSearch(View view, RecyclerView recyclerView) {
        EditText searchField = view.findViewById(R.id.search_field);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            // Post-text change event to filter data based on user input.

            @Override
            public void afterTextChanged(Editable editable) {
                filterData(editable.toString()); // Filters data as the user types.
            }
        });
    }


    /**
     * Filters data based on user query with an efficient search mechanism.
     * Demonstrates time complexity considerations with direct database access and updating RecyclerView.
     */    private void filterData(String query) {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Item> filteredItems = db.getItemsFilteredBy(query);
        adapter.updateData(filteredItems); // Update the adapter with filtered data
    }


    /**
     * Navigation method encapsulating Fragment transaction logic for item detail viewing.
     * Efficiently handles fragment transactions and data passing.
     */    private void navigateToGridItemFragmentWithItemDetails(Item item) {
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
    // Simple method to navigate to adding a new item, showcasing clear and efficient Fragment management.
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
        // Refresh RecyclerView content to reflect any updates or changes efficiently.

        setupRecyclerView(getView());
    }
}


