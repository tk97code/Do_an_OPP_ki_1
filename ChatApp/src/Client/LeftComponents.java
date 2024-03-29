package Client;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import TheSedativePackage.*;
import io.socket.emitter.Emitter;
import io.socket.emitter.Emitter.Listener;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jdesktop.swingx.graphics.ShadowRenderer;

import ClientEvent.Event;
import ClientEvent.EventMenuLeft;
import Data.ListUsersAccountData;
import Data.RequestChatData;
import Data.SendMessageData;
import Data.UserAccountData;
import Login.ClientService;



public class LeftComponents extends JPanel {		
	/* Primary Color */
	private Color _primaryWhite = new Color(248, 250, 255);
	private Color _primaryLightBlue = new Color(163, 192, 240);
	
	/* Primary Font */
	private Font _ManropeExtraBold24 = new Font("Manrope ExtraBold", Font.PLAIN, 24);

	private JLayeredPane managerPanel;
	
	public LeftComponents() {
		setLayout(null);
		setBounds(0, 0, 340, 860);
	
		managerPanel = new JLayeredPane();
		managerPanel.setBounds(0, 0, 340, 860);
		managerPanel.setLayout(null);
		add(managerPanel);
		
		managerPanel.add(new AccountPanel(), JLayeredPane.DEFAULT_LAYER);
		managerPanel.add(new UserPanel(), JLayeredPane.DEFAULT_LAYER);
		managerPanel.add(new TabBarPanel(), JLayeredPane.PALETTE_LAYER);
	}
		
		
	
	class UserPanel extends JPanel {
		private JLabel lblUserName;
		private Image avtURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\1.jpg");
		private Image avt = avtURL.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
		private ImageLoader avtImg;
		
		public UserPanel() {
			setLayout(null);
			setBounds(0, 0, 340, 100);
			setBackground(_primaryWhite);
			setBorder(new MatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 51)));
//			add(userPanel);
			
