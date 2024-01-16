package Data;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginData {
	private String email;
	private String pass;
	
	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String pass) {
        this.pass = pass;
    }

    public LoginData(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }
    
    public LoginData() {
    	
    }
    
    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", pass);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }

}
