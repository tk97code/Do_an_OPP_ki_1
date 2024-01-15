package Login;

import java.net.URISyntaxException;

import io.socket.client.*;

public class LoginService {
	
	private int port = 1234;
	private static LoginService instance;
	private Socket client;
	private String ip = "localhost";
	
	public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }
	
	public Socket getClient() {
		return client;
	}
	
	public void startServer() {
        try {
            client = IO.socket("http://" + ip + ":" + port);
            client.open();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
