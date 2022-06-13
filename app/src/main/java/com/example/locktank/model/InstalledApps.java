package com.example.locktank.model;

import android.graphics.drawable.Drawable;

public class InstalledApps {
    String title = "";
    String packageName = "";
    Drawable icon;
    boolean isLocked = false;

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
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

    public InstalledApps(String title, String packageName, Drawable icon) {
        this.title = title;
        this.packageName = packageName;
        this.icon = icon;
    }

    public InstalledApps(String title, String packageName, Drawable icon, boolean isLocked) {
        this.title = title;
        this.packageName = packageName;
        this.icon = icon;
        this.isLocked = isLocked;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public InstalledApps(String title, String packageName) {
        this.title = title;
        this.packageName = packageName;
    }
}
