package com.example.lforestor.dominodemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MenuActivity extends Activity {
    Button bt1, bt2, bt3, bt4, close, close_instruction;
    Dialog dialog,dialog_instruction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        bt4 = (Button) findViewById(R.id.button4);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.math_tapping);
        bt1.setTypeface(typeface);
        bt2.setTypeface(typeface);
        bt3.setTypeface(typeface);
        bt4.setTypeface(typeface);
        //
        //set dialog info
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.about);
        close = (Button) dialog.findViewById(R.id.cl);
        //set dialog instruction
        dialog_instruction = new Dialog(this);
        dialog_instruction.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_instruction.setCanceledOnTouchOutside(true);
        dialog_instruction.setContentView(R.layout.instruction);
        close_instruction = (Button) dialog_instruction.findViewById(R.id.close);

        //
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_instruction.show();

            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        close_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_instruction.cancel();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}
