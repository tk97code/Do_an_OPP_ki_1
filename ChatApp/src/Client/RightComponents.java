package Client;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import TheSedativePackage.ImageLoader;
import TheSedativePackage.RoundedBorder;

import java.awt.*;

public class RightComponents extends JPanel {

	/* Primary Color */
	private Color _primaryWhite = new Color(248, 250, 255);
	private Color _primaryLightBlue = new Color(163, 192, 240);
	
	private Font _ManropeExtraBold20 = new Font("Manrope ExtraBold", Font.PLAIN, 20);
	
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
			setBorder(new MatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 51)));
//			add(userPanel);
			
			avtImg = new ImageLoader(avt);
			avtImg.setBounds(35, 22, 55, 55);
			avtImg.setBorder(new RoundedBorder(new Color(175, 187, 247), 2, 100));
			add(avtImg);
			
			lblReceiverImage = new JLabel();
			lblReceiverImage.setText("<html><font color='#000000'>TestUser</font></html>");
			lblReceiverImage.setBounds(105, 32, 230, 38);
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
		
	}
	
}
