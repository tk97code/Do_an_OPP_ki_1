package Data;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterData {
	private String userName;
    private String password;
    private String email;
	
	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public RegisterData(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public RegisterData() {
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("userName", userName);
            json.put("password", password);
            json.put("email", email);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
