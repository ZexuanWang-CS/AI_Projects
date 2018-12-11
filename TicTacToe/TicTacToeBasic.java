import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeBasic {
	public static String[][] Gameboard;
	public static ArrayList<Integer> Freespot;
	public static int IsAIplayer;
	public static String CurrPlayer;
	public static String AIPlayer;
	public static String HMPlayer;
	public static Integer LastMove;
	public static String flag;
	public static int alpha;
	public static int beta;

	public static void main(String[] args) {
		Freespot = new ArrayList<Integer>();
		flag = "Y";
		alpha = -1000;
		beta = 1000;
		while (flag.equalsIgnoreCase("Y")) {
			TicTacToeBasic game = new TicTacToeBasic();
			Gameboard = game.GameBoard();
			Freespot = game.FreeSpot();
			IsAIplayer = game.IsAIPlayer();
			while (IsAIplayer == 0) {
				IsAIplayer = game.IsAIPlayer();
			}
			Scanner scanPlace = new Scanner(System.in);
			Integer place;
			int Checkscore = game.CheckScore();
			Freespot = game.FreeSpot();
			while(Checkscore == 0 && !Freespot.isEmpty()) {
				if (IsAIplayer == 1) {
					CurrPlayer = AIPlayer;
					if(Freespot.size() == 9) {
						System.out.println("AI moves at 5");
						game.PlaceMarker(5);
						IsAIplayer = -1*IsAIplayer;
					}
					else {
						for(int pos=0;pos<Freespot.size(); pos++){
							Integer Freespot_ele = Freespot.get(pos);
							Gameboard[(Freespot_ele-1)/3][(Freespot_ele-1)%3] = "[" + AIPlayer + "]";
							Freespot = game.FreeSpot();
							IsAIplayer = -1*IsAIplayer;
							int score = game.MiniMax(false,alpha,beta);
							System.out.println("Score evaluation on spot " + Freespot_ele + ": " + score);
							game.GameBoardRevert(Freespot_ele);
							Freespot = game.FreeSpot();
							IsAIplayer = -1*IsAIplayer;
							if (score == 1) {
								System.out.println(Freespot_ele);
								System.out.println();
								Gameboard[(Freespot_ele-1)/3][(Freespot_ele-1)%3] = "[" + AIPlayer + "]";
								System.out.println("AI moves at " + Freespot_ele);
								IsAIplayer = -1*IsAIplayer;
								break;
							}
							else if (score == 0) {
								LastMove = Freespot_ele;
							}
						}
						if (IsAIplayer == 1) {
							System.out.println(LastMove);
							System.out.println();
							CurrPlayer = AIPlayer;
							System.out.println("AI moves at " + LastMove);
							game.PlaceMarker(LastMove);
							IsAIplayer = -1*IsAIplayer;
						}
					}
					game.GameBoardPrint();
				}
				else if (IsAIplayer == -1) {
					System.out.println("Your turn. Enter the spot you want to place.");
					place = scanPlace.nextInt();
					CurrPlayer = HMPlayer;
					if (game.PlaceMarker(place)) {
						game.GameBoardPrint();
						IsAIplayer = -1*IsAIplayer;
					}
				}
				Freespot = game.FreeSpot();
				Checkscore = game.CheckScore();
			}
			if(Checkscore == 0 && Freespot.isEmpty()) {
				System.out.println("Game Draws!!!");
			}
			else if(Checkscore == 1) {
				System.out.println("AI Player Wins!!!");
			}
			else {
				System.out.println("Human Player Wins!!!");
			}
			System.out.println("A new game? Press Y/N.");
			Scanner scanReplay = new Scanner(System.in);
			flag = scanReplay.next();
			while (!flag.equalsIgnoreCase("Y") && !flag.equalsIgnoreCase("N")) {
				System.out.println("Invalid input!!! Please Press Y/N.");
				scanReplay = new Scanner(System.in);
				flag = scanReplay.next();
			}
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

	public String[][] GameBoard() {
		Gameboard = new String[3][3];
		for (int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				Gameboard[i][j] = "[ ]";
				System.out.print(Gameboard[i][j]);
			}
			System.out.println();
		}
		System.out.println("Game board initialized");
		return Gameboard;
	}

	public void GameBoardPrint() {
		for (int i = 0; i<3; i++) {
			System.out.print(Gameboard[i][0]+Gameboard[i][1]+Gameboard[i][2]);
			System.out.println();
		}
	}

	public ArrayList<Integer> FreeSpot() {
		Freespot.clear();
		for (int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				if (Gameboard[i][j].equals("[ ]")) {
					Freespot.add(3*i+j+1);
				}
			}
		}
		return Freespot;
	}

	public void GameBoardRevert(Integer Freespot_ele) {
		Gameboard[(Freespot_ele-1)/3][(Freespot_ele-1)%3] = "[ ]";
	}

	public int CheckScore() {
		String[] WinPos = new String[] {"123","456","789","159","357","147","258","369"};
		for (int i = 0; i<=6; i++) {
			for (int j = 8; j>=i+2; j--) {
				if (Gameboard[i/3][i%3].equals("[" + AIPlayer + "]")&&Gameboard[j/3][j%3].equals("[" + AIPlayer + "]")&&
						Gameboard[(i+j)/2/3][(i+j)/2%3].equals("[" + AIPlayer + "]")) {
					int k = 0;
					while (k<=7) {
						if (WinPos[k].equals(Integer.toString(i+1) + Integer.toString((i+j+2)/2) + Integer.toString(j+1))) {
							return 1;
						}
						else {
							k++;
						}
					}
				}
				else if (Gameboard[i/3][i%3].equals("[" + HMPlayer + "]")&&Gameboard[j/3][j%3].equals("[" + HMPlayer + "]")&&
						Gameboard[(i+j)/2/3][(i+j)/2%3].equals("[" + HMPlayer + "]")) {
					int k = 0;
					while (k<=7) {
						if (WinPos[k].equals(Integer.toString(i+1) + Integer.toString((i+j+2)/2) + Integer.toString(j+1))) {
							return -1;
						}
						else {
							k++;
						}
					}
				}
			}
		}
		return 0;
	}

	public boolean PlaceMarker(Integer place) {
		if(Freespot.contains(place)) {
			Gameboard[(place-1)/3][(place-1)%3] = "[" + CurrPlayer + "]";
			return true;
		}
		else {
			System.out.println("Place is invalid, retry!!!");
			return false;
		}
	}

	public int MaxScore(ArrayList<Integer> scorelist) {
		int Maxscore = -5;
		for (int i = 0; i<scorelist.size(); i++) {
			if (Maxscore<scorelist.get(i)) {
				Maxscore = scorelist.get(i);
			}
		}
		return Maxscore;
	}

	public int MinScore(ArrayList<Integer> scorelist) {
		int Minscore = 5;
		for (int i = 0; i<scorelist.size(); i++) {
			if (Minscore>scorelist.get(i)) {
				Minscore = scorelist.get(i);
			}
		}
		return Minscore;
	}

	public int MiniMax(boolean IsAI, int alpha, int beta) {
		int Checkscore = this.CheckScore();
		if (Checkscore != 0 || Freespot.isEmpty()) {
			return Checkscore;
		}
		else {
			if (IsAI) {
				CurrPlayer = AIPlayer;
				ArrayList<Integer> scorelist = new ArrayList<Integer>();
				FreeSpot();
				for(int pos = 0; pos<Freespot.size(); pos++) {
					Integer Freespot_ele = Freespot.get(pos);
					CurrPlayer = AIPlayer;
					Gameboard[(Freespot_ele-1)/3][(Freespot_ele-1)%3] = "[" + AIPlayer + "]";
					FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					int score = MiniMax(false,alpha,beta);
					scorelist.add(score);
					GameBoardRevert(Freespot_ele);
					Freespot = FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					if (score >= beta) {
						return score;
					}
					alpha = Math.max(score, alpha);
				}
				return MaxScore(scorelist);
			}
			else {
				CurrPlayer = HMPlayer;
				ArrayList<Integer> scorelist = new ArrayList<Integer>();
				FreeSpot();
				for(int pos = 0; pos<Freespot.size(); pos++) {
					Integer Freespot_ele = Freespot.get(pos);
					CurrPlayer = HMPlayer;
					Gameboard[(Freespot_ele-1)/3][(Freespot_ele-1)%3] = "[" + HMPlayer + "]";
					FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					int score = MiniMax(true,alpha,beta);
					scorelist.add(score);
					GameBoardRevert(Freespot_ele);
					Freespot = FreeSpot();
					IsAIplayer = -1*IsAIplayer;
					if (score <= alpha) {
						return score;
					}
					beta = Math.min(score, beta);
				}
				return MinScore(scorelist);
			}
		}
	}
}
