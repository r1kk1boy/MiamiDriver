package com.example.miamidriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private boolean isSound, isMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                mediaPlayer.stop();
                finish();
            }
        });
        findViewById(R.id.shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
                finish();
                mediaPlayer.stop();
            }
        });
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.main2);

        TextView textScore = findViewById(R.id.textHighScore);
        TextView textMoney = findViewById(R.id.money);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        textScore.setText("high score: " + prefs.getInt("highscore", 0));
        textMoney.setText("money: " + prefs.getInt("money", 0) + "$");

        isSound = prefs.getBoolean("isSound", false);
        isMusic = prefs.getBoolean("isMusic", false);

        final ImageView soundCtrl = findViewById(R.id.sound);
        final ImageView musicCtrl = findViewById(R.id.music);

        if (isMusic)
            musicCtrl.setImageResource(R.drawable.music_off);
        else
            musicCtrl.setImageResource(R.drawable.music_on);

        if (isSound)
            soundCtrl.setImageResource(R.drawable.sound_off);
        else
            soundCtrl.setImageResource(R.drawable.sound_on);

        soundCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isSound = !isSound;
                if (isSound) {
                    soundCtrl.setImageResource(R.drawable.sound_off);
                }
                else {
                    soundCtrl.setImageResource(R.drawable.sound_on);
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isSound", isSound);
                editor.apply();

            }
        });

        if(!prefs.getBoolean("isMusic", false)) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        musicCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isMusic = !isMusic;
                if (isMusic) {
                    musicCtrl.setImageResource(R.drawable.music_off);
                    mediaPlayer.pause();
                }
                else{
                    musicCtrl.setImageResource(R.drawable.music_on);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMusic", isMusic);
                editor.apply();

            }
        });

    }
}