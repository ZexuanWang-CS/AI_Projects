package com.zexuan_wang.tictactoeai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static boolean buttonBasTTTClicked, buttonAdvTTTClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonBasTTTClicked = false;
        buttonAdvTTTClicked = false;
        Button buttonBasTTT = (Button) findViewById(R.id.buttonBasTTT);
        buttonBasTTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonBasTTTClicked = true;
                buttonAdvTTTClicked = false;
                ModeSelection();
            }
        });
        Button buttonAdvTTT = (Button) findViewById(R.id.buttonAdvTTT);
        buttonAdvTTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdvTTTClicked = true;
                buttonBasTTTClicked = false;
                ModeSelection();
            }
        });
        Button buttonAbt = (Button) findViewById(R.id.buttonAbt);
        buttonAbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutSelection();
            }
        });
    }

    public void ModeSelection() {
        Intent ModeSelect = new Intent();
        ModeSelect.setClass(this, ModeSelectActivity.class);
        startActivity(ModeSelect);
    }

    public void AboutSelection() {
        Intent AboutSelect = new Intent();
        AboutSelect.setClass(this, AboutActivity.class);
        startActivity(AboutSelect);
    }
}