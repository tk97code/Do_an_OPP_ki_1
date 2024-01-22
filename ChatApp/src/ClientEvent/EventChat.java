package ClientEvent;

import Data.ReceiveMessageData;
import Data.SendMessageData;

public interface EventChat {

    public void sendMessage(SendMessageData data);

    public void receiveMessage(ReceiveMessageData data);
    
}
