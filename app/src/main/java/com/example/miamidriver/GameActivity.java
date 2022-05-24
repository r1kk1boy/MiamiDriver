package com.example.miamidriver;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    protected static TextView textMoney, textScore;
    private TextView pause, play, back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);

        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.gameLayout);

        pause = (TextView)findViewById(R.id.pause);
        back = (TextView)findViewById(R.id.exit);
        play = (TextView)findViewById(R.id.play);

        back.setVisibility(View.INVISIBLE);
        play.setVisibility(View.INVISIBLE);

        pause.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.pause();
                pause.setVisibility(View.INVISIBLE);
                back.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.resume();
                gameView.gameOver = true;
            }
        });

        play.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.resume();
                play.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
            }
        });
        textScore = (TextView)findViewById(R.id.textScoreGame);
        textMoney = (TextView)findViewById(R.id.textMoney);
        gameLayout.addView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}

