package com.example.lforestor.dominodemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Main2Activity extends Activity {
    ImageButton bt1,bt2,bt3;
    TextView Tscore, Tbest, Tnew,gameOver, titleBest, titleScore;
    SharedPreferences sharedPreferences;
    int best, score;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_main2);
        bt1 = (ImageButton) findViewById(R.id.back);
        bt2 = (ImageButton) findViewById(R.id.home);
        bt3 = (ImageButton) findViewById(R.id.rank);
        Tbest = (TextView) findViewById(R.id.best);
        Tscore = (TextView) findViewById(R.id.score);
        titleBest = (TextView) findViewById(R.id.title1);
        titleScore = (TextView) findViewById(R.id.title2);
        Tnew = (TextView) findViewById(R.id.newscore);
        gameOver = (TextView) findViewById(R.id.gameOver);
        //add font
        Typeface typeface = ResourcesCompat.getFont(this, R.font.math_tapping);
        Tbest.setTypeface(typeface);
        Tscore.setTypeface(typeface);
        Tnew.setTypeface(typeface);
        gameOver.setTypeface(typeface);
        titleBest.setTypeface(typeface);
        titleScore.setTypeface(typeface);

        Tnew.setVisibility(View.INVISIBLE);

        //admob
        MobileAds.initialize(this,
                "ca-app-pub-8228726508975919~3851183438");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8228726508975919/4392898086");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        Log.d("TAG", "The interstitial is loading.");

        //
        Intent intent = getIntent();
        score = intent.getIntExtra("score",0);
        //
        sharedPreferences = getSharedPreferences("bestscore",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        best = sharedPreferences.getInt("bezt",0);
        if (best<score){
            Tnew.setVisibility(View.VISIBLE);
            best=score;
            editor.putInt("bezt",best);
            editor.commit();
        }
        Tscore.setText(""+score);
        Tbest.setText(""+best);
        //
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this,MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick = sharedPreferences.getString("nickName","");
                Intent intent;
                Log.d("logcat", "your nick name: "+nick);
                if (nick=="") {
                    intent = new Intent(Main2Activity.this, SignUp.class);
                }else{
                    intent = new Intent(Main2Activity.this, ReactRanking.class);
                    intent.putExtra("NICKNAME",nick);
                    intent.putExtra("BESTSCORE", best);

                }
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Main2Activity.this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        // your code.
    }
    @Override
    protected void onPause() {
        super.onPause();
       // Main2Activity.this.finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
       // Main2Activity.this.finish();
    }
}
