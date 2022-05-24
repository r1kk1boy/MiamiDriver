package com.example.miamidriver;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Car{

    public int height;
    public int width;
    Bitmap car;
    public int x, y;
    public double velocity;

    public int road;
    public int numCar;


    public Car(float ratioX, float ratioY, int screenW, int screenH, Resources res){
        numCar = (int) ((Math.random() * 9) + 1);

        velocity = 5 * ratioY;

        switch (numCar) {
            case (1):
                car = BitmapFactory.decodeResource(res, R.drawable.car_norm1);
                break;
            case (2):
                car = BitmapFactory.decodeResource(res, R.drawable.car_norm2);
                break;
            case (3):
                car = BitmapFactory.decodeResource(res, R.drawable.car_norm3);
                break;
            case (4):
                car = BitmapFactory.decodeResource(res, R.drawable.car_ez1);
                break;
            case (5):
                car = BitmapFactory.decodeResource(res, R.drawable.car_ez2);
                break;
            case (6):
                car = BitmapFactory.decodeResource(res, R.drawable.car_ez3);
                break;
            case (7):
                car = BitmapFactory.decodeResource(res, R.drawable.car_hard1);
                break;
            case (8):
                car = BitmapFactory.decodeResource(res, R.drawable.car_hard2);
                break;
            case (9):
                car = BitmapFactory.decodeResource(res, R.drawable.car_hard3);
                break;
        }
        height = (int) (car.getHeight() * ratioX);
        width = (int) (car.getWidth() * ratioY);

        y= -height;
        road = (int) ((Math.random() * 3) + 1);

        switch (road) {
            case (1):
                x = (screenW / 2) - (width / 2);
                break;
            case (2):
                x = (screenW / 5) - (width / 2);
                break;
            case (3):
                x = (screenW - (screenW / 5)) - (width / 2);
                break;
        }

        car = Bitmap.createScaledBitmap(car, width, height, false);

    }


    public void draw(Paint paint, Canvas canvas){
        canvas.drawBitmap(car, x, y, paint);
    }

    public void update(){
        y += velocity;
    }

    public boolean isCollision(int scarX, int scarY, int carH, int carW) {
        return !(((x+width) < scarX)||(x > (scarX+carW))||((y+height) < scarY)||(y > (scarY+carH)));
    }
}
