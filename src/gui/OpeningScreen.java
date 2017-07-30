package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;

import mason.Data;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpeningScreen {

	private JFrame frame;
	private JTextField textField;
	private Handling handling;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpeningScreen window = new OpeningScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OpeningScreen() {
		handling = new Handling();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBounds(400, 100, 510, 272);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html>Mason's gain formula</html>");
		lblNewLabel.setBounds(88, 11, 333, 64);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Bernard MT Condensed", Font.BOLD, 36));
		lblNewLabel.setForeground(Color.WHITE);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("enter maximum number of node");
		lblNewLabel_1.setBounds(95, 86, 326, 47);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Serif", Font.BOLD, 21));
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(215, 144, 89, 37);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnDraw = new JButton("Draw");
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String content = textField.getText().toString();
					Data.numOfNodes = handling.maxNumOfNodes(content);
					Canvas canvas = new Canvas();
					canvas.setVisible(true);
					frame.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					 JOptionPane.showMessageDialog(new JFrame(), e1.getMessage().toString(), "Dialog",
						        JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnDraw.setBounds(215, 192, 89, 23);
		frame.getContentPane().add(btnDraw);
	}
}
