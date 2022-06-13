package com.example.locktank.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locktank.R;
import com.example.locktank.listeners.ClickListener;
import com.example.locktank.model.InstalledApps;

import java.util.List;

public class PackageListAdapter extends RecyclerView.Adapter<PackageListViewHolder>{

    Context context;
    List<InstalledApps> list;
    ClickListener listener;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String APPS = "apps";

    public PackageListAdapter(Context context, List<InstalledApps> list, ClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
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
        holder.imageView_package.setImageDrawable(createDrawableFromArray(item.getIcon()));
        if(item.isLocked()){
            holder.imageView_lock.setImageResource(R.drawable.ic_locked);
        }
        else holder.imageView_lock.setImageResource(R.drawable.ic_unlocked);
        //holder.imageView_package.setImageDrawable(context.getResources().getDrawable(item.getIcon(), context.getTheme()));

        holder.imageView_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isLocked()){
                    item.setLocked(false);
                    listener.onLockClicked(item);
                    holder.imageView_lock.setImageResource(R.drawable.ic_unlocked);
                }
                else{
                    item.setLocked(true);
                    listener.onLockClicked(item);
                    holder.imageView_lock.setImageResource(R.drawable.ic_locked);
                }
            }
        });
    }

    private Drawable createDrawableFromArray(byte[] icon) {
        Drawable image = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(icon, 0, icon.length));
        return image;
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
