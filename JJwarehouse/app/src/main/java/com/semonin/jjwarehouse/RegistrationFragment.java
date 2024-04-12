/**
 * RegistrationFragment handles the user registration process.
 *
 * Purpose:
 * Allows new users to register by providing a username and password. This fragment ensures that
 * the user inputs are valid and that the username is not already in use before creating a new account.
 *
 * Features:
 * - User input validation for registration.
 * - Checks for existing usernames to avoid duplicates.
 * - Directs successful registrations to the login screen.
 *
 * Usage:
 * This fragment is accessed from the main activity or the login screen when a user chooses to register a new account.
 *
 * Author: Jared Semonin
 * Date: 04/11/2024
 * Version: 2.0
 */




package com.semonin.jjwarehouse;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegistrationFragment extends Fragment {

    // UI components for username, password, and confirm password inputs
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        // Initialize UI components
        editTextUsername = view.findViewById(R.id.registrationUsername);
        editTextPassword = view.findViewById(R.id.registrationPassword);
        editTextConfirmPassword = view.findViewById(R.id.registrationPasswordConfirm);
        ImageButton submitButton = view.findViewById(R.id.registrationSubmit);

        // Database helper to access the database for user operations
        DatabaseHelper db = new DatabaseHelper(getContext());

        // Submit button handler for registering a new user
        submitButton.setOnClickListener(view1 -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            // Validation: Check if username is empty
            if (username.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a username.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validation: Check if passwords are filled and match
            if (password.isEmpty() || confirmPassword.isEmpty() || !password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Passwords do not match or are empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if username already exists in the database
            if (db.checkUserExists(username)) {
                Toast.makeText(getContext(), "Username already exists.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Input validation passed, proceed with registration
            db.addUser(username, password); // Ensure you're hashing the password in the addUser method
            Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

            // Navigating back to LoginFragment
            if (isAdded()) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
            }
        });

        // Back button handler for navigating back to the previous screen
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
}

