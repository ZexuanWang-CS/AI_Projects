package com.zexuan_wang.tictactoeai;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameAdvEasy extends AppCompatActivity implements View.OnClickListener {

    public Button[][] BoardButtons = new Button[9][9];
    public String CurrPlayer;
    public ArrayList<ArrayList<Integer>> Freespot = new ArrayList<ArrayList<Integer>>();
    public int FreespotSize;
    public ArrayList<ArrayList<Integer>> AIspot = new ArrayList<ArrayList<Integer>>();
    public ArrayList<ArrayList<Integer>> HMspot = new ArrayList<ArrayList<Integer>>();
    public int isAIPlayer;
    public String AIPlayer;
    public String HMPlayer;
    public int Checkscore;
    public int[] AILastPlace = new int[2];
    public int[] HMLastPlace = new int[2];
    public int placeIndex;
    public int downDepth = 6;
    public int semiscore;
    public boolean hasWin = false;
    public Random r = new Random();
    public MediaPlayer mp;
    public TextView AIloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_adv_easy);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String butID = "B" + i + j;
                int resID = getResources().getIdentifier(butID, "id", getPackageName());
                BoardButtons[i][j] = findViewById(resID);
                BoardButtons[i][j].setOnClickListener(this);
            }
        }
        AIloc = findViewById(R.id.plyAI);
        AIloc.setTextColor(-16777216);
        Button resBut = (Button) findViewById(R.id.reset);
        resBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        BoardButtons[i][j].setText("");
                    }
                }
                Intent GameRestart = new Intent(GameAdvEasy.this, GameAdvEasy.class);
                startActivity(GameRestart);
            }
        });
        Integer[] sub = {-1};
        ArrayList<Integer> subList = new ArrayList<Integer>(Arrays.asList(sub));
        for (int i = 0; i < 9; i++) {
            Freespot.add(i, subList);
            AIspot.add(i, subList);
            HMspot.add(i, subList);
        }
        AILastPlace[0] = -1;
        AILastPlace[1] = -1;
        HMLastPlace[0] = -1;
        HMLastPlace[1] = -1;
        isAIPlayer = IsAIPlayer();
        Checkscore = CheckScore(downDepth);
        mp = MediaPlayer.create(GameAdvEasy.this, R.raw.soundclick1);
        if (isAIPlayer == 1) {
            CurrPlayer = AIPlayer;
            int rand1 = r.nextInt(9) + 1;
            int rand2 = r.nextInt(9) + 1;
            while (!Freespot.get(rand1 - 1).contains(rand2)) {
                rand1 = r.nextInt(9) + 1;
                rand2 = r.nextInt(9) + 1;
            }
            Freespot = FreeSpot();
            PlaceMarker(rand1, rand2);
            AIloc.setText("AI plays at [" + rand1 + ", " + rand2 + "]");
            AILastPlace[1] = rand2;
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

    public ArrayList<ArrayList<Integer>> FreeSpot() {
        FreespotSize = 0;
        for (int i = 1; i <= 9; i++) {
            Integer[] sublist = {};
            ArrayList<Integer> subList1 = new ArrayList<>(Arrays.asList(sublist));
            ArrayList<Integer> subList2 = new ArrayList<>(Arrays.asList(sublist));
            ArrayList<Integer> subList3 = new ArrayList<>(Arrays.asList(sublist));
            for (int j = 1; j <= 9; j++) {
                placeIndex = PlaceIndex(i, j);
                if (BoardButtons[(placeIndex - 1) / 9][(placeIndex - 1) % 9].getText().toString().contentEquals("")) {
                    subList1.add(j);
                    FreespotSize++;
                } else if (BoardButtons[(placeIndex - 1) / 9][(placeIndex - 1) % 9].getText().toString().contentEquals(AIPlayer)) {
                    subList2.add(j);
                } else if (BoardButtons[(placeIndex - 1) / 9][(placeIndex - 1) % 9].getText().toString().contentEquals(HMPlayer)) {
                    subList3.add(j);
                }
            }
            Freespot.set(i - 1, subList1);
            AIspot.set(i - 1, subList2);
            HMspot.set(i - 1, subList3);
        }
        return Freespot;
    }

    public int PlaceIndex(int location1, int location2) {
        placeIndex = (location1 - 1) / 3 * 27 + (location2 - 1) / 3 * 9 + (location1 - 1) % 3 * 3 + (location2 - 1) % 3 + 1;
        return placeIndex;
    }

    public void PlaceMarker(int i, int j) {
        if (Freespot.get(i - 1).contains(j)) {
            placeIndex = PlaceIndex(i, j);
            BoardButtons[(placeIndex - 1) / 9][(placeIndex - 1) % 9].setText(CurrPlayer);
        }
    }

    public int CheckScore(int searchDepthforScore) {
        FreeSpot();
        String[] WinPos = new String[]{"123", "456", "789", "159", "357", "147", "258", "369"};
        for (int k = 0; k < 8; k++) {
            char[] CA = WinPos[k].toCharArray();
            for (int m = 1; m <= 9; m++) {
                if (HMspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(CA[2]))) {
                    return (-500) * (searchDepthforScore + 2);
                }
                if (AIspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(CA[2]))) {
                    return 500 * (searchDepthforScore + 2);
                }
            }
        }
        String[] SemiWinPos = new String[]{"12", "23", "13", "45", "56", "46", "78", "89", "79", "14",
                "47", "17", "25", "58", "28", "36", "69", "39", "15", "59", "19", "35", "57", "37"};
        semiscore = 0;
        for (int k = 0; k < 24; k++) {
            for (int m = 1; m <= 9; m++) {
                if (AIspot.get(m - 1).contains(Character.getNumericValue(SemiWinPos[k].charAt(0)))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(SemiWinPos[k].charAt(1)))) {
                    semiscore += searchDepthforScore + 1;
                }
                if (HMspot.get(m - 1).contains(Character.getNumericValue(SemiWinPos[k].charAt(0)))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(SemiWinPos[k].charAt(1)))) {
                    semiscore -= searchDepthforScore + 1;
                }
            }
        }
        return semiscore;
    }

    public void ShowWin() {
        Freespot = FreeSpot();
        Checkscore = CheckScore(downDepth);
        if (Checkscore < -500 || Checkscore > 500 || FreespotSize == 0) {
            if (Checkscore > 500) {
                Toast.makeText(GameAdvEasy.this, "AI Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else if (Checkscore < -500) {
                Toast.makeText(GameAdvEasy.this, "Human Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else {
                Toast.makeText(GameAdvEasy.this, "Game Draws", Toast.LENGTH_LONG).show();
                hasWin = true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!hasWin) {
            boolean flag = false;
            if (AILastPlace[1] != -1) {
                for (int i = 0; i < 9; i++) {
                    placeIndex = PlaceIndex(AILastPlace[1], i + 1);
                    if ((BoardButtons[(placeIndex - 1) / 9][(placeIndex - 1) % 9].equals((Button) v)) && ((Button) v).getText().toString().contentEquals("")) {
                        ((Button) v).setText(HMPlayer);
                        mp.start();
                        HMLastPlace[1] = i + 1;
                        CurrPlayer = HMPlayer;
                        flag = true;
                        ShowWin();
                        break;
                    }
                }
                if (!flag) {
                    return;
                }
            } else {
                ((Button) v).setText(HMPlayer);
                mp.start();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        placeIndex = PlaceIndex(i + 1, j + 1);
                        if (BoardButtons[(placeIndex - 1) / 9][(placeIndex - 1) % 9].equals((Button) v)) {
                            HMLastPlace[1] = j + 1;
                            CurrPlayer = HMPlayer;
                        }
                    }
                }
            }
            if (!hasWin) {
                isAIPlayer = 1;
                CurrPlayer = AIPlayer;
                int rand2 = r.nextInt(9) + 1;
                while (!Freespot.get(HMLastPlace[1] - 1).contains(rand2)) {
                    rand2 = r.nextInt(9) + 1;
                }
                PlaceMarker(HMLastPlace[1], rand2);
                AIloc.setText("AI plays at [" + HMLastPlace[1] + ", " + rand2 + "]");
                AILastPlace[1] = rand2;
                ShowWin();
                isAIPlayer = -1;
            }
        }
    }
}