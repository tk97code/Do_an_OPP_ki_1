package Client;

import javax.swing.SwingUtilities;

import Login.LoginFrame;
import Login.LoginService;

public class MainClass {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        LoginFrame loginFrame = new LoginFrame();
//		        LoginService.getInstance().startServer();
		        loginFrame.setVisible(true);
		    }
		});
	}
		
}