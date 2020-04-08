package com.example.lforestor.dominodemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.welcome);
        ImageView imageView = (ImageView) findViewById(R.id.pic);
        TextView title = (TextView) findViewById(R.id.t1);
        TextView nameTag = (TextView) findViewById(R.id.nameTag);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.math_tapping);
        title.setTypeface(typeface);
        nameTag.setTypeface(typeface);

        imageView.startAnimation(animation);
        Timer timer = new Timer();
        timer.schedule(new after_delay(),2000);
    }
    class after_delay extends TimerTask {
        public void run(){
            Intent i = new Intent(Splash.this, MenuActivity.class);
            startActivity(i);
            finish();
        }
    }

}
