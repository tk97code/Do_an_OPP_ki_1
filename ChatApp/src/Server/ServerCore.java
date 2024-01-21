package Server;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Highlighter.HighlightPainter;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;

import ClientEvent.Event;
import Data.ClientData;
import Data.LoginData;
import Data.MessageData;
import Data.RegisterData;
import Data.UserAccountData;

public class ServerCore {
	
	private static int port;
	private SocketIOServer server;
	private static JTextPane logArea;
	private static ServerCore instance;
	private DBService dbService;
	private List<ClientData> listClient;
	private boolean isRunning = false;
	
	
	
	ServerCore(int port, JTextPane logArea) {
		this.port = port;
		Configuration config = new Configuration();
		this.logArea = logArea;
		config.setPort(port);
		server = new SocketIOServer(config);
		listClient = new ArrayList<>();
	}
	
	public static ServerCore getInstance() {
		if (instance == null) {
			instance = new ServerCore(port, logArea);
		}
		return instance;
	}

	public void startServer() {
		dbService = new DBService();
		
		
		server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
            	logArea.setForeground(Color.white);
            	appendLog("A client connect: " + client.getSessionId() + "\n", Color.white);
            }
        });
		
		
		server.addEventListener("register", RegisterData.class, new DataListener<RegisterData>() {
			@Override
			public void onData(SocketIOClient client, RegisterData data, AckRequest req) throws Exception {
				// TODO Auto-generated method stub
				MessageData message = dbService.register(data);
				req.sendAckData(message.isAction(), message.getMessage(), message.getData());
				if (message.isAction()) {
                    appendLog("Client " + client.getSessionId() + " registered successful \n", Color.white);
                    server.getBroadcastOperations().sendEvent("list_user", (UserAccountData) message.getData());
                }
			}
		});
		
		server.addEventListener("login", LoginData.class, new DataListener<LoginData>() {
			@Override
			public void onData(SocketIOClient client, LoginData data, AckRequest req) throws Exception {
//				System.out.println(client.getSessionId());
				UserAccountData accountData = null;
				try {
					accountData = dbService.login(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (accountData != null) {
                    req.sendAckData(true, accountData);
                    addClient(client, accountData);
                    appendLog("Client " + client.getSessionId() + " logged in successful \n", Color.white);
                    userConnect(accountData.getUserID());
                } else {
                    req.sendAckData(false);
                }
			}
		});
		
		server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient client, Integer userReqID, AckRequest req) throws Exception {
                try {
                    List<UserAccountData> list = dbService.getOnlineUser(userReqID);
                    client.sendEvent("list_user", list.toArray());
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        });
		
		server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				appendLog("Client " + client.getSessionId() + " disconnected \n", Color.yellow);
				int userID = removeClient(client);
                if (userID != 0) {
                    userDisconnect(userID);
                }
			}
		});
		
		server.start();
		logArea.setForeground(Color.green);
		appendLog("SERVER IS STARTED \n", Color.green);
	}
	
	public void stopServer() {
		server.stop();
		logArea.setForeground(Color.red);
		appendLog("SERVER IS STOPPED \n", Color.red);
	}
	
	public void addClient(SocketIOClient client, UserAccountData user) {
		listClient.add(new ClientData(client, user));
	}
	
	public int removeClient(SocketIOClient client) {
        for (ClientData d : listClient) {
            if (d.getClient() == client) {
                listClient.remove(d);
                return d.getUser().getUserID();
            }
        }
        return 0;
    }
	
	private void userConnect(int userID) {
		server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }

    private void userDisconnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }
	
	public void appendLog(String s, Color c) {
		StyledDocument doc = logArea.getStyledDocument();
        Style style = logArea.addStyle("", null);
        StyleConstants.setForeground(style, c);
        try {
        	doc.insertString(doc.getLength(), s + "\n", style);
        } catch (BadLocationException e1) {
        	// TODO Auto-generated catch block
        	e1.printStackTrace();
        }
	}
	
	public List<ClientData> getListClient() {
        return listClient;
    }
	
}