package Client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;

import javax.swing.*;

import ClientEvent.Event;
import ClientEvent.EventMenuRight;
import Login.ClientService;
import io.socket.client.*;



public class ClientFrame extends JFrame {
	
	/* ClientFrame info */
	private int fWidth = 1215 + 15; //+15
	private int fHeight = 860 + 40; //+40
	
	private ClientService client;
	private WelcomePanel welcome;
	private LeftComponents left;
	private RightComponents right;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					ClientFrame frame = new ClientFrame();
//					frame.setVisible(true);
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//	}

	public ClientFrame() {
		setTitle("Welkin Chat - Message");
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		setSize(fWidth, fHeight);
		setLocationRelativeTo(null);
		left = new LeftComponents();
		welcome = new WelcomePanel();
		
		
		Event.getInstance().addEventMenuRight(new EventMenuRight() {
			@Override
			public void loadRightComponents(String name) {
				welcome.setVisible(false);
				if (right != null) {
					remove(right);
				}
				right = new RightComponents(name);
				add(right);
				revalidate();
				repaint();
			}
		});
		
		add(left);
		add(welcome);
	}
	
}
