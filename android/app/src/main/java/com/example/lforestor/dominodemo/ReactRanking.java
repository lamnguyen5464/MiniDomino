package com.example.lforestor.dominodemo;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

public class ReactRanking extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "Domino";
    }
    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new ReactActivityDelegate(this, getMainComponentName()) {
            @Override
            protected Bundle getLaunchOptions() {

                Bundle initialProperties = new Bundle();
                Intent intent = getIntent();
                initialProperties.putString("nickName", intent.getStringExtra("NICKNAME"));
                initialProperties.putString("bestScore", Integer.toString(intent.getIntExtra("BESTSCORE",0)));
                return initialProperties;
            }
        };
    }
}
