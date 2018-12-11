package com.zexuan_wang.tictactoeai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayOrderActivity extends AppCompatActivity {

    public static int IsAIplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_order);
        IsAIplayer = 0;
        Button buttonX = (Button) findViewById(R.id.buttonX);
        buttonX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsAIplayer = -1;
                GameStart();
            }
        });
        Button buttonO = (Button) findViewById(R.id.buttonO);
        buttonO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsAIplayer = 1;
                GameStart();
            }
        });
    }

    public void GameStart() {
        Intent GameStart = new Intent();
        if (MainActivity.buttonBasTTTClicked && ModeSelectActivity.buttonEasyClicked) {
            GameStart.setClass(this, GameBasEasy.class);
            startActivity(GameStart);
        } else if (MainActivity.buttonBasTTTClicked && ModeSelectActivity.buttonHardClicked) {
            GameStart.setClass(this, GameBasHard.class);
            startActivity(GameStart);
        } else if (MainActivity.buttonAdvTTTClicked && ModeSelectActivity.buttonEasyClicked) {
            GameStart.setClass(this, GameAdvEasy.class);
            startActivity(GameStart);
        } else if (MainActivity.buttonAdvTTTClicked && ModeSelectActivity.buttonHardClicked) {
            GameStart.setClass(this, GameAdvHard.class);
            startActivity(GameStart);
        }
    }
}