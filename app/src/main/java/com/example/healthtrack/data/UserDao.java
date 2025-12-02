package com.example.healthtrack.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.healthtrack.model.User;

/**
 * DAO for User operations
 */
@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User checkUserExists(String username);
}
