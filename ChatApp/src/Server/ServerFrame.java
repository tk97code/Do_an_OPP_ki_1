package Server;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;

import TheSedativePackage.ModernScrollBarUI;
import TheSedativePackage.MyTextField;
import TheSedativePackage.RoundedBorder;

import java.awt.*;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ServerFrame extends JFrame {
	private static ServerFrame instance;
	
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
	
	private JPanel startTimePanel;
	private JLabel lblStartTime;
	private JLabel lblStartTimeValue;
	
	private JPanel startDatePanel;
	private JLabel lblStartDate;
	private JLabel lblStartDateValue;
	
	private JPanel uptimePanel;
	private JLabel lblUptime;
	private JLabel lblUptimeValue;
	
	
	/* Log */
	private JPanel logPanel;
	private JTextPane logArea;
//	private JLabel lblUserOnline;

	
	/* Primary Color */
	private Color _primaryBackground = new Color(28, 28, 28);
	private Color _primaryPanelWhite = new Color(227, 245, 255);
	private Color _primaryPanelGrey = new Color(40, 40, 40);
	private Color _primaryBlue = new Color(47, 111, 237);
	
	
	/* Primary Font */
	private Font _Popins20 = new Font("SVN-Poppins", Font.BOLD, 20);
	private Font _Popins30 = new Font("SVN-Poppins", Font.BOLD, 30);
	
	
	/* Server Core */
	private ServerCore server;
	
	
	/* ServerFrame info */
	private int fWidth = 1065;
	private int fHeight = 760;
	
	private Boolean isStop = false;
	private int second = 0;
	private int minute = 0;
	private int hour = 0;
	private Timer timer;
	
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
	
	public static ServerFrame getInstance() {
		if (instance == null) {
			instance = new ServerFrame();
		}
		return instance;
	}
	
	private void updateTimer() {
        second++;
        if (second == 60) {
            second = 0;
            minute++;
            if (minute == 60) {
                minute = 0;
                hour++;
            }
        }
        updateTimerLabel();
    }
	
	private void updateTimerLabel() {
        String formattedTime = String.format("%02d:%02d:%02d", hour, minute, second);
//        System.out.println(second);
        lblUptimeValue.setText(formattedTime);
    }
	
	public ServerFrame() {
		/* Main Frame */
		setResizable(false);
		setTitle("Welkin Server");
		getContentPane().setBackground(_primaryBackground);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((sWidth-fWidth)/2, (sHeight-fHeight)/2, fWidth, fHeight);
		setLayout(null);
		
		timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
		
		/* Server Info Panel */
		serverInfoPanel = new JPanel();
//		serverInfoPanel.setOpaque(false);
		serverInfoPanel.setBackground(_primaryBackground);
		serverInfoPanel.setBounds(61, 42, 672, 252);
		serverInfoPanel.setLayout(null);
		add(serverInfoPanel);
		generateServerInfoComponents();
		
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
					DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
					DateTimeFormatter hf = DateTimeFormatter.ofPattern("HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();  
//					System.out.println(dtf.format(now));  
					lblStartDateValue.setText(df.format(now));
					lblStartTimeValue.setText(hf.format(now));
					timer.start();
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
					timer.stop();
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
		
		
		/* Log Panel */
		logPanel = new JPanel();
		logPanel.setLayout(null);
//		listOnlinePanel.setOpaque(false);
		logPanel.setBounds(61, serverInfoPanel.getHeight() + 28 + 42, 927, 362);
		logPanel.setBackground(_primaryPanelGrey);
		logPanel.setBorder(new RoundedBorder(_primaryPanelGrey, 1, 30));
		add(logPanel);
		
		logArea = new JTextPane();		
		logArea.setBackground(_primaryPanelGrey);
		logArea.setFont(new Font("SVN-Poppins", Font.PLAIN, 15));
		logArea.setForeground(Color.white);
//		logArea.setEnabled(true);
		logArea.setEnabled(true);
		logArea.setEditable(false);
		
		
		
		JLabel lblLog = new JLabel();
		lblLog.setText("[LOGS: ]");
		lblLog.setBounds(10, 15, 100, 20);
		lblLog.setFont(_Popins20);
		lblLog.setForeground(Color.white);
		
		JScrollPane log = new JScrollPane(logArea);
		log.setBounds(15, 45, 890, 310);
		log.setBackground(_primaryPanelGrey);
		log.setBorder(null);
		
		
//		log.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		log.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		
		
		JScrollBar customScrollBar = new JScrollBar(JScrollBar.VERTICAL);
        customScrollBar.setModel(log.getVerticalScrollBar().getModel());
        customScrollBar.setBounds(910, 20, 10, 342);
        customScrollBar.setForeground(new Color(48, 144, 216));
        customScrollBar.setBackground(_primaryPanelGrey);
        customScrollBar.setUI(new ModernScrollBarUI());
        
        log.getVerticalScrollBar().setUnitIncrement(4);
        
        log.getVerticalScrollBar().addHierarchyListener(new HierarchyListener() {
        	  @Override
        	  public void hierarchyChanged(HierarchyEvent e) {
        		  if (e.getID() == HierarchyEvent.HIERARCHY_CHANGED && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
        			  	customScrollBar.setVisible(log.getVerticalScrollBar().isVisible());
        		  }
        	  }
        });
        
        logPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
            	int wheelRotation = e.getWheelRotation();
                int scrollValue = customScrollBar.getValue();
                int newValue = scrollValue + wheelRotation * 50;

                if (newValue >= customScrollBar.getMinimum() && newValue <= customScrollBar.getMaximum()) {
                    customScrollBar.setValue(newValue);
                }
            }
        });
        
		logPanel.add(lblLog);
		logPanel.add(log);
		logPanel.add(customScrollBar); 
		
		/* Server */
		server = new ServerCore(Integer.parseInt(txtPort.getText()), logArea);
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
		
		txtIP = new MyTextField("Input IP");
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
		
		txtPort = new MyTextField("Input Port");
		txtPort.setType(false);
		txtPort.setBounds(24, lblPort.getX() + lblPort.getHeight() + 8, 274, 36);
		txtPort.setBackground(_primaryPanelWhite);
		txtPort.setBorder(null);
		txtPort.setFont(_Popins20);
		txtPort.setText("1234");
		txtPort.requestFocusInWindow();
		portPanel.add(txtPort);
		
		
		/* Uptime Panel */
		startTimePanel = new JPanel();
		startTimePanel.setLayout(null);
