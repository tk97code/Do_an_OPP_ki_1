package Login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Client.ClientFrame;
import ClientEvent.Event;
import ClientEvent.EventLogin;
import ClientEvent.EventMessage;
import Data.LoginData;
import Data.MessageData;
import Data.RegisterData;
import Data.UserAccountData;
import Test.PopupTester;
import TheSedativePackage.*;
import TheSedativePackage.RoundedBorder;
import okhttp3.Call;
import popup.ssn.NotificationPopup;
import io.socket.client.*;

public class LoginFrame extends JFrame {
	
	/* Left Components*/
	private PanelGradient designPanel;	
	private JLabel lblDesign;
	private Image designURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\design.png").getScaledInstance(545, 600, Image.SCALE_SMOOTH);
	private ImageIcon designImg = new ImageIcon(designURL);
	
	
	/* Right Component */
	private JPanel welcomePanel;
	
	private LoginComponent userName;
	private LoginComponent email;
	private LoginComponent pass;
	private LoginComponent passConfirm;

	private JLabel lblNotify;
	private JLabel lblAction;
	
	private boolean isLogin = true;

	private JButton btnSubmit;
	private JLabel lblButton;
	
	private JPanel managerAuthPanel;
	
	private Image errorURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\error.png");
	private ImageIcon errorImg = new ImageIcon(errorURL);
	
	private Image successURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\success.png");
	private ImageIcon successImg = new ImageIcon(successURL);
	
	
//	private ClientFrame clientFrame;
	

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					LoginFrame frame = new LoginFrame();
//					ClientService.getInstance().startServer();
//					frame.setVisible(true);
//					frame.requestFocusInWindow(false);
//					frame.getContentPane().setBackground(Color.white);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//			}
//		});
//	}
	
	public LoginFrame() {
		setTitle("Welkin Chat - Login");
		this.requestFocusInWindow(false);
		this.getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setSize(1215, 790);
		setResizable(false);
		setLocationRelativeTo(null);
//		ClientService.getInstance().startServer();
		
		Event.getInstance().addEventLogin(new EventLogin() {
			@Override
			public void login(LoginData data) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						System.out.println("chay vao luong login");
						ClientService.getInstance().getClient().emit("login", data.toJsonObject(), new Ack() {
							@Override
							public void call(Object... res) {
								// TODO Auto-generated method stub
								if (res.length > 0) {
									boolean action = (boolean) res[0];
									if (action) {
										dispose();
										SwingUtilities.invokeLater(new Runnable() {
										    public void run() {
										    	ClientService.getInstance().setUser(new UserAccountData(res[1]));
										    	dispose();
										        ClientFrame clientFrame = new ClientFrame();
										        clientFrame.setVisible(true);
										    	ClientService.getInstance().getClient().emit("list_user", ClientService.getInstance().getUser().getUserID());
//										    	System.out.println(res[]);
										    }
										});
//										System.out.println("Logged in");
										Popuper.getInstance().setPopup(successImg, "Welkin chat \nLogged in successfully", 300, 80, 2000);
									} else {
//										System.out.println("Failed");
										Popuper.getInstance().setPopup(errorImg, "Welkin chat \nLog in failed", 300, 80, 2000);
									}
								} else {
									System.out.println("not receive response");
								}
							}
						});
					}
				}).start();
			}
			
			@Override
			public void register(RegisterData data, EventMessage message) {
				// TODO Auto-generated method stub
				// Ack handle response from server
				ClientService.getInstance().getClient().emit("register", data.toJsonObject(), new Ack() {
					@Override
					public void call(Object... res) {
						// TODO Auto-generated method stub
						if (res.length > 0) {
							MessageData msg = new MessageData((boolean) res[0], res[1].toString());
							message.callMessage(msg);
						}
					}
				});
			}
		});
		
		designPanel = new PanelGradient();
		designPanel.setLayout(null);
		designPanel.setBounds(60, 25, 485, 700);
		designPanel.setBorder(new RoundedBorder(new Color(0, 0, 0, 50), 2, 100));
		designPanel.addColor(new ModelColor(new Color(76, 201, 229), 0.3f), new ModelColor(new Color(123, 222, 244), 0.6f), new ModelColor(new Color(80, 127, 247), 1f));
		add(designPanel);
		
		lblDesign = new JLabel();
