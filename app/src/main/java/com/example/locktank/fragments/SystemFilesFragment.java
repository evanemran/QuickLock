package com.example.locktank.fragments;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locktank.R;
import com.example.locktank.adapters.PackageListAdapter;
import com.example.locktank.model.InstalledApps;

import java.util.ArrayList;
import java.util.List;

public class SystemFilesFragment extends Fragment {

    View view;
    RecyclerView recycler_system;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_systemfiles, container, false);

        recycler_system = view.findViewById(R.id.recycler_system);

        setupSystemList();
        return view;
    }

    private void setupSystemList() {
        recycler_system.setHasFixedSize(true);
        recycler_system.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        PackageListAdapter systemFileAdapter = new PackageListAdapter(getContext(), getInstalledApps(true));
        recycler_system.setAdapter(systemFileAdapter);
    }
    private List<InstalledApps> getInstalledApps(boolean getSysPackages) {
        List<InstalledApps> res = new ArrayList<InstalledApps>();
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            res.add(new InstalledApps(p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(),
                    p.packageName,
                    p.applicationInfo.loadIcon(getActivity().getPackageManager())));

//            if ((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
//                res.add(new InstalledApps(p.applicationInfo.loadLabel(getPackageManager()).toString(),
//                        p.packageName,
//                        p.applicationInfo.loadIcon(getPackageManager())));
//            }

            /*if ((getSysPackages) && (p.versionName == null)) {
                continue;
            }
            res.add(new InstalledApps(p.applicationInfo.loadLabel(getPackageManager()).toString(),
                    p.packageName,
                    p.applicationInfo.loadIcon(getPackageManager())));*/

        }
        return res;
    }
}
