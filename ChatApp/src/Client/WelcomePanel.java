package Client;

import TheSedativePackage.ModelColor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.jdesktop.swingx.painter.ImagePainter.ScaleType;

public class WelcomePanel extends JPanel {
	private JLabel noti1;
	private JLabel noti2;
	private JLabel noti0;
	
	private JLabel lblChatIcon;
	private Image chatIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\chat_black.png");
	private ImageIcon chatIcon = new ImageIcon(chatIconUrl);
	
	private JLabel lblUsersIcon;
	private Image usersIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\users_black.png");
	private ImageIcon usersIcon = new ImageIcon(usersIconUrl);
	
	private JLabel lblWelcome;
	private Image welcomeUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\welcome.png").getScaledInstance(300, 300, Image.SCALE_SMOOTH);
	private ImageIcon welcomeImg = new ImageIcon(welcomeUrl);
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
        
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.setFont(new Font("Manrope ExtraBold", Font.PLAIN, 40));
		g2.setColor(Color.black);
		g2.drawString("Welcome to", 230, 330);
		
		List<ModelColor> colors = new ArrayList<ModelColor>();
		         
		colors.add(new ModelColor(new Color(76, 201, 229), 0.3f));
		colors.add(new ModelColor(new Color(80, 97, 247), 1f));
		
		float[] position = new float[colors.size()];
		Color color[] = new Color[colors.size()];
	            
		for (int i = 0; i < colors.size(); i++) {
	        	position[i] = colors.get(i).getPosition();
	        	color[i] = colors.get(i).getColor();
		}
	    		        
		g2.setPaint(new LinearGradientPaint(480, 0, 730, 0, position, color));
//		g2.setFont(font);
		g2.drawString("Welkin!", 480, 330);
	}
	
	public WelcomePanel() {
		setLayout(null);
		setBounds(340, -100, 885, 860);
		setBackground(Color.white);
		
		noti1 = new JLabel("Choose a people to chat");
		noti1.setFont(new Font("Manrope Bold", Font.PLAIN, 17));
		noti1.setForeground(new Color(98, 107, 108));
		noti1.setBounds(330, 245, 200, 200);
		add(noti1);
		
		
		lblWelcome = new JLabel();
		lblWelcome.setIcon(welcomeImg);
		lblWelcome.setBounds(278, 330, 350, 350);
		add(lblWelcome);
		
		lblUsersIcon = new JLabel();
		lblUsersIcon.setIcon(usersIcon);
		lblUsersIcon.setBounds(280, 650, 60, 60);
		add(lblUsersIcon);
		
		noti1 = new JLabel("Online Users");
		noti1.setFont(new Font("Manrope Bold", Font.PLAIN, 17));
		noti1.setForeground(Color.black);
		noti1.setBounds(320, 580, 200, 200);
		add(noti1);
		
		lblChatIcon = new JLabel();
		lblChatIcon.setIcon(chatIcon);
		lblChatIcon.setBounds(278, 690, 60, 60);
		add(lblChatIcon);
		
		noti2 = new JLabel("Users who you chat recently");
		noti2.setFont(new Font("Manrope Bold", Font.PLAIN, 17));
		noti2.setForeground(Color.black);
		noti2.setBounds(320, 620, 300, 200);
		add(noti2);
		
	}
}
