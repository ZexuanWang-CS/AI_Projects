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

public class GameAdvHard extends AppCompatActivity implements View.OnClickListener {

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
    public int[] MinmaxLastPlace = new int[downDepth + 2];
    public int score = -10000;
    public int semiscore;
    public ArrayList<Integer> scoreList = new ArrayList<Integer>();
    public int scoreListMaxInd = -1;
    public boolean hasWin = false;
    public int alpha = -1000;
    public int beta = 1000;
    public MediaPlayer mp;
    public TextView AIloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_adv_hard);
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
                Intent GameRestart = new Intent(GameAdvHard.this, GameAdvHard.class);
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
        Checkscore = CheckScore();
        mp = MediaPlayer.create(GameAdvHard.this, R.raw.soundclick1);
        if (isAIPlayer == 1) {
            CurrPlayer = AIPlayer;
            Freespot = FreeSpot();
            PlaceMarker(5, 5);
            AIloc.setText("AI plays at [" + 5 + ", " + 5 + "]");
            AILastPlace[1] = 5;
            isAIPlayer = -1;
            MinmaxLastPlace[downDepth] = AILastPlace[1];
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

    public void GameBoardRevert(int location1, Integer location2) {
        placeIndex = PlaceIndex(location1, location2);
        BoardButtons[(placeIndex - 1) / 9][(placeIndex - 1) % 9].setText("");
    }

    public int CheckScore() {
        FreeSpot();
        String[] WinPos = new String[]{"123", "456", "789", "159", "357", "147", "258", "369"};
        semiscore = 0;
        for (int k = 0; k < 8; k++) {
            char[] CA = WinPos[k].toCharArray();
            for (int m = 1; m <= 9; m++) {
                if (AIspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(CA[2]))) {
                    return 2000;
                }
            }
        }
        for (int k = 0; k < 8; k++) {
            char[] CA = WinPos[k].toCharArray();
            for (int m = 1; m <= 9; m++) {
                if (HMspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(CA[2]))) {
                    return -2000;
                }
            }
        }
        for (int k = 0; k < 8; k++) {
            char[] CA = WinPos[k].toCharArray();
            for (int m = 1; m <= 9; m++) {
                if (HMspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && !AIspot.get(m - 1).contains(Character.getNumericValue(CA[2]))) {
                    semiscore -= 10;
                }
                if (HMspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(CA[2]))
                        && !AIspot.get(m - 1).contains(Character.getNumericValue(CA[1]))) {
                    semiscore -= 10;
                }
                if (HMspot.get(m - 1).contains(Character.getNumericValue(CA[2]))
                        && HMspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && !AIspot.get(m - 1).contains(Character.getNumericValue(CA[0]))) {
                    semiscore -= 10;
                }
                if (AIspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && !HMspot.get(m - 1).contains(Character.getNumericValue(CA[2]))) {
                    semiscore += 10;
                }
                if (AIspot.get(m - 1).contains(Character.getNumericValue(CA[0]))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(CA[2]))
                        && !HMspot.get(m - 1).contains(Character.getNumericValue(CA[1]))) {
                    semiscore += 10;
                }
                if (AIspot.get(m - 1).contains(Character.getNumericValue(CA[2]))
                        && AIspot.get(m - 1).contains(Character.getNumericValue(CA[1]))
                        && !HMspot.get(m - 1).contains(Character.getNumericValue(CA[0]))) {
                    semiscore += 10;
                }
            }
        }
        return semiscore;
    }

    public int MaxScore(ArrayList<Integer> scorelist) {
        int Maxscore = -10000;
        for (int i = 0; i < scorelist.size(); i++) {
            if (Maxscore < scorelist.get(i)) {
                Maxscore = scorelist.get(i);
            }
        }
        return Maxscore;
    }

    public int MinScore(ArrayList<Integer> scorelist) {
        int Minscore = 10000;
        for (int i = 0; i < scorelist.size(); i++) {
            if (Minscore > scorelist.get(i)) {
                Minscore = scorelist.get(i);
            }
        }
        return Minscore;
    }

    public int ScoreListMax(ArrayList<Integer> scList) {
        int scoreListmax = -10000;
        for (int i = 0; i < scList.size(); i++) {
            if (scList.get(i) > scoreListmax) {
                scoreListmax = scList.get(i);
                scoreListMaxInd = i;
            }
        }
        return scoreListMaxInd;
    }

    public int MiniMax(boolean IsAI, int searchDepth, int alpha, int beta) {
        Checkscore = CheckScore();
        if (Checkscore >= 500 || Checkscore <= -500 || searchDepth == 0 || FreespotSize == 0) {
            return Checkscore;
        } else {
            if (IsAI) {
                CurrPlayer = AIPlayer;
                ArrayList<Integer> scorelist = new ArrayList<Integer>();
                Freespot = FreeSpot();
                for (int pos = 0; pos < Freespot.get(MinmaxLastPlace[searchDepth] - 1).size(); pos++) {
                    Integer Freespot_ele = Freespot.get(MinmaxLastPlace[searchDepth] - 1).get(pos);
                    CurrPlayer = AIPlayer;
                    PlaceMarker(MinmaxLastPlace[searchDepth], Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = -1 * isAIPlayer;
                    MinmaxLastPlace[searchDepth - 1] = Freespot_ele;
                    int AIscore = MiniMax(false, searchDepth - 1, alpha, beta);
                    scorelist.add(AIscore);
                    GameBoardRevert(MinmaxLastPlace[searchDepth], Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = -1 * isAIPlayer;
                    if (AIscore >= beta) {
                        return AIscore;
                    }
                    alpha = Math.max(AIscore, alpha);
                }
                return MaxScore(scorelist);
            } else {
                CurrPlayer = HMPlayer;
                ArrayList<Integer> scorelist = new ArrayList<Integer>();
                Freespot = FreeSpot();
                for (int pos = 0; pos < Freespot.get(MinmaxLastPlace[searchDepth] - 1).size(); pos++) {
                    Integer Freespot_ele = Freespot.get(MinmaxLastPlace[searchDepth] - 1).get(pos);
                    CurrPlayer = HMPlayer;
                    PlaceMarker(MinmaxLastPlace[searchDepth], Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = -1 * isAIPlayer;
                    MinmaxLastPlace[searchDepth - 1] = Freespot_ele;
                    int HMscore = MiniMax(true, searchDepth - 1, alpha, beta);
                    scorelist.add(HMscore);
                    GameBoardRevert(MinmaxLastPlace[searchDepth], Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = -1 * isAIPlayer;
                    if (HMscore <= alpha) {
                        return HMscore;
                    }
                    beta = Math.min(HMscore, beta);
                }
                return MinScore(scorelist);
            }
        }
    }

    public void ShowWin() {
        Freespot = FreeSpot();
        Checkscore = CheckScore();
        if (Checkscore < -500 || Checkscore > 500 || FreespotSize == 0) {
            if (Checkscore > 500) {
                Toast.makeText(GameAdvHard.this, "AI Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else if (Checkscore < -500) {
                Toast.makeText(GameAdvHard.this, "Human Player Wins", Toast.LENGTH_LONG).show();
                hasWin = true;
            } else {
                Toast.makeText(GameAdvHard.this, "Game Draws", Toast.LENGTH_LONG).show();
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
                        FreeSpot();
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
                FreeSpot();
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
                scoreList.clear();
                for (int i = 0; i < Freespot.get(HMLastPlace[1] - 1).size(); i++) {
                    Integer Freespot_ele = Freespot.get(HMLastPlace[1] - 1).get(i);
                    CurrPlayer = AIPlayer;
                    PlaceMarker(HMLastPlace[1], Freespot_ele);
                    AILastPlace[0] = HMLastPlace[1];
                    AILastPlace[1] = Freespot_ele;
                    MinmaxLastPlace[downDepth + 1] = HMLastPlace[1];
                    MinmaxLastPlace[downDepth] = Freespot_ele;
                    Freespot = FreeSpot();
                    isAIPlayer = -1;
                    score = MiniMax(false, downDepth, alpha, beta);
                    if (score >= 500) {
                        CurrPlayer = AIPlayer;
                        AILastPlace[0] = HMLastPlace[1];
                        AILastPlace[1] = Freespot_ele;
                        PlaceMarker(AILastPlace[0], AILastPlace[1]);
                        AIloc.setText("AI plays at [" + AILastPlace[0] + ", " + AILastPlace[1] + "]");
                        FreeSpot();
                        ShowWin();
                        scoreList.clear();
                        isAIPlayer = -1 * isAIPlayer;
                        break;
                    }
                    scoreList.add(i, score);
                    GameBoardRevert(HMLastPlace[1], Freespot_ele);
                    Freespot = FreeSpot();
                    isAIPlayer = -1 * isAIPlayer;
                }
                if (!scoreList.isEmpty()) {
                    CurrPlayer = AIPlayer;
                    scoreListMaxInd = ScoreListMax(scoreList);
                    AILastPlace[0] = HMLastPlace[1];
                    AILastPlace[1] = Freespot.get(HMLastPlace[1] - 1).get(scoreListMaxInd);
                    PlaceMarker(AILastPlace[0], AILastPlace[1]);
                    AIloc.setText("AI plays at [" + AILastPlace[0] + ", " + AILastPlace[1] + "]");
                    FreeSpot();
                    ShowWin();
                    MinmaxLastPlace[downDepth] = AILastPlace[1];
                    isAIPlayer = -1 * isAIPlayer;
                }
            }
        }
    }
}