package com.example.healthtrack.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthtrack.R;
import com.example.healthtrack.model.HealthStatusRecord;
import com.example.healthtrack.util.SimpleLineChartView;
import com.example.healthtrack.viewmodel.ReportViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private ReportViewModel viewModel;
    private SimpleLineChartView chartSleep, chartScreen, chartSteps, chartWeight;
    private TextView tvAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        String username = getIntent().getStringExtra("USERNAME");
        viewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        // Init Views
        chartSleep = findViewById(R.id.chart_sleep);
        chartScreen = findViewById(R.id.chart_screen);
        chartSteps = findViewById(R.id.chart_steps);
        chartWeight = findViewById(R.id.chart_weight);
        tvAdvice = findViewById(R.id.tv_advice);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        // --- Load Data
        viewModel.getRecentRecords(username).observe(this, records -> {
            if (records != null && !records.isEmpty()) {
                List<HealthStatusRecord> chartData = new ArrayList<>(records);
                Collections.reverse(chartData);
                setupCharts(chartData);
                generateAdvice(chartData.get(chartData.size() - 1));
            } else {
                tvAdvice.setText("No data available to generate report.");
            }
        });
    }

    private void setupCharts(List<HealthStatusRecord> records) {
        List<String> dates = new ArrayList<>();
        List<Float> sleep = new ArrayList<>();
        List<Float> screen = new ArrayList<>();
        List<Float> steps = new ArrayList<>();
        List<Float> weight = new ArrayList<>();

        for (HealthStatusRecord r : records) {
            // 简单防空保护，防止 substring 崩溃（虽然一般数据库里会有日期）
            if (r.getDate() != null && r.getDate().length() >= 5) {
                dates.add(r.getDate().substring(5)); // show MM-dd
            } else {
                dates.add(r.getDate());
            }
            sleep.add(r.getSleep_time());
            screen.add(r.getScreen_time());
            steps.add((float) r.getSteps());
            weight.add(r.getWeight());
        }

        chartSleep.setData(sleep, dates);
        chartScreen.setData(screen, dates);
        chartSteps.setData(steps, dates);
        chartWeight.setData(weight, dates);
    }

    // -- Generate Advice Logic
    private void generateAdvice(HealthStatusRecord latest) {
        StringBuilder advice = new StringBuilder("Health Advice:\n\n");

        // -- Sleep Time Advice
        if (latest.getSleep_time() < 7.0) {
            advice.append("- Sleep: You slept less than 7 hours. Try to get more rest.\n");
        } else {
            advice.append("- Sleep: Great job on your sleep schedule!\n");
        }

        // Screen Time Advice
        if (latest.getScreen_time() > 6.0) {
            advice.append("- Screen: High screen time detected. Take breaks for your eyes.\n");
        } else {
            advice.append("- Screen: Your screen time is within a healthy range.\n");
        }

        // Steps Count Advice
        if (latest.getSteps() < 5000) {
            advice.append("- Activity: Try to walk more. Aim for 8000 steps.\n");
        } else {
            advice.append("- Activity: You are active! Keep it up.\n");
        }

        tvAdvice.setText(advice.toString());
    }
}