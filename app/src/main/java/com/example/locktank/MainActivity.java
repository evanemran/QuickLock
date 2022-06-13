package com.example.locktank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.example.locktank.adapters.PackageListAdapter;
import com.example.locktank.adapters.ViewPagerAdapter;
import com.example.locktank.fragments.InstalledFIlesFragment;
import com.example.locktank.fragments.SystemFilesFragment;
import com.example.locktank.fragments.VaultFragment;
import com.example.locktank.model.InstalledApps;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PackageManager packageManager;
    RecyclerView recyclerView_home;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public TextView textView_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

//        recyclerView_home = findViewById(R.id.recyclerView_home);

        packageManager = getPackageManager();

//        recyclerView_home.setHasFixedSize(true);
//        recyclerView_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        PackageListAdapter packageListAdapter = new PackageListAdapter(this, getInstalledApps(true));
//        recyclerView_home.setAdapter(packageListAdapter);

    }

    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        SystemFilesFragment systemFilesFragment = new SystemFilesFragment();
        InstalledFIlesFragment installedFIlesFragment = new InstalledFIlesFragment();
        VaultFragment vaultFragment = new VaultFragment();

        viewPagerAdapter.addFragment(systemFilesFragment, "System");
        viewPagerAdapter.addFragment(installedFIlesFragment, "Installed");
        viewPagerAdapter.addFragment(vaultFragment, "Vault");

        viewPager.setAdapter(viewPagerAdapter);
    }
}