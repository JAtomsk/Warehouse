/**
 * RegistrationFragment
 *
 * Purpose:
 * This fragment facilitates the user registration process within the app. The update to this fragment
 * reflects a shift from using a local SQLite database to a cloud-based API, ensuring that registration data is managed centrally and securely.
 *
 * How it Works:
 * - Users provide a username and password, which are validated and then sent to a cloud API for registration.
 * - The API checks if the username already exists and, if not, creates a new user account.
 * - Successful registrations are confirmed with a message, and failures due to duplicate usernames or server errors are also handled.
 *
 * Features:
 * - Input validation ensures that the username and password are not empty and that the passwords match.
 * - The cloud API integration helps avoid duplicate usernames and centralizes user management.
 * - Directs successful registrations to the login screen.
 *
 * Usage:
 * Accessed from the main activity or the login screen when a user opts to register a new account.
 *
 * Author: Jared Semonin
 * Date: 04/11/2024
 * Version: 3.0
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private ApiInterface apiInterface; // Retrofit API Interface

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        editTextUsername = view.findViewById(R.id.registrationUsername);
        editTextPassword = view.findViewById(R.id.registrationPassword);
        editTextConfirmPassword = view.findViewById(R.id.registrationPasswordConfirm);
        // Initialize submit button and set up the click listener for user registration

        ImageButton submitButton = view.findViewById(R.id.registrationSubmit);
        // Retrofit API interface for making network calls

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        submitButton.setOnClickListener(v -> {
            // Initialize EditText fields for user input

            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            // Validate user input for non-empty and matching passwords

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Username or password cannot be empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Proceed with the API call to register the user if validations pass
            registerUser(new User(username, password));
        });
        // Initialize back button to allow users to return to the previous screen

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void registerUser(User user) {
        // Make an asynchronous API call to register the user

        Call<UserResponse> call = apiInterface.registerUser(user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                // Handle successful registration

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    // Optionally navigate to login after successful registration
                } else {
                    // Handle registration failure, typically due to a duplicate username

                    Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle errors during the network request

                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}



