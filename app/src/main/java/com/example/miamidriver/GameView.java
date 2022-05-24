package com.example.miamidriver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.Set;

public class GameView extends SurfaceView implements Runnable{

    private Thread thread;
    public float deltaX, deltaY;
    protected GameActivity activity;

    private int score = 0, money;
    public static volatile boolean running = true;
    protected boolean gameOver = false;

    private Paint paint = new Paint();

    public float ratioX;
    public float ratioY;

    private Road road1, road2;
    public int screenH;
    public int screenW;

    private Set<Car> cars = new LinkedSet<>();
    private int intervalCars = 70;
    private int currentTime = 0;

    private SoundPool soundPool;
    private int sound;


    private Supercar supercar;
    protected int scarX;
    protected int scarY;
    protected float moveX;
    protected float moveY;
    protected int supercarN;

    public Handler mainHandler;
    private SharedPreferences prefs;
    private MediaPlayer mediaPlayer;


    public GameView(GameActivity activity, int screenW, int screenH) {
        super(activity);

        this.activity = activity;

        mainHandler = new Handler(Looper.getMainLooper());
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        supercarN = prefs.getInt("supercar", 1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        sound = soundPool.load(activity, R.raw.money, 1);

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(activity, R.raw.game);

        this.screenH = screenH;
        this.screenW = screenW;


        road1 = new Road(screenW, screenH, getResources());
        road2 = new Road(screenW, screenH, getResources());
        ratioX = screenW / road1.roadScreenW;
        ratioY = screenH / road1.roadScreenH;

        road2.y = -screenH;


        supercar = new Supercar(ratioX, ratioY, getResources(), supercarN);
        moveY = (int) ((screenH / 4) + supercar.height * 1.1);
        moveX = (screenW / 2) - (supercar.width / 2);

    }

    @Override
    public void run() {

        while (running) {
            if(!prefs.getBoolean("isMusic", false)) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
            update();
            draw();
            checkCollision();
            checkIfNewCar();
            sleep();
        }
    }

    private void sleep () {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause () {

        try {
            running = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(){

        road1.update();
        road2.update();

        if(moveY < screenH - supercar.height * 0.9 && moveY > 0 && moveX < screenW - supercar.width * 1.25 && moveX > supercar.width * 0.25){
            scarX = (int) moveX;
            scarY = (int) moveY;
        }

        for (Car car : cars) {
            car.update();
            if(car.y > screenH + car.height) {
                score += 1;
                money += 5;
                if (!prefs.getBoolean("isSound", false)) {
                    soundPool.play(sound, 1, 1, 0, 0, 1);
                }
                cars.remove(car);
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                GameActivity.textMoney.setText("money: " + money + "$");
                GameActivity.textScore.setText("score: " + score);
                    }
                };
                mainHandler.post(myRunnable);
            }
        }
    }

    private void checkCollision(){
        for (Car car : cars) {
            if(car.isCollision(scarX, scarY, supercar.height, supercar.width)){
                gameOver = true;
            }
        }
    }

    public void draw(){

        if(getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            road1.draw(paint, canvas);
            road2.draw(paint, canvas);
            supercar.draw(paint, canvas, scarX, scarY);

            for (Car car : cars) {
                car.draw(paint, canvas);
            }

            if (gameOver) {
                mediaPlayer.stop();
                canvas.drawBitmap(supercar.getDead(), scarX, scarY, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }


    public boolean touch(float x, float y){
        return x >= scarX - scarX * 0.1 && x <= (scarX + supercar.width) + (scarX + supercar.width) * 0.1
                && y >= scarY - scarY * 0.1 && y <= (scarY + supercar.height) + (scarY + supercar.height) * 0.1;
    }

    private void checkIfNewCar(){
        if(currentTime >= intervalCars){
            Car car = new Car(ratioX, ratioY, screenW, screenH, getResources());
            cars.add(car);
            currentTime = 0;
        }else{
            currentTime ++;
        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void saveIfHighScore() {
        SharedPreferences.Editor editor = prefs.edit();
        if (prefs.getInt("highscore", 0) < score) {
            editor.putInt("highscore", score);
            editor.apply();
        }
        editor.putInt("money", money + prefs.getInt("money", 0));
        editor.apply();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                deltaX = x - scarX;
                deltaY = y - scarY;
                break;
            case MotionEvent.ACTION_MOVE: // движение
                if(touch(x, y)) {
                    moveX = x - deltaX;
                    moveY = y - deltaY;
                }
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

}

