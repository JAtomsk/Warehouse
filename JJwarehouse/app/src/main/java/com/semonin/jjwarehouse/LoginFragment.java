/**
 * LoginFragment
 *
 * Purpose:
 * This fragment is responsible for managing user authentication within the app. It provides input fields for the
 * username and password, and buttons for initiating the login process or navigating to the account creation screen.
 *
 * How it Works:
 * - The user enters their username and password, which are then passed to the DatabaseHelper for verification.
 * - If the credentials are valid, the user is navigated to the DataGridFragment, where they can view the inventory.
 * - If the credentials are invalid, a toast message informs the user of incorrect login details.
 * - The "Create Account" button navigates the user to the RegistrationFragment for account creation.
 *
 * Security Features:
 * - Implements SHA-256 hashing for password storage and verification, enhancing the security of user credentials.
 * - By hashing passwords before storage and comparing hashed passwords during login, this class demonstrates an anticipatory approach to security, protecting against common vulnerabilities such as plain-text password storage.
 * - The use of prepared statements in DatabaseHelper for querying the database helps prevent SQL injection attacks, further securing the application's data layer.
 *
 * Meeting Course Outcome:
 * This class aligns with the course outcome of developing a security mindset by implementing best practices in secure
 * software design. Specifically, it addresses the need to anticipate and mitigate potential vulnerabilities in software
 * architecture and design, ensuring the privacy and security of user data through:
 * - Secure hashing of passwords (SHA-256).
 * - Use of secure database access patterns to protect against SQL injection.
 *
 * Reflecting on the Enhancement Process:
 * Enhancing the app to include secure login functionality presented challenges, particularly in securely managing user credentials and ensuring a robust authentication mechanism. Implementing SHA-256 hashing and learning about secure database operations were essential steps in addressing these challenges. This process highlighted the importance of a proactive security mindset in software development, emphasizing the need to design with privacy and data security as central considerations.
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class LoginFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize  UI components here
        editTextUsername = view.findViewById(R.id.username);
        editTextPassword = view.findViewById(R.id.password);
        ImageButton loginButton = view.findViewById(R.id.loginButton);
        ImageButton createAccountButton = view.findViewById(R.id.createAccountButton);

        // Handle login button click

        loginButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Authenticate user

            DatabaseHelper db = new DatabaseHelper(getContext());

            if (db.checkUser(username, password)) {
                // Navigate to the DataGridFragment upon successful login
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataGridFragment()).commit();
            } else {
                // Notify user of invalid credentials
                Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigate to the registration screen
        createAccountButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrationFragment()).addToBackStack(null).commit());

        return view;
    }
}