package com.example.trail.NoInterrupt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trail.MainActivity;
import com.example.trail.R;

public class NoInterruptActivity extends AppCompatActivity {

    protected void hideBottomUIMenu() {
        /*if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {*/
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_interrupt);
        //View theview = new CustomSwitchView(this);
        //setContentView(theview);
        //((CustomSwitchView) theview).setOnScrollCompletedListener(this);
        Toast.makeText(NoInterruptActivity.this,"成功",Toast.LENGTH_SHORT).show();
        hideBottomUIMenu();
    }

    /*@Override
    public void scrollCompleted() {
        Intent intent = new Intent(NoInterruptActivity.this, MainActivity.class);
        startActivity(intent);
    }*/
}
