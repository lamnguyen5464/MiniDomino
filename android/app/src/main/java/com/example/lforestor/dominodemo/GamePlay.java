package com.example.lforestor.dominodemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.lforestor.dominodemo.MainActivity.sound;
import static java.lang.Math.min;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.max;

import java.util.ArrayList;
import java.util.Random;

public class GamePlay extends View {
    float x,prex,getX,getY;
    float width, height;
    float Const_y,y = 800,by=-500,rx,ry=-500,speed=10,rx1,rx2,ry1,ry2,x1,x2,y2,dr=-1;
    int Size,score = 0;
    byte nt,nd,tp,bt;
    boolean touch=false;
    protected byte epsi =0,max_epsi = 10,min_epsi=10;
    Paint text_white = new Paint();
    Paint text_black = new Paint();
    Paint tap = new Paint();
    Paint background_dark = new Paint();
    Paint background_light = new Paint();
    Drawable Bt,Tp,b1,b2,rt,rd,theme,volume,finger;
    Random rand = new Random();
    boolean lost=false, first_load = true, touched=false;
    boolean Cast_processing = false;
    byte Cast_mode=0,tmp=0,t1,t2=0,c1,c2,cT,cB;
    android.content.res.TypedArray T_arr;
    GamePlay(Context context){
        super(context);
        //ad

        //

        //font
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.math_tapping);
        text_white.setTypeface(typeface);
        text_black.setTypeface(typeface);
        tap.setTypeface(typeface);
        //dark theme
        text_white.setColor(Color.WHITE);
        text_white.setTextSize(100);
        background_dark.setStyle(Paint.Style.FILL);
        int c = context.getResources().getColor(R.color.colorDark);
        background_dark.setColor(c);
        //bright theme
        text_black.setColor(Color.BLACK);
        text_black.setTextSize(100);
        background_light.setStyle(Paint.Style.FILL);
        int C = context.getResources().getColor(R.color.colorBright);
        background_light.setColor(C);

        //
        tap.setColor(Color.parseColor("#8A8A8A"));
        tap.setTextSize(40);
        finger = getResources().getDrawable(R.drawable.touch);
        //
        T_arr = getResources().obtainTypedArray(R.array.pic);
        tp = (byte)rand.nextInt(7); bt = (byte)rand.nextInt(7);
        cB = (byte)(rand.nextInt(4)+1); cT = (byte)(rand.nextInt(4)+1);
        Bt = getdrawable(cB,bt); rt = Bt;
        Tp  = getdrawable(cT,tp); rd = Tp;
        b1 = getResources().getDrawable(R.drawable.border);
        b2 = getResources().getDrawable(R.drawable.border);
        //
        if (MainActivity.dark==true) theme = getResources().getDrawable(R.drawable.dark);
        else theme = getResources().getDrawable(R.drawable.light);

