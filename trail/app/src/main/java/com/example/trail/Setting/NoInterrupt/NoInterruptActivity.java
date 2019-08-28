package com.example.trail.Setting.NoInterrupt;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trail.R;

public class NoInterruptActivity extends AppCompatActivity {

    private ImageButton music_btn;
    private ImageButton end_btn;
    private Boolean musicOn;
    private int touchX;
    private boolean isCompelted = false;
    private boolean move = false;
    private MediaPlayer player;

    protected void hideBottomUIMenu() {
        /*if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {*/
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //}
    }

    private void startmusic() {
        player = MediaPlayer.create(this, R.raw.qiangu);
        player.start();
    }

    private void endmusic() {
        player.stop();
    }

    @SuppressLint("ClickableViewAccessibility")
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

        music_btn = findViewById(R.id.btn_music);
        music_btn.setOnClickListener(new View.OnClickListener() { //qq
            @Override
            public void onClick(View view) {
                if (musicOn==false) {
                    Animation animation = AnimationUtils.loadAnimation(NoInterruptActivity.this, R.anim.music_btn_anim);
                    LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
                    animation.setInterpolator(lin);
                    musicOn = true;
                    startmusic();
                    music_btn.startAnimation(animation);
                }
                else {
                    Animation animation = AnimationUtils.loadAnimation(NoInterruptActivity.this, R.anim.music_btn_stop);
                    musicOn = false;
                    endmusic();
                    music_btn.startAnimation(animation);
                }
            }
        });

        end_btn = findViewById(R.id.btn_end);
        end_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //int[] endbtnlocation = new int[2];
                //end_btn.getLocationOnScreen(endbtnlocation);
                //Toast.makeText(NoInterruptActivity.this,""+end_btn.getLeft()+" "+end_btn.getTop(),Toast.LENGTH_SHORT).show();

                if (!isCompelted) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchX = (int) event.getRawX();
                            if (touchX>280&&touchX<480) {
                                move = true;
                            }
                        case MotionEvent.ACTION_MOVE:
                            touchX = (int) event.getRawX();
                            //Toast.makeText(NoInterruptActivity.this,""+touchX+" "+end_btn.getLeft(),Toast.LENGTH_SHORT).show();
                            if (move) {
                                if (end_btn.getLeft()>-280||end_btn.getLeft()<280) {
                                    end_btn.setLeft(touchX-380);
                                }
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (move) {
                                move = false;
                                if (end_btn.getLeft()<-200) {
                                    end_btn.setLeft(-280);
                                    player.release();
                                    player = null;
                                    NoInterruptActivity.this.finish();
                                }
                                else if (end_btn.getLeft()>200) {
                                    end_btn.setLeft(280);
                                    player.release();
                                    player = null;
                                    NoInterruptActivity.this.finish();
                                }
                                else {
                                    end_btn.setLeft(0);
                                }
                            }
                            break;
                    }
                }
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
