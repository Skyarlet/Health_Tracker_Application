package com.example.healthtrack.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.healthtrack.model.User;
import com.example.healthtrack.repository.HealthRepository;

public class LoginViewModel extends AndroidViewModel{
    private final HealthRepository repository;
    public MutableLiveData<Boolean> loginResult = new MutableLiveData<>();
    public MutableLiveData<String> registerResult = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application){
        super(application);
        repository = HealthRepository.getInstance(application);
    }

    public void login(String username, String password){
        repository.login(username, password, user -> {
            loginResult.postValue(user != null);
        });
    }

    public void register(String username, String password){
        repository.register(username, password, success ->{
            if(success) registerResult.postValue("Registration Successful");
            else registerResult.postValue("Username already exists");
        });
    }


}
