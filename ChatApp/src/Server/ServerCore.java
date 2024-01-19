package Server;

import java.awt.Color;
import java.sql.SQLException;
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
import com.corundumstudio.socketio.listener.EventInterceptor;
import com.corundumstudio.socketio.transport.NamespaceClient;

import Data.LoginData;
import Data.MessageData;
import Data.RegisterData;
import Data.UserAccountData;

public class ServerCore {
	
	private int port;
	private SocketIOServer server;
	private JTextPane logArea;
	private static ServerCore instance;
	private DBService dbService;
	private boolean isRunning = false;
	
	ServerCore(int port, JTextPane logArea) {
		this.port = port;
		Configuration config = new Configuration();
		this.logArea = logArea;
		config.setPort(port);
		server = new SocketIOServer(config);
	}
	
	public ServerCore getInstance() {
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
//                    server.getBroadcastOperations().sendEvent("list_user", (UserAccountData) message.getData());
                }
			}
		});
		
		server.addEventListener("login", LoginData.class, new DataListener<LoginData>() {
			@Override
			public void onData(SocketIOClient client, LoginData data, AckRequest req) throws Exception {
				System.out.println(client.getSessionId());
				UserAccountData accountData = null;
				try {
					accountData = dbService.login(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (accountData != null) {
                    req.sendAckData(true, accountData);
                    appendLog("Client " + client.getSessionId() + " logged in successful \n", Color.white);
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
                    for (UserAccountData u: list) {
                    	System.out.println(u.getEmail());
                    }
                } catch (SQLException e) {
                    System.err.println(e);
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
	
}