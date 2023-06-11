package com.evanemran.quicklock.database;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.evanemran.quicklock.model.InstalledApps;

import java.util.List;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    void insert(InstalledApps apps);

    @Delete
    void delete(InstalledApps apps);

    @Query("UPDATE apps SET isLocked = :status WHERE id = :id")
    void update(int id, boolean status);

    @Query("SELECT * FROM apps ORDER BY title ASC")
    List<InstalledApps> getAll();

    @Query("SELECT * FROM apps WHERE isLocked = 1 ORDER BY title ASC")
    List<InstalledApps> getAllLockedApp();
}
