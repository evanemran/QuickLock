package com.example.locktank.fragments;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locktank.LockScreenActivity;
import com.example.locktank.R;
import com.example.locktank.adapters.PackageListAdapter;
import com.example.locktank.database.RoomDb;
import com.example.locktank.listeners.ClickListener;
import com.example.locktank.model.InstalledApps;
import com.example.locktank.service.LockAccessibilityService;
import com.example.locktank.service.LockerService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InstalledFIlesFragment extends Fragment {

    View view;
    RecyclerView recycler_system;
    RoomDb database;
    List<InstalledApps> dbList = new ArrayList<>();
    Button button_save;
    Boolean isLockedFiles;

    public InstalledFIlesFragment(Boolean isLockedFiles) {
        this.isLockedFiles = isLockedFiles;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_installedfiles, container, false);
        recycler_system = view.findViewById(R.id.recycler_system);
        button_save = view.findViewById(R.id.button_save);

        database = RoomDb.getInstance(getContext());
        dbList = isLockedFiles ? database.mainDAO().getAllLockedApp() : database.mainDAO().getAll() ;

        if (dbList.isEmpty()){
            setupSystemList(getInstalledApps(true));
        }
        else setupSystemList(dbList);



        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                getActivity().startService(new Intent(getActivity(), LockAccessibilityService.class));
            }
        });

        return view;
    }

    private void setupSystemList(List<InstalledApps> appsList) {
        recycler_system.setHasFixedSize(true);
        recycler_system.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        PackageListAdapter systemFileAdapter = new PackageListAdapter(getContext(), appsList, clickListener);
        recycler_system.setAdapter(systemFileAdapter);
    }
    private List<InstalledApps> getInstalledApps(boolean getSysPackages) {
        List<InstalledApps> res = new ArrayList<InstalledApps>();
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
//            res.add(new InstalledApps(p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(),
//                    p.packageName,
//                    p.applicationInfo.loadIcon(getActivity().getPackageManager())));

            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                res.add(new InstalledApps(p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(),
                        p.packageName,
                        getIconByteArray(p.applicationInfo.loadIcon(getActivity().getPackageManager())), false));

                database.mainDAO().insert(new InstalledApps(p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(),
                        p.packageName,
                        getIconByteArray(p.applicationInfo.loadIcon(getActivity().getPackageManager())),
                        false));
            }

            /*if ((getSysPackages) && (p.versionName == null)) {
                continue;
            }
            res.add(new InstalledApps(p.applicationInfo.loadLabel(getPackageManager()).toString(),
                    p.packageName,
                    p.applicationInfo.loadIcon(getPackageManager())));*/

        }
        return res;
    }

    private byte[] getIconByteArray(Drawable loadIcon) {
        Bitmap bitmap = getBitmapFromDrawable(loadIcon);
//        Bitmap bitmap = ((BitmapDrawable) loadIcon).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }

    private final ClickListener clickListener = new ClickListener() {
        @Override
        public void onLockClicked(InstalledApps app) {
            database.mainDAO().update(app.getId(), app.isLocked());
        }
    };
}
