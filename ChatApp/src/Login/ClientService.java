package Login;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import Client.LeftComponents;
import Client.RightComponents;
import Data.ListUsersAccountData;
import Data.ReceiveMessageData;
import Data.UserAccountData;
import io.socket.client.*;
import io.socket.emitter.Emitter;
//import Event.Event;
import ClientEvent.*;

public class ClientService {
	
	private int port = 1234;
	private static ClientService instance;
	private static Socket client;
	private String ip = "localhost";
	private static UserAccountData user;
	private RightComponents right;
	
	public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }
	
	public Socket getClient() {
		return client;
	}
	
	public void startServer() {
        try {
            client = IO.socket("http://" + ip + ":" + port);
            
            Event.getInstance().addEventUserStatus(new EventUserStatus() {
				
				@Override
				public void userDisconnect(int userId) {
					System.out.println("Offline " + userId);
					for (UserAccountData u : ListUsersAccountData.getInstance().getList()) {
	                    if (u.getUserID() == userId) {
	                        u.setStatus(false);
//	                        RightComponents.getInstance().updateOnline();
	                        break;
	                    }
	                }
//					RightComponents.getInstance().updateOffline();
				}
				
				@Override
				public void userConnect(int userId) {
					for (UserAccountData u: ListUsersAccountData.getInstance().getList()) {
						if (u.getUserID() == userId) {
	                        u.setStatus(true);
	                        System.out.println("Online " + userId);
	                        break;
	                    }
					}
				}
			});
            
            client.on("list_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    //  list user
                    List<UserAccountData> users = new ArrayList<>();
                    for (Object o : os) {
                    	UserAccountData u = new UserAccountData(o);
//                    	System.out.println(o);
                    	try {
                    		if (u.getUserID() != user.getUserID()) {
    						    users.add(u);
    						}
                    	} catch (Exception e) {
                    		
                    	}
                    }
                    
                    try {
                    	ClientEvent.Event.getInstance().getEventMenuLeft().listUser(users);
                    } catch (Exception e) {
                    	System.out.println("User doen't log in");
                    }
                    
                }
            });
            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer) os[0];
                    boolean status = (Boolean) os[1];
                    if (status) {
//                    	System.out.println(userID);
                        ClientEvent.Event.getInstance().getEventUserStatus().userConnect(userID);
                    } else {
//                    	System.out.println(userID);
                        ClientEvent.Event.getInstance().getEventUserStatus().userDisconnect(userID);
                    }
                }
            });
            
            client.on("receive_ms", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    ReceiveMessageData message = new ReceiveMessageData(os[0]);
                    Event.getInstance().getEventChat().receiveMessage(message);
                }
            });
            client.open();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

	public UserAccountData getUser() {
        return user;
    }
	
	public void setUser(UserAccountData user) {
		this.user = user;
	}
}
