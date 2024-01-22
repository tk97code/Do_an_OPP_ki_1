package ClientEvent;

import Data.ReceiveMessageData;
import Data.RequestChatData;
import Data.SendMessageData;
import Data.UserAccountData;

public interface EventChat {

    public void sendMessage(SendMessageData data);

    public void receiveMessage(ReceiveMessageData data);
    
    public void updateStatus(UserAccountData user);
   
}
