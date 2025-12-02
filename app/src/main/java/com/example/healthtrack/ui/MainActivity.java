package com.example.healthtrack.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthtrack.R;
import com.example.healthtrack.viewmodel.MainViewModel;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private String currentUser;
    private TextView tvDate;
    private EditText etSleep, etScreen, etSteps, etWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get logged in user
        currentUser = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("CURRENT_USER", null);
        if(currentUser == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //Views
        tvDate = findViewById(R.id.tv_date);
        etSleep = findViewById(R.id.et_sleep);
        etScreen = findViewById(R.id.et_screen);
        etSteps = findViewById(R.id.et_steps);
        etWeight = findViewById(R.id.et_weight);
        Button btnDate = findViewById(R.id.btn_select_date);
        Button btnSave = findViewById(R.id.btn_save_advice);
        Button btnLogout = findViewById(R.id.btn_logout);

        // --- 1. Date Picker ---
        btnDate.setOnClickListener(v ->{
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                tvDate.setText(date);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // --- 2. Save & Get Advice ---
        btnSave.setOnClickListener(v -> {
            String date = tvDate.getText().toString();
            if (date.isEmpty() || date.equals("Select Date")){
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }
            try{
                float sleep = Float.parseFloat(etSleep.getText().toString());
                float screen = Float.parseFloat(etScreen.getText().toString());
                int steps = Integer.parseInt(etSteps.getText().toString());
                float weight = Float.parseFloat(etWeight.getText().toString());

                viewModel.saveRecord(currentUser, date, sleep, screen, steps, weight);
            } catch (NumberFormatException e){
                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show();
            }
        });

        // ---3. Logout ---
        btnLogout.setOnClickListener(v -> {
            getSharedPreferences("AppPrefs", MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // ---4. Observer ---
        viewModel.saveSucess.observe(this, success ->{
            if(success){
                Intent intent = new Intent(this, ReportActivity.class);
                intent.putExtra("USERNAME", currentUser);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }

}