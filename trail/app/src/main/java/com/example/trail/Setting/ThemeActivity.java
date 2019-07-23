package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.trail.MainActivity;
import com.example.trail.R;

public class ThemeActivity extends AppCompatActivity {
    private ImageButton back;
    private ImageButton purple;
    private ImageButton black;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        back=findViewById(R.id.btn_themeBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        purple=findViewById(R.id.btn_themePurple);
        black=findViewById(R.id.btn_themeBlack);

        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme(R.style.Light_Theme);
                recreate();
                startActivity(new Intent(ThemeActivity.this, MainActivity.class));
            }
        });

        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme(R.style.Night_Theme);
                recreate();
            }
        });
    }
}
