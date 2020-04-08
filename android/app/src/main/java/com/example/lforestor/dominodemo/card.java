package com.example.lforestor.dominodemo;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class card implements Serializable {
    int num; Drawable domino;

    public card(int num, Drawable domino) {
        this.num = num;
        this.domino = domino;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Drawable getDomino() {
        return domino;
    }

    public void setDomino(Drawable domino) {
        this.domino = domino;
    }
}
