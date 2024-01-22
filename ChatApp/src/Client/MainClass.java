package Client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import Login.LoginFrame;
import Login.ClientService;

public class MainClass {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        ClientService.getInstance().startServer();
//		        ClientFrame clientFrame = new ClientFrame();
		        
		        LoginFrame loginFrame = new LoginFrame();
//		        clientFrame.setVisible(true);
		        loginFrame.setVisible(true);
		    }
		});
	}
		
}