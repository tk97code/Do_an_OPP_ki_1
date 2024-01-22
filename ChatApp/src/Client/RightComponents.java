package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.PanelUI;
import javax.swing.text.DefaultEditorKit;

import ClientEvent.Event;
import ClientEvent.EventChat;
import Data.ListUsersAccountData;
import Data.MessageData;
import Data.ReceiveMessageData;
import Data.RequestChatData;
import Data.SendMessageData;
import Data.UserAccountData;
import Login.ClientService;
import TheSedativePackage.ImageLoader;
import TheSedativePackage.ModernScrollBarUI;
import TheSedativePackage.RoundedBorder;
import TheSedativePackage.ScrollablePanel;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.*;
import java.util.List;

public class RightComponents extends JPanel {

	/* Primary Color */
	private Color _primaryWhite = new Color(248, 250, 255);
	private Color _primaryLightBlue = new Color(163, 192, 240);
	
	private Font _ManropeExtraBold20 = new Font("Manrope ExtraBold", Font.PLAIN, 20);
	private Font _ManropeSemiBold15 = new Font("Manrope SemiBold", Font.PLAIN, 15);
	
	private String userName;
	private UserAccountData toUser;
	private static RightComponents instance;
	private static ReceiverUserPanel receiverPanel;
	private ChatPanel chat;
	private InputMessagePanel inputMessage;
	private RequestChatData requestMsg;

	
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public static RightComponents getInstance() {
		if (instance == null) {
			instance = new RightComponents("", null);
		}
		return instance;
	}
	
	public ChatPanel getChat() {
		return chat;
	}
	
	public void updateOnline() {
		receiverPanel.lblStatus.setText("Online");
		receiverPanel.lblStatus.setForeground(new Color(118, 212, 94));
		receiverPanel.repaint();
		receiverPanel.revalidate();
	}
	
	public void updateOffline() {
		receiverPanel.lblStatus.setText("Offline");
		receiverPanel.lblStatus.setForeground(new Color(105, 105, 105));
		receiverPanel.repaint();
		receiverPanel.revalidate();
	}
	
	private void loadChat(RequestChatData data) {
        ClientService.getInstance().getClient().emit("load_chat", data.toJsonObject(), new Ack() {
			
			@Override
			public void call(Object... data) {
				List<SendMessageData> list = new ArrayList<>();
				
				for (Object o : data) {
                	SendMessageData msg = new SendMessageData(o);
//                	System.out.println(o);
                	try {
                		if (msg.getFromUserID() == ClientService.getInstance().getUser().getUserID()) {
                			chat.addSendMessage(msg.getText());
						} else {
							chat.addReceiveMessage(msg.getText());
						}
                	} catch (Exception e) {
                		
                	}
                }
				
			}
		});
    }
	
	public RightComponents(String userName, UserAccountData toUser) {
		this.userName = userName;
		this.toUser = toUser;
		receiverPanel = new ReceiverUserPanel();
		chat = new ChatPanel();
		inputMessage = new InputMessagePanel();
		RequestChatData requestMsg = new RequestChatData(ClientService.getInstance().getUser().getUserID(), toUser.getUserID());
		
		Event.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(SendMessageData data) {
//                chatBody.addItemRight(data);
            	chat.addSendMessage(data.getText());
            }

            @Override
            public void receiveMessage(ReceiveMessageData data) {
                if (toUser.getUserID() == data.getFromUserID()) {
                	chat.addReceiveMessage(data.getText());
                }
            }
            
            @Override
            public void updateStatus(UserAccountData user) {
            	// TODO Auto-generated method stub
            	if (user.getUserID() == toUser.getUserID()) {
            		if (user.getStatus() == true) {
            			updateOnline();
            		} else {
            			updateOffline();
            		}
            	}
            	
            }
        });
		
		setBounds(340, 0, 885, 860);
		setLayout(null);
		add(receiverPanel);
		add(chat);
		add(inputMessage);
		
    	loadChat(requestMsg);
	}
	
	class ReceiverUserPanel extends JPanel {
		private JLabel lblReceiver;
		private Image avtURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\0.jpg");
		private Image avt = avtURL.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
		private ImageLoader avtImg;
		private JLabel lblStatus;
		
		
		public ReceiverUserPanel() {
			setLayout(null);
			setBounds(0, 0, 885, 100);
			setBackground(_primaryWhite);
			setBorder(new MatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 51)));
			
			
