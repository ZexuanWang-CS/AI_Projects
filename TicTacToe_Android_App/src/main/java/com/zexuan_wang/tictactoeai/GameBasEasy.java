package com.zexuan_wang.tictactoeai;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameBasEasy extends AppCompatActivity implements View.OnClickListener {

    public Button[][] BoardButtons = new Button[3][3];
    public String CurrPlayer;
    public ArrayList<Integer> Freespot = new ArrayList<Integer>();
    public String[] WinPos = new String[]{"123", "456", "789", "159", "357", "147", "258", "369"};
    public int isAIPlayer;
    public String AIPlayer;
    public String HMPlayer;
    public int Checkscore;
    public boolean hasWin = false;
    public Random r = new Random();
    public MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_bas_easy);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String butID = "B" + i + j;
                int resID = getResources().getIdentifier(butID, "id", getPackageName());
                BoardButtons[i][j] = findViewById(resID);
                BoardButtons[i][j].setOnClickListener(this);
            }
        }
        Button resBut = (Button) findViewById(R.id.reset);
        resBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        BoardButtons[i][j].setText("");
                    }
                }
                Intent GameRestart = new Intent(GameBasEasy.this, GameBasEasy.class);
                startActivity(GameRestart);
            }
        });
        Freespot = FreeSpot();
        isAIPlayer = IsAIPlayer();
        Checkscore = CheckScore();
        mp = MediaPlayer.create(GameBasEasy.this, R.raw.soundclick2);
        if (isAIPlayer == 1) {
            CurrPlayer = AIPlayer;
            int rand = r.nextInt(9) + 1;
            while (!Freespot.contains(rand)) {
                rand = r.nextInt(9) + 1;
            }
            PlaceMarker(rand);
            isAIPlayer = -1;
        }
    }

    public int IsAIPlayer() {
        isAIPlayer = PlayOrderActivity.IsAIplayer;
        if (isAIPlayer == 1) {
            AIPlayer = "X";
            HMPlayer = "O";
        } else if (isAIPlayer == -1) {
            AIPlayer = "O";
            HMPlayer = "X";
        }
        return isAIPlayer;
    }

    public ArrayList<Integer> FreeSpot() {
        Freespot.clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (BoardButtons[i][j].getText().toString().contentEquals("")) {
                    Freespot.add(3 * i + j + 1);
                }
            }
        }
        return Freespot;
    }

    public boolean PlaceMarker(Integer place) {
        if (Freespot.contains(place)) {
            BoardButtons[(place - 1) / 3][(place - 1) % 3].setText(CurrPlayer);
            return true;
        } else {
            return false;
        }
    }

    public int CheckScore() {
        for (int i = 0; i <= 6; i++) {
            for (int j = 8; j >= i + 2; j--) {
                if (BoardButtons[i / 3][i % 3].getText().equals(AIPlayer)
                        && BoardButtons[j / 3][j % 3].getText().equals(AIPlayer)
                        && BoardButtons[(i + j) / 2 / 3][(i + j) / 2 % 3].getText().equals(AIPlayer)) {
                    int k = 0;
                    while (k <= 7) {
                        if (WinPos[k].equals(Integer.toString(i + 1)
                                + Integer.toString((i + j + 2) / 2)
                                + Integer.toString(j + 1))) {
                            return 1;
                        } else {
                            k++;
                        }
                    }
                } else if (BoardButtons[i / 3][i % 3].getText().equals(HMPlayer)
                        && BoardButtons[j / 3][j % 3].getText().equals(HMPlayer)
                        && BoardButtons[(i + j) / 2 / 3][(i + j) / 2 % 3].getText().equals(HMPlayer)) {
                    int k = 0;
                    while (k <= 7) {
                        if (WinPos[k].equals(Integer.toString(i + 1)
                                + Integer.toString((i + j + 2) / 2)
                                + Integer.toString(j + 1))) {
                            return -1;
                        } else {
                            k++;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public void ShowWin() {
        Freespot = FreeSpot();
        Checkscore = CheckScore();
        if (Checkscore != 0 || Freespot.isEmpty()) {
            if (Checkscore == 1) {
                Toast.makeText(GameBasEasy.this, "AI Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else if (Checkscore == -1) {
                Toast.makeText(GameBasEasy.this, "Human Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else {
                Toast.makeText(GameBasEasy.this, "Game Draws", Toast.LENGTH_LONG).show();
                hasWin = true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!hasWin) {
            if (Freespot.size() == 1) {
                CurrPlayer = HMPlayer;
                PlaceMarker(Freespot.get(0));
                mp.start();
                ShowWin();
            }
            if (((Button) v).getText().toString().contentEquals("")) {
                ((Button) v).setText(HMPlayer);
                mp.start();
                CurrPlayer = HMPlayer;
                ShowWin();
                if (!hasWin) {
                    isAIPlayer = 1;
                    CurrPlayer = AIPlayer;
                    int rand = r.nextInt(9) + 1;
                    while (!Freespot.contains(rand)) {
                        rand = r.nextInt(9) + 1;
                    }
                    PlaceMarker(rand);
                    ShowWin();
                    isAIPlayer = -1;
                }
            }
        }
    }
}