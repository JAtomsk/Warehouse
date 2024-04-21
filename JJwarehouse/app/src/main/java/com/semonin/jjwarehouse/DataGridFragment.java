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

import android.util.Log;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataGridFragment extends Fragment {
    private InventoryAdapter adapter;
    private ApiInterface apiInterface;
    private String authToken; // JWT token for authentication
    private Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data_grid, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        authToken = "Bearer " + SharedPreferenceManager.getToken(getContext()); // Retrieve and prepare the JWT token for authorization header

        RecyclerView recyclerView = view.findViewById(R.id.dataGrid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new InventoryAdapter(new ArrayList<>(), this::navigateToGridItemFragmentWithItemDetails);
        recyclerView.setAdapter(adapter);

        fetchData();
        setupSearch(view);

        ImageButton addDataButton = view.findViewById(R.id.addDataButton);
        addDataButton.setOnClickListener(v -> navigateToAddNewItem());
    }

    private void fetchData() {
        String token = SharedPreferenceManager.getToken(getContext());
        apiInterface.getItems("Bearer " + token).enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getItems() != null) {
                    adapter.updateData(response.body().getItems());
                } else {
                    Toast.makeText(getContext(), "Failed to fetch items: " + (response.body() == null ? "No response" : response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    adapter.updateData(new ArrayList<>()); // Pass an empty list if fetch fails
                }
            }

            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                adapter.updateData(new ArrayList<>()); // Pass an empty list on failure
            }
        });
    }

    private void setupSearch(View view) {
        EditText searchField = view.findViewById(R.id.search_field);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Cancel any previous search operations
                searchHandler.removeCallbacks(searchRunnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Delay new search request until user stops typing for 300ms
                searchRunnable = () -> filterData(s.toString());
                searchHandler.postDelayed(searchRunnable, 300);
            }
        });
    }

    private void filterData(String query) {
        String token = SharedPreferenceManager.getToken(getContext());
        apiInterface.getFilteredItems("Bearer " + token, query).enqueue(new Callback<ItemsResponse>() {
            @Override
            public void onResponse(Call<ItemsResponse> call, Response<ItemsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body().getItems()); // Update adapter with filtered data
                } else {
                    Toast.makeText(getContext(), "Failed to fetch items: " + (response.body() == null ? "No response" : response.body().getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemsResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToAddNewItem() {
        GridItem gridItemFragment = new GridItem();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, gridItemFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToGridItemFragmentWithItemDetails(Item item) {
        GridItem fragment = new GridItem();
        Bundle args = new Bundle();
        args.putInt("itemId", item.getId());
        args.putString("itemName", item.getName());
        args.putInt("itemQuantity", item.getQuantity());
        fragment.setArguments(args);

        if (isAdded()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}

