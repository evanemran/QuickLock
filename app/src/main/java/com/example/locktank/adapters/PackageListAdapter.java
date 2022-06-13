package com.example.locktank.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locktank.R;
import com.example.locktank.model.InstalledApps;

import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListViewHolder>{

    Context context;
    List<InstalledApps> list;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String APPS = "apps";

    public PackageListAdapter(Context context, List<InstalledApps> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PackageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageListViewHolder(LayoutInflater.from(context).inflate(R.layout.list_packages, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PackageListViewHolder holder, int position) {
        final InstalledApps item = list.get(position);

        holder.textView_appName.setText(item.getTitle());
        holder.textView_packageName.setText(item.getPackageName());
        holder.imageView_package.setImageDrawable(item.getIcon());

        holder.imageView_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isLocked()){
                    item.setLocked(false);
                    holder.imageView_lock.setImageResource(R.drawable.ic_unlocked);
                }
                else{
                    item.setLocked(true);
                    holder.imageView_lock.setImageResource(R.drawable.ic_locked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class PackageListViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_package;
    ImageButton imageView_lock;
    TextView textView_appName, textView_packageName;

    public PackageListViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_packageName = itemView.findViewById(R.id.textView_packageName);
        textView_appName = itemView.findViewById(R.id.textView_appName);
        imageView_package = itemView.findViewById(R.id.imageView_package);
        imageView_lock = itemView.findViewById(R.id.imageView_lock);
    }
}
