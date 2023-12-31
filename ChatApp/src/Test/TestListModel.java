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
			model.addElement(new AccountComponent());
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
