package Test;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Client.AccountComponent;
import Server.ServerCore;
import TheSedativePackage.ModernScrollBarUI;
import TheSedativePackage.MyTextField;
import TheSedativePackage.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.concurrent.Flow;

import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Data.UserAccountData;
import TheSedativePackage.ImageLoader;
import TheSedativePackage.RoundedBorder;

class TestAccountComponent extends JPanel {
	private String userName;
	private JLabel lblName;
	
	private UserAccountData toUser;
	
	private String status;
	protected JLabel lblStatus;
	
	private ImageLoader avtImg;
	private Image avtURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\0.jpg");
	private Image avt = avtURL.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
	
	private Font _ManropeExtraBold16 = new Font("Manrope ExtraBold", Font.PLAIN, 16);
	private Font _ManropeSemiBold13 = new Font("Manrope SemiBold", Font.PLAIN, 13);

	public String getUserName() {
		return userName;
	}
	
	public UserAccountData getToUser() {
		return toUser;
	}
	
	public JLabel getLblName() {
		return lblName;
	}
	
	public TestAccountComponent(String userName, UserAccountData toUser) {
//        setPreferredSize(new Dimension(290, 70));
		this.userName = userName;
		this.toUser = toUser;
        setBackground(Color.white);
        setLayout(null);
        setBorder(new RoundedBorder(new Color(0, 0, 0, 50), 1, 20));
        
        avtImg = new ImageLoader(avt);
        avtImg.repaint();
		avtImg.setBounds(10, 12, 45, 45);
        avtImg.setPreferredSize(new Dimension(45, 45));
		avtImg.setBorder(new RoundedBorder(new Color(175, 187, 247), 1, 100));
		add(avtImg);
		
        lblName = new JLabel(userName);
        lblName.setBounds(avtImg.getX() + avtImg.getWidth() + 15, 14, 175, 20);
        lblName.setFont(_ManropeExtraBold16);
        add(lblName);
        
        lblStatus = new JLabel();
        if (toUser.getStatus()) {
        	status = "Online";
        	lblStatus.setForeground(new Color(118, 212, 94));
        } else {
        	status = "Offline";
        	lblStatus.setForeground(new Color(105, 105, 105));
        }
        lblStatus.setText(status);
        
        lblStatus.setBounds(avtImg.getX() + avtImg.getWidth() + 15, lblName.getHeight() + lblName.getY() + 5, 175, 15);
        lblStatus.setFont(new Font("Manrope Bold", Font.PLAIN, 14));
        add(lblStatus);

	}
}



public class TestListModel extends JFrame {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TestListModel frame = new TestListModel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	private Component createSpacer() {
        return Box.createRigidArea(new Dimension(0, 24));
    }
	
	public TestListModel() {
		setSize(1024, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		JLayeredPane lp = new JLayeredPane();
		lp.setBounds(0, 0, 1024, 900);
		lp.setLayout(null);
		lp.setBackground(new Color(0, 0, 0));
//		p3.setComponentZOrder(this, 3);
		add(lp);
		
		JPanel p3 = new JPanel();
		p3.setBounds(50, 800, 200, 50);
		p3.setLayout(new FlowLayout());
		p3.setBackground(new Color(0, 0, 0));
//		p3.setComponentZOrder(this, 3);
		add(p3);
		
		JLayeredPane p = new JLayeredPane();
		p.setBounds(0, 0, 325, 900);
		p.setLayout(new FlowLayout());
		p.setBackground(new Color(248, 250, 255));
		add(p);		
		
		JPanel p2 = new JPanel();
		p2.setBounds(325, 0, 337, 900);
		p2.setLayout(new BorderLayout());
		p2.setBackground(new Color(248, 250, 255));
		add(p2);
		
//		add(new tett());
		
		DefaultListModel<AccountComponent> model = new DefaultListModel<>();
		for (int i = 0; i < 20; i++) {
//			model.addElement(createSpacer());
			model.addElement(new AccountComponent("sdfgdgf", null));
		}
		
		JList<AccountComponent> list = new JList<>(model);
//		list.setBounds(50,50,300,300);
//		list.setLayout(new BorderLayout());
		
		list.setCellRenderer(new PanelListCellRenderer());
		
//		list.setBorder(null);
		list.setBorder(BorderFactory.createEmptyBorder());
		list.setBackground(new Color(248, 250, 255));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
//		list.setFixedCellHeight(94);
//		list.setFixedCellWidth(290);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
//		scrollPane.setBounds(350, 50, 300, 300);
		scrollPane.setPreferredSize(new Dimension(290, 900));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		// Create a custom vertical scroll bar
        JScrollBar customScrollBar = new JScrollBar(JScrollBar.VERTICAL);

        // Synchronize the models of the JList and the custom scroll bar
        customScrollBar.setModel(scrollPane.getVerticalScrollBar().getModel());
        customScrollBar.setPreferredSize(new Dimension(10, 900));
        customScrollBar.setForeground(new Color(48, 144, 216));
        customScrollBar.setBackground(new Color(248, 250, 255));
        customScrollBar.setUI(new ModernScrollBarUI());
//        customScrollBar.setBounds(500, 0, 20, 760);
        list.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Adjust the value of the custom scroll bar based on the mouse wheel movement
                customScrollBar.setValue(customScrollBar.getValue() + e.getWheelRotation() * 50);
            }
        });
        
		
//		p.add(list, BorderLayout.CENTER);
//		add(scrollPane, BorderLayout.EAST);
		p.add(scrollPane);
//		p.add(customScrollBar, FlowLayout.CENTER);
		p2.add(customScrollBar, BorderLayout.WEST);
		
		lp.add(p, JLayeredPane.DEFAULT_LAYER);
		lp.add(p3, JLayeredPane.PALETTE_LAYER);
	}
}
