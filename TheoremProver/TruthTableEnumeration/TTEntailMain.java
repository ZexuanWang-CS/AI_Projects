import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TTEntailMain {

	public static ArrayList<String> SentenceSymbolSplit = new ArrayList<String>();
	public static String SentenceInKB;
	public static String SentenceInAlpha;

	public static void main(String[] args) {
		String flag = "Y";
		while (flag.equalsIgnoreCase("Y")) {
			TTEntailMain TTEntailmain = new TTEntailMain();
			String ProblemNum = TTEntailmain.ProbChoose();
			while (!ProblemNum.matches("[1-6]")) {
				System.out.println("\n" + "Invalid input! Please choose the problem from 1 to 6");
				ProblemNum = TTEntailmain.ProbChoose();
			}
			if (ProblemNum.equals("1")) {
				/*
				 * Modus Ponens
				 */
				long start = System.nanoTime();
				SentenceInKB = "(P&(¬P~Q))";
				SentenceInAlpha = "Q";
				boolean EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("The Modus Ponens result is " + EntailFlag);
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 1 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("2")) {
				/*
				 * Wumpus World (Simple)
				 */
				long start = System.nanoTime();
				String InKB1 = "¬P11";
				String InKB21 = "(¬B11~(P12~P21))";
				String InKB22 = "(¬(P12~P21)~B11)";
				String InKB31 = "(¬B21~((P11~P22)~P31))";
				String InKB32 = "(¬((P11~P22)~P31)~B21)";
				String InKB4 = "¬B11";
				String InKB5 = "B21";
				SentenceInKB = "((((((" + InKB1 + "&" + InKB21 + ")&" + InKB22 
						+ ")&" + InKB31 + ")&" + InKB32 + ")&" + InKB4 + 
						")&" + InKB5 + ")";
				SentenceInAlpha = "P12";
				boolean EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("The Wumpus World (Simple) result is " + EntailFlag);
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 2 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("3")) {
				/*
				 * Horn Clauses
				 */
				long start = System.nanoTime();
				String InKB1 = "(¬Myt~Imm)";
				String InKB2 = "((Myt~¬Imm)&(Myt~Mam))";
				String InKB3 = "((¬Imm~Hor)&(¬Mam~Hor))";
				String InKB4 = "(¬Hor~Mag)";
				SentenceInKB = "(((" + InKB1 + "&" + InKB2 + ")&" + InKB3 + ")&" + InKB4 + ")";
				SentenceInAlpha = "Myt";
				boolean EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("The Horn Clauses result is " + EntailFlag + " for Mythical");
				SentenceInAlpha = "Mag";
				EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("The Horn Clauses result is " + EntailFlag + " for Magical");
				SentenceInAlpha = "Hor";
				EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("The Horn Clauses result is " + EntailFlag + " for Horned");
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 3 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("4")) {
				/*
				 * Liars and Truth-tellers
				 */
				long start = System.nanoTime();
				String InKB11a = "(¬A~(A&C))";
				String InKB12a = "(A~(¬A~¬C))";
				String InKB21a = "(¬B~¬C)";
				String InKB22a = "(B~C)";
				String InKB31a = "(¬C~(B~¬A))";
				String InKB32a = "(C~¬(B~¬A))";
				SentenceInKB = "(((((" + InKB11a + "&" + InKB12a + ")&" + InKB21a + ")&" + InKB22a + ")&" + InKB31a + ")&" + InKB32a + ")";
				SentenceInAlpha = "A";
				boolean EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("Part a: The Liars and Truth-tellers result is " + EntailFlag + " for A");
				SentenceInAlpha = "B";
				EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("Part a: The Liars and Truth-tellers result is " + EntailFlag + " for B");
				SentenceInAlpha = "C";
				EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("Part a: The Liars and Truth-tellers result is " + EntailFlag + " for C");
				String InKB11b = "(¬A~¬C)";
				String InKB12b = "(A~C)";
				String InKB21b = "(¬B~(A&C))";
				String InKB22b = "(B~(¬A~¬C))";
				String InKB31b = "(¬C~B)";
				String InKB32b = "(C~¬B)";
				SentenceInKB = "(((((" + InKB11b + "&" + InKB12b + ")&" + InKB21b + ")&" + InKB22b + ")&" + InKB31b + ")&" + InKB32b + ")";
				SentenceInAlpha = "A";
				EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("Part b: The Liars and Truth-tellers result is " + EntailFlag + " for A");
				SentenceInAlpha = "B";
				EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("Part b: The Liars and Truth-tellers result is " + EntailFlag + " for B");
				SentenceInAlpha = "C";
				EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
				System.out.println("Part b: The Liars and Truth-tellers result is " + EntailFlag + " for C");
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 4 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("5")) {
				/*
				 * More Liars and Truth-tellers
				 */
				long start = System.nanoTime();
				String InKBa1 = "(¬A~(H&I))";
				String InKBa2 = "(A~(¬H~¬I))";
				String InKBb1 = "(¬B~(A&L))";
				String InKBb2 = "(B~(¬A~¬L))";
				String InKBc1 = "(¬C~(B&G))";
				String InKBc2 = "(C~(¬B~¬G))";
				String InKBd1 = "(¬D~(E&L))";
				String InKBd2 = "(D~(¬E~¬L))";
				String InKBe1 = "(¬E~(C&H))";
				String InKBe2 = "(E~(¬C~¬H))";
				String InKBf1 = "(¬F~(D&I))";
				String InKBf2 = "(F~(¬D~¬I))";
				String InKBg1 = "(¬G~(¬E&¬J))";
				String InKBg2 = "(G~(E~J))";
				String InKBh1 = "(¬H~(¬F&¬K))";
				String InKBh2 = "(H~(F~K))";
				String InKBi1 = "(¬I~(¬G&¬K))";
				String InKBi2 = "(I~(G~K))";
				String InKBj1 = "(¬J~(¬A&¬C))";
				String InKBj2 = "(J~(A~C))";
				String InKBk1 = "(¬K~(¬D&¬F))";
				String InKBk2 = "(K~(D~F))";
				String InKBl1 = "(¬L~(¬B&¬J))";
				String InKBl2 = "(L~(B~J))";
				SentenceInKB = "(((((((((((((((((((((((" + InKBa1 + "&" + InKBa2 + ")&" + InKBb1 + ")&" 
						+ InKBb2 + ")&" + InKBc1 + ")&" + InKBc2 + ")&" + InKBd1 + ")&" + InKBd2 + ")&" 
						+ InKBe1 + ")&" + InKBe2 + ")&" + InKBf1 + ")&" + InKBf2 + ")&" + InKBg1 + ")&" 
						+ InKBg2 + ")&" + InKBh1 + ")&" + InKBh2 + ")&" + InKBi1 + ")&" + InKBi2 + ")&" 
						+ InKBj1 + ")&" + InKBj2 + ")&" + InKBk1 + ")&" + InKBk2 + ")&" + InKBl1 + ")&" 
						+ InKBl2 + ")";
				List<String> Peop = Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L");
				for (String i : Peop) {
					SentenceInAlpha = i;
					boolean EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
					System.out.println("The More Liars and Truth-tellers result is " + EntailFlag + " for " + i);
				}
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 5 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("6")) {
				/*
				 * Doors of Enlightenment
				 */
				long start = System.nanoTime();
				String InKBa11 = "(¬A~X)";
				String InKBa12 = "(A~¬X)";
				String InKBa21 = "((¬B~Y)~Z)";
				String InKBa22 = "(B~¬Y)";
				String InKBa23 = "(B~¬Z)";
				String InKBa31 = "(¬C~A)";
				String InKBa32 = "(¬C~B)";
				String InKBa33 = "((C~¬A)~¬B)";
				String InKBa41 = "(¬D~X)";
				String InKBa42 = "(¬D~Y)";
				String InKBa43 = "((D~¬X)~¬Y)";
				String InKBa51 = "(¬E~X)";
				String InKBa52 = "(¬E~Z)";
				String InKBa53 = "((E~¬X)~¬Z)";
				String InKBa61 = "((¬F~D)~E)";
				String InKBa62 = "(F~¬D)";
				String InKBa63 = "(F~¬E)";
				String InKBa71 = "((¬G~¬C)~F)";
				String InKBa72 = "(G~C)";
				String InKBa73 = "(G~¬F)";
				String InKBa81 = "((¬H~¬G)~A)";
				String InKBa82 = "(H~G)";
				String InKBa83 = "H";
				String InKBa84 = "(H~¬A)";
				String InKBa9 = "(((X~Y)~Z)~W)";
				SentenceInKB = "((((((((((((((((((((((((" + InKBa11 + "&" + InKBa12 + ")&" + InKBa21 + ")&" + InKBa22 + ")&"
						+ InKBa23 + ")&" + InKBa31 + ")&" + InKBa32 + ")&" + InKBa33 + ")&" + InKBa41 + ")&" + InKBa42 + ")&" 
						+ InKBa43 + ")&" + InKBa51 + ")&" + InKBa52 + ")&" + InKBa53 + ")&" + InKBa61 + ")&" + InKBa62 + ")&" 
						+ InKBa63 + ")&" + InKBa71 + ")&" + InKBa72 + ")&" + InKBa73 + ")&" + InKBa81 + ")&" + InKBa82 + ")&" 
						+ InKBa83 + ")&" + InKBa84 + ")&" + InKBa9 + ")";
				List<String> Door = Arrays.asList("X","Y","Z","W");
				for (String i : Door) {
					SentenceInAlpha = i;
					boolean EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
					System.out.println("Part a: The Doors of Enlightenment result is " + EntailFlag + " for " + i);
				}
				String InKBb11 = "(¬A~X)";
				String InKBb12 = "(A~¬X)";
				String InKBb31 = "(¬C~A)";
				String InKBb32 = "(((((((¬C~B)~C)~D)~E)~F)~G)~H)";
				String InKBb33 = "((C~¬A)~¬B)";
				String InKBb34 = "((C~¬A)~¬C)";
				String InKBb35 = "((C~¬A)~¬D)";
				String InKBb36 = "((C~¬A)~¬E)";
				String InKBb37 = "((C~¬A)~¬F)";
				String InKBb38 = "((C~¬A)~¬G)";
				String InKBb39 = "((C~¬A)~¬H)";
				String InKBb81 = "((¬H~¬G)~A)";
				String InKBb82 = "(H~G)";
				String InKBb83 = "H";
				String InKBb84 = "(H~¬A)";
				String InKBb1 = "(G~C)";
				String InKBb9 = "(((X~Y)~Z)~W)";
				SentenceInKB = "((((((((((((((((" + InKBb11 + "&" + InKBb12 + ")&" + InKBb31 + ")&" + InKBb32 + ")&"
						+ InKBb33 + ")&" + InKBb34 + ")&" + InKBb35 + ")&" + InKBb36 + ")&" + InKBb37 + ")&" + InKBb38 + ")&" 
						+ InKBb39 + ")&" + InKBb81 + ")&" + InKBb82 + ")&" + InKBb83 + ")&" + InKBb84 + ")&" + InKBb1 + ")&" 
						+ InKBb9 + ")";
				for (String i : Door) {
					SentenceInAlpha = i;
					boolean EntailFlag = TTEntailmain.isEntail(SentenceInKB, SentenceInAlpha);
					System.out.println("Part b: The Doors of Enlightenment result is " + EntailFlag + " for " + i);
				}
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 6 is " + runtime + "ms");
			}
			System.out.println("\n" + "Try again? Press Y/N.");
			Scanner scanReplay = new Scanner(System.in);
			flag = scanReplay.next();
			while (!flag.equalsIgnoreCase("Y") && !flag.equalsIgnoreCase("N")) {
				System.out.println("Invalid input!!! Please Press Y/N.");
				scanReplay = new Scanner(System.in);
				flag = scanReplay.next();
			}
		}
	}

	public String ProbChoose() {
		Scanner ProblemNumScan = new Scanner(System.in);
		System.out.println("Please choose the problem");
		System.out.println("Problem list:" + "\n" + "1. Modus Ponens" + "\n" + 
				"2. Wumpus World (Simple)" + "\n" + "3. Horn Clauses" + "\n" + 
				"4. Liars and Truth-tellers" + "\n" + "5. More Liars and Truth-tellers"
				+ "\n" + "6. Doors of Enlightenment");
		String ProblemNum = ProblemNumScan.next();
		return ProblemNum;
	}

	public boolean isEntail (String sentenceInKB, String sentenceInAlpha) {
		LogicalExpressionTree KBTree = new LogicalExpressionTree(sentenceInKB);
		LogicalExpressionTree AlphaTree = new LogicalExpressionTree(sentenceInAlpha);
		ArrayList<String> KBSentenceSplit = KBTree.SentenceToken(sentenceInKB);
		ArrayList<String> KBandAlphaSentenceSplit = AlphaTree.SentenceToken(sentenceInAlpha);
		for (String KBandAlphaSentenceSplit_ele : KBandAlphaSentenceSplit) {
			if (KBandAlphaSentenceSplit_ele.matches("[a-zA-Z0-9]+")) {
				if (!SentenceSymbolSplit.contains(KBandAlphaSentenceSplit_ele)) {
					SentenceSymbolSplit.add(KBandAlphaSentenceSplit_ele);
				}
			}
		}
		HashMap<String, Boolean> Models = new HashMap<String, Boolean>();
		boolean isEntailFlag = ModelCheck(KBTree, AlphaTree, SentenceSymbolSplit, Models);
		LogicalExpressionTree.SentenceSplit.clear();
		SentenceSymbolSplit = new ArrayList<String>();
		return isEntailFlag;
	}

	public boolean ModelCheck(LogicalExpressionTree KBtree, LogicalExpressionTree Alphatree, 
			ArrayList<String> Symbols, HashMap models) {
		if (Symbols.isEmpty()) {
			if (isLETrue(KBtree.Root, models)) {
				return isLETrue(Alphatree.Root, models);
			}
			else {
				return true;
			}
		}
		else {
			ArrayList<String> SymRest = deepCloneSym(Symbols);
			String CurrSym = SymRest.remove(0);
			HashMap<String, Boolean> modelsTrue = deepCloneModel(models);
			modelsTrue.put(CurrSym, true);
			HashMap<String, Boolean> modelsFalse = deepCloneModel(models);
			modelsFalse.put(CurrSym, false);
			boolean flag_A = ModelCheck(KBtree, Alphatree, SymRest, modelsTrue);
			boolean flag_B = ModelCheck(KBtree, Alphatree, SymRest, modelsFalse);
			return flag_A && flag_B;
		}
	}

	public boolean isLETrue(LETreeNode KBorAlphaTree, HashMap mOdels) {
		LETreeNode CurrNode = KBorAlphaTree;
		if (CurrNode.value.contains("&")) {
			if ((isLETrue(CurrNode.ChildLeft, mOdels) == false) || 
					(isLETrue(CurrNode.ChildRight, mOdels) == false)) {
				return false;
			}
			else {
				return true;
			}
		}
		else if (CurrNode.value.contains("~")) {
			if ((isLETrue(CurrNode.ChildLeft, mOdels) == true) || 
					(isLETrue(CurrNode.ChildRight, mOdels) == true)) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (CurrNode.value.contains("¬")) {
			return !isLETrue(CurrNode.ChildLeft, mOdels);
		}
		else {
			boolean flag = (boolean) mOdels.get(CurrNode.value);
			return flag;
		}
	}

	public HashMap<String, Boolean> deepCloneModel(HashMap<String, Boolean> hashMapOri) {
		HashMap<String, Boolean> hashMapNew = new HashMap<String, Boolean>();
		for (Map.Entry<String, Boolean> hashMapOri_ele : hashMapOri.entrySet()) {
			hashMapNew.put(hashMapOri_ele.getKey(), hashMapOri_ele.getValue());
		}
		return hashMapNew;
	}

	public ArrayList<String> deepCloneSym(ArrayList<String> Symrest) {
		ArrayList<String> SymrestNew = new ArrayList<String>();
		for (String Symrest_ele : Symrest) {
			SymrestNew.add(Symrest_ele);
		}
		return SymrestNew;
	}
}