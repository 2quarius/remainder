package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;

import com.example.trail.R;

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
        sbVoice0.setMax(100);
        sbVoice0.setProgress(50);

        sbVoice1=findViewById(R.id.sb_voice1);
        sbVoice1.setMax(100);
        sbVoice1.setProgress(50);

        vibra=findViewById(R.id.vibration);
    }

    private void changeStreamVolume(int i){
        int max=0,current=0;
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM );
        current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, AudioManager.FLAG_PLAY_SOUND);
    }
}