			avtImg = new ImageLoader(avt);
			avtImg.setBounds(35, 22, 55, 55);
			avtImg.setBorder(new RoundedBorder(new Color(175, 187, 247), 2, 15));
			add(avtImg);
			
			
			lblUserName = new JLabel();
			lblUserName.setText(ClientService.getInstance().getUser().getUserName());
			lblUserName.setForeground(Color.black);
			lblUserName.setBounds(105, 32, 230, 38);
			lblUserName.setFont(_ManropeExtraBold24);
			add(lblUserName);
		}
	}
	
	class AccountPanel extends JPanel {
//		private JPanel bottomLeftPanel;
		private JPanel listAccountPanel;
		private DefaultListModel<AccountComponent> modelList;
		private JList<AccountComponent> listAccount;
		private JScrollPane showingPane;
		private JScrollBar customScrollBar;
		
		
		
		public AccountPanel() {
			setLayout(new BorderLayout());
			setBounds(0, 100, 340, 760);
			setBackground(_primaryWhite);
			setBorder(new MatteBorder(0, 0, 0, 2, new Color(0, 0, 0, 51)));
			
			modelList = new DefaultListModel<AccountComponent>();
			
			
			listAccountPanel = new JPanel();
			listAccountPanel.setBounds(0, 86, 325, 760);
			listAccountPanel.setLayout(new FlowLayout());
			listAccountPanel.setBackground(_primaryWhite);
			add(listAccountPanel);
			
//			for (int i = 0; i < 25; i++) {
//				modelList.addElement(new AccountComponent());
//			}
			listAccount = new JList<>(modelList) {
				@Override
				protected void processMouseMotionEvent(MouseEvent e) {
					if (MouseEvent.MOUSE_DRAGGED != e.getID())
					super.processMouseMotionEvent(e);
				}
			};
			
			listAccount.addListSelectionListener(new ListSelectionListener() {
			    @Override
			    public void valueChanged(ListSelectionEvent e) {
			        if (!e.getValueIsAdjusting()) {
			            // This block will be executed when the selection changes
			            AccountComponent selectedComponent = listAccount.getSelectedValue();
			            if (selectedComponent != null) {
//			                System.out.println(selectedComponent.getUserName());
			                Event.getInstance().getEventMenuRight().loadRightComponents(selectedComponent.getUserName(), selectedComponent.getToUser());
			                Event.getInstance().getEventChat().updateStatus(selectedComponent.getToUser());
			            }
			        }
			    }
			});
			
			listAccount.setCellRenderer(new PanelListCellRenderer());
			listAccount.setBorder(BorderFactory.createEmptyBorder());
			listAccount.setBackground(_primaryWhite);
			listAccount.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			showingPane = new JScrollPane(listAccount);
			showingPane.setBorder(BorderFactory.createEmptyBorder());
			showingPane.setPreferredSize(new Dimension(290, 760));
//			showingPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
//			showingPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			showingPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
//			showingPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
			
			showingPane.getVerticalScrollBar().setUnitIncrement(2);
			showingPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
			
			Event.getInstance().addEventMenuLeft(new EventMenuLeft() {
	            @Override
	            public void listUser(List<UserAccountData> users) {
	            	SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
//							modelList.clear();
							if (modelList.size() > 0) {
								modelList.clear();
							}
							showingPane.repaint();
							showingPane.revalidate();
							
							try {
								modelList.remove(modelList.getSize() - 1);
							} catch(Exception e) {
								System.out.println("undefined last element");
							}
							
							// TODO Auto-generated method stub
							for (UserAccountData d : users) {
			                    ListUsersAccountData.getInstance().add(d);
			                    modelList.addElement(new AccountComponent(d.getUserName(), d));
			                    showingPane.repaint();
//			                    refreshMenuList();
			                }
							try {
								modelList.addElement(new AccountComponent(users.get(users.size() - 1).getUserName(), users.get(users.size() - 1)));
							} catch(Exception e) {
							}
							
							
							showingPane.repaint();
							showingPane.revalidate();
						}
					});  
	            }
			});
			
	        customScrollBar = new JScrollBar(JScrollBar.VERTICAL);
	        customScrollBar.setModel(showingPane.getVerticalScrollBar().getModel());
	        customScrollBar.setPreferredSize(new Dimension(10, 760));
	        customScrollBar.setForeground(new Color(48, 144, 216));
	        customScrollBar.setBackground(_primaryWhite);
	        customScrollBar.setUI(new ModernScrollBarUI());
	        
	        
	        addMouseWheelListener(new MouseWheelListener() {
	            @Override
	            public void mouseWheelMoved(MouseWheelEvent e) {
	            	int wheelRotation = e.getWheelRotation();
	                int scrollValue = customScrollBar.getValue();
	                int newValue = scrollValue + wheelRotation * 50;

	                if (newValue >= customScrollBar.getMinimum() && newValue <= customScrollBar.getMaximum()) {
	                    customScrollBar.setValue(newValue);
	                }
	                showingPane.repaint();
                    showingPane.revalidate();
	            }
	        });
	        
	        showingPane.getVerticalScrollBar().addHierarchyListener(new HierarchyListener() {
	        	  @Override
	        	  public void hierarchyChanged(HierarchyEvent e) {
	        		  if (e.getID() == HierarchyEvent.HIERARCHY_CHANGED && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
	        			  	customScrollBar.setVisible(showingPane.getVerticalScrollBar().isVisible());
	        		  }
	        	  }
	        });
	        listAccountPanel.add(showingPane);
	        add(customScrollBar, BorderLayout.EAST);
		}
	}
	
	class TabBarPanel extends JLayeredPane {
		private JLabel lblChatIcon;
		private Image chatIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\chat_white.png");
		private ImageIcon chatIcon = new ImageIcon(chatIconUrl);
		
		private JLabel lblUsersIcon;
		private Image usersIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\users_black.png");
		private ImageIcon usersIcon = new ImageIcon(usersIconUrl);
		
		private JPanel selectPanel;
		
		private Animator animator;
		
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
			
			RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(0, 0, 135, 50, 50, 100);
			
			Area area = new Area(bubble);
			
            Rectangle rect = new Rectangle(0,0,155, 50);
            Area borderRegion = new Area(rect);
            borderRegion.subtract(area);
            g2.setClip(borderRegion);
            g2.setColor(new Color(0, 0, 0,0));
            g2.fillRect(0, 0, 135, 50);
            g2.setClip(null);
            
            g2.setClip(bubble);
            g2.setColor(new Color(174, 202, 250));
            g2.fillRect(0, 0, 135, 50);
            g2.setClip(null);
            	
//            g2.setColor(new Color(112, 156, 230));
//            g2.setStroke(new BasicStroke(1));
//            g2.draw(area);
            
//            g2.drawImage(url, 25, 15, this);
		}
		
		public TabBarPanel() {
			setBounds(91, 795, 135, 50);
			setLayout(null);
			
			addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
                    // Handle the click event on the abovePanel
//                    System.out.println("Panel clicked");
                    e.consume(); // Consume the event to prevent it from being passed to the underlying components
                }
			});
			
			lblChatIcon = new JLabel();
			lblChatIcon.setIcon(chatIcon);
			lblChatIcon.setBounds(25, 0-10/2, 60, 60);
			add(lblChatIcon, JLayeredPane.PALETTE_LAYER);
			
			lblUsersIcon = new JLabel();
			lblUsersIcon.setIcon(usersIcon);
			lblUsersIcon.setBounds(90, 0-10/2, 60, 60);
			add(lblUsersIcon, JLayeredPane.PALETTE_LAYER);
			
			selectPanel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Graphics2D g2 = (Graphics2D) g;
	                int diameter = Math.min(getWidth(), getHeight());
	                Ellipse2D.Double circle = new Ellipse2D.Double(0+2.5f, 0, diameter-5, diameter-5);
	                g2.setColor(new Color(91, 150, 247)); 
	                g2.fill(circle);
	            }
	        };

