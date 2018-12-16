import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class DecisionTree_CarEvalDataSet {

	public DecisionTreeNode Root;
	public ArrayList<String[]> CarEval;
	public ArrayList<String> Attr;
	public ArrayList<String> Value;

	public DecisionTree_CarEvalDataSet() {
		this.Root = null;
	}

	public static void main(String[] args) throws IOException {
		DecisionTree_CarEvalDataSet DeciTree = new DecisionTree_CarEvalDataSet();
		DeciTree.Attr = new ArrayList<String>();
		DeciTree.Value = new ArrayList<String>();
		DeciTree.CarEval = new ArrayList<String[]>();
		DeciTree.Attr.add("buying0");
		DeciTree.Attr.add("maint1");
		DeciTree.Attr.add("doors2");
		DeciTree.Attr.add("persons3");
		DeciTree.Attr.add("lug_boot4");
		DeciTree.Attr.add("safety5");
		DeciTree.Value.add("unacc");
		DeciTree.Value.add("acc");
		DeciTree.Value.add("good");
		DeciTree.Value.add("vgood");
		DeciTree.CarEval = DeciTree.readCar("../DataSet/car.data.txt");
		DeciTree.Root = DeciTree.DecisionTreeLearn(DeciTree.CarEval, DeciTree.Attr, null, null, DeciTree.CarEval);
		int k = 0;
		File fi = new File("../DecisionTree_CarEval.txt");
		try (PrintWriter Pw = new PrintWriter(fi)) {
			while (k < 50) {
				Pw.println(DeciTree.kFoldCrosValid(DeciTree.CarEval, 5));
				k++;
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<Double> CVerrorArr = new ArrayList<Double>();
		for (int i = 2; i <= DeciTree.CarEval.size(); i+=10) {
			CVerrorArr.add(DeciTree.kFoldCrosValid(DeciTree.CarEval, i));
			System.out.println(CVerrorArr.get(CVerrorArr.size()-1));
		}
		File file = new File("../DecisionTree_CarEval2.txt");
		try (PrintWriter pw = new PrintWriter(file)) {
			for (Double CVerrorArr_ele : CVerrorArr) {
				pw.println(CVerrorArr_ele);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		File File = new File("../DecisionTree_CarEval3.txt");
		try (PrintWriter pW = new PrintWriter(File)) {
			Collections.shuffle(DeciTree.CarEval);
			ArrayList<String[]> CarEvalTest = new ArrayList<String[]>();
			ArrayList<String[]> CarEvalTrain = new ArrayList<String[]>();
			ArrayList<String[]> CarEvalTrainSub = new ArrayList<String[]>();
			for (int i = 0; i < DeciTree.CarEval.size(); i++) {
				if (i < DeciTree.CarEval.size()/10) {
					CarEvalTest.add(DeciTree.CarEval.get(i));
				}
				else {
					CarEvalTrain.add(DeciTree.CarEval.get(i));
				}
			}
			for (int m = 1; m <=90; m++) {
				CarEvalTrainSub.clear();
				for (int n = 0; n < (m*CarEvalTrain.size()/90); n++) {
					CarEvalTrainSub.add(CarEvalTrain.get(n));
				}
				pW.println(DeciTree.Error(CarEvalTrainSub, CarEvalTest));
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");
	}

	public ArrayList<String[]> readCar(String Carpath) throws IOException {
		ArrayList<String[]> car = new ArrayList<String[]>();
		BufferedReader BR = new BufferedReader(new FileReader(Carpath));
		String line = BR.readLine();
		while (line != null) {
			car.add(line.split(","));
			line = BR.readLine();
		}
		BR.close();
		return car;
	}

	public DecisionTreeNode DecisionTreeLearn(ArrayList<String[]> example, ArrayList<String> attrRem, String attrTar, String attrTarVal, ArrayList<String[]> examplePar) {
		if (example.isEmpty()) {
			return new DecisionTreeNode(attrTar, attrTarVal, Common(attrTar, examplePar), null);
		}
		else if (Same(example)) {
			return new DecisionTreeNode(attrTar, attrTarVal, example.get(0)[6], null);
		}
		else if (attrRem.isEmpty()) {
			return new DecisionTreeNode(attrTar, attrTarVal, Common(attrTar, example), null);
		}
		else {
			String attrNext = MaxInfoGain(attrRem, example);
			String index = attrNext.substring(attrNext.length()-1, attrNext.length());
			DecisionTreeNode Tree = new DecisionTreeNode(attrTar, attrTarVal, attrNext, null);
			ArrayList<String> attrValAll = AllValue(attrNext, CarEval);
			ArrayList<String[]> exampleSub = new ArrayList<String[]>();
			ArrayList<String[]> exampleCP = new ArrayList<String[]>();
			for (String attrValAll_ele : attrValAll) {
				exampleSub.clear();
				exampleCP.clear();
				for (String[] example_ele : example) {
					exampleCP.add(example_ele);
					if (example_ele[Integer.valueOf(index)].equals(attrValAll_ele)) {
						exampleSub.add(Arrays.copyOf(example_ele, example_ele.length));
					}
				}
				ArrayList<String> attrRemCP = deepCopy(attrRem, attrNext);
				DecisionTreeNode subTree = DecisionTreeLearn(exampleSub, attrRemCP, attrNext, attrValAll_ele, exampleCP);
				Tree.Children.add(subTree);
			}
			return Tree;			
		}
	}

	public double kFoldCrosValid(ArrayList<String[]> car, int foldNum) {
		ArrayList<ArrayList<String[]>> kFoldSets = new ArrayList<ArrayList<String[]>>();
		kFoldSets = kFoldSetGen(car, foldNum);
		ArrayList<Double> Error = new ArrayList<Double>();
		for (int i = 0; i<foldNum; i++) {
			ArrayList<String[]> train = new ArrayList<String[]>();
			ArrayList<String[]> test = new ArrayList<String[]>();
			for (int j = 0; j<foldNum; j++) {
				if (i != j) {
					train = merge(train, kFoldSets.get(j));
				}
				else {
					test = merge(test, kFoldSets.get(j));
				}
			}
			double err = Error(train, test);
			Error.add(err);
		}
		double errSum = 0.00;
		for (double err : Error) {
			errSum += err;
		}
		return errSum/(double)foldNum;
	}

	public ArrayList<ArrayList<String[]>> kFoldSetGen(ArrayList<String[]> car, int foldNum) {
		ArrayList<ArrayList<String[]>> Res = new ArrayList<ArrayList<String[]>>();
		int carLen = car.size();
		int foldSize = carLen/foldNum;
		Collections.shuffle(car);
		for (int i = 0; i<foldNum; i++) {
			ArrayList<String[]> res = new ArrayList<String[]>();
			for (int k = i*foldSize; k<carLen; k++) {
				if (k != (foldNum-1) * foldSize) {
					res.add(car.get(k));
					if (res.size() == foldSize) {
						break;
					}
				}
				else {
					res.add(car.get(k));
				}
			}
			Res.add(res);
		}
		return Res;
	}

	public double Error(ArrayList<String[]> train, ArrayList<String[]> test) {
		double res = 0.00;
		DecisionTreeNode tree = DecisionTreeLearn(train, Attr, null, null, train);
		for (String[] test_ele : test) {
			DecisionTreeNode leafNode = treeTrack(tree, test_ele);
			if (leafNode.Self.equals(test_ele[test_ele.length-1])) {
				res++;
			}
		}
		return res/(double)test.size();
	}

	public DecisionTreeNode treeTrack(DecisionTreeNode node, String[] test_ele) {
		if (node.Children.size() == 0) {
			return node;
		}
		else {
			for (DecisionTreeNode tmpNode : node.Children) {
				int index = Integer.valueOf(tmpNode.Par.substring(tmpNode.Par.length()-1, tmpNode.Par.length()));
				if (tmpNode.ParVal.equals(test_ele[index])) {
					DecisionTreeNode TmpNode = treeTrack(tmpNode, test_ele);
					return TmpNode;
				}
			}
			return null;
		}
	}

	public ArrayList<String[]> merge(ArrayList<String[]> a, ArrayList<String[]> b) {
		ArrayList<String[]> c = new ArrayList<String[]>();
		if (a.size() != 0) {
			for (String[] a_ele : a) {
				c.add(a_ele);
			}
		}
		if (b.size() != 0) {
			for (String[] b_ele : b) {
				c.add(b_ele);
			}
		}
		return c;
	}

	public ArrayList<String> deepCopy(ArrayList<String> attrRem, String attrNext) {
		ArrayList<String> res = new ArrayList<String>();
		for (String attrRem_ele : attrRem) {
			if (!attrRem_ele.equals(attrNext)) {
				res.add(attrRem_ele);
			}
		}
		return res;
	}

	public String MaxInfoGain(ArrayList<String> attrRem, ArrayList<String[]> example) {
		String attrNext = new String();
		double IG = -1000.00;
		for (String attrRem_ele : attrRem) {
			String index = attrRem_ele.substring(attrRem_ele.length()-1, attrRem_ele.length());
			if (IG < InfoGain(index, attrRem_ele, example)) {
				IG = InfoGain(index, attrRem_ele, example);
				attrNext = new String(attrRem_ele);
			}
		}
		return attrNext;
	}

	public double InfoGain(String index, String attrRem_ele, ArrayList<String[]> example) {
		ArrayList<String> attrValAll = AllValue(attrRem_ele, CarEval);
		HashMap<String, ArrayList<Integer>> InfoGainMap = new HashMap<String, ArrayList<Integer>>();
		for (String attrValAll_ele : attrValAll) {
			InfoGainMap.put(attrValAll_ele, new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
		}
		for (String attrValAll_ele : attrValAll) {
			for (String[] example_ele : example) {
				if (example_ele[Integer.valueOf(index)].equals(attrValAll_ele)) {
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					if (example_ele[example_ele.length-1].equals("unacc")) {
						tmp = new ArrayList<Integer>(InfoGainMap.get(attrValAll_ele));
						tmp.set(0, InfoGainMap.get(attrValAll_ele).get(0)+1);
						InfoGainMap.put(attrValAll_ele, tmp);
					}
					else if (example_ele[example_ele.length-1].equals("acc")) {
						tmp = new ArrayList<Integer>(InfoGainMap.get(attrValAll_ele));
						tmp.set(1, InfoGainMap.get(attrValAll_ele).get(1)+1);
						InfoGainMap.put(attrValAll_ele, tmp);
					}
					else if (example_ele[example_ele.length-1].equals("good")) {
						tmp = new ArrayList<Integer>(InfoGainMap.get(attrValAll_ele));
						tmp.set(2, InfoGainMap.get(attrValAll_ele).get(2)+1);
						InfoGainMap.put(attrValAll_ele, tmp);
					}
					else if (example_ele[example_ele.length-1].equals("vgood")) {
						tmp = new ArrayList<Integer>(InfoGainMap.get(attrValAll_ele));
						tmp.set(3, InfoGainMap.get(attrValAll_ele).get(3)+1);
						InfoGainMap.put(attrValAll_ele, tmp);
					}
				}
			}
		}
		double entropyBF = EntropyBF(InfoGainMap, attrValAll);
		double entropyAF = EntropyAF(InfoGainMap, attrValAll);
		return entropyBF - entropyAF;
	}

	public double EntropyBF(HashMap<String, ArrayList<Integer>> infoGainMap, ArrayList<String> attrValAll) {
		double unaccNum = 0.00;
		double accNum = 0.00;
		double goodNum = 0.00;
		double vgoodNum = 0.00;
		for (String attrValAll_ele : attrValAll) {
			if (infoGainMap.containsKey(attrValAll_ele)) {
				unaccNum += (double)infoGainMap.get(attrValAll_ele).get(0);
				accNum += (double)infoGainMap.get(attrValAll_ele).get(1);
				goodNum += (double)infoGainMap.get(attrValAll_ele).get(2);
				vgoodNum += (double)infoGainMap.get(attrValAll_ele).get(3);
			}
		}
		double unaccNumNorm = unaccNum/(unaccNum + accNum + goodNum + vgoodNum);
		double accNumNorm = accNum/(unaccNum + accNum + goodNum + vgoodNum);
		double goodNumNorm = goodNum/(unaccNum + accNum + goodNum + vgoodNum);
		double vgoodNumNorm = vgoodNum/(unaccNum + accNum + goodNum + vgoodNum);
		return LogTwo(unaccNumNorm, accNumNorm, goodNumNorm, vgoodNumNorm);
	}

	public double EntropyAF(HashMap<String, ArrayList<Integer>> infoGainMap, ArrayList<String> attrValAll) {
		double NUM = 0.00;
		double Res = 0.00;
		for (String attrValAll_ele : attrValAll) {
			if (infoGainMap.containsKey(attrValAll_ele)) {
				NUM += infoGainMap.get(attrValAll_ele).get(0)+infoGainMap.get(attrValAll_ele).get(1)+infoGainMap.get(attrValAll_ele).get(2)+infoGainMap.get(attrValAll_ele).get(3);
			}
		}
		ArrayList<Double> tmpA = new ArrayList<Double>();
		ArrayList<Double> tmpB = new ArrayList<Double>();
		for (String attrValAll_ele : attrValAll) {
			double resA = 0.00;
			double resB = 0.00;
			if (infoGainMap.containsKey(attrValAll_ele)) {
				double a = infoGainMap.get(attrValAll_ele).get(0);
				double b = infoGainMap.get(attrValAll_ele).get(1);
				double c = infoGainMap.get(attrValAll_ele).get(2);
				double d = infoGainMap.get(attrValAll_ele).get(3);
				if (a == 0.0 && b == 0.0 && c == 0.0 && d == 0.0) {
					resA = 0.0;
					resB = 0.0;
				}
				else {
					resA = LogTwo(a/(a+b+c+d),b/(a+b+c+d),c/(a+b+c+d),d/(a+b+c+d));
					resB = (infoGainMap.get(attrValAll_ele).get(0)+infoGainMap.get(attrValAll_ele).get(1)+infoGainMap.get(attrValAll_ele).get(2)+infoGainMap.get(attrValAll_ele).get(3))/NUM;
				}
			}
			tmpA.add(resA);
			tmpB.add(resB);
		}
		for (int i = 0; i < tmpA.size();i++) {
			Res += tmpA.get(i) * tmpB.get(i);
		}
		return Res;
	}

	public double LogTwo(double a, double b, double c, double d) {
		double res = 0.00;
		if (Math.max(Math.max(a, b),Math.max(c, d)) == 1) {
			return res;
		}
		ArrayList<Double> tmp = new ArrayList<Double>();
		tmp.add(a);
		tmp.add(b);
		tmp.add(c);
		tmp.add(d);
		while (tmp.contains(0.0)) {
			tmp.remove(0.0);
		}
		for (Double tmp_ele : tmp) {
			res += (-1) * tmp_ele * (Math.log(tmp_ele)/Math.log(2.00));
		}
		return res;
	}

	public String Common(String attrTar, ArrayList<String[]> examplePar) {
		HashMap<String, Integer> ComMap = new HashMap<String, Integer>();
		ComMap.put(Value.get(0), 0);
		ComMap.put(Value.get(1), 0);
		ComMap.put(Value.get(2), 0);
		ComMap.put(Value.get(3), 0);
		for (String[] examplePar_ele : examplePar) {
			ComMap.put(examplePar_ele[6], ComMap.get(examplePar_ele[6])+1);
		}
		Integer max = Math.max(Math.max(ComMap.get("unacc"), ComMap.get("acc")),Math.max(ComMap.get("good"), ComMap.get("vgood")));
		if (ComMap.get("unacc") == max) {
			return "unacc";
		}
		else if (ComMap.get("acc") == max) {
			return "acc";
		}
		else if (ComMap.get("good") == max) {
			return "good";
		}
		else {
			return "vgood";
		}
	}

	public boolean Same(ArrayList<String[]> example) {
		String tmp = example.get(0)[6];
		int k = 0;
		while (k < example.size() && example.get(k)[6].equals(tmp)) {
			k++;
		}
		if (k == example.size()) {
			return true;
		}
		else {
			return false;
		}
	}

	public ArrayList<String> AllValue(String attrNext, ArrayList<String[]> Car) {
		ArrayList<String> Vals = new ArrayList<String>();
		String index = attrNext.substring(attrNext.length()-1, attrNext.length());
		for (String[] Car_ele : Car) {
			if (!Vals.contains(Car_ele[Integer.valueOf(index)])) {
				Vals.add(Car_ele[Integer.valueOf(index)]);
			}
		}
		return Vals;
	}
}