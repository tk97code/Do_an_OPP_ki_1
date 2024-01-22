package Data;

import org.json.JSONException;
import org.json.JSONObject;

public class SendMessageData {

	private int fromUserID;
    private int toUserID;
    private String text;
	
    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SendMessageData(int fromUserID, int toUserID, String text) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.text = text;
    }
    
    public SendMessageData(Object o) {
    	JSONObject obj = (JSONObject) o;
        try {
        	fromUserID = obj.getInt("fromUserID");
        	toUserID = obj.getInt("toUserID");
        	text = obj.getString("text");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public SendMessageData() {
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("toUserID", toUserID);
            json.put("text", text);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
