package com.example.healthtrack.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.healthtrack.model.HealthStatusRecord;
import com.example.healthtrack.repository.HealthRepository;

import java.util.List;
public class ReportViewModel extends AndroidViewModel{
    private final HealthRepository repository;

    public ReportViewModel(@NonNull Application application){
        super(application);
        repository = HealthRepository.getInstance(application);
    }

    public LiveData<List<HealthStatusRecord>> getRecentRecords(String username){
        return repository.getRecentRecords(username);
    }

}
