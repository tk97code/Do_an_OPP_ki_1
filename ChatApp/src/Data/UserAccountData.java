package Data;

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

    public UserAccountData() {
    }

    
}
