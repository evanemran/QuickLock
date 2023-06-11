package com.evanemran.quicklock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.evanemran.quicklock.service.LockAccessibilityService;
import com.example.locktank.R;
import com.evanemran.quicklock.adapters.DrawerAdapter;
import com.evanemran.quicklock.adapters.ViewPagerAdapter;
import com.evanemran.quicklock.fragments.InstalledFIlesFragment;
import com.evanemran.quicklock.fragments.SystemFilesFragment;
import com.evanemran.quicklock.fragments.VaultFragment;
import com.evanemran.quicklock.listeners.NavMenuClickListener;
import com.evanemran.quicklock.model.NavMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    PackageManager packageManager;
    RecyclerView recyclerView_home;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public TextView textView_count;
    DrawerLayout drawer;
    TextView version_name;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        version_name = findViewById(R.id.version_name);
        button_save = findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                startService(new Intent(MainActivity.this, LockAccessibilityService.class));
            }
        });

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.open_nav_drawer, R.string.close_nav_drawer
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNavMenu();

//        recyclerView_home = findViewById(R.id.recyclerView_home);

        packageManager = getPackageManager();

//        recyclerView_home.setHasFixedSize(true);
//        recyclerView_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        PackageListAdapter packageListAdapter = new PackageListAdapter(this, getInstalledApps(true));
//        recyclerView_home.setAdapter(packageListAdapter);

    }

    private void setupNavMenu() {
        List<NavMenu> navMenus = new ArrayList<>();
        navMenus.add(NavMenu.HOME);
        navMenus.add(NavMenu.SETTINGS);
        navMenus.add(NavMenu.THEME);
        navMenus.add(NavMenu.ABOUT);
        navMenus.add(NavMenu.POLICY);
        RecyclerView recycler_nav = findViewById(R.id.recycler_nav);
        recycler_nav.setHasFixedSize(true);
        recycler_nav.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, navMenus, navMenuClickListener);
        recycler_nav.setAdapter(drawerAdapter);

        try {
            PackageInfo pInfo =
                    getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version_name.setText("Version: " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        SystemFilesFragment systemFilesFragment = new SystemFilesFragment();
        InstalledFIlesFragment installedFIlesFragment = new InstalledFIlesFragment(false);
        InstalledFIlesFragment lockedFilesFragment = new InstalledFIlesFragment(true);
        VaultFragment vaultFragment = new VaultFragment();

//        viewPagerAdapter.addFragment(systemFilesFragment, "System");
        viewPagerAdapter.addFragment(installedFIlesFragment, "Installed");
        viewPagerAdapter.addFragment(lockedFilesFragment, "Locked");
//        viewPagerAdapter.addFragment(vaultFragment, "Vault");

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private final NavMenuClickListener navMenuClickListener = new NavMenuClickListener() {
        @Override
        public void onNavClicked(NavMenu menu) {
            Toast.makeText(MainActivity.this, "Will be added soon!", Toast.LENGTH_SHORT).show();
        }

    };
}