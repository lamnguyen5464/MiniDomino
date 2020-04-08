package com.example.lforestor.dominodemo;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.lforestor.dominodemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private String TAG = "logcat";
    SharedPreferences sharedPreferences;
    ProgressBar pb;
    Button btConfirm;
    static boolean check(String s){
        if (s.length()==0) return false;
        for(int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            if ('a'<=ch&&ch<='z' || 'A'<=ch&&ch<='Z' || '0'<=ch&&ch<='9' || ch=='_') ch='a';
            else return false;
        }
        return true;
    }
    void createUser(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email+"@teoapp.com", "123123123")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nickName",email);
                            editor.commit();
                            finish();
//                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            pb.setVisibility(View.INVISIBLE);
                            btConfirm.setClickable(true);
                            Toast.makeText(SignUp.this, "Your nick name was already used by other user or There are some problems with your Internet connection"
                                    ,Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        btConfirm= (Button) findViewById(R.id.button);
        EditText input = (EditText) findViewById(R.id.input);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.math_tapping);
        input.setTypeface(typeface);
        btConfirm.setTypeface(typeface);
        //
        sharedPreferences = getSharedPreferences("bestscore",MODE_PRIVATE);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input.getText().toString();
                if (check(name)) {
                    pb.setVisibility(View.VISIBLE);
                    btConfirm.setClickable(false);
                    createUser(input.getText().toString());
                }else{
                    Toast.makeText(SignUp.this, "Only a-z, A-Z, 0-9 and '_' are allowed",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