//		lblDesign.setPreferredSize(new Dimension(545, 600));
		lblDesign.setIcon(designImg);
		lblDesign.setBounds(0, 50, 545, 600);
		designPanel.add(lblDesign);
		
		welcomePanel = new JPanel() {
			
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
		        
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				g2.setFont(new Font("Manrope ExtraBold", Font.PLAIN, 40));
				g2.setColor(Color.black);
				g2.drawString("Welcome back to", 0, 30);
				
				List<ModelColor> colors = new ArrayList<ModelColor>();
				         
				colors.add(new ModelColor(new Color(76, 201, 229), 0.3f));
				colors.add(new ModelColor(new Color(80, 97, 247), 1f));
				
				float[] position = new float[colors.size()];
				Color color[] = new Color[colors.size()];
			            
				for (int i = 0; i < colors.size(); i++) {
			        	position[i] = colors.get(i).getPosition();
			        	color[i] = colors.get(i).getColor();
				}
			    		        
				g2.setPaint(new LinearGradientPaint(350, 0, 580, 0, position, color));
//				g2.setFont(font);
				g2.drawString("Welkin!", 350, 30);
			}
		};
		welcomePanel.setBounds(580, 60, 525, 50);
		welcomePanel.setBackground(Color.WHITE);
		add(welcomePanel);
		
		managerAuthPanel = new JPanel();
		managerAuthPanel.setLayout(null);
		managerAuthPanel.setBounds(620, 150, 525, 510);
		managerAuthPanel.setBackground(Color.white);
		add(managerAuthPanel);
		
		createLogInPanel();
	}

	public void createSignUpPanel() {
		userName = new LoginComponent("Username", "Enter your user name", "src\\Image\\user.png", false);
		userName.addComponets();
		userName.setBounds(0, 0, 490, 65);
		managerAuthPanel.add(userName);
		
		email = new LoginComponent("Email", "Enter your email address", "src\\Image\\email.png", false);
		email.addComponets();
		email.setBounds(0, 95, 490, 65);
		managerAuthPanel.add(email);
		
		pass = new LoginComponent("Password", "Enter your Password", "src\\Image\\password.png", true);
		pass.addComponets();
		pass.setBounds(0, 190, 490, 65);
		managerAuthPanel.add(pass);
		
		passConfirm = new LoginComponent("Confirm Password", "Confirm your Password", "src\\Image\\password.png", true);
		passConfirm.addComponets();
		passConfirm.setBounds(0, 285, 490, 65);
		managerAuthPanel.add(passConfirm);
		
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.095);
        
        lblNotify = new JLabel();
        lblNotify.setForeground(Color.black);
        lblNotify.setText("Already have account log in,");
		lblNotify.setBounds(0, 395, 400, 30);
		lblNotify.setFont(new Font("SVN-Poppins", Font.PLAIN, 16));
        lblNotify.setFont(lblNotify.getFont().deriveFont(attributes));
        managerAuthPanel.add(lblNotify);
        
        lblAction = new JLabel();
        lblAction.setForeground(new Color(0, 137, 237));
        lblAction.setText("here");
        lblAction.setBounds(275, 0, 50, 30);
		lblAction.setFont(new Font("SVN-Poppins", Font.PLAIN, 16));
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblAction.setFont(lblNotify.getFont().deriveFont(attributes));
		lblNotify.add(lblAction);
		
		btnSubmit = new JButton();
		btnSubmit.setBounds(35, 445, 450, 55);
		btnSubmit.setBackground(new Color(91, 150, 247));
		btnSubmit.setBorder(new RoundedBorder(new Color(91, 150, 247), 2, 30));
		btnSubmit.setLayout(new FlowLayout(FlowLayout.CENTER, 0, -15));
		managerAuthPanel.add(btnSubmit);
		
		lblButton = new JLabel();
		lblButton.setBackground(Color.red);
		lblButton.setText("Sign Up");
		lblButton.setForeground(Color.white);
		lblButton.setFont(new Font("SVN-Poppins Bold", Font.PLAIN, 17));
		lblButton.setBorder(new EmptyBorder(5, 0, 0, 0));
