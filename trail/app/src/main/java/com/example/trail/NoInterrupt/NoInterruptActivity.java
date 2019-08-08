package com.example.trail.NoInterrupt;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trail.R;

public class NoInterruptActivity extends AppCompatActivity {

    private ImageButton music_btn;
    private ImageButton end_btn;
    private Boolean musicOn;
    public float width;

    protected void hideBottomUIMenu() {
        /*if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {*/
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //}
    }

    private void startmusic() {

    }

    private void endmusic() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_interrupt);
        //View theview = new CustomSwitchView(this);
        //setContentView(theview);
        //((CustomSwitchView) theview).setOnScrollCompletedListener(this);
        //Toast.makeText(NoInterruptActivity.this,"成功",Toast.LENGTH_SHORT).show();
        hideBottomUIMenu();

        musicOn = false;
        width =  this.getResources().getDisplayMetrics().widthPixels;
        Toast.makeText(NoInterruptActivity.this,""+width,Toast.LENGTH_SHORT).show();

        music_btn = findViewById(R.id.btn_music);
        music_btn.setOnClickListener(new View.OnClickListener() { //qq
            @Override
            public void onClick(View view) {
                if (musicOn==false) {
                    musicOn = true;
                    startmusic();
                }
                else {
                    musicOn = false;
                    endmusic();
                }
            }
        });

        end_btn = findViewById(R.id.btn_end);
        end_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(NoInterruptActivity.this,"控件内部的触摸事件 ACTION",Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    /*@Override
    public void scrollCompleted() {
        Intent intent = new Intent(NoInterruptActivity.this, MainActivity.class);
        startActivity(intent);
    }*/
}
