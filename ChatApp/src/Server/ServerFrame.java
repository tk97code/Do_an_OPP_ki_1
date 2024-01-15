package Server;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import TheSedativePackage.MyTextField;
import TheSedativePackage.RoundedBorder;

import java.awt.*;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class ServerFrame extends JFrame {
	/* Server Info Panel */
	private JPanel serverInfoPanel;
	
	private JPanel portPanel;
	private MyTextField txtPort;
	private JLabel lblPort;
	
	private JPanel ipPanel;
//	private JTextField txtIP;
	private MyTextField txtIP;
	private JLabel lblIP;
	
	private JButton btnStartServer;
	private JButton btnStopServer;
	private JLabel lblTextButtonServer;
	
	private JPanel uptimePanel;
	private JLabel lblUpTime;
	private JLabel lblUptimeValue;
	
	private JPanel startTimePanel;
	private JLabel lblStartTime;
	private JLabel lblStartTimeValue;
	
	private JPanel connectedClientPanel;
	private JLabel lblConnectedClient;
	private JLabel lblNumbersConnectedClient;
	
	
	/* List Online Client */
	private JPanel listOnlinePanel;
	private JLabel lblUserOnline;

	
	/* Primary Color */
	private Color _primaryBackground = new Color(28, 28, 28);
	private Color _primaryPanelWhite = new Color(227, 245, 255);
	private Color _primaryPanelGrey = new Color(40, 40, 40);
	private Color _primaryBlue = new Color(47, 111, 237);
	
	
	/* Primary Font */
	private Font _Popins20 = new Font("Poppins", Font.BOLD, 20);
	private Font _Popins30 = new Font("Poppins", Font.BOLD, 30);
	
	
	/* Server Core */
	private ServerCore server;
	
	
	/* ServerFrame info */
	private int fWidth = 1065;
	private int fHeight = 760;
	
	
	/* Get Screen Size */
	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private int sWidth = gd.getDisplayMode().getWidth();
	private int sHeight = gd.getDisplayMode().getHeight();
	
		
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
		/* Main Frame */
		setResizable(false);
		setTitle("Server");
		getContentPane().setBackground(_primaryBackground);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((sWidth-fWidth)/2, (sHeight-fHeight)/2, fWidth, fHeight);
		setLayout(null);
		
		/* Server Info Panel */
		serverInfoPanel = new JPanel();
//		serverInfoPanel.setOpaque(false);
		serverInfoPanel.setBackground(_primaryBackground);
		serverInfoPanel.setBounds(61, 42, 672, 252);
		serverInfoPanel.setLayout(null);
		add(serverInfoPanel);
		generateServerInfoComponents();
		
		
		/* Server */
		server = new ServerCore(Integer.parseInt(txtPort.getText()));
		
		
		/* Run Server Button */
		btnStartServer = new JButton();
		btnStartServer.setLayout(null);
//		btnStartServer.setContentAreaFilled(false);
		btnStartServer.setBounds(serverInfoPanel.getWidth() + 28 + 61, 42, 227, 252);
		btnStartServer.setBackground(_primaryBlue);
		btnStartServer.setBorder(new RoundedBorder(_primaryBlue, 1, 30));
		btnStartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("Button clicked!");
            	try {
					DBConnection.getInstance().connectToDatabase();
					server.startServer();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	btnStartServer.setVisible(false);
            	btnStopServer.setVisible(true);
            }
        });
		add(btnStartServer);
		
		lblTextButtonServer = new JLabel();
		lblTextButtonServer.setText("<html><font color='#FFFFFF'>Start</font></html>");
		lblTextButtonServer.setBounds(74, 118, 100, 30);
		lblTextButtonServer.setFont(_Popins30);
		btnStartServer.add(lblTextButtonServer);
		btnStartServer.setVisible(true);
		
		
		/* Stop Server Button */
		btnStopServer = new JButton();
		btnStopServer.setLayout(null);
//		btnStartServer.setContentAreaFilled(false);
		btnStopServer.setBounds(serverInfoPanel.getWidth() + 28 + 61, 42, 227, 252);
		btnStopServer.setBackground(_primaryBlue);
		btnStopServer.setBorder(new RoundedBorder(_primaryBlue, 1, 30));
		btnStopServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println("Button clicked!");
            	
            	try {
					DBConnection.getInstance().closeConnection();
					server.stopServer();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	btnStartServer.setVisible(true);
            	btnStopServer.setVisible(false);
            }
        });
		add(btnStopServer);
		
		lblTextButtonServer = new JLabel();
		lblTextButtonServer.setText("<html><font color='#FFFFFF'>Stop</font></html>");
		lblTextButtonServer.setBounds(74, 118, 100, 30);
		lblTextButtonServer.setFont(_Popins30);
		btnStopServer.add(lblTextButtonServer);
		btnStopServer.setVisible(false);
		
		
		/* List Online Panel */
		listOnlinePanel = new JPanel();
		listOnlinePanel.setLayout(null);
