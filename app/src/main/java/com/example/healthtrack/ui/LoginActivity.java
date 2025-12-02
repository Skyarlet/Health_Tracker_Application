package com.example.healthtrack.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthtrack.R;
import com.example.healthtrack.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity{
    private LoginViewModel viewModel;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);

        // --- Login Logic ---
        btnLogin.setOnClickListener(v -> {
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString().trim();
            if(!u.isEmpty() && !p.isEmpty()){
                viewModel.login(u, p);
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
        });

        // ---Register Logic ---
        btnRegister.setOnClickListener(v -> {
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString().trim();
            if(!u.isEmpty() && !p.isEmpty()){
                viewModel.register(u, p);
            }
        });

        // --- Observers ---
        viewModel.loginResult.observe(this, success -> {
            if (success) {
                // Save current user to SharePrefs for session management
                getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
                        .putString("CURRENT_USER", etUsername.getText().toString())
                        .apply();

                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.registerResult.observe(this, msg -> Toast
                .makeText(this, msg, Toast.LENGTH_SHORT).show());

    }
}
