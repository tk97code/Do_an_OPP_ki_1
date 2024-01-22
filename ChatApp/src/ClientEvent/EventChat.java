package ClientEvent;

import Data.ReceiveMessageData;
import Data.RequestChatData;
import Data.SendMessageData;

public interface EventChat {

    public void sendMessage(SendMessageData data);

    public void receiveMessage(ReceiveMessageData data);
    
//    public void loadChat(RequestChatData data);
}
