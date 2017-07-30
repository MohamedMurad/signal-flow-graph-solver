package gui;

import mason.Data;

public class Handling {
	private Validation validation = new Validation();

	public int maxNumOfNodes(String sNum) {
		int num;
		num = isNumber(sNum);
		if (!validation.validMaxNumOfNodes(num)) {
			Data.msg = new String("your nodes exceeds 20\nyou may have trouble in visulization");
		}
		return num;
	}

	public int isNumber(String sNum) {
		int num;
		try {
			num = Integer.parseInt(sNum);
		} catch (Exception e) {
			throw new RuntimeException("Invalid number");
		}
		return num;
	}
	
	public int selectedNode(String node) {
		int num;
		num = isNumber(node);
		if (num >0 && num<Data.numOfNodes-1) {
			return num;
		}else{
			throw new RuntimeException("nodes not found !");
		}	
	}
}
