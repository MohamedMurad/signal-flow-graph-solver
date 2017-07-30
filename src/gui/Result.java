package gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class Result extends JFrame {
	
	private static final long serialVersionUID = 1L;

	String result;

	public Result(String result) {
		setBackground(Color.BLACK);
		getContentPane().setBackground(Color.BLACK);
		this.result = result;
		initialize();

	}

	private void initialize() {
		this.setBounds(100, 100, 660, 409);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBackground(Color.DARK_GRAY);
		lblNewLabel.setFont(new Font("Book Antiqua", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 642, 368);
		JScrollPane scroller = new JScrollPane(lblNewLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		lblNewLabel.setText(result);
		this.getContentPane().add(scroller);
	}
}
