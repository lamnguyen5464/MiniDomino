package com.example.lforestor.dominodemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    GamePlay gamePlay;
    Handler handler = new Handler();
    Handler hl = new Handler();
    static int SCORE;
    static boolean dark=true;
    static boolean volume=true;
    boolean lost=false;
    static SoundPlayer sound;

    public void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ad
//        MobileAds.initialize(this,"ca-app-pub-3182913652008713~8355473028");
//        AdView admview = (AdView)findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        admview.loadAd(adRequest);

        //
        gamePlay = new GamePlay(this);
        sound = new SoundPlayer(this);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.vMain);
        layout.addView(gamePlay);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("teoteo","lost");
                        gamePlay.invalidate();
                        lost = gamePlay.getRes();
                        if (lost){
                            timer.cancel();
                            vibrate();
                            hl.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("score",SCORE);
                                    startActivity(intent);
                                }
                            },1000);
                        }
                    }
                });
            }
        }, 0,10);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
