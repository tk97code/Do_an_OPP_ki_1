package Data;

import com.corundumstudio.socketio.SocketIOClient;

public class ClientData {
	private SocketIOClient client;
    private UserAccountData user;
	
	public SocketIOClient getClient() {
        return client;
    }

    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    public UserAccountData getUser() {
        return user;
    }

    public void setUser(UserAccountData user) {
        this.user = user;
    }

    public ClientData(SocketIOClient client, UserAccountData user) {
        this.client = client;
        this.user = user;
    }

    public ClientData() {
    }
}
