package com.evanemran.quicklock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locktank.R;
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

        editText_pin.setShowSoftInputOnFocus(false);

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
        this.finish();
        super.onBackPressed();
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
    }

    public void pinClicked(View view){
        TextView textView = (TextView) view;
        editText_pin.setText(editText_pin.getText().toString() + textView.getText().toString());
    }

    public void onOkClicked(View view){
        Button button = (Button) view;
        validatePin(editText_pin.getText().toString());
    }

    private void validatePin(String pin) {
        if (pin.isEmpty()){
            editText_pin.setError("Enter Pin");
            return;
        }
        else if (pin.equals("123456")){
            this.finish();
        }
    }
}