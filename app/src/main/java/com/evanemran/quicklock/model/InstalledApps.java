package com.evanemran.quicklock.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "apps")
public class InstalledApps implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id ;
    @ColumnInfo(name = "title")
    String title = "";
    @ColumnInfo(name = "packageName")
    String packageName = "";
    @ColumnInfo(name = "icon")
    byte[] icon;
    boolean isLocked = false;

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public InstalledApps(String title, String packageName, byte[] icon, boolean isLocked) {
        this.title = title;
        this.packageName = packageName;
        this.icon = icon;
        this.isLocked = isLocked;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
