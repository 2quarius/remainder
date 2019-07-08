package com.example.trail.NewTask;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.example.trail.R;

public class NewTaskActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtask);
//        Button btn = findViewById(R.id.btn);
//        btn.setBackground(Drawable.createFromPath("G:\\GitHub\\remainder\\trail\\app\\src\\main\\res\\drawable-hdpi\\place.jpg"));
    }


}
