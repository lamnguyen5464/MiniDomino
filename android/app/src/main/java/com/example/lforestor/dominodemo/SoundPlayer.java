package com.example.lforestor.dominodemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.lforestor.dominodemo.R;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int hit;
    private static int lose;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createSoundPoolWithBuilder(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(3).build();
    }
    public SoundPlayer(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) createSoundPoolWithBuilder();
        else soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        hit = soundPool.load(context, R.raw.hit,1);
        lose = soundPool.load(context,R.raw.lose,1);
    }
    public void PlayHitSound(){
        soundPool.play(hit,1,1,1,0,1);
    }
    public void PlayLoseSound(){
        soundPool.play(lose,1,1,1,0,1);
    }
}
