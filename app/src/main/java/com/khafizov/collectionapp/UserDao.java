package com.khafizov.collectionapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Query("SELECT * FROM User ORDER BY id DESC LIMIT 1")
    User getLastUser();
}