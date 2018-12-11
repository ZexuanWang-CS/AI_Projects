package com.zexuan_wang.tictactoeai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeSelectActivity extends AppCompatActivity {

    public static boolean buttonEasyClicked, buttonHardClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);
        buttonEasyClicked = false;
        buttonHardClicked = false;
        Button buttonEasy = (Button) findViewById(R.id.buttonEasy);
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEasyClicked = true;
                buttonHardClicked = false;
                PlayOrder();
            }
        });
        Button buttonHard = (Button) findViewById(R.id.buttonHard);
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHardClicked = true;
                buttonEasyClicked = false;
                PlayOrder();
            }
        });
    }

    public void PlayOrder() {
        Intent PlayOrder = new Intent();
        PlayOrder.setClass(this, PlayOrderActivity.class);
        startActivity(PlayOrder);
    }
}