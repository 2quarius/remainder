package com.example.trail.NewTask;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.trail.R;

public class NewTaskActivity extends Activity {
    private RadioButton rbTimePoint;
    private RadioButton rbTimeExtention;
    private TextView txYear;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtask);
    }


}
