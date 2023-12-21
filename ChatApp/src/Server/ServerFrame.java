package Server;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.*;

public class ServerFrame extends JFrame {
	// Run Server Panel
	private static JPanel serverRunningPanel;
	private static JPanel portPanel;
	private static JTextField txtPort;
	private static JLabel lblPort;
	private static JTextField txtIP;
	private static JLabel lblIP;
	private static JButton btnStart;
	private static JButton btnStop;
	
	// Server Info Panel
	private static JPanel serverInfoPanel;
	private static JLabel lblUpTime;
	private static JLabel lblUptimeValue;
	private static JLabel lblStartTime;
	private static JLabel lblStartTimeValue;
	private static JLabel lblConnectedClient;
	private static JLabel lblNumbersConnectedClient;
	
	// List Online Client
	private static JPanel listOnlinePanel;
	private static JLabel lblUserOnline;
	
	
	// ServerFrame info
	private static int fWidth = 720;
	private static int fHeight = 480;
	
	
	// Get Screen Size
	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	int sWidth = gd.getDisplayMode().getWidth();
	int sHeight = gd.getDisplayMode().getHeight();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ServerFrame frame = new ServerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ServerFrame() {
		setResizable(false);
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((sWidth-fWidth)/2, (sHeight-fHeight)/2, fWidth, fHeight);
		setLayout(null);
		
		serverRunningPanel = new JPanel();
		serverRunningPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		serverRunningPanel.setBackground(Color.RED);
		serverRunningPanel.setBounds(0, 0, 720, 200);
		add(serverRunningPanel);
		generateServerRunningComponent();
		
		serverInfoPanel = new JPanel();
		serverInfoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		serverInfoPanel.setBackground(Color.blue);
		serverInfoPanel.setBounds(0, 200, 720, 200);
		add(serverInfoPanel);
		generateServerInfoComponent();
	}
	
	public void generateServerRunningComponent() {
		
	}
	
	public void generateServerInfoComponent() {
		
	}
	
}
