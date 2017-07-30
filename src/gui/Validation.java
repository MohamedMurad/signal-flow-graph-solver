package gui;

public class Validation {
	
	public boolean validMaxNumOfNodes(int num){
		if (num>20) {
			return false;
		}
		return true;
	}
}