//	        selectPanel.setPreferredSize(new Dimension(50, 50));
	        selectPanel.setOpaque(false); // Make the panel transparent
	        add(selectPanel, JLayeredPane.DEFAULT_LAYER);
	        selectPanel.setBounds(lblChatIcon.getX() / 2, lblChatIcon.getY() / 2 + 5, 50, 50);
	        
	        
	        lblChatIcon.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mousePressed(MouseEvent e) {
//	        		selectPanel.setBounds(lblChatIcon.getX() / 2, lblChatIcon.getY() / 2 + 5, 50, 50);
	        		Point newPos = new Point(lblChatIcon.getX() / 2, lblChatIcon.getY() / 2 + 5);
	        		selectedAnimation(100, selectPanel, selectPanel.getLocation(), newPos);
	        		usersIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\users_black.png");
	        		usersIcon = new ImageIcon(usersIconUrl);
	        		lblUsersIcon.setIcon(usersIcon);
	        		
	        		chatIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\chat_white.png");
	        		chatIcon = new ImageIcon(chatIconUrl);
	        		lblChatIcon.setIcon(chatIcon);
	        	}
			});
	        
	        lblUsersIcon.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mousePressed(MouseEvent e) {
//	        		selectPanel.setBounds(lblUsersIcon.getX() - 15, lblUsersIcon.getY() / 2 + 5, 50, 50);
	        		Point newPos = new Point(lblUsersIcon.getX() - 15, lblUsersIcon.getY() / 2 + 5);
	        		selectedAnimation(100, selectPanel, selectPanel.getLocation(), newPos);
	        		usersIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\users_white.png");
	        		usersIcon = new ImageIcon(usersIconUrl);
	        		lblUsersIcon.setIcon(usersIcon);
	        		
	        		chatIconUrl = Toolkit.getDefaultToolkit().getImage("src\\Image\\chat_black.png");
	        		chatIcon = new ImageIcon(chatIconUrl);
	        		lblChatIcon.setIcon(chatIcon);
	        		
	        	}
			});
		}
		
		private void selectedAnimation(int duration, Component comp, Point currentPos, Point endPos) {
			if (animator != null && animator.isRunning()) {
	            animator.stop();
			}
		 
			animator = PropertySetter.createAnimator(duration, comp, "location", currentPos, endPos);
	        //  To refresh or repaint graphics
	        animator.addTarget(new TimingTargetAdapter() {
	            @Override
	            public void timingEvent(float fraction) {
	                repaint();
	            }
	        });
	        animator.setResolution(5);
	        animator.start();
		}
		
	}
	
}
