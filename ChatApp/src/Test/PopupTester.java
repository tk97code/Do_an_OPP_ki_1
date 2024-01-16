package Test;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import popup.ssn.NotificationPopup;

/**
 *
 * @author User
 */
public class PopupTester extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	PopupTester frame = new PopupTester();
    	frame.setBounds(0, 0, 1000, 700);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
        for (int i = 0; i < 3; i++) {
            NotificationPopup nf = new NotificationPopup(" Seudo databases \n has been updated \n Time: 12:27:00 23/08/2015 ");
            nf.setWIDTH(400);
            nf.setHEIGHT(150);
            nf.setDisplayTime(2000);
            nf.setBackgroundColor1(Color.WHITE);
            nf.setBackGroundColor2(Color.LIGHT_GRAY);
            nf.setForegroundColor(Color.darkGray);	
            nf.display();
//            frame.add(nf);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PopupTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        

    }
}
