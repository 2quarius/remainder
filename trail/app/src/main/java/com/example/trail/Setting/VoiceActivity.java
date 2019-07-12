package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.trail.R;

import static java.nio.file.attribute.AclEntryType.ALARM;

public class VoiceActivity extends AppCompatActivity {
    private SeekBar sbVoice0;
    private SeekBar sbVoice1;
    private Switch vibra;
    private AudioManager mAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        sbVoice0=findViewById(R.id.sb_voice0);
        sbVoice0.setMax(getMaxSystemVolume());
        sbVoice0.setProgress(getSystemVolume());
        sbVoice0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int systemVoice=sbVoice0.getProgress();
                changeSystemVolume(systemVoice);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        sbVoice1=findViewById(R.id.sb_voice1);
        sbVoice1.setMax(getMaxMusicVolume());
        int max=getMaxMusicVolume();
        Log.d("ALARM","Max:"+max);
        sbVoice1.setProgress(getMusicVolume());
        sbVoice1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int musicVoice=sbVoice1.getProgress();
                changeMusicVolume(musicVoice);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        vibra=findViewById(R.id.vibration);
        vibra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Vibrator vb = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    vb.cancel();
                }
            }
        });
    }



    //改变系统音量
    private void changeSystemVolume(int voice){
        int max=0,current=0;
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
        Log.d("ALARM","MAX: "+max+" current: "+current);
        mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, voice, AudioManager.FLAG_PLAY_SOUND);
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
        Log.d("ALARM","MAX: "+max+" current: "+current);
    }

    //获取当前系统音量
    private int getSystemVolume(){
        int volume=0;
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        volume = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
        return volume;
    }

    //获取系统最大音量
    private int getMaxSystemVolume(){
        int maxVolume=0;
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
        return maxVolume;
    }

    //获取当前媒体音量
    private int getMusicVolume(){
        int volume=0;
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        volume = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        return volume;
    }

    //获取最大媒体音量
    private int getMaxMusicVolume(){
        int maxVolume=0;
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        maxVolume=mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
        return maxVolume;
    }
    //改变媒体音量
    private void changeMusicVolume(int voice){
        int max=0,current=0;
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        Log.d("ALARM","MAX: "+max+" current: "+current);
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, voice, AudioManager.FLAG_PLAY_SOUND);
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        Log.d("ALARM","MAX: "+max+" current: "+current);
    }
}
