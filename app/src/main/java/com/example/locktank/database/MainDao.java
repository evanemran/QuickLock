package com.example.locktank.database;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.locktank.model.InstalledApps;

import java.util.List;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(InstalledApps apps);

    @Delete
    void delete(InstalledApps apps);

    @Query("UPDATE apps SET isLocked = :status WHERE id = :id")
    void update(int id, boolean status);

    @Query("SELECT * FROM apps ORDER BY title DESC")
    List<InstalledApps> getAll();
}
