package Client;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Data.UserAccountData;
import TheSedativePackage.ImageLoader;
import TheSedativePackage.RoundedBorder;

public class AccountComponent extends JPanel {
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
	
	public AccountComponent(String userName, UserAccountData toUser) {
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
