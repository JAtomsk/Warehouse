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


        loginButton.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            DatabaseHelper db = new DatabaseHelper(getContext());

            if (db.checkUser(username, password)) {
                //Navigates to datagridfragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DataGridFragment()).commit();
            } else {
                Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        createAccountButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrationFragment()).addToBackStack(null).commit());

        return view;
    }
}