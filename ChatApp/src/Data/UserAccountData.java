package Data;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAccountData {
	
	private int userID;
    private String userName;
    private String email;
    private String image;
    private boolean status;
    
	public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserAccountData(int userID, String userName, String email, String image, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.image = image;
        this.status = status;
    }
    
    public UserAccountData(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            userID = obj.getInt("userID");
            userName = obj.getString("userName");
            email = obj.getString("email");
            image = obj.getString("image");
            status = obj.getBoolean("status");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public UserAccountData() {
    }

    
}
