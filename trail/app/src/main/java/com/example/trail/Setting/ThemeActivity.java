package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.trail.MainActivity;
import com.example.trail.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ThemeActivity extends AppCompatActivity {
    private Button back;
    private ImageButton purple;
    private ImageButton black;
    private String text;
    final  private String FILE_NAME = "theme.txt";
    final  private String FILE_NAME3 = "theme.txt";

    private void setTheTheme() {
        String theme = "";
        try {
            FileInputStream ios = openFileInput(FILE_NAME3);
            byte[] temp = new byte[10];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            theme = sb.toString();
        }catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
        if (theme.equals("purple")) {
            setTheme(R.style.LightTheme);
        }
        else if (theme.equals("black")){
            setTheme(R.style.NightTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheTheme();
        setContentView(R.layout.activity_theme);
        back = findViewById(R.id.btn_themeBack);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        purple = findViewById(R.id.btn_themePurple);
        black = findViewById(R.id.btn_themeBlack);

        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = "purple";
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    //Log.d("errMsg", e.toString());
                }
                setTheme(R.style.LightTheme);
                startActivity(new Intent(ThemeActivity.this, MainActivity.class));
            }
        });

        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = "black";
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    //Log.d("errMsg", e.toString());
                }
                setTheme(R.style.NightTheme);
                startActivity(new Intent(ThemeActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        setTheTheme();
    }
}
