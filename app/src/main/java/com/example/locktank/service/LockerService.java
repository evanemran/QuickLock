package com.example.locktank.service;

import static android.content.ContentValues.TAG;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Observable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.locktank.LockScreenActivity;

import java.security.Provider;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;


public class LockerService extends Service {
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public void onCreate() {
        Toast.makeText(this, "Congrats! Lock Service Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PackageManager packageManager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo a = p.applicationInfo;
            // skip system apps if they shall not be included
            //apps.add(p.packageName);
        }

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
        String activityOnTop = ar.topActivity.getClassName();

        check.start();


        /*if (!activityOnTop.equals("com.global.foodpanda.android")) {
            Intent lockIntent = new Intent(this, LockScreenActivity.class);
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(lockIntent);
        }
        Toast.makeText(this, "Lock Service Running", Toast.LENGTH_LONG).show();*/
        return super.onStartCommand(intent, flags, startId);
    }

    protected CountDownTimer check = new CountDownTimer(5000, 5000)
    {

        @Override
        public void onTick(long millisUntilFinished)
        {
            //Do nothing here

        }

        @Override
        public void onFinish()
        {
            PackageManager packageManager = getPackageManager();
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
            Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
            List<PackageInfo> packs = packageManager.getInstalledPackages(0);
            for(int i=0; i < packs.size(); i++)
            {
                PackageInfo p = packs.get(i);
                ApplicationInfo a = p.applicationInfo;
                // skip system apps if they shall not be included
//                apps.add(p.packageName);
            }

            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo ar = RunningTask.get(0);

            String activityOnTop = ar.topActivity.getClassName();
            String packageTop = ar.baseActivity.getPackageName();

            Log.e("TOP", activityOnTop);
//            Log.e("Package", packageTop);
            String app_name = printCurrentActivity();


            if (activityOnTop.startsWith("com.example")) {
                Intent lockIntent = new Intent(getApplicationContext(), LockScreenActivity.class);
                lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(lockIntent);
                this.cancel();
            }
            Toast.makeText(getApplicationContext(), "Lock Service Running", Toast.LENGTH_LONG).show();
            this.cancel();
            this.start();
        }
    };

    String printCurrentActivity(){
        String name = "";
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        List l = am.getRecentTasks(1 , ActivityManager.RECENT_WITH_EXCLUDED);
        Iterator i = l.iterator();
        PackageManager pm = getApplicationContext().getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RecentTaskInfo info = (ActivityManager.RecentTaskInfo)(i.next());
            try {
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(
                        info.baseActivity.getPackageName(), PackageManager.GET_META_DATA));
                Log.e("LABEL", c.toString());
                name = c.toString();
            } catch (Exception e) {
// Name Not FOund Exception
            }
        }
        return name;
    }
}
