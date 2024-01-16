package TheSedativePackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Data.MessageData;
import Data.RegisterData;
import Event.Event;
import Event.EventLogin;
import Event.EventMessage;
import Login.LoginService;
import io.socket.client.Ack;

public class LoginComponent extends JPanel {
	private JLabel label;
	private MyTextField input;
	
	private String textLabel;
	private String placeHolder;
	private String url;
	private boolean isPass;
	
	private Font _poppins15 = new Font("SVN-Poppins Medium", Font.PLAIN, 15);
	private Font _poppins17 = new Font("SVN-Poppins", Font.PLAIN, 17);
	
	public LoginComponent(String textLabel, String placeHolder, String url, boolean isPass) {
		this.textLabel = textLabel;
		this.placeHolder = placeHolder;
		this.url = url;
		this.isPass = isPass;
	}
	
	public String getText() {
		return input.getText();
	}
	
	public void addComponets() {
		setLayout(null);
		setBackground(Color.white);
		
		label = new JLabel();
		label.setText(this.textLabel);
		label.setFont(_poppins15);
		label.setForeground(new Color(153, 153, 153));
		label.setBounds(0, 0, 220, 20);
		add(label);
		
		input = new MyTextField(placeHolder) {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				Image img = Toolkit.getDefaultToolkit().getImage(url);
				g2.drawImage(img, 5, this.getHeight() / 2 - 10, this);
			}
		};
		input.setBounds(0, 20, 490, 35);
		input.setForeground(Color.black);
		input.setFont(_poppins17);
//		input.setText(this.placeHolder);
		input.setType(isPass);
		input.setBorder(new EmptyBorder(0, 40, 0, 0));
//		input.addFocusListener(new FocusListener(){
//			
//		    @Override
//		    public void focusGained(FocusEvent e) {
//		        if (input.getText().equals(placeHolder)) {
//		        	input.setText("");
//		        	if (isPass) {
//		        		input.setType(true);
//		        	}
//		        }
//		    }
//		    @Override
//		    public void focusLost(FocusEvent e) {
//		        if (input.getText().isEmpty()) {
//		        	input.setText(placeHolder);
//		        	if (isPass) {
//		        		input.setType(false);
//		        	}
//		        }
//		    }
//		});
		add(input);
	}
}
