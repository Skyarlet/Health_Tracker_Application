package com.example.healthtrack.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * User entity for authentication.
 * Stores username and password.
 */
@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    private String username;
    private String password;

    public User(@NonNull String username, String password){
        this.username = username;
        this.password = password;
    }

    @NonNull
    public String getUsername() { return username; }
    public void setUsername(@NonNull String username){ this.username = username; }

    public String getPassword() { return  password; }
    public void setPassword(String password) { this.password = password; }
}
