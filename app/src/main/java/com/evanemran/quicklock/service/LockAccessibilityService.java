package com.evanemran.quicklock.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.evanemran.quicklock.LockScreenActivity;
import com.evanemran.quicklock.database.RoomDb;
import com.evanemran.quicklock.model.InstalledApps;

import java.util.ArrayList;
import java.util.List;

public class LockAccessibilityService extends AccessibilityService {

    public static final String TAG = "LockService";
    RoomDb database;
    List<InstalledApps> dbList = new ArrayList<>();

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPES_ALL_MASK;

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;


        info.notificationTimeout = 100;

        this.setServiceInfo(info);

        Log.e(TAG, "onServiceConnected: " );
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "onAccessibilityEvent: ");

        String packageName = event.getPackageName().toString();
        Log.e(TAG, "packagename: " + packageName);

        database = RoomDb.getInstance(this);
        dbList = database.mainDAO().getAllLockedApp();

        List<String> lockedPackages = new ArrayList<>();

        for(InstalledApps apps : dbList) {
            lockedPackages.add(apps.getPackageName());
        }

        if (lockedPackages.contains(packageName) /*packageName.contains("com.evanemran.calculator")*/) {
            Intent lockIntent = new Intent(getApplicationContext(), LockScreenActivity.class);
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            lockIntent.putExtra("package", packageName);
            getApplicationContext().startActivity(lockIntent);
        }

    }

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt: something wrong" );
    }
}
