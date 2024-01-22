package Data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ListUsersAccountData {

	private List<UserAccountData> listAccount;
	private static ListUsersAccountData instance;
	
	public static ListUsersAccountData getInstance() {
		if (instance == null) {
			instance = new ListUsersAccountData();
		}
		return instance;
	}
	
	public void add(UserAccountData data) {
		listAccount.add(data);
	}
	
	public List<UserAccountData> getList() {
		return listAccount;
	}
	
	private ListUsersAccountData() {
		listAccount = new ArrayList<>();
	}
	
	public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("list", listAccount);
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
	
}
