package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import TheSedativePackage.ImageLoader;
import TheSedativePackage.RoundedBorder;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

public class RightComponents extends JPanel {

	/* Primary Color */
	private Color _primaryWhite = new Color(248, 250, 255);
	private Color _primaryLightBlue = new Color(163, 192, 240);
	
	private Font _ManropeExtraBold20 = new Font("Manrope ExtraBold", Font.PLAIN, 20);
	private Font _ManropeSemiBold15 = new Font("Manrope SemiBold", Font.PLAIN, 15);
	
	public RightComponents() {
		setBounds(340, 0, 885, 860);
		setLayout(null);
		
		add(new ReceiverUserPanel());
		add(new MessagePanel());
		add(new InputMessagePanel());
		
	}
	
	class ReceiverUserPanel extends JPanel {
		private JLabel lblReceiverImage;
		private Image avtURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\0.jpg");
		private Image avt = avtURL.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
		private ImageLoader avtImg;
		
		public ReceiverUserPanel() {
			setLayout(null);
			setBounds(0, 0, 885, 100);
			setBackground(_primaryWhite);
//			setBorder(new MatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 51)));
//			add(userPanel);
			
			avtImg = new ImageLoader(avt);
			avtImg.setBounds(35, 26, 50, 50);
			avtImg.setBorder(new RoundedBorder(new Color(175, 187, 247), 2, 100));
			add(avtImg);
			
			lblReceiverImage = new JLabel();
			lblReceiverImage.setText("<html><font color='#000000'>TestUser</font></html>");
			lblReceiverImage.setBounds(105, 40, 230, 20);
			lblReceiverImage.setFont(_ManropeExtraBold20);
			add(lblReceiverImage);
		}
	}
	
	class MessagePanel extends JPanel {
		
		public MessagePanel() {
//			setBounds();
			
		}
	}
	
	class InputMessagePanel extends JPanel {
		private JTextField inputMessage;
		
		private JButton btnSendMessage;
		private JLabel lblSendIcon;
		private Image sendIconURL = Toolkit.getDefaultToolkit().getImage("src\\Image\\send_white.png");
		private ImageIcon sendIcon = new ImageIcon(sendIconURL);
		
		
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
			inputMessage.setBorder(new EmptyBorder(0, 20, 0, 0));
			inputMessage.setFont(_ManropeSemiBold15);
			inputMessage.setForeground(new Color(73, 133, 234));
			inputMessage.setText("Write a message...");
			inputMessage.addFocusListener(new FocusListener(){
			    @Override
			    public void focusGained(FocusEvent e) {
			        if (inputMessage.getText().equals("Write a message...")) {
			        	inputMessage.setText("");
			        }
			    }
			    @Override
			    public void focusLost(FocusEvent e) {
			        if (inputMessage.getText().isEmpty()) {
			        	inputMessage.setText("Write a message...");
			        }
			    }
			});
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
			
			lblSendIcon = new JLabel();
			lblSendIcon.setIcon(sendIcon);
//			lblSendIcon.setPreferredSize(new Dimension(45, 45));
			lblSendIcon.setBounds(10, 2, 45, 45);
			btnSendMessage.add(lblSendIcon);
		}
	}
	
}
