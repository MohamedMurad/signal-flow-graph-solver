package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import mason.Data;
import mason.Mason;

import java.awt.SystemColor;

public class Canvas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField fromNode;
	private JTextField toNode;
	private JTextField weight;
	Handling handling = new Handling();
	Mason mason = new Mason();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Canvas window = new Canvas();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Canvas() {
		getContentPane().setBackground(Color.GRAY);
		Data.gains = new Integer[Data.numOfNodes][Data.numOfNodes];
		Data.arrows = new Arrow[Data.numOfNodes][Data.numOfNodes];
		if (!Data.msg.equals("")) {
			JOptionPane.showMessageDialog(new JFrame(), Data.msg, "Dialog", JOptionPane.WARNING_MESSAGE);
			Data.msg = new String();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(20, 20, 1300, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		fromNode = new JTextField();
		fromNode.setBounds(352, 116, 62, 20);
		this.getContentPane().add(fromNode);
		fromNode.setColumns(10);

		toNode = new JTextField();
		toNode.setBounds(485, 116, 62, 20);
		this.getContentPane().add(toNode);
		toNode.setColumns(10);

		weight = new JTextField();
		weight.setBounds(661, 116, 62, 20);
		this.getContentPane().add(weight);
		weight.setColumns(10);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Data.width = (int) screenSize.getWidth() - 120;
		Data.height = (int) screenSize.getHeight() - 120;
		JPanel panel = new DrawSFG();
		panel.setBounds(10, 153, 1264, 497);
		getContentPane().add(panel);
		panel.repaint();

		JButton btnNewButton_3 = new JButton("add edge");
		btnNewButton_3.setFont(new Font("Book Antiqua", Font.BOLD, 12));
		btnNewButton_3.setBackground(Color.LIGHT_GRAY);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEdge();
				panel.repaint();
			}

			private void addEdge() {
				// TODO Auto-generated method stub
				try {
					int node1, node2;
					if (fromNode.getText().toString().equalsIgnoreCase("s")) {
						node1 = 0;
					} else {
						node1 = handling.selectedNode(fromNode.getText().toString());
					}
					if (toNode.getText().toString().equalsIgnoreCase("e")) {
						node2 = Data.numOfNodes - 1;
					} else {
						node2 = handling.selectedNode(toNode.getText().toString());
					}
					int weightValue = handling.isNumber(weight.getText().toString());

					Data.arrows[node1][node2] = new Arrow(node1, node2, weightValue);
					Data.gains[node1][node2] = new Integer(weightValue);
					fromNode.setText("");
					toNode.setText("");
					weight.setText("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), e.getMessage().toString(), "Dialog",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_3.setBounds(747, 112, 97, 29);
		this.getContentPane().add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Solve");
		btnNewButton_4.setFont(new Font("Book Antiqua", Font.BOLD, 11));
		btnNewButton_4.setBackground(Color.LIGHT_GRAY);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mason.solve();
				String result = mason.buildResult();
				Result resultView = new Result(result);
				resultView.setVisible(true);
			}
		});
		btnNewButton_4.setBounds(854, 112, 62, 29);
		getContentPane().add(btnNewButton_4);

		JLabel lblFrom = new JLabel("from");
		lblFrom.setFont(new Font("Book Antiqua", Font.BOLD, 17));
		lblFrom.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFrom.setForeground(Color.WHITE);
		lblFrom.setBackground(Color.GRAY);
		lblFrom.setBounds(265, 114, 77, 20);
		getContentPane().add(lblFrom);

		JLabel lblTo = new JLabel("to");
		lblTo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTo.setForeground(Color.WHITE);
		lblTo.setFont(new Font("Book Antiqua", Font.BOLD, 17));
		lblTo.setBackground(Color.GRAY);
		lblTo.setBounds(424, 114, 51, 20);
		getContentPane().add(lblTo);

		JLabel lblWeight = new JLabel("weight");
		lblWeight.setHorizontalAlignment(SwingConstants.TRAILING);
		lblWeight.setForeground(Color.WHITE);
		lblWeight.setFont(new Font("Book Antiqua", Font.BOLD, 17));
		lblWeight.setBackground(Color.GRAY);
		lblWeight.setBounds(574, 114, 77, 20);
		getContentPane().add(lblWeight);

		JLabel lblMaisonSolver = new JLabel("Mason Solver");
		lblMaisonSolver.setForeground(SystemColor.textHighlight);
		lblMaisonSolver.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaisonSolver.setFont(new Font("Bernard MT Condensed", Font.BOLD, 48));
		lblMaisonSolver.setBounds(302, 11, 614, 90);
		getContentPane().add(lblMaisonSolver);

	}
}