//		uptimePanel.setOpaque(false);
		startTimePanel.setBounds(0, ipPanel.getHeight() + 28, 205, 112);
		startTimePanel.setBackground(_primaryPanelGrey);
		startTimePanel.setBorder(new RoundedBorder(_primaryPanelGrey, 0, 30));
		serverInfoPanel.add(startTimePanel);
		
		lblStartTime = new JLabel();
		lblStartTime.setText("<html><font color='#FFFFFF'>Start time:</font></html>");
		lblStartTime.setBounds(24, 24, 157, 20);
		lblStartTime.setFont(_Popins20);
		startTimePanel.add(lblStartTime);
		
		lblStartTimeValue = new JLabel();
		lblStartTimeValue.setForeground(Color.white);
		lblStartTimeValue.setText("00:00:00");
		lblStartTimeValue.setBounds(24, 60, 157, 25);
		lblStartTimeValue.setFont(new Font("SVN-Poppins", Font.BOLD, 25));
		startTimePanel.add(lblStartTimeValue);
		
		
		/* Start Time Panel */
		startDatePanel = new JPanel();
		startDatePanel.setLayout(null);
		startDatePanel.setBounds(startTimePanel.getWidth()+28, ipPanel.getHeight() + 28, 205, 112);
		startDatePanel.setBackground(_primaryPanelGrey);
		startDatePanel.setBorder(new RoundedBorder(_primaryPanelGrey, 0, 30));
		serverInfoPanel.add(startDatePanel);
		
		lblStartDate = new JLabel();
		lblStartDate.setText("<html><font color='#FFFFFF'>Start Date:</font></html>");
		lblStartDate.setBounds(24, 24, 157, 20);
		lblStartDate.setFont(_Popins20);
		startDatePanel.add(lblStartDate);
		
		lblStartDateValue = new JLabel();
		lblStartDateValue.setForeground(Color.white);
		lblStartDateValue.setText("00/00/0000");
		lblStartDateValue.setBounds(24, 60, 157, 25);
		lblStartDateValue.setFont(new Font("SVN-Poppins", Font.BOLD, 25));
		startDatePanel.add(lblStartDateValue);
		
		
		/* Connected Client Panel */
		uptimePanel = new JPanel();
		uptimePanel.setLayout(null);
		uptimePanel.setBounds((startTimePanel.getWidth()+28) * 2, ipPanel.getHeight() + 28, 205, 112);
		uptimePanel.setBackground(_primaryPanelGrey);
		uptimePanel.setBorder(new RoundedBorder(_primaryPanelGrey, 0, 30));
		serverInfoPanel.add(uptimePanel);
		
		lblUptimeValue = new JLabel();
		lblUptimeValue.setForeground(Color.white);
		lblUptimeValue.setText("0");
		lblUptimeValue.setBounds(24, 60, 157, 25);
		lblUptimeValue.setFont(new Font("SVN-Poppins", Font.BOLD, 25));
		uptimePanel.add(lblUptimeValue);
		
		lblUptime = new JLabel();
		lblUptime.setText("<html><font color='#FFFFFF'>Uptime:</font></html>");
		lblUptime.setBounds(24, 24, 157, 20);
		lblUptime.setFont(_Popins20);
		uptimePanel.add(lblUptime);
		
	}
}
