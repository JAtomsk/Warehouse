/**
 * LoginFragment
 *
 * Purpose:
 * This fragment is now responsible for managing user authentication within the app by interfacing with a cloud-based database
 * via an API instead of using a local SQLite database. This change enables more scalable, secure, and versatile user management.
 *
 * How it Works:
 * - The user inputs their username and password, which are sent to the cloud database through the ApiInterface.
 * - The authentication response determines whether the user proceeds to the DataGridFragment or receives an error message.
 *
 * Security Features:
 * - Utilizes bcrypt via the API for enhanced security in password storage and verification.
 * - Integrates Retrofit for secure HTTP communication, significantly reducing vulnerabilities associated with direct database connections.
 * - Implements JSON Web Tokens (JWT) for maintaining secure sessions.
 *
 *
 Meeting Course Outcome: 5

 "DevelopED a security mindset that anticipates adversarial exploits in software architecture and designs to expose potential vulnerabilities, mitigate design flaws,
 and ensure privacy and enhanced security of data and resources by completing the following enhancements_________"

 * This class demonstrates an advanced understanding of software architecture by integrating modern cloud-based technologies and security practices.
 * It addresses the necessity for scalable and secure API interactions in modern Android applications, emphasizing best practices in network security and data management.
 *
 *
 *   Differences and Reasons for Changes:
 *
 *   From SQLite to Cloud Database:
 *   - The original class used local SQLite, limiting the app to single-device use without real-time data syncing.
 *   - The new setup using a cloud database through an API allows for real-time updates and access from multiple devices,
 *     enhancing scalability and flexibility.
 *
 *   Security Improvements:
 *   - The shift to bcrypt for password hashing (implemented via the API) and the use of JWT for session management
 *     significantly enhance security.
 *   - These methods are more robust against attacks and ensure secure transmission of sensitive information over the network.
 *
 *   API Communication:
 *   - Retrofit is used for network operations instead of direct database interactions, which abstracts the data layer
 *     and reduces direct exposure of the database to the app.
 * - This method adheres to best practices in modern application architecture, ensuring better maintenance and security.
 *
 *
 *
 *
 *
 *
 *
 * Author: Jared Semonin
 * Date: 04/14/2024
 * Version: 3.0
 */



package com.semonin.jjwarehouse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private ApiInterface apiInterface;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Initialize EditText fields for user input

        editTextUsername = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        // Initialize buttons and setup listeners for login and account creation

        ImageButton loginButton = view.findViewById(R.id.loginButton);
        ImageButton createAccountButton = view.findViewById(R.id.createAccountButton);
        // Initialize the API interface to interact with the cloud database

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        loginButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            loginUser(new User(username, password));
        });

        // Set listener for the create account button to transition to the RegistrationFragment

        createAccountButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrationFragment()).addToBackStack(null).commit());

        return view;
    }

    private void loginUser(User user) {
        Call<LoginResponse> call = apiInterface.loginUser(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Save the token using SharedPreferencesManager
                    SharedPreferenceManager.saveToken(getContext(), response.body().getToken());


                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    navigateToInventory();
                } else {
                    Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToInventory() {
        // Navigate to the inventory display fragment after successful login and token storage
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataGridFragment()).commit();
    }
}