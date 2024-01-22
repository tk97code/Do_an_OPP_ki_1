package Data;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestChatData {
	private int fromUserId;
	private int toUserId;
	
	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	
	public RequestChatData(int fromUserId, int toUserId) {
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
	}
	
	public RequestChatData() {
		
	}
	
	public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fromUserId", fromUserId);
            json.put("toUserId", toUserId);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