        if (MainActivity.volume==true) volume = getResources().getDrawable(R.drawable.sound);
        else volume = getResources().getDrawable(R.drawable.notsound);
    }
    boolean getRes(){
        return lost;
    }
    int getScore(){return score;}
    protected Drawable getdrawable(int color, int num){
        return T_arr.getDrawable((color-1)*10+num);
    }

    void update(){
        //update immediately
        x1=(int)prex-Size/2; x2=(int)prex+Size/2; y2 = y+Size*2+5;
        rx1 = (int) rx-Size/2; rx2 = (int) rx+Size/2; ry1=ry; ry2 = ry1+Size*2+5;
        //
    }

    void random_fallen(){
        if (ry > height){ touched=false; ry = -500; if (score<20 &&(score&1)==0) speed++;else if (score>20 &&score%3==0)speed++;}
        if (ry == -500){
            rx = rand.nextInt((int)width - Size-11) + (int)Size/2+10;
            nd =(byte) rand.nextInt(100);
            if (nd < 70) {nt = (byte)rand.nextInt(7); nd = tp;}
            else {nd = (byte)rand.nextInt(7); nt = bt;}
            c1 = (byte)(rand.nextInt(4)+1); c2 = (byte)(rand.nextInt(4)+1);
            rt = getdrawable(c1,nt);
            rd = getdrawable(c2,nd);
        }
        ry+=speed;
    }
    void Draw(Canvas canvas){
        update();
        //theme switch
        theme.setBounds(11,(int)11,11+100,(int)111);
        theme.draw(canvas);
        //volume switch
        volume.setBounds((int)width-111,(int)11,(int)width-11,(int)111);
        volume.draw(canvas);
        //current cube
        Tp.setBounds((int)x1,(int)y,(int)x2,(int)y+Size);
        Bt.setBounds((int)x1,(int)y+Size+5,(int)x2,(int)y2);
        Tp.draw(canvas);
        Bt.draw(canvas);
        //Instruct
        //draw finger when ti==0

        //border
        b1.setBounds(0, (int)by,10,(int)by+700);
        b2.setBounds((int)width-10, (int)by,(int)width,(int)by+700);
        b1.draw(canvas);
        b2.draw(canvas);
        //falling cube
        rt.setBounds((int)rx1, (int)ry1, (int)rx2, (int)ry1+Size);
        rd.setBounds((int)rx1, (int)ry1+Size+5, (int)rx2, (int)ry2);
        rt.draw(canvas);
        rd.draw(canvas);

    }
    void border(){
        if (Cast_mode!=0) by+=speed*2*Cast_mode; else by+=speed;
        if (by>height) by = -700;

    }
    void swap_position(){
        float tmp;
        tmp = y; y=ry; ry=tmp;
        tmp = prex; prex=rx; rx=tmp;
        Tp = getdrawable(c1,nt); tp = nt;
        Bt = getdrawable(c2,nd); bt = nd;
    }
    void fix(){
        update();
        if (rx2<x1 || x2<rx1) return;
        if (ry2>y && ry<y2 ){
            if (dr<0 && x1+epsi>=rx2 && x1<rx2) {rx -= rx2-x1;}
            if (dr>0 && x2-epsi<=rx1 && x2>rx1) {rx += x2-rx1;}
        }
        if ( ry2>=y && ry2-2*speed<=y) {y=ry2; t2=1;}
    }
    byte match(){
        if (y<=ry+Size*2+5 && ry+Size*2+5 <=y+Size+5){
            if (tp==nd) return 1; else return 0;
        }
        if (y+Size<=ry && ry<=y+Size*2+5){
            if (bt==nt) return 1; else return 0;
        }
        return 0;
    }
    void HaveLost(){
        lost=true;
        if (MainActivity.volume) sound.PlayLoseSound();
        MainActivity.SCORE=score;
    }
    void progress(){
        if (!Cast_processing){
            if (ry+Size*2+5 > y && ry<y+Size*2+5){
                if ((rx1<x1&&x1<rx2) || (rx1<x2&&x2<rx2) || (rx1==x1 && rx2==x2)){ //Touched
                    touched=true;
                    tmp=match();
                    if (tmp!=0){
                        score+=tmp;
                        Cast_processing=true;
                        if (MainActivity.volume==true) sound.PlayHitSound();
                        fix();
                    }else{
                        HaveLost();
                        fix();
                        return;
                    }
                }
            }
            if (!lost){
                random_fallen();
                move();
            }
        }
        if (Cast_processing){
            if (Cast_mode==0) if (ry < y) Cast_mode = 1; else Cast_mode=-1;
            if (Cast_mode==1){ // positon: On;
                if (ry<Const_y) ry+=speed*2;
                y+=speed*2;
                fix();
                if (y > height){
                    swap_position();
                    Cast_mode=0;
                    Cast_processing=false;
                }
            }else{ //position: Bottom
                if (ry>Const_y) ry-=speed*2;
                y+=speed*2;
                fix();
                if (y > height) {
                    swap_position();
                    Cast_mode = 0;
                    Cast_processing = false;
                }
            }
        }

    }
    @Override
    public void onDraw(Canvas canvas){
        if (first_load){
            t1=0;
            ry = -500;
            lost = false;
            width = canvas.getWidth(); height = canvas.getHeight();
            x= prex =  width/2;

            Size = (int) (width/5.3);
            y = Const_y = (int)height-3*Size;
            first_load = false;
        }

        if (!lost) border();
        update();
        if (ry2>height && tp == nd && touched==false) HaveLost();
        if (!lost && t1==1){
            progress();
        }else {
            fix();
        };
        update();

        if (MainActivity.dark){
            canvas.drawRect(0,0,width,height,background_dark);
            canvas.drawText(""+score,canvas.getWidth()/2-50/2-score/10*25, canvas.getHeight()/2-canvas.getHeight()/5,text_white);
        }else{
            canvas.drawRect(0,0,width,height,background_light);
            canvas.drawText(""+score,canvas.getWidth()/2-50/2-score/10*25, canvas.getHeight()/2-canvas.getHeight()/5,text_black);
        }
        if (t1==0){
            finger.setBounds((int)width/2-75,(int)height/2-75-75,(int)width/2+75,(int)height/2);
            finger.draw(canvas);
            canvas.drawText("tap tap...",width/2-70,height/2+50,tap);
        }

        Draw(canvas);
    }

    void move(){
            if (touch&&dr==-1 || !touch&&dr==1) {epsi=max_epsi; }   //event touch
            if (epsi>min_epsi && (epsi&1)==0) epsi--; else if (epsi==min_epsi) epsi= (byte)((max_epsi+min_epsi)/2+1);
            if (touch) dr=1; else dr=-1;
            if (dr>0) prex = min(prex+epsi,width-11-Size/2);
            else if (dr<0)prex = max(prex-epsi,11+Size/2);
            //

//        epsi=max_epsi;
//        if (abs(prex-x)>max_epsi){
//            if (prex < x) {dr = 1;}
//            else dr = -1;
//            prex+=dr*max_epsi;
//        }
//            epsi=10;
//            if (dr>0) prex = min(prex+epsi,width-11-Size/2);
//            else if (dr<0)prex = max(prex-epsi,11+Size/2);

        }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                getX =x = event.getX();
                getY =    event.getY();
                // check event touch change theme button
                if (11<=getY&&getY <= 111 && 11<=getX&&getX<=111){
                    MainActivity.dark=!MainActivity.dark;
                    if (MainActivity.dark) theme = getResources().getDrawable(R.drawable.dark);
                    else theme = getResources().getDrawable(R.drawable.light);
                    break;
                }
                //check if the volume change button is clicked
                if (11<=getY&&getY <= 111 && width-111<=getX&&getX<=width-11){
                    MainActivity.volume = !MainActivity.volume;
                    if (MainActivity.volume==true) volume=getResources().getDrawable(R.drawable.sound);
                    else volume = getResources().getDrawable(R.drawable.notsound);
                    break;
                }
                //

                touch=!touch;
                t1=1;
                break;
            case MotionEvent.ACTION_UP:
                x=event.getX();
                break;
        }

        return true;
    }
}
