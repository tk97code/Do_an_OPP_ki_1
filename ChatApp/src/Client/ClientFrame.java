package Client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;

import javax.swing.*;



public class ClientFrame extends JFrame {

////	/* ClientFrame info */
//	private int fWidth = 1265; //+15
//	private int fHeight = 940; //+40
	
	
//	/* ClientFrame info */
	private int fWidth = 1215 + 15; //+15
	private int fHeight = 860 + 40; //+40
	
	
	/* Get Screen Size */
	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private int sWidth = gd.getDisplayMode().getWidth();
	private int sHeight = gd.getDisplayMode().getHeight();
	
	
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
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
//		setBounds((sWidth-fWidth)/2, (sHeight-fHeight)/2, fWidth, fHeight);
		setSize(fWidth, fHeight);
		setLocationRelativeTo(null);
		
		add(new LeftComponents());
		add(new RightComponents());
	}
	
}