//			add(userPanel);
			lblStatus = new JLabel();
//			lblStatus.setText("Offline");
			lblStatus.setFont(new Font("Manrope Bold", Font.PLAIN, 14));
//			lblStatus.setForeground(new Color(105, 105, 105));
			lblStatus.setBounds(105, 50, 230, 25);
			add(lblStatus);
			
			
			avtImg = new ImageLoader(avt);
			avtImg.setBounds(35, 26, 50, 50);
			avtImg.setBorder(new RoundedBorder(new Color(175, 187, 247), 2, 100));
			add(avtImg);
			
			lblReceiver = new JLabel();
			lblReceiver.setText(userName);
			lblReceiver.setForeground(Color.black);
			lblReceiver.setBounds(105, 30, 230, 25);
			lblReceiver.setFont(_ManropeExtraBold20);
			add(lblReceiver);
		}
	}
	
	public class ChatPanel extends JPanel {
		
		private ScrollablePanel contentPanel;
		private JScrollPane scrollPane;
		private GridBagConstraints gbc;
		private JScrollBar customScrollBar;
		
		private int row;
		
		public void addSendMessage(String msg) {
			gbc.gridy = row++;
			
        	JPanel p = new JPanel( new FlowLayout(FlowLayout.RIGHT) );
            p.setBorder( new EmptyBorder(10, 10, 10, 10) );
            p.setBackground(new Color(0, 0, 0, 0));
            JTextArea text = new JTextArea() {
            	@Override
			    protected void paintBorder(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			        g2.setColor(new Color(112, 156, 230));
			        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
			    }
            	
            	@Override
			    protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			        g2.setColor(getBackground());
			        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
			        
			        super.paintComponent(g);
			        
				}
            };
            text.setOpaque(false);
            text.append(msg);
            text.setFont(new Font("Manrope", Font.PLAIN, 15));
            text.setEditable(false);
            text.setBorder(new EmptyBorder(15, 15, 15, 15));
            text.setForeground(Color.white);
            text.setBackground(new Color(91, 150, 247));
            Canvas c = new Canvas();
            int w = c.getFontMetrics(text.getFont()).stringWidth(text.getText());
            if (w >= 100 && w < 200) {
            	text.setColumns(10);
            } else if (w >= 200 && w < 300) { 
            	text.setColumns(20);
            }
            else if (w >= 300 && w < 500) {
            	text.setColumns(30);
            }
            else if (w >= 500) {
            	text.setColumns(50);
            } else {
            	text.setBounds(30, 0, 0, 0);
            }
            text.setLineWrap( true );
            p.add(text, FlowLayout.LEFT);
            contentPanel.add(p, gbc);
            contentPanel.revalidate();
		}
		
		public void addReceiveMessage(String msg) {
			gbc.gridy = row++;	
			
			JPanel p = new JPanel( new FlowLayout(FlowLayout.LEFT) );
            p.setBorder( new EmptyBorder(10, 10, 10, 10) );
            p.setBackground(new Color(0, 0, 0, 0));
            JTextArea text = new JTextArea() {
            	@Override
			    protected void paintBorder(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			        g2.setColor(Color.white);
			        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
			    }
            	
            	@Override
			    protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			        g2.setColor(getBackground());
			        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
			        
			        super.paintComponent(g);
			        
				}
            };
            text.setOpaque(false);
            text.append(msg);
            text.setEditable(false);
            text.setFont(new Font("Manrope", Font.PLAIN, 15));
            text.setBorder(new EmptyBorder(15, 15, 15, 15));
            text.setForeground(Color.black);
            text.setBackground(Color.white);
            Canvas c = new Canvas();
            int w = c.getFontMetrics(text.getFont()).stringWidth(text.getText());
            
            if (w >= 100 && w < 200) {
            	text.setColumns(10);
            } else if (w >= 200 && w < 300) { 
            	text.setColumns(20);
            }
            else if (w >= 300 && w < 500) {
            	text.setColumns(30);
            }
            else if (w >= 500) {
            	text.setColumns(50);
            } else {
            	text.setBounds(30, 0, 0, 0);
            }
            text.setLayout(new BorderLayout());
            text.setLineWrap( true );
            p.add(text, FlowLayout.LEFT);
            contentPanel.add(p, gbc);
            contentPanel.revalidate();
		}
		
		public ChatPanel() {
			setBounds(0, 100, 885, 680);
			setLayout(null);
			
			contentPanel = new ScrollablePanel(new GridBagLayout());
//			contentPanel.setBackground(new Color(240, 244, 250));
			contentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
	        gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.weightx = 1.0;
	        gbc.fill = GridBagConstraints.HORIZONTAL;
//	        gbc.anchor = GridBagConstraints.PAGE_START;
	        
	        scrollPane = new JScrollPane(contentPanel);
//	        scrollPane.setSize(new Dimension(1000, 700));
	        scrollPane.setBounds(0, 0, 875, 680);
	        scrollPane.setBackground(new Color(240, 244, 250));
	        scrollPane.setBorder(null);
	        contentPanel.addContainerListener(new ContainerAdapter() {
	            public void componentAdded(ContainerEvent e) {
	                SwingUtilities.invokeLater(new Runnable() {
	                    public void run() {
	                    	scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	                    }
	                });
	            }
	        }); 
	        
	        add(scrollPane);
		}
		
	}
	
	class InputMessagePanel extends JPanel {
		private JTextField inputMessage;
		
		private JButton btnSendMessage;
		private JLabel lblSendIcon;
		private Image sendIconURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\send_white.png");
		private ImageIcon sendIcon = new ImageIcon(sendIconURL);
		
		private void send(SendMessageData data) {
	        ClientService.getInstance().getClient().emit("send_to_user", data.toJsonObject());
	    }
		
		public InputMessagePanel() {
			setBounds(0, 782, 885, 80);
			setLayout(null);
			setBackground(new Color(247, 249, 253));
			
			inputMessage = new JTextField() {
				@Override
			    protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			        g2.setColor(getBackground());
			        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
			        
			        if (getText().isEmpty()) {
			            g2.setColor(this.getForeground());
			            g2.drawString("Write a message", getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top + 12);
			        }
			        super.paintComponent(g);
			        
				}
				
				@Override
			    protected void paintBorder(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			        g2.setColor(new Color(112, 156, 230));
			        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
			    }
			};
			inputMessage.addKeyListener(new KeyAdapter() {
	    		@Override
	    		public void keyPressed(KeyEvent e) {
	    			// TODO Auto-generated method stub
	    			super.keyPressed(e);
	    			if (inputMessage.getText().length() == 0) {
	    	    		inputMessage.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);
	    	    	} else {
	    	    		inputMessage.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(true);
	    	    	}
	    		}
	    	});
			
			inputMessage.setBorder(new EmptyBorder(0, 20, 0, 0));
			inputMessage.setFont(_ManropeSemiBold15);
			inputMessage.setForeground(new Color(73, 133, 234));
			inputMessage.setOpaque(false);
			inputMessage.setBounds(20, 17, 765, 45);
			inputMessage.setBackground(new Color(234, 242, 254));
			add(inputMessage);
			
			btnSendMessage = new JButton();
			btnSendMessage.setBounds(805, 17, 45, 45);
			btnSendMessage.setBackground(new Color(91, 150, 247));
			btnSendMessage.setBorder(new RoundedBorder(new Color(91, 150, 247), 2, 15));
			btnSendMessage.setLayout(null);
			add(btnSendMessage);
			
			btnSendMessage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String text = inputMessage.getText();
					SendMessageData message = new SendMessageData(ClientService.getInstance().getUser().getUserID(), toUser.getUserID(), text);
					send(message);
//					chat.addSendMessage(text);
					Event.getInstance().getEventChat().sendMessage(message);
					inputMessage.setText("");
				}
			});
			
			lblSendIcon = new JLabel();
			lblSendIcon.setIcon(sendIcon);
			lblSendIcon.setBounds(10, 2, 45, 45);
			btnSendMessage.add(lblSendIcon);
		}
	}
	
}
