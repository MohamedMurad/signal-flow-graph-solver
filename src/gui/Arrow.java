package gui;

import java.awt.Color;

import mason.Data;

public class Arrow {
	public Arrow(int fromNode, int toNode, int weight) {
		// TODO Auto-generated constructor stub
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.weight = weight;
		arrowsAccessories();
		setColor(fromNode, toNode);
	}

	private void setColor(int fromNode, int toNode) {
		if (toNode - fromNode == 1) {
			color = Color.BLUE;
		} else if (toNode - fromNode > 1 || toNode - fromNode == 0) {
			color = Color.GREEN;
		} else {
			color = Color.MAGENTA;
		}
	}

	int fromNode, toNode;
	Color color;
	int weight;
	int x1, x2, y1, y2, yb, yt;
	int[] xa,ya;

	void arrowsAccessories() {
		Node node1 = Data.nodes.get(fromNode);
		Node node2 = Data.nodes.get(toNode);
		x1 = node1.x;
		y1 = node1.y;
		x2 = node2.x;
		y2 = node2.y;
		if (toNode - fromNode > 1) {
			yb = y1 - (x2 - x1) / 5;
			x1 += 20;
			x2 += 20;
			yt = y1 - (x2 - x1) / 7 - 10;
			xa = new int[]{x2-3,x2+3,x2};
			ya = new int[]{y2-5,y2-5,y2};
		} else if (toNode - fromNode < 0) {
			y1 += 40;
			y2 += 40;
			yb = y1 - (x2 - x1) / 5;
			x1 += 20;
			x2 += 20;
			yt = y1 + ((x1 - x2) / 7 + 30);
			xa = new int[]{x2-3,x2+3,x2};
			ya = new int[]{y2+5,y2+5,y2};

		} else if (toNode - fromNode == 1) {
			y1 += 20;
			y2 += 20;
			x1 += 40;
			yb = y1;
			yt = y1 - 2;
			xa = new int[]{x2-5,x2-5,x2};
			ya = new int[]{y2-3,y2+3,y2};
		}else if(toNode - fromNode == 0){
			yb = 0;
			x1 += 20;
			x2 += 20;
			yt = 0;
			xa = new int[]{x2-3,x2+3,x2};
			ya = new int[]{y2-5,y2-5,y2};
		}
	}
}
