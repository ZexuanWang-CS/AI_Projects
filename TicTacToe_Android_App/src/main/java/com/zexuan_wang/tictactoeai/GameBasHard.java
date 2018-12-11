package com.zexuan_wang.tictactoeai;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class GameBasHard extends AppCompatActivity implements View.OnClickListener {

    public Button[][] BoardButtons = new Button[3][3];
    public String CurrPlayer;
    public ArrayList<Integer> Freespot = new ArrayList<Integer>();
    public String[] WinPos = new String[]{"123", "456", "789", "159", "357", "147", "258", "369"};
    public int isAIPlayer;
    public String AIPlayer;
    public String HMPlayer;
    public int Checkscore;
    public Integer LastMove;
    public boolean hasWin = false;
    public int alpha = -1000;
    public int beta = 1000;
    public MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_bas_hard);
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
                Intent GameRestart = new Intent(GameBasHard.this, GameBasHard.class);
                startActivity(GameRestart);
            }
        });
        Freespot = FreeSpot();
        isAIPlayer = IsAIPlayer();
        Checkscore = CheckScore();
        mp = MediaPlayer.create(GameBasHard.this, R.raw.soundclick1);
        if (isAIPlayer == 1) {
            CurrPlayer = AIPlayer;
            PlaceMarker(5);
            isAIPlayer = -1 * isAIPlayer;
        } else if (isAIPlayer == -1) {
            CurrPlayer = HMPlayer;
        }
        Freespot = FreeSpot();
        Checkscore = CheckScore();
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

    public void GameBoardRevert(Integer Freespot_ele) {
        BoardButtons[(Freespot_ele - 1) / 3][(Freespot_ele - 1) % 3].setText("");
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

    public int MaxScore(ArrayList<Integer> scorelist) {
        int Maxscore = -100;
        for (int i = 0; i < scorelist.size(); i++) {
            if (Maxscore < scorelist.get(i)) {
                Maxscore = scorelist.get(i);
            }
        }
        return Maxscore;
    }

    public int MinScore(ArrayList<Integer> scorelist) {
        int Minscore = 100;
        for (int i = 0; i < scorelist.size(); i++) {
            if (Minscore > scorelist.get(i)) {
                Minscore = scorelist.get(i);
            }
        }
        return Minscore;
    }

    public int MiniMax(boolean IsAI, int alpha, int beta) {
        int Checkscore = CheckScore();
        if (Checkscore != 0 || Freespot.isEmpty()) {
            return Checkscore;
        } else {
            if (IsAI) {
                CurrPlayer = AIPlayer;
                ArrayList<Integer> scorelist = new ArrayList<Integer>();
                FreeSpot();
                for (int pos = 0; pos < Freespot.size(); pos++) {
                    Integer Freespot_ele = Freespot.get(pos);
                    CurrPlayer = AIPlayer;
                    BoardButtons[(Freespot_ele - 1) / 3][(Freespot_ele - 1) % 3].setText(AIPlayer);
                    Freespot = FreeSpot();
                    isAIPlayer = -1;
                    int score = MiniMax(false, alpha, beta);
                    scorelist.add(score);
                    GameBoardRevert(Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = 1;
                    if (score >= beta) {
                        return score;
                    }
                    alpha = Math.max(score, alpha);
                }
                return MaxScore(scorelist);
            } else {
                CurrPlayer = HMPlayer;
                ArrayList<Integer> scorelist = new ArrayList<Integer>();
                Freespot = FreeSpot();
                for (int pos = 0; pos < Freespot.size(); pos++) {
                    Integer Freespot_ele = Freespot.get(pos);
                    CurrPlayer = HMPlayer;
                    BoardButtons[(Freespot_ele - 1) / 3][(Freespot_ele - 1) % 3].setText(HMPlayer);
                    Freespot = FreeSpot();
                    isAIPlayer = 1;
                    int score = MiniMax(true, alpha, beta);
                    scorelist.add(score);
                    GameBoardRevert(Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = -1;
                    if (score <= alpha) {
                        return score;
                    }
                    beta = Math.min(score, beta);
                }
                return MinScore(scorelist);
            }
        }
    }

    public void ShowWin() {
        Freespot = FreeSpot();
        Checkscore = CheckScore();
        if (Checkscore != 0 || Freespot.isEmpty()) {
            if (Checkscore == 1) {
                Toast.makeText(GameBasHard.this, "AI Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else if (Checkscore == -1) {
                Toast.makeText(GameBasHard.this, "Human Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else {
                Toast.makeText(GameBasHard.this, "Game Draws", Toast.LENGTH_LONG).show();
                hasWin = true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!hasWin) {
            if (((Button) v).getText().toString().contentEquals("")) {
                ((Button) v).setText(HMPlayer);
                mp.start();
                CurrPlayer = HMPlayer;
                ShowWin();
                isAIPlayer = 1;
            }
            if (!hasWin) {
                CurrPlayer = AIPlayer;
                for (int pos = 0; pos < Freespot.size(); pos++) {
                    Integer Freespot_ele = Freespot.get(pos);
                    BoardButtons[(Freespot_ele - 1) / 3][(Freespot_ele - 1) % 3].setText(AIPlayer);
                    Freespot = FreeSpot();
                    isAIPlayer = -1;
                    int score = MiniMax(false, alpha, beta);
                    GameBoardRevert(Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = 1;
                    if (score == 1) {
                        BoardButtons[(Freespot_ele - 1) / 3][(Freespot_ele - 1) % 3].setText(AIPlayer);
                        isAIPlayer = -1;
                        break;
                    } else if (score == 0) {
                        LastMove = Freespot_ele;
                    }
                }
                if (isAIPlayer == 1) {
                    CurrPlayer = AIPlayer;
                    PlaceMarker(LastMove);
                    isAIPlayer = -1;
                }
                ShowWin();
                isAIPlayer = -1;
            }
        }
    }
}