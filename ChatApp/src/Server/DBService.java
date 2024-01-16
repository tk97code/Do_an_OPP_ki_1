package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Data.LoginData;
import Data.MessageData;
import Data.RegisterData;
import Data.UserAccountData;

public class DBService {
	private Connection con;
	
	private String INSERT_USER = "INSERT INTO users (userName, email, pass) VALUES (?, ?, ?)";
    private String CHECK_USER = "SELECT userID FROM users WHERE userName = ? LIMIT 1";
    private String CHECK_EMAIL = "SELECT userID FROM users WHERE email = ? LIMIT 1";
    private String LOGIN = "SELECT UserID, users_account.UserName, users_account.email, imageString FROM users JOIN users_account USING (UserID) WHERE users.email=BINARY(?) AND users.pass=BINARY(?) AND users_account.status='1'";
	
	public DBService() {
		this.con = DBConnection.getInstance().getConnection();
	}
	
	public MessageData register(RegisterData data) {
		MessageData message = new MessageData();
		try {
            PreparedStatement stmt = con.prepareStatement(CHECK_USER);
            stmt.setString(1, data.getUserName());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                message.setAction(false);
                message.setMessage("User Already Exist");
            } else {
            	stmt = con.prepareStatement(CHECK_EMAIL);
            	stmt.setString(1, data.getEmail());
            	resultSet = stmt.executeQuery();
            	if (resultSet.next()) {
                    message.setAction(false);
                    message.setMessage("Email Already Exist");
                } else {
                	message.setAction(true);
                }
            }
            resultSet.close();
            stmt.close();
            if (message.isAction()) {
                //  Insert User Register
            	stmt = con.prepareStatement(INSERT_USER);
            	stmt.setString(1, data.getUserName());
            	stmt.setString(2, data.getEmail());
            	stmt.setString(3, data.getPassword());
            	stmt.execute();
            	stmt.close();
                message.setAction(true);
                message.setMessage("Ok");
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            message.setAction(false);
            message.setMessage("Server Error");
        }
		
		return message;
	}
	
	public UserAccountData login(LoginData loginData) throws SQLException {
		UserAccountData data = null;
        PreparedStatement stmt = con.prepareStatement(LOGIN);
        stmt.setString(1, loginData.getEmail());
        stmt.setString(2, loginData.getPassword());
        ResultSet resultSet = stmt.executeQuery();
//        System.out.println(resultSet.getInt(1));
        if (resultSet.next()) {
            int userID = resultSet.getInt(1);
            String email = resultSet.getString(2);
            String userName = resultSet.getString(3);
            String image = resultSet.getString(4);
            data = new UserAccountData(userID, userName, email, image, true);
        }
        System.out.println(data.toString());
        resultSet.close();
        stmt.close();
        return data;
    }
}
