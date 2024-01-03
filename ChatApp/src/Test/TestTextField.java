package Test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TestTextField extends JFrame {
	private TheSedativePackage.MyTextField textField;
	private JTextField txt;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TestTextField frame = new TestTextField();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public TestTextField() {
		setResizable(false);
		setTitle("Server");
//		getContentPane().setBackground();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 500);
		setLayout(null);
		
		textField = new TheSedativePackage.MyTextField();
		textField.setBounds(100, 100, 300, 40);
		add(textField);
		
		txt = new JTextField();
		txt.setBounds(10, 10, 100, 30);
//		txt.setBorder(new EmptyBorder(20, 3, 10, 3));
		txt.setText("Hi there");
		System.out.println(txt.getText());
		add(txt);
	}
}
