/**
 * DataGridFragment.java
 *
 * Purpose:
 * This class represents a fragment within the application that displays a grid of inventory items.
 * It uses a RecyclerView to list items fetched from the database, providing a dynamic and responsive user interface.
 * Users can navigate from this fragment to add new items or view and edit existing item details.
 *
 *  Features:
 * - Efficient List Management: Uses RecyclerView for memory-efficient display and scrolling of large datasets.
 * - Dynamic Data Handling: Dynamically fetches inventory data from the SQLite database and updates the UI accordingly.
 *
 *
 * Algorithmic and UX/UI Design Considerations:
 * - The RecyclerView is set up with a LinearLayoutManager to display items in a linear list.
 * - Custom ItemDecoration is applied to the RecyclerView to manage the spacing between items, enhancing the UI.
 * - Database interactions are handled through the DatabaseHelper class, demonstrating effective use of data structures to fetch and display data.
 * - Bundle data structure is utilized for passing item details between fragments, showcasing efficient data handling and navigation within the app.
 *
 * Meeting Course Outcome:
 * - The setup and management of the RecyclerView in `setupRecyclerView` method reflect algorithmic thinking in handling UI elements and data presentation. It demonstrates evaluating and applying suitable data structures (like lists) and UI components for optimal performance and user experience.
 * - This class also illustrates the practical application of computer science standards in UI design, particularly in the Android's RecyclerView and Adapter pattern to efficiently manage and display data.
 *
 *  * Reflecting on the Enhancement Process:
 *  * Developing the `DataGridFragment` highlighted the challenges and solutions in creating fluid and responsive UIs for data-intensive applications. Learning to effectively use RecyclerView transformed the application's data presentation layer,  theoretically improving scrolling performance and memory usage.
 *
 * Author: [Jared Semonin]
 * Date: [03/31/2024]
 * Version: 1.0
 */



package com.semonin.jjwarehouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataGridFragment extends Fragment {

    public DataGridFragment() {
        // Required empty public constructor
    }

    public static DataGridFragment newInstance() {
        return new DataGridFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set up the RecyclerView to display inventory items
        setupRecyclerView(view);

        // Set an OnClickListener to navigate to the item addition fragment
        ImageButton addDataButton = view.findViewById(R.id.addDataButton);

        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddNewItem();
            }
        });
    }

    //ALGO     // Sets up the RecyclerView with a LinearLayoutManager and an adapter
    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.dataGrid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adding spacing between RecyclerView items

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_spacing); // Define this dimension in your res/values/dimens.xml
        recyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));

        // Fetching items from the database and setting up the adapter

        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Item> items = db.getItems();

        InventoryAdapter adapter = new InventoryAdapter(items, new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                navigateToGridItemFragmentWithItemDetails(item);
            }
        });
        recyclerView.setAdapter(adapter);
    }
                // bundle    // Navigates to the GridItem fragment to display or edit the details of an item
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
