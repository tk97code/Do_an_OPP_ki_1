package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private String INSERT_USER_ACCOUNT = "INSERT INTO users_account(userID, username, email) VALUES (?, ?, ?)";
    private String SELECT_ONLINE_USER = "SELECT userID, username, email, imageString FROM users_account WHERE status='1' AND UserID<>?";
	
	public DBService() {
		this.con = DBConnection.getInstance().getConnection();
	}
	
	public MessageData register(RegisterData data) {
		MessageData message = new MessageData();
		try {
            PreparedStatement stmt = con.prepareStatement(CHECK_EMAIL);
            stmt.setString(1, data.getEmail());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                message.setAction(false);
                message.setMessage("Email Already Exist");
            } else {
//            	stmt = con.prepareStatement(CHECK_EMAIL);
//            	stmt.setString(1, data.getEmail());
//            	resultSet = stmt.executeQuery();
//            	if (resultSet.next()) {
//                    message.setAction(false);
//                    message.setMessage("Email Already Exist");
//                } else {
//                	message.setAction(true);
//                }
            	message.setAction(true);
            }
            resultSet.close();
            stmt.close();
            if (message.isAction()) {
                //  Insert User Register
            	con.setAutoCommit(false);
            	stmt = con.prepareStatement(INSERT_USER, stmt.RETURN_GENERATED_KEYS);
            	stmt.setString(1, data.getUserName());
            	stmt.setString(2, data.getEmail());
            	stmt.setString(3, data.getPassword());
            	stmt.executeUpdate();
            	resultSet = stmt.getGeneratedKeys(); //get key auto generated
                resultSet.first();
                int userID = resultSet.getInt(1);
                resultSet.close();
                stmt.close();
                
                // Create user account
                stmt = con.prepareStatement(INSERT_USER_ACCOUNT);
                stmt.setInt(1, userID);
                stmt.setString(2, data.getUserName());
                stmt.setString(3, data.getEmail());
                stmt.execute();
                stmt.close();
                con.commit();
                con.setAutoCommit(true);
                message.setAction(true);
                message.setMessage("Ok");
                message.setData(new UserAccountData(userID, data.getUserName(), data.getEmail(), "", true));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            message.setAction(false);
            message.setMessage("Server Error");
            try {
                if (con.getAutoCommit() == false) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException e1) {
            	e.printStackTrace();
            }
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
            String userName = resultSet.getString(2);
            String email = resultSet.getString(3);
            String image = resultSet.getString(4);
            data = new UserAccountData(userID, userName, email, image, true);
        }
        resultSet.close();
        stmt.close();
        return data;
    }
	
	public List<UserAccountData> getOnlineUser(int userReqID) throws SQLException {
		List<UserAccountData> list = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement(SELECT_ONLINE_USER);
        stmt.setInt(1, userReqID);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            int userID = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String email = resultSet.getString(3);
            String image = resultSet.getString(4);
//            System.out.println(email);
            list.add(new UserAccountData(userID, userName, email, image, true));
        }
        resultSet.close();
        stmt.close();
        return list;
	}
}
