package com.example.miamidriver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {

    public static int money, price_el = 500, price_be = 1000, price_fu = 5000;
    private boolean buyEl, buyBe, buyFu;
    protected int supercar;
    TextView textTuInfo, textElInfo, textBeInfo, textFuInfo;
    private SharedPreferences prefs;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = getSharedPreferences("game", MODE_PRIVATE);

        money = prefs.getInt("money", 0);

        TextView textMoney = findViewById(R.id.money);
        textMoney.setText("money: " + money + "$");

        textTuInfo = findViewById(R.id.turismo_info);
        textElInfo = findViewById(R.id.elegant_info);
        textBeInfo = findViewById(R.id.beast_info);
        textFuInfo = findViewById(R.id.furious_info);

        buyEl = prefs.getBoolean("buyel", false);
        buyBe = prefs.getBoolean("buybe", false);
        buyFu = prefs.getBoolean("buyfu", false);

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.main2);

        if(!prefs.getBoolean("isMusic", false)) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        supercar = 0;

        isCarNum();

        TextView back = (TextView) findViewById(R.id.back_menu);
        back.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                if(supercar == 0){
                    supercar = 1;
                }
                editor.putInt("supercar", supercar);
                editor.putInt("money", money);
                editor.apply();
                mediaPlayer.stop();
                startActivity(new Intent(ShopActivity.this, MainActivity.class));
                finish();
                }
        });

        if(supercar != 1) {
            ImageView turismo = (ImageView) findViewById(R.id.super_tu);
            turismo.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    supercar = 1;
                    isCarNum();
                }
            });
        }

        if((buyEl || money >= 500) && supercar != 2) {
            ImageView elegant = (ImageView) findViewById(R.id.super_el);
            elegant.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!buyEl){
                        money -= 500;
                        buyEl = true;
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("money", money);
                        editor.putBoolean("buyel", buyEl);
                        editor.apply();
                        textMoney.setText("money: " + money + "$");
                    }
                    supercar = 2;
                    isCarNum();
                }
            });
        }

        if((buyBe || money >= 1000) && supercar != 3) {
            ImageView beast = (ImageView) findViewById(R.id.super_be);
            beast.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!buyBe){
                        money -= 1000;
                        buyBe = true;
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("money", money);
                        editor.putBoolean("buybe", buyBe);
                        editor.apply();
                        textMoney.setText("money: " + money + "$");
                    }
                    supercar = 3;
                    isCarNum();
                }
            });
        }

        if((buyFu || money >= 5000) && supercar != 4) {
            ImageView furious = (ImageView) findViewById(R.id.super_fu);
            furious.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!buyFu){
                        money -= 5000;
                        buyFu = true;
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putInt("money", money);
                        editor.putBoolean("buyfu", buyFu);
                        editor.apply();
                        textMoney.setText("money: " + money + "$");
                    }
                    supercar = 4;
                    isCarNum();
                }
            });
        }

    }

    public void isCarNum(){
        switch(supercar){
            case(0):
                textTuInfo.setText("equip");
                textTuInfo.setTextColor(Color.parseColor("#FFD500"));

                if(buyEl) {
                    textElInfo.setText("equip");
                    textElInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                if(buyBe) {
                    textBeInfo.setText("equip");
                    textBeInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                if(buyFu) {
                    textFuInfo.setText("equip");
                    textFuInfo.setTextColor(Color.parseColor("#FFD500"));
                }
                break;
            case(1):
                textTuInfo.setText("equipped");
                textTuInfo.setTextColor(Color.parseColor("#9EEC23"));

                if(buyEl) {
                    textElInfo.setText("equip");
                    textElInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                if(buyBe) {
                    textBeInfo.setText("equip");
                    textBeInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                if(buyFu) {
                    textFuInfo.setText("equip");
                    textFuInfo.setTextColor(Color.parseColor("#FFD500"));
                }
                break;
            case(2):
                textTuInfo.setText("equip");
                textTuInfo.setTextColor(Color.parseColor("#FFD500"));

                textElInfo.setText("equipped");
                textElInfo.setTextColor(Color.parseColor("#9EEC23"));

                if(buyBe) {
                    textBeInfo.setText("equip");
                    textBeInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                if(buyFu) {
                    textFuInfo.setText("equip");
                    textFuInfo.setTextColor(Color.parseColor("#FFD500"));
                }
                break;
            case(3):
                textTuInfo.setText("equip");
                textTuInfo.setTextColor(Color.parseColor("#FFD500"));

                if(buyEl) {
                    textElInfo.setText("equip");
                    textElInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                textBeInfo.setText("equipped");
                textBeInfo.setTextColor(Color.parseColor("#9EEC23"));


                if(buyFu) {
                    textFuInfo.setText("equip");
                    textFuInfo.setTextColor(Color.parseColor("#FFD500"));
                }
                break;
            case(4):
                textTuInfo.setText("equip");
                textTuInfo.setTextColor(Color.parseColor("#FFD500"));

                if(buyEl) {
                    textElInfo.setText("equip");
                    textElInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                if(buyBe) {
                    textBeInfo.setText("equip");
                    textBeInfo.setTextColor(Color.parseColor("#FFD500"));
                }

                textFuInfo.setText("equipped");
                textFuInfo.setTextColor(Color.parseColor("#9EEC23"));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}
