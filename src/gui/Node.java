package gui;

import java.awt.Color;

public class Node {
	public Node(int x, int y ,boolean terminalNode) {
		this.x = x;
		this.y = y;
		if (terminalNode) {
			color = Color.GRAY;
		}else{
			color = Color.lightGray;
		}
	}
	int x,y;
	Color color;
}
