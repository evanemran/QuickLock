package com.example.locktank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.itsxtt.patternlock.PatternLockView;

import java.util.ArrayList;

public class LockScreenActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    ImageView imageView_appIcon;
    EditText editText_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lock_screen);

        imageView_appIcon = findViewById(R.id.imageView_appIcon);
        editText_pin = findViewById(R.id.edittext_pin);

        disableSoftInputFromAppearing(editText_pin);

        String packageName = getIntent().getStringExtra("package");

        try
        {
            Drawable icon = this.getPackageManager().getApplicationIcon(packageName);
            imageView_appIcon.setImageDrawable(icon);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        patternLockView = findViewById(R.id.patternLockView);

        patternLockView.setOnPatternListener(new PatternLockView.OnPatternListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(ArrayList<Integer> ids) {

            }

            @Override
            public boolean onComplete(ArrayList<Integer> ids) {
                /*
                 * A return value required
                 * if the pattern is not correct and you'd like change the pattern to error state, return false
                 * otherwise return true
                 */
                return isPatternCorrect(ids);
            }
        });

    }

    private boolean isPatternCorrect(ArrayList<Integer> ids) {
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        super.onBackPressed();
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
    }
}