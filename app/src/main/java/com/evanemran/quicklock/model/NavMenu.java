package com.evanemran.quicklock.model;

import com.example.locktank.R;

public enum NavMenu {
    HOME(R.string.home, R.drawable.ic_home),
    SETTINGS(R.string.settings, R.drawable.ic_settings),
    THEME(R.string.theme, R.drawable.ic_theme),
    ABOUT(R.string.about, R.drawable.ic_about),
    POLICY(R.string.policy, R.drawable.ic_policy);

    private int stringId = 0;
    private int icon = 0;

    NavMenu(int stringId, int icon){
        this.stringId = stringId;
        this.icon = icon;
    }

    public int getStringId() {
        return stringId;
    }

    public void setStringId(int stringId) {
        this.stringId = stringId;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
