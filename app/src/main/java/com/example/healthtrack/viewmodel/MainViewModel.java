package com.example.healthtrack.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.healthtrack.model.HealthStatusRecord;
import com.example.healthtrack.repository.HealthRepository;
public class MainViewModel extends AndroidViewModel{
    private final HealthRepository repository;
    public MutableLiveData<Boolean> saveSucess = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application){
        super(application);
        repository = HealthRepository.getInstance(application);
    }

    public void saveRecord(String username, String date, float sleep, float screen, int steps, float weight){
        HealthStatusRecord record = new HealthStatusRecord(username, date, sleep, screen, steps, weight);
        repository.insertRecord(record, id -> {
            saveSucess.postValue(id > 0);
        });
    }


}
