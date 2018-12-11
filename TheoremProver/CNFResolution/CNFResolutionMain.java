import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CNFResolutionMain {

	public static String CNFSentenceIn;
	public static Set<String> Clause;
	public static Set<String> NewClause;

	public static void main(String[] args) {
		String flag = "Y";
		while (flag.equalsIgnoreCase("Y")) {
			CNFResolutionMain CNFResolutionmain = new CNFResolutionMain();
			Clause = new HashSet<String>();
			NewClause = new HashSet<String>();
			String ProblemNum = CNFResolutionmain.ProbChoose();
			while (!ProblemNum.matches("[1-6]")) {
				System.out.println("\n" + "Invalid input! Please choose the problem from 1 to 6");
				ProblemNum = CNFResolutionmain.ProbChoose();
			}
			if (ProblemNum.equals("1")) {
				/*
				 * Modus Ponens: True
				 */
				long start = System.nanoTime();
				CNFSentenceIn = "(P)&(¬P~Q)&(¬Q)";
				boolean ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("The Modus Ponens result is " + ResFlag);
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 1 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("2")) {
				/*
				 * Wumpus World (Simple): False
				 */
				long start = System.nanoTime();
				String InKB1 = "(¬P11)";
				String InKB21 = "(¬B11~P12~P21)";
				String InKB221 = "(¬P12~B11)";
				String InKB222 = "(¬P21~B11)";
				String InKB31 = "(¬B21~P11~P22~P31)";
				String InKB321 = "(B21~¬P11)";
				String InKB322 = "(B21~¬P22)";
				String InKB323 = "(B21~¬P31)";
				String InKB4 = "(¬B11)";
				String InKB5 = "(B21)";
				String InAlp = "(¬P12)";
				CNFSentenceIn = InKB1 + "&" + InKB21 + "&" + InKB221 + "&" + InKB222 
						+ "&" + InKB31 + "&" + InKB321 + "&" + InKB322 + "&" + InKB323
						+ "&" + InKB4 + "&" + InKB5 + "&" + InAlp;
				boolean ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("The Wumpus World (Simple) result is " + ResFlag);
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 2 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("3")) {
				/*
				 * Horn Clauses: False, True, True
				 */
				long start = System.nanoTime();
				String InKB1 = "(¬Myt~Imm)";
				String InKB21 = "(Myt~¬Imm)";
				String InKB22 = "(Myt~Mam)";
				String InKB31 = "(¬Imm~Hor)";
				String InKB32 = "(¬Mam~Hor)";
				String InKB4 = "(¬Hor~Mag)";
				String InAlp1 = "(¬Myt)";
				String InAlp2 = "(¬Mag)";
				String InAlp3 = "(¬Hor)";
				CNFSentenceIn = InKB1 + "&" + InKB21 + "&" + InKB22 + "&" + InKB31 + "&" + InKB32 
						+ "&" + InKB4 + "&" + InAlp1;
				boolean ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("The Horn Clauses result is " + ResFlag + " for Mythical");
				CNFSentenceIn = InKB1 + "&" + InKB21 + "&" + InKB22 + "&" + InKB31 + "&" + InKB32 
						+ "&" + InKB4 + "&" + InAlp2;
				ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("The Horn Clauses result is " + ResFlag + " for Magical");
				CNFSentenceIn = InKB1 + "&" + InKB21 + "&" + InKB22 + "&" + InKB31 + "&" + InKB32 
						+ "&" + InKB4 + "&" + InAlp3;
				ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("The Horn Clauses result is " + ResFlag + " for Horned");
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 3 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("4")) {
				/*
				 * Liars and Truth-tellers: False, False, True, True, False, False
				 */
				long start = System.nanoTime();
				String InKB11a = "(¬A~A)";
				String InKB12a = "(¬A~C)";
				String InKB13a = "(A~¬A~C)";
				String InKB21a = "(¬B~¬C)";
				String InKB22a = "(B~C)";
				String InKB31a = "(¬C~B~¬A)";
				String InKB32a = "(C~¬B)";
				String InKB33a = "(C~A)";
				String InAlp1a = "(¬A)";
				String InAlp2a = "(¬B)";
				String InAlp3a = "(¬C)";
				CNFSentenceIn = InKB12a + "&" + InKB21a + "&" + InKB22a + "&" + InKB31a + "&" + InKB32a + "&" + InKB33a + "&" + InAlp1a;
				boolean ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("Part a: The Liars and Truth-tellers result is " + ResFlag + " for A");
				CNFSentenceIn = InKB12a + "&" + InKB21a + "&" + InKB22a + "&" + InKB31a + "&" + InKB32a + "&" + InKB33a + "&" + InAlp2a;
				ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("Part a: The Liars and Truth-tellers result is " + ResFlag + " for B");
				CNFSentenceIn = InKB12a + "&" + InKB21a + "&" + InKB22a + "&" + InKB31a + "&" + InKB32a + "&" + InKB33a + "&" + InAlp3a;
				ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("Part a: The Liars and Truth-tellers result is " + ResFlag + " for C");
				String InKB11b = "(¬A~¬C)";
				String InKB12b = "(A~C)";
				String InKB21b = "(¬B~A)";
				String InKB22b = "(¬B~C)";
				String InKB23b = "(B~¬A~¬C)";
				String InKB31b = "(¬C~B)";
				String InKB32b = "(C~¬B)";
				String InAlp1b = "(¬A)";
				String InAlp2b = "(¬B)";
				String InAlp3b = "(¬C)";
				CNFSentenceIn = InKB11b + "&" + InKB12b + "&" + InKB21b + "&" + InKB22b + "&" + InKB23b + "&" + InKB31b + "&" + InKB32b + "&" + InAlp1b;
				ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("Part b: The Liars and Truth-tellers result is " + ResFlag + " for A");
				CNFSentenceIn = InKB11b + "&" + InKB12b + "&" + InKB21b + "&" + InKB22b + "&" + InKB23b + "&" + InKB31b + "&" + InKB32b + "&" + InAlp2b;
				ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("Part b: The Liars and Truth-tellers result is " + ResFlag + " for B");
				CNFSentenceIn = InKB11b + "&" + InKB12b + "&" + InKB21b + "&" + InKB22b + "&" + InKB23b + "&" + InKB31b + "&" + InKB32b + "&" + InAlp3b;
				ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceIn);
				System.out.println("Part b: The Liars and Truth-tellers result is " + ResFlag + " for C");
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 4 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("5")) {
				/*
				 * More Liars and Truth-tellers: A False, B False, C False, D False, 
				 * E False, F False, G False, H False, I False, J True, K True, L False, 
				 */
				long start = System.nanoTime();
				String InKBa1 = "(¬A~H)";
				String InKBa2 = "(¬A~I)";
				String InKBa3 = "(A~¬H~¬I)";
				String InKBb1 = "(¬B~A)";
				String InKBb2 = "(¬B~L)";
				String InKBb3 = "(B~¬A~¬L)";
				String InKBc1 = "(¬C~B)";
				String InKBc2 = "(¬C~G)";
				String InKBc3 = "(C~¬B~¬G)";
				String InKBd1 = "(¬D~E)";
				String InKBd2 = "(¬D~L)";
				String InKBd3 = "(D~¬E~¬L)";
				String InKBe1 = "(¬E~C)";
				String InKBe2 = "(¬E~H)";
				String InKBe3 = "(E~¬C~¬H)";
				String InKBf1 = "(¬F~D)";
				String InKBf2 = "(¬F~I)";
				String InKBf3 = "(F~¬D~¬I)";
				String InKBg1 = "(¬G~¬E)";
				String InKBg2 = "(¬G~¬J)";
				String InKBg3 = "(G~E~J)";
				String InKBh1 = "(¬H~¬F)";
				String InKBh2 = "(¬H~¬K)";
				String InKBh3 = "(H~F~K)";
				String InKBi1 = "(¬I~¬G)";
				String InKBi2 = "(¬I~¬K)";
				String InKBi3 = "(I~G~K)";
				String InKBj1 = "(¬J~¬A)";
				String InKBj2 = "(¬J~¬C)";
				String InKBj3 = "(J~A~C)";
				String InKBk1 = "(¬K~¬D)";
				String InKBk2 = "(¬K~¬F)";
				String InKBk3 = "(K~D~F)";
				String InKBl1 = "(¬L~¬B)";
				String InKBl2 = "(¬L~¬J)";
				String InKBl3 = "(L~B~J)";
				List<String> Peop = Arrays.asList("¬A","¬B","¬C","¬D","¬E","¬F","¬G","¬H","¬I","¬J","¬K","¬L");
				CNFSentenceIn = InKBa1 + "&" + InKBa2 + "&" + InKBa3 + "&" + InKBb1 + "&" + InKBb2 + "&" + InKBb3 
						+ "&" + InKBc1 + "&" + InKBc2 + "&" + InKBc3 + "&" + InKBd1 + "&" + InKBd2 + "&" + InKBd3 
						+ "&" + InKBe1 + "&" + InKBe2 + "&" + InKBe3 + "&" + InKBf1 + "&" + InKBf2 + "&" + InKBf3 
						+ "&" + InKBg1 + "&" + InKBg2 + "&" + InKBg3 + "&" + InKBh1 + "&" + InKBh2 + "&" + InKBh3 
						+ "&" + InKBi1 + "&" + InKBi2 + "&" + InKBi3 + "&" + InKBj1 + "&" + InKBj2 + "&" + InKBj3 
						+ "&" + InKBk1 + "&" + InKBk2 + "&" + InKBk3 + "&" + InKBl1 + "&" + InKBl2 + "&" + InKBl3;
				for (String i : Peop) {
					String CNFSentenceInNew = new String();
					CNFSentenceInNew = CNFSentenceIn + "&(" + i + ")";
					boolean ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceInNew);
					System.out.println("The More Liars and Truth-tellers result is " + ResFlag + " for " + i.substring(1,2));
				}
				long end = System.nanoTime();
				long runtime = (end - start)/1000000;
				System.out.println("\n" + "The running time for 5 is " + runtime + "ms");
			}
			else if (ProblemNum.equals("6")) {
				/*
				 * The Doors of Enlightenment: X True, Y TBD, Z TBD, W TBD
				 */
				long start = System.nanoTime();
				String InKBa11 = "(¬A~X)";
				String InKBa12 = "(A~¬X)";
				String InKBa21 = "(¬B~Y~Z)";
				String InKBa22 = "(B~¬Y)";
				String InKBa23 = "(B~¬Z)";
				String InKBa31 = "(¬C~A)";
				String InKBa32 = "(¬C~B)";
				String InKBa33 = "(C~¬A~¬B)";
				String InKBa41 = "(¬D~X)";
				String InKBa42 = "(¬D~Y)";
				String InKBa43 = "(D~¬X~¬Y)";
				String InKBa51 = "(¬E~X)";
				String InKBa52 = "(¬E~Z)";
				String InKBa53 = "(E~¬X~¬Z)";
				String InKBa61 = "(¬F~D~E)";
				String InKBa62 = "(F~¬D)";
				String InKBa63 = "(F~¬E)";
				String InKBa71 = "(¬G~¬C~F)";
				String InKBa72 = "(G~C)";
				String InKBa73 = "(G~¬F)";
				String InKBa81 = "(¬H~¬G~A)";
				String InKBa82 = "(H~G)";
				String InKBa83 = "(H)";
				String InKBa84 = "(H~¬A)";
				String InKBa9 = "(X~Y~Z~W)";
				List<String> Door = Arrays.asList("¬X","¬Y","¬Z","¬W");
				CNFSentenceIn = InKBa11 + "&" + InKBa12 + "&" + InKBa21 + "&" + InKBa22 + "&" + InKBa23 + "&" + InKBa31 
						+ "&" + InKBa32 + "&" + InKBa33 + "&" + InKBa41 + "&" + InKBa42 + "&" + InKBa43 + "&" + InKBa51 
						+ "&" + InKBa52 + "&" + InKBa53 + "&" + InKBa61 + "&" + InKBa62 + "&" + InKBa63 + "&" + InKBa71 
						+ "&" + InKBa72 + "&" + InKBa73 + "&" + InKBa81 + "&" + InKBa82 + "&" + InKBa83 + "&" + InKBa84 
						+ "&" + InKBa9;
				for (String i : Door) {
					String CNFSentenceInNew = new String();
					CNFSentenceInNew = CNFSentenceIn + "&(" + i + ")";
					boolean ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceInNew);
					System.out.println("Part a: The Doors of Enlightenment result is " + ResFlag + " for " + i.substring(1,2));
				}
				String InKBb11 = "(¬A~X)";
				String InKBb12 = "(A~¬X)";
				String InKBb31 = "(¬C~A)";
				String InKBb32 = "(¬C~B~C~D~E~F~G~H)";
				String InKBb33 = "(C~¬A~¬B)";
				String InKBb34 = "(C~¬A~¬C)";
				String InKBb35 = "(C~¬A~¬D)";
				String InKBb36 = "(C~¬A~¬E)";
				String InKBb37 = "(C~¬A~¬F)";
				String InKBb38 = "(C~¬A~¬G)";
				String InKBb39 = "(C~¬A~¬H)";
				String InKBb81 = "(¬H~¬G~A)";
				String InKBb82 = "(H~G)";
				String InKBb83 = "(H)";
				String InKBb84 = "(H~¬A)";
				String InKBb1 = "(G~C)";
				String InKBb9 = "(X~Y~Z~W)";
				CNFSentenceIn = InKBb11 + "&" + InKBb12 + "&" + InKBb31 + "&" + InKBb32 + "&" + InKBb33 
						+ "&" + InKBb34 + "&" + InKBb35 + "&" + InKBb36 + "&" + InKBb37 + "&" + InKBb38 
						+ "&" + InKBb39 + "&" + InKBb81 + "&" + InKBb82 + "&" + InKBb83 + "&" + InKBb84 
						+ "&" + InKBb1 + "&" + InKBb9;
				for (String i : Door) {
					String CNFSentenceInNew = new String();
					CNFSentenceInNew = CNFSentenceIn + "&(" + i + ")";
					boolean ResFlag = CNFResolutionmain.CNFResolution(CNFSentenceInNew);
					System.out.println("Part b: The Doors of Enlightenment result is " + ResFlag + " for " + i.substring(1,2));
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

	public boolean CNFResolution(String cnfSentenceIn) {
		CNFtoClau(cnfSentenceIn);
		while (!Clause.isEmpty()) {
			ArrayList<String> ClauseArray = new ArrayList<String>();
			Object[] Clausearray = Clause.toArray();
			for (int i = 0; i < Clausearray.length; i++ ) {
				ClauseArray.add((String) Clausearray[i]);
			}
			for (int i = ClauseArray.size()-1; i >= 0; i--) {
				for (int j = ClauseArray.size()-1; j >= 0; j--) {
					if (!(i == j)) {
						String Resolvent = Resolve (ClauseArray.get(i), ClauseArray.get(j));
						if (Resolvent == null) {
							Clause = new HashSet<String>();
							NewClause = new HashSet<String>();
							return true;
						}
						else if (!Resolvent.equals("ToSkip")) {
							NewClause.add(Resolvent);
						}
					}
				}
			}
			if (Clause.containsAll(NewClause)) {
				Clause = new HashSet<String>();
				NewClause = new HashSet<String>();
				return false;
			}
			else {
				Clause.addAll(NewClause);
			}
		}
		System.out.println("Check!!!");
		return (Boolean) null;
	}

	public String Resolve(String stringA, String stringB) {
		String tmp = new String();
		ArrayList<String> SymstringA= SymbolExtract(stringA);
		ArrayList<String> SymstringB= SymbolExtract(stringB);
		int k = 0;
		int m = 0;
		int q = 0;
		for (int i = 0; i < SymstringA.size(); i++) {
			for (int j = 0; j < SymstringB.size(); j++) {
				if (SymstringA.get(i).equals("¬" + SymstringB.get(j)) 
						|| SymstringB.get(j).equals("¬" + SymstringA.get(i))) {
					k++;
					m = i;
					q = j;
					if (k == 2) {
						return "ToSkip";
					}
					break;
				}
			}
		}
		if (k == 1) {
			if (SymstringA.size() == 1 && SymstringB.size() == 1) {
				return null;
			}
			else {
				String SymstringARemove = SymstringA.get(m);
				String SymstringBRemove = SymstringB.get(q);
				ArrayList<String> resolventRetnBef = new ArrayList<String>();
				for (String SymstringA_ele : SymstringA) {
					if (!(SymstringA_ele == SymstringARemove) 
							&& !resolventRetnBef.contains(SymstringA_ele)) {
						resolventRetnBef.add(SymstringA_ele);
					}
				}
				for (String SymstringB_ele : SymstringB) {
					if (!(SymstringB_ele == SymstringBRemove) 
							&& !resolventRetnBef.contains(SymstringB_ele)) {
						resolventRetnBef.add(SymstringB_ele);
					}
				}
				if (SymstringA.size()>=2 && SymstringB.size()>=2 && resolventRetnBef.size()>=2) {
					return "ToSkip";
				}
				else {
					String resolventRetn = "(";
					for (String resolventRetnBef_ele : resolventRetnBef) {
						resolventRetn = resolventRetn + resolventRetnBef_ele + "~";
					}
					resolventRetn = resolventRetn.substring(0, resolventRetn.length()-1) + ")";
					return resolventRetn;
				}
			}				
		}
		else {
			return "ToSkip";
		}
	}

	public ArrayList<String> SymbolExtract (String sTring) {
		ArrayList<String> symbolExtract = new ArrayList<String>();
		for (int i = 0; i < sTring.length(); i++) {
			String tmp = new String();
			if (sTring.substring(i, i+1).matches("[¬a-zA-Z0-9]")) {
				do {
					tmp = tmp + sTring.substring(i, i+1);
					i++;
				} while (sTring.substring(i, i+1).matches("[a-zA-Z0-9]"));
				i--;
				symbolExtract.add(tmp);
			}
		}
		return symbolExtract;
	}

	public void CNFtoClau(String CNFin) {
		String tmp = new String();
		for (int i = 0; i < CNFin.length(); i++) {
			if (!CNFin.substring(i, i+1).contains("&")) {
				tmp = tmp + CNFin.substring(i, i+1);
				if (i == CNFin.length()-1) {
					Clause.add(tmp);
				}
			}
			else {
				Clause.add(tmp);
				tmp = new String();
			}
		}
	}	
}