package com.example.healthtrack.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.healthtrack.data.AppDatabase;
import com.example.healthtrack.data.HealthStatusRecordDao;
import com.example.healthtrack.data.UserDao;
import com.example.healthtrack.model.HealthStatusRecord;
import com.example.healthtrack.model.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Central Repository handling data operations.
 * Connects ViewModel to Room Database.
 */
public class HealthRepository {
    private static HealthRepository instance;
    private final HealthStatusRecordDao healthDao;
    private final UserDao userDao;
    private final ExecutorService executor;

    private HealthRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        healthDao = db.healthStatusRecordDao();
        userDao = db.userDao();
        executor = Executors.newFixedThreadPool(4);
    }

    public static synchronized HealthRepository getInstance(Context context) {
        if (instance == null) {
            instance = new HealthRepository(context);
        }
        return instance;
    }

    // --- User Operations ---

    public void register(String username, String password, RepoCallback<Boolean> callback){
        executor.submit(() -> {
            User existing = userDao.checkUserExists(username);
            if (existing == null){
                userDao.insert(new User(username, password));
                callback.onResult(true); //Success
            } else{
                callback.onResult(false); //Username already be taken
            }
        });
    }

    public void login(String username, String password, RepoCallback<User> callback){
        executor.submit(() -> {
            User user = userDao.login(username, password);
            callback.onResult(user);
        });
    }


    // --- Health Record Operation ---

    public void insertRecord(HealthStatusRecord record, RepoCallback<Long> callback){
        executor.submit(() -> {
            long id = healthDao.insert(record);
            if (callback != null) callback.onResult(id);
        });
    }

    public LiveData<List<HealthStatusRecord>> getRecentRecords(String username){
        return healthDao.getRecentRecords(username);
    }

    // 回调接口，供异步方法使用
    public interface RepoCallback<T> {
        void onResult(T result);
    }
}
