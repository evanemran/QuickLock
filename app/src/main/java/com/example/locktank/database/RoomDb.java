package com.example.locktank.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.locktank.model.InstalledApps;

@Database(entities = {InstalledApps.class}, version = 2, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {
    private static RoomDb database;
    private static String DATABASE_NAME = "LockTank";

    public synchronized static RoomDb getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            /*//adding demo data
            Notes note1 = new Notes();
            Notes note2 = new Notes();
            Notes note3 = new Notes();
            note1.setTitle("Your title");
            note1.setNotes("Your description here!");
            note2.setTitle("Create a new note");
            note2.setNotes("You can edit this note or also make a new note by clicking on the \"plus\" button below.");
            note3.setTitle("Task name");
            note3.setNotes("Things to do!");
            database.mainDAO().insert(note1);
            database.mainDAO().insert(note2);
            database.mainDAO().insert(note3);*/
        }
        return database;
    }

    public abstract MainDao mainDAO();
}
