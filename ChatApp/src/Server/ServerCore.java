package Server;

import java.util.List;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;

import Login.MessageData;
import Login.RegisterData;

public class ServerCore {
	
	private int port;
	private SocketIOServer server;
	private static ServerCore instance;
	private boolean isRunning = false;
	
	ServerCore(int port) {
		this.port = port;
		Configuration config = new Configuration();
		config.setPort(port);
		server = new SocketIOServer(config);
	}
	
	public ServerCore getInstance() {
		if (instance == null) {
			instance = new ServerCore(port);
		}
		return instance;
	}

	public void startServer() {
		
		server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                System.out.println("A Client connected");
            }
        });
		
		
		server.addEventListener("register", RegisterData.class, new DataListener<RegisterData>() {
			@Override
			public void onData(SocketIOClient client, RegisterData data, AckRequest sendRes) throws Exception {
				// TODO Auto-generated method stub
				MessageData message = new DBService().register(data);
				sendRes.sendAckData(message.isAction(), message.getMessage());
			}
		});
		
		server.start();
		System.out.println("Server is started");
	}
	
	public void stopServer() {
		server.stop();
		System.out.println("Server is stopped");
	}
	
}