//		lblButton.setBounds(190, 15, 70, 25);
		lblButton.setVerticalAlignment(JLabel.CENTER);
		btnSubmit.add(lblButton);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String usernameInput = userName.getText();
				String emailInput = email.getText();
				String passInput = pass.getText();
				
				Pattern valid_email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[a-z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher isValid = valid_email.matcher(emailInput);

				
				if (usernameInput.length() == 0 || emailInput.length() == 0 
						|| passInput.length() == 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
				            Popuper.getInstance().setPopup(errorImg, "Welkin chat \nYou have not entered Username, email, or password", 450, 80, 2000);
						}
					}).start();
				} else if (passInput.length() < 8) {
					new Thread(new Runnable() {
						@Override
						public void run() {
				            Popuper.getInstance().setPopup(errorImg, "Welkin chat \nPlease enter password more than 8 words", 400, 80, 2000);
						}
					}).start();
				} else if (passInput.length() > 25) {
					new Thread(new Runnable() {
						@Override
						public void run() {
				            Popuper.getInstance().setPopup(errorImg, "Welkin chat \nPlease enter password less than 25 words", 400, 80, 2000);
						}
					}).start();
				} else if (!passInput.equals(passConfirm.getText())) {
					new Thread(new Runnable() {
						@Override
						public void run() {
				            Popuper.getInstance().setPopup(errorImg, "Welkin chat \nConfirm password and password must same", 400, 80, 2000);
						}
					}).start();
				} else if (!isValid.matches()) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Popuper.getInstance().setPopup(errorImg, "Welkin chat \nPlease enter valid email address", 400, 80, 2000);
						}
					}).start();
				}else {
					RegisterData data = new RegisterData(usernameInput, emailInput, passInput);
					Event.getInstance().getEventLogin().register(data, new EventMessage() {
						@Override
						public void callMessage(MessageData message) {
							// TODO Auto-generated method stub
							if (!message.isAction()) {
//								System.out.println(message.getMessage());
								Popuper.getInstance().setPopup(errorImg, "Welkin chat \nRegistered failed \n"+message.getMessage(), 300, 80, 2000);
							} else {
//								System.out.println(message.getMessage());
								Popuper.getInstance().setPopup(successImg, "Welkin chat \nRegistered Successfully", 300, 80, 2000);
							}
						}
					});
				}
			}
		});
		
		lblAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				setCursor(Cursor.HAND_CURSOR);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				setCursor(Cursor.DEFAULT_CURSOR);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				managerAuthPanel.removeAll();
				createLogInPanel();
				managerAuthPanel.revalidate();
				managerAuthPanel.repaint();
			}
		});
	}
	
	public void createLogInPanel() {
		email = new LoginComponent("Email", "Enter your email address", "src\\Image\\email.png", false);
		email.addComponets();
		email.setBounds(0, 0, 490, 65);
		managerAuthPanel.add(email);
		
		pass = new LoginComponent("Password", "Enter your Password", "src\\Image\\password.png", true);
		pass.addComponets();
		pass.setBounds(0, 95, 490, 65);
		managerAuthPanel.add(pass);
		
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.095);
        
        lblNotify = new JLabel();
        lblNotify.setForeground(Color.black);
        lblNotify.setText("Don't have account sign up,");
		lblNotify.setBounds(0, 395, 400, 30);
		lblNotify.setFont(new Font("SVN-Poppins", Font.PLAIN, 16));
        lblNotify.setFont(lblNotify.getFont().deriveFont(attributes));
		managerAuthPanel.add(lblNotify);
        
        lblAction = new JLabel();
        lblAction.setForeground(new Color(0, 137, 237));
        lblAction.setText("here");
        lblAction.setBounds(270, 0, 50, 30);
		lblAction.setFont(new Font("SVN-Poppins", Font.PLAIN, 16));
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblAction.setFont(lblNotify.getFont().deriveFont(attributes));
		lblNotify.add(lblAction);
		
		btnSubmit = new JButton();
		btnSubmit.setBounds(35, 445, 450, 55);
		btnSubmit.setBackground(new Color(91, 150, 247));
		btnSubmit.setBorder(new RoundedBorder(new Color(91, 150, 247), 2, 30));
		btnSubmit.setLayout(new FlowLayout(FlowLayout.CENTER, 0, -15));
		managerAuthPanel.add(btnSubmit);
		
		lblButton = new JLabel();
		lblButton.setText("Log in");
		lblButton.setBorder(new EmptyBorder(5, 0, 0, 0));
		lblButton.setForeground(Color.white);
		lblButton.setFont(new Font("SVN-Poppins Bold", Font.PLAIN, 17));
//		lblButton.setBounds(190, 15, 70, 25);
		lblButton.setVerticalAlignment(JLabel.CENTER);
		btnSubmit.add(lblButton);
		
		lblAction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				setCursor(Cursor.HAND_CURSOR);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				setCursor(Cursor.DEFAULT_CURSOR);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				managerAuthPanel.removeAll();
				createSignUpPanel();
				managerAuthPanel.revalidate();
				managerAuthPanel.repaint();
			}
		}); 
		
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String emailInput = email.getText();
				String passInput = pass.getText();
				Pattern valid_email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[a-z]{2,6}$", Pattern.CASE_INSENSITIVE);
				Matcher isValid = valid_email.matcher(emailInput);
				
				if (passInput.length() == 0 || emailInput.length() == 0) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Popuper.getInstance().setPopup(errorImg, "Welkin chat \nYou have not entered email or password", 400, 80, 2000);
						}
					}).start();
				}else if (passInput.length() > 25) {
					new Thread(new Runnable() {
						@Override
						public void run() {
				            Popuper.getInstance().setPopup(errorImg, "Welkin chat \nPlease enter password less than 25 words", 400, 80, 2000);
						}
					}).start();
				}  else if (!isValid.matches()) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							Popuper.getInstance().setPopup(errorImg, "Welkin chat \nPlease enter valid email address", 400, 80, 2000);
						}
					}).start();
				} else {
					LoginData data = new LoginData(emailInput, passInput);
//					System.out.println("Socket.IO Connection State: " + LoginService.getInstance().getClient().id());
					Event.getInstance().getEventLogin().login(data);
				}
			}
			
		});
	}
}
