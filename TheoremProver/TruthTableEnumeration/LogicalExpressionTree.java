import java.util.ArrayList;

public class LogicalExpressionTree {

	public LETreeNode Root;
	public static ArrayList<String> SentenceSplit = new ArrayList<String>();

	public LogicalExpressionTree (String SentenceIn) {
		Root = BuildLETree(SentenceToken(SentenceIn));
	}

	public LogicalExpressionTree (LETreeNode root) {
		Root = root;
	}

	public ArrayList<String> SentenceToken (String SentenceIn) {
		for (int i = 0; i < SentenceIn.length(); i++) {
			String tmp = new String();
			if (SentenceIn.substring(i, i+1).contains("(")) {
				SentenceSplit.add("(");
			}
			else if (SentenceIn.substring(i, i+1).contains("&")) {
				SentenceSplit.add("&");
			}
			else if (SentenceIn.substring(i, i+1).contains("~")) {
				SentenceSplit.add("~");
			}
			else if (SentenceIn.substring(i, i+1).contains("¬")) {
				SentenceSplit.add("¬");
			}
			else if (SentenceIn.substring(i, i+1).matches("[a-zA-Z0-9]")) {
				do {
					tmp = tmp + SentenceIn.substring(i, i+1);
					i++;
				} while (i+1 <= SentenceIn.length() && SentenceIn.substring(i, i+1).matches("[a-zA-Z0-9]"));
				i--;
				SentenceSplit.add(tmp);
			}
		}
		return SentenceSplit;
	}

	public LETreeNode BuildLETree (ArrayList<String> sentencesplit) {
		if (sentencesplit.get(0).contains("(")) {
			sentencesplit.remove(0);
			LETreeNode ChildL = BuildLETree (sentencesplit);
			String connect = sentencesplit.remove(0);
			LETreeNode ChildR = BuildLETree (sentencesplit);
			return new LETreeNode(connect, ChildL, ChildR);
		}
		else if (sentencesplit.get(0).contains("¬")) {
			sentencesplit.remove(0);
			LETreeNode ChildL = BuildLETree (sentencesplit);
			return new LETreeNode("¬", ChildL, null);
		}
		else if (sentencesplit.get(0).matches("[a-zA-Z0-9]+")) {
			String tmp = sentencesplit.remove(0);
			return new LETreeNode(tmp, null, null);
		}
		else {
			System.out.println("Check the BuildLETree function!!!");
			return null;
		}
	}

	public static void main(String[] args) {

	}
}