import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToeAdv {
	public static String[][] Gameboard;
	public static ArrayList<ArrayList<Integer>> Freespot;
	public static ArrayList<ArrayList<Integer>> AIspot;
	public static ArrayList<ArrayList<Integer>> HMspot;
	public static int FreespotSize;
	public static ArrayList<Integer> subFreespot;
	public static String CurrPlayer;
	public static int IsAIplayer;
	public static String AIPlayer;
	public static String HMPlayer;
	public static int[] AILastPlace;
	public static int[] HMLastPlace;
	public static int placeIndex;
	public static int downDepth;
	public static int[] MinmaxLastPlace;
	public static int score;
	public static int semiscore;
	public static ArrayList<Integer> scoreList;
	public static int scoreListMaxInd;
	public static String RepFlag;
	public static int alpha;
	public static int beta;

	public static void main(String[] args) {
		Freespot = new ArrayList<ArrayList<Integer>>();
		AIspot = new ArrayList<ArrayList<Integer>>();
		HMspot = new ArrayList<ArrayList<Integer>>();
		subFreespot = new ArrayList<Integer>();
		AILastPlace = new int[2];
		HMLastPlace = new int[2];
		downDepth = 6;
		MinmaxLastPlace = new int[downDepth+2];
		score = -10000;
		scoreList = new ArrayList<Integer>();
		scoreListMaxInd = -1;
		RepFlag = "Y";
		alpha = -1000;
		beta = 1000;
		while (RepFlag.equalsIgnoreCase("Y")) {
			TicTacToeAdv game = new TicTacToeAdv();
			Gameboard = game.GameBoard();
			Integer[] sub = {-1};
			ArrayList<Integer> subList = new ArrayList<>(Arrays.asList(sub));
			for (int i = 0; i<9; i++) {
				Freespot.add(i, subList);
				AIspot.add(i, subList);
				HMspot.add(i, subList);
			}
			AILastPlace[0] = -1;
			AILastPlace[1] = -1;
			HMLastPlace[0] = -1;
			HMLastPlace[1] = -1;
			Freespot = game.FreeSpot();
			IsAIplayer = game.IsAIPlayer();
			while (IsAIplayer == 0) {
				IsAIplayer = game.IsAIPlayer();
			}
			Scanner scanPlace = new Scanner(System.in);
			int Checkscore = game.CheckScore(downDepth);
			while(Checkscore < 500 && Checkscore > -500 && FreespotSize != 0) {
				if (IsAIplayer == 1) {
					CurrPlayer = AIPlayer;
					if (FreespotSize == 81) {
						game.PlaceMarker(5,5);
						System.out.println("AI moves at 5 and 5");
						System.out.println(5);
						System.out.println(5);
						AILastPlace[0] = 5;
						AILastPlace[1] = 5;
						IsAIplayer = -1*IsAIplayer;
						MinmaxLastPlace[downDepth] = AILastPlace[1];
					}
					else {
						scoreList.clear();
						for(int i=0;i<Freespot.get(HMLastPlace[1]-1).size(); i++){
							Integer Freespot_ele = Freespot.get(HMLastPlace[1]-1).get(i);
							game.PlaceMarker(HMLastPlace[1],Freespot_ele);
							AILastPlace[0] = HMLastPlace[1];
							AILastPlace[1] = Freespot_ele;
							MinmaxLastPlace[downDepth+1] = HMLastPlace[1];
							MinmaxLastPlace[downDepth] = Freespot_ele;
							Freespot = game.FreeSpot();
							IsAIplayer = -1*IsAIplayer;
							score = game.MiniMax(false,downDepth,alpha,beta);
//							score = game.MiniMax(false,downDepth);
							System.out.println("H-Score evaluation on spot " + Freespot_ele + ": " + score);
							if (score >= 500) {
								AILastPlace[0] = HMLastPlace[1];
								AILastPlace[1] = Freespot_ele;
								System.out.println(AILastPlace[0]);
								System.out.println(AILastPlace[1]);
								System.out.println("AI moves at " + AILastPlace[0] + " and " + AILastPlace[1]);
								scoreList.clear();
								IsAIplayer = -1*IsAIplayer;
								break;
							}
							scoreList.add(i, score);
							game.GameBoardRevert(HMLastPlace[1],Freespot_ele);
							Freespot = game.FreeSpot();
							IsAIplayer = -1*IsAIplayer;
						}
						if (!scoreList.isEmpty()) {
							CurrPlayer = AIPlayer;
							scoreListMaxInd = game.ScoreListMax(scoreList);
							AILastPlace[0] = HMLastPlace[1];
							AILastPlace[1] = Freespot.get(HMLastPlace[1]-1).get(scoreListMaxInd);
							game.PlaceMarker(AILastPlace[0],AILastPlace[1]);
							System.out.println(AILastPlace[0]);
							System.out.println(AILastPlace[1]);
							System.out.println("AI moves at " + AILastPlace[0] + " and " + AILastPlace[1]);
							MinmaxLastPlace[downDepth] = AILastPlace[1];
							IsAIplayer = -1*IsAIplayer;
						}
					}
					game.GameBoardPrint();
				}
				else if (IsAIplayer == -1) {
					System.out.println("Your turn. Enter the spot you want to place.");
					HMLastPlace[0] = scanPlace.nextInt();
					while (AILastPlace[1] != -1 && HMLastPlace[0] != AILastPlace[1]) {
						System.out.println("Invalid input!!! Please enter " + AILastPlace[1]);
						HMLastPlace[0] = scanPlace.nextInt();
					}
					HMLastPlace[1] = scanPlace.nextInt();
					CurrPlayer = HMPlayer;
					if (game.PlaceMarker(HMLastPlace[0],HMLastPlace[1])) {
						game.GameBoardPrint();
						MinmaxLastPlace[downDepth+1] = HMLastPlace[1];
						IsAIplayer = -1*IsAIplayer;
					}
				}
				Freespot = game.FreeSpot();
				Checkscore = game.CheckScore(downDepth);
			}
			if(Checkscore >= 500) {
				System.out.println("AI Player Wins!!!");
			}
			else if (Checkscore <= -500) {
				System.out.println("Human Player Wins!!!");
			}
			else if(FreespotSize == 0) {
				System.out.println("Game Draws!!!");
			}
			System.out.println("A new game? Press Y/N.");
			Scanner scanReplay = new Scanner(System.in);
			RepFlag = scanReplay.next();
			while (!RepFlag.equalsIgnoreCase("Y") && !RepFlag.equalsIgnoreCase("N")) {
				System.out.println("Invalid input!!! Please Press Y/N.");
				scanReplay = new Scanner(System.in);
				RepFlag = scanReplay.next();
			}
		}
	}

	public String[][] GameBoard() {
		Gameboard = new String[9][9];
		for (int i = 0; i<9; i++) {
			for (int j = 0; j<9; j++) {
				Gameboard[i][j] = "[ ]";
				System.out.print(Gameboard[i][j]);
			}
			System.out.println();
		}
		System.out.println("Game board initialized");
		return Gameboard;
	}

	public void GameBoardPrint() {
		for (int i = 0; i<9; i++) {
			System.out.print(Gameboard[i][0]+Gameboard[i][1]+Gameboard[i][2]+Gameboard[i][3]+Gameboard[i][4]+Gameboard[i][5]+Gameboard[i][6]+Gameboard[i][7]+Gameboard[i][8]);
			System.out.println();
		}
	}

	public int IsAIPlayer() {
		Scanner scanPlayer = new Scanner(System.in);
		System.out.println("X is the first, and O is the second. Please choose from X or O to start with.");
		HMPlayer = scanPlayer.next();
		if (HMPlayer.equalsIgnoreCase("O")) {
			AIPlayer = "X";
			return 1;
		}
		else if (HMPlayer.equalsIgnoreCase("X")) {
			AIPlayer = "O";
			return -1;
		}
		else {
			System.out.println("Invalid input!!! Please choose from X or O to start with.");
			return 0;
		}
	}

	public ArrayList<ArrayList<Integer>> FreeSpot() {
		FreespotSize = 0;
		for (int i = 1; i<=9; i++) {
			Integer[] sublist = {};
			ArrayList<Integer> subList1 = new ArrayList<>(Arrays.asList(sublist));
			ArrayList<Integer> subList2 = new ArrayList<>(Arrays.asList(sublist));
			ArrayList<Integer> subList3 = new ArrayList<>(Arrays.asList(sublist));
			for (int j = 1; j<=9; j++) {
				placeIndex = PlaceIndex(i,j);
				if (Gameboard[(placeIndex-1)/9][(placeIndex-1)%9].equals("[ ]")) {
					subList1.add(j);
					FreespotSize++;
				}
				else if (Gameboard[(placeIndex-1)/9][(placeIndex-1)%9].equals("[" + HMPlayer + "]")) {
					subList2.add(j);
				}
				else {
					subList3.add(j);
				}
			}
			Freespot.set(i-1,subList1);
			HMspot.set(i-1,subList2);
			AIspot.set(i-1,subList3);
		}
		return Freespot;
	}

	public int PlaceIndex(int location1, int location2) {
		placeIndex = (location1-1)/3*27+(location2-1)/3*9+(location1-1)%3*3+(location2-1)%3+1;
		return placeIndex;
	}

	public boolean PlaceMarker(int i, int j) {
		if(Freespot.get(i-1).contains(j)) {
			placeIndex = PlaceIndex(i,j);
			Gameboard[(placeIndex-1)/9][(placeIndex-1)%9] = "[" + CurrPlayer + "]";
			return true;
		}
		else {
			System.out.println("Place is invalid, retry!!!");
			return false;
		}
	}

	public void GameBoardRevert(int location1, Integer location2) {
		placeIndex = PlaceIndex(location1,location2);
		Gameboard[(placeIndex-1)/9][(placeIndex-1)%9] = "[ ]";
	}

	public int CheckScore(int searchDepthforScore) {
		FreeSpot();
		String[] WinPos = new String[] {"123","456","789","159","357","147","258","369"};
		for (int k = 0; k<8; k++) {
			char[] CA = WinPos[k].toCharArray();
			for (int m = 1; m<=9; m++) {
				if (HMspot.get(m-1).contains((Integer)Character.getNumericValue(CA[0]))  
						&& HMspot.get(m-1).contains((Integer)Character.getNumericValue(CA[1]))  
						&& HMspot.get(m-1).contains((Integer)Character.getNumericValue(CA[2]))) {
					return (-500)*(searchDepthforScore+2);
				}
				else if (AIspot.get(m-1).contains((Integer)Character.getNumericValue(CA[0])) 
						&& AIspot.get(m-1).contains((Integer)Character.getNumericValue(CA[1])) 
						&& AIspot.get(m-1).contains((Integer)Character.getNumericValue(CA[2]))) {
					return 500*(searchDepthforScore+2);
				}
			}
		}
		String[] SemiWinPos = new String[] {"12","23","13","45","56","46","78","89","79","14",
				"47","17","25","58","28","36","69","39","15","59","19","35","57","37"};
		semiscore = 0;
		for (int k = 0; k<24; k++) {
			for (int m = 1; m<=9; m++) {
				if (AIspot.get(m-1).contains((Integer)Character.getNumericValue(SemiWinPos[k].charAt(0))) 
						&& AIspot.get(m-1).contains((Integer)Character.getNumericValue(SemiWinPos[k].charAt(1)))) {
					semiscore += searchDepthforScore+1;
				}
				if (HMspot.get(m-1).contains((Integer)Character.getNumericValue(SemiWinPos[k].charAt(0))) 
						&& HMspot.get(m-1).contains((Integer)Character.getNumericValue(SemiWinPos[k].charAt(1)))) {
					semiscore -= searchDepthforScore+1;
				}
			}
		}
		return semiscore;
	}

	public int MaxScore(ArrayList<Integer> scorelist) {
		int Maxscore = -10000;
		for (int i = 0; i<scorelist.size(); i++) {
			if (Maxscore<scorelist.get(i)) {
				Maxscore = scorelist.get(i);
			}
		}
		return Maxscore;
	}

	public int MinScore(ArrayList<Integer> scorelist) {
		int Minscore = 10000;
		for (int i = 0; i<scorelist.size(); i++) {
			if (Minscore>scorelist.get(i)) {
				Minscore = scorelist.get(i);
			}
		}
		return Minscore;
	}

	public int ScoreListMax(ArrayList<Integer> scList) {
		int scoreListmax = -10000;
		for (int i = 0; i<scList.size(); i++) {
			if (scList.get(i) > scoreListmax) {
				scoreListmax = scList.get(i);
				scoreListMaxInd = i;
			}
		}
		return scoreListMaxInd;
	}

	public int MiniMax(boolean IsAI, int searchDepth, int alpha, int beta) {
//	public int MiniMax(boolean IsAI, int searchDepth) {
		int Checkscore = CheckScore(searchDepth);
		if (Checkscore >= 500 || Checkscore <= -500 || searchDepth == 0 || FreespotSize == 0) {
			return Checkscore;
		}
		else {
			if (IsAI) {
				CurrPlayer = AIPlayer;
				ArrayList<Integer> scorelist = new ArrayList<Integer>();
				Freespot = FreeSpot();
				for(int pos = 0; pos<Freespot.get(MinmaxLastPlace[searchDepth]-1).size(); pos++) {
					Integer Freespot_ele = Freespot.get(MinmaxLastPlace[searchDepth]-1).get(pos);
					CurrPlayer = AIPlayer;
					PlaceMarker(MinmaxLastPlace[searchDepth],Freespot_ele);				
					Freespot = FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					MinmaxLastPlace[searchDepth-1] = Freespot_ele;
					int AIscore = MiniMax(false, searchDepth-1, alpha, beta);
//					int AIscore = MiniMax(false, searchDepth-1);
					scorelist.add(AIscore);
					GameBoardRevert(MinmaxLastPlace[searchDepth],Freespot_ele);
					Freespot = FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					if (AIscore >= beta) {
						return AIscore;
					}
					alpha = Math.max(AIscore, alpha);
				}
				return MaxScore(scorelist);
			}
			else {
				CurrPlayer = HMPlayer;
				ArrayList<Integer> scorelist = new ArrayList<Integer>();
				Freespot = FreeSpot();
				for(int pos = 0; pos<Freespot.get(MinmaxLastPlace[searchDepth]-1).size(); pos++) {
					Integer Freespot_ele = Freespot.get(MinmaxLastPlace[searchDepth]-1).get(pos);
					CurrPlayer = HMPlayer;
					PlaceMarker(MinmaxLastPlace[searchDepth],Freespot_ele);
					Freespot = FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					MinmaxLastPlace[searchDepth-1] = Freespot_ele;
					int HMscore = MiniMax(true, searchDepth-1, alpha, beta);
//					int HMscore = MiniMax(true, searchDepth-1);
					scorelist.add(HMscore);
					GameBoardRevert(MinmaxLastPlace[searchDepth],Freespot_ele);
					Freespot = FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					if (HMscore <= alpha) {
						return HMscore;
					}
					beta = Math.min(HMscore, beta);
				}
				return MinScore(scorelist);
			}
		}
	}
}
