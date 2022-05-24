package com.example.miamidriver;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Road {

    public float roadScreenW;
    public float roadScreenH;
    public int screenW;
    public int screenH;


    public int x = 0, y = 0;
    public float velocity;
    public float ratioY;
    Bitmap road;

    Road (int screenW, int screenH, Resources res) {
        this.screenW = screenW;
        this.screenH = screenH;

        road = BitmapFactory.decodeResource(res, R.drawable.road);
        roadScreenW = road.getWidth();
        roadScreenH = road.getHeight();
        ratioY = screenH / roadScreenH;
        velocity = 15 * ratioY;

        road = Bitmap.createScaledBitmap(road, screenW, (int) (screenH * 1.2), false);

    }

    public void draw(Paint paint, Canvas canvas){
        canvas.drawBitmap(road, x, y, paint);
    }

    public void update(){
        y += velocity;

        if(y >= screenH){
            y = -screenH;
        }
    }

}
