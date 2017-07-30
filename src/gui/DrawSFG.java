package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import mason.Data;

public class DrawSFG extends JPanel {

	private static final long serialVersionUID = 1L;
	Path2D.Double path = new Path2D.Double();

	public DrawSFG() {
		// TODO Auto-generated constructor stub
		super();
		Data.nodes = new ArrayList<>();
		initializeNodes();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Font font = new Font("Serif", Font.PLAIN, 24);
		g.setFont(font);
		for (int i = 0; i < Data.nodes.size(); i++) {
			Node node = Data.nodes.get(i);
			g.setColor(node.color);
			g.fillOval(node.x, node.y, 40, 40);
			g.setColor(Color.black);
			g.setFont(Font.getFont(Font.SANS_SERIF));
			if (i == 0) {
				g.drawString("S", node.x + 15, node.y + 25);
			} else if (i == Data.nodes.size() - 1) {
				g.drawString("E", node.x + 15, node.y + 25);
			} else {
				g.drawString("" + i, node.x + 15, node.y + 25);
			}
		}
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < Data.numOfNodes; i++) {
			for (int j = 0; j < Data.numOfNodes; j++) {
				if (Data.arrows[i][j] != null) {
					Arrow arrow = Data.arrows[i][j];
					int weightValue = arrow.weight;
					Color color = arrow.color;
					int x1 = arrow.x1, y1 = arrow.y1, x2 = arrow.x2, y2 = arrow.y2, yb = arrow.yb, yt = arrow.yt;

					path = new Path2D.Double();
					g2.setColor(color);
					if (yb == 0 && yt == 0) {
						g.setColor(color);
						g.drawOval(x1 - 8, y1 - 30, 16, 30);
						g.drawString("" + weightValue, x1-5, y1 - 32);
					} else {
						path.moveTo(x1, y1);
						path.curveTo(x1, yb, x2, yb, x2, y2);
						g2.draw(path);
						g.fillPolygon(arrow.xa, arrow.ya, 3);
						g.setColor(color);
						g.drawString("" + weightValue, (x1 + x2) / 2, yt);
					}
				}

			}
		}
	}

	private void initializeNodes() {
		int x = Data.width / 2, y = Data.height / 2 - 100;
		int nodesNum = Data.numOfNodes;
		nodesNum = nodesNum / 2 - nodesNum + 1;
		if (Data.numOfNodes % 2 == 0) {
			nodesNum--;
		}
		int space = Data.width / Data.numOfNodes;
		for (int i = 0; i < Data.numOfNodes; i++) {
			if (i == 0 || i == Data.numOfNodes - 1) {
				Data.nodes.add(new Node(x + (i + nodesNum) * space, y, true));
			} else {
				Data.nodes.add(new Node(x + (i + nodesNum) * space, y, false));
			}
		}
		repaint();
	}

}