//		listOnlinePanel.setOpaque(false);
		listOnlinePanel.setBounds(61, serverInfoPanel.getHeight() + 28 + 42, 927, 362);
		listOnlinePanel.setBackground(_primaryPanelGrey);
		listOnlinePanel.setBorder(new RoundedBorder(_primaryPanelGrey, 1, 30));
		add(listOnlinePanel);
	}
	
	/* Add Components into Server Info Panel */
	public void generateServerInfoComponents() {
		/* IP Panel */
		ipPanel = new JPanel();
		ipPanel.setLayout(null);
		ipPanel.setBounds(0, 0, 322, 112);
		ipPanel.setBackground(_primaryPanelWhite);
		ipPanel.setBorder(new RoundedBorder(_primaryPanelWhite, 1, 30));
		serverInfoPanel.add(ipPanel);
		
		lblIP = new JLabel();
		lblIP.setText("<html><font color='#1c1c1c'>IP:</font></html>");
		lblIP.setBounds(24, 24, 274, 20);
		lblIP.setFont(_Popins20);
		ipPanel.add(lblIP);
		
		txtIP = new MyTextField();
		txtIP.setType(false);
		txtIP.setBounds(24, lblIP.getX() + lblIP.getHeight() + 8, 274, 36);
		txtIP.setBackground(_primaryPanelWhite);
		txtIP.setBorder(null);
		txtIP.setFont(_Popins20);
		try {
			txtIP.setText(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		txtIP.setEditable(false);
		txtIP.setCursor(DragSource.DefaultLinkNoDrop);
		ipPanel.add(txtIP);
		
		
		/* Port Panel */
		portPanel = new JPanel();
		portPanel.setLayout(null);
		portPanel.setBounds(ipPanel.getWidth() + 28, 0, 322, 112);
		portPanel.setBackground(_primaryPanelWhite);
		portPanel.setBorder(new RoundedBorder(_primaryPanelWhite, 0, 30));
		serverInfoPanel.add(portPanel);
		
		lblPort = new JLabel();
		lblPort.setText("<html><font color='#1c1c1c'>Port:</font></html>");
		lblPort.setBounds(24, 24, 274, 20);
		lblPort.setFont(_Popins20);
		portPanel.add(lblPort);
		
		txtPort = new MyTextField();
		txtPort.setType(false);
		txtPort.setBounds(24, lblPort.getX() + lblPort.getHeight() + 8, 274, 36);
		txtPort.setBackground(_primaryPanelWhite);
		txtPort.setBorder(null);
		txtPort.setFont(_Popins20);
		txtPort.setText("1234");
		txtPort.requestFocusInWindow();
		portPanel.add(txtPort);
		
		
		/* Uptime Panel */
		uptimePanel = new JPanel();
		uptimePanel.setLayout(null);
//		uptimePanel.setOpaque(false);
		uptimePanel.setBounds(0, ipPanel.getHeight() + 28, 205, 112);
		uptimePanel.setBackground(_primaryPanelGrey);
		uptimePanel.setBorder(new RoundedBorder(_primaryPanelGrey, 0, 30));
		serverInfoPanel.add(uptimePanel);
		
		lblUpTime = new JLabel();
		lblUpTime.setText("<html><font color='#FFFFFF'>Uptime:</font></html>");
		lblUpTime.setBounds(24, 24, 157, 20);
		lblUpTime.setFont(_Popins20);
		uptimePanel.add(lblUpTime);
		
		
		/* Start Time Panel */
		startTimePanel = new JPanel();
		startTimePanel.setLayout(null);
		startTimePanel.setBounds(uptimePanel.getWidth()+28, ipPanel.getHeight() + 28, 205, 112);
		startTimePanel.setBackground(_primaryPanelGrey);
		startTimePanel.setBorder(new RoundedBorder(_primaryPanelGrey, 0, 30));
		serverInfoPanel.add(startTimePanel);
		
		lblStartTime = new JLabel();
		lblStartTime.setText("<html><font color='#FFFFFF'>Start Time:</font></html>");
		lblStartTime.setBounds(24, 24, 157, 20);
		lblStartTime.setFont(_Popins20);
		startTimePanel.add(lblStartTime);
		
		
		/* Connected Client Panel */
		connectedClientPanel = new JPanel();
		connectedClientPanel.setLayout(null);
		connectedClientPanel.setBounds((uptimePanel.getWidth()+28) * 2, ipPanel.getHeight() + 28, 205, 112);
		connectedClientPanel.setBackground(_primaryPanelGrey);
		connectedClientPanel.setBorder(new RoundedBorder(_primaryPanelGrey, 0, 30));
		serverInfoPanel.add(connectedClientPanel);
		
		lblConnectedClient = new JLabel();
		lblConnectedClient.setText("<html><font color='#FFFFFF'>Clients:</font></html>");
		lblConnectedClient.setBounds(24, 24, 157, 20);
		lblConnectedClient.setFont(_Popins20);
		connectedClientPanel.add(lblConnectedClient);
	}
}
