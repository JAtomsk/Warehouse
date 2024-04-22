/**
 * Class Name: MainActivity
 *
 * Purpose:
 * The MainActivity class serves as the entry point for the Android application. It initializes the
 * main layout and is responsible for managing the initial fragment transaction that loads the
 * login interface into the application.
 *
 * Features:
 * - Fragment Management: Handles the addition of the initial fragment to the user interface.
 * - Initial Setup: Sets up the main activity layout and ensures that the login fragment is loaded
 *   upon the first launch.
 *
 * Usage:
 * The MainActivity is automatically invoked when the application starts. It sets up the initial
 * view by loading the `LoginFragment`, which allows the user to log in to the application.
 *
 * Author: Jared Semonin
 * Date: 04/21/2024
 * Version: 3.0
 */

package com.semonin.jjwarehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sets the content view to the activity_main layout.

        // Checks if there is a previously saved instance of the fragment.
        if (savedInstanceState == null) {

            // Begins a transaction to add the LoginFragment to the 'fragment_container'.
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }
}