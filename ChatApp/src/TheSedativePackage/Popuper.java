package TheSedativePackage;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;

import popup.ssn.NotificationPopup;

public class Popuper {
	
	private static Popuper instance;
	
	public static Popuper getInstance() {
		if (instance == null) {
			instance = new Popuper();
		}
		return instance;
	}
	
	public void setPopup(ImageIcon ic, String s, int w, int h, int displayTime) {
		NotificationPopup nf = new NotificationPopup(s);
		nf.setFont(new Font("SVN-Poppins", Font.PLAIN, 15));
		nf.setIcon(ic);
		nf.setWIDTH(w);
        nf.setHEIGHT(h);
        nf.setDisplayTime(displayTime);
        nf.setBackgroundColor1(Color.white);
        nf.setBackGroundColor2(Color.white);
        nf.setForegroundColor(Color.black);	
        nf.display();
	}
	
	public void setPopup(ImageIcon ic, String s, int w, int h, int displayTime, Color color1, Color color2, Color foreColor) {
		NotificationPopup nf = new NotificationPopup(s);
		nf.setFont(new Font("SVN-Poppins", Font.PLAIN, 15));
		nf.setIcon(ic);
		nf.setWIDTH(w);
        nf.setHEIGHT(h);
        nf.setDisplayTime(displayTime);
        nf.setBackgroundColor1(color1);
        nf.setBackGroundColor2(color2);
        nf.setForegroundColor(foreColor);	
        nf.display();
	}
	
}
