import java.util.ArrayList;

public class DecisionTreeNode {

	public String Par;
	public String ParVal;
	public String Self;
	public ArrayList<DecisionTreeNode> Children;

	public DecisionTreeNode(String par, String parVal, String self, ArrayList<DecisionTreeNode> children) {
		this.Par = par;
		this.ParVal = parVal;
		this.Self = self;
		this.Children = new ArrayList<DecisionTreeNode>();
		if (children != null) {
			for (DecisionTreeNode child : children) {
				Children.add(child);
			}
		}
	}

	public static void main(String[] args) {
	}

}