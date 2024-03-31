package com.semonin.jjwarehouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        setupRecyclerView(view);

        Button addDataButton = view.findViewById(R.id.addDataButton);
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddNewItem();
            }
        });
    }

    //ALGO
    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.dataGrid);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                // bundle
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
        setupRecyclerView(getView());
    }
}
