package com.example.miamidriver;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Supercar{
    public int height;
    public int width;
    Bitmap supercar, dead;



    public Supercar(float ratioX, float ratioY, Resources res, int numCar){
        switch (numCar) {
            case (1):
                supercar = BitmapFactory.decodeResource(res, R.drawable.super_tu);
                dead = BitmapFactory.decodeResource(res, R.drawable.super_tu_dead);
                break;
            case (2):
                supercar = BitmapFactory.decodeResource(res, R.drawable.super_el);
                dead = BitmapFactory.decodeResource(res, R.drawable.super_el_dead);
                break;
            case (3):
                supercar = BitmapFactory.decodeResource(res, R.drawable.super_be);
                dead = BitmapFactory.decodeResource(res, R.drawable.super_be_dead);
                break;
            case (4):
                supercar = BitmapFactory.decodeResource(res, R.drawable.super_fu);
                dead = BitmapFactory.decodeResource(res, R.drawable.super_fu_dead);
                break;
        }
        height = (int) (supercar.getHeight() * ratioX);
        width = (int) (supercar.getWidth() * ratioY);

        supercar = Bitmap.createScaledBitmap(supercar, width, height, false);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

    }

    public void draw(Paint paint, Canvas canvas, int x, int y){
        canvas.drawBitmap(supercar, x, y, paint);
    }

    Bitmap getDead () {
        return dead;
    }

}
