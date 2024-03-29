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
import Data.RequestChatData;
import Data.SendMessageData;
import Data.UserAccountData;

public class DBService {
	private Connection con;
	
	private String INSERT_USER = "INSERT INTO users (userName, email, pass) VALUES (?, ?, ?)";
	private String INSERT_USER_ACCOUNT = "INSERT INTO users_account(userID, username, email) VALUES (?, ?, ?)";
	private String SAVE_TO_DB = "INSERT INTO messages(fromUserId, toUserId, msg) VALUES (?, ?, ?)";
	
    private String CHECK_USER = "SELECT userID FROM users WHERE userName = ? LIMIT 1";
    private String CHECK_EMAIL = "SELECT userID FROM users WHERE email = ? LIMIT 1";
    private String LOGIN = "SELECT UserID, users_account.UserName, users_account.email, imageString, status FROM users JOIN users_account USING (UserID) WHERE users.email=BINARY(?) AND users.pass=BINARY(?)";
    private String SELECT_USERS = "SELECT userID, username, email, imageString, status FROM users_account WHERE UserID<>?";
	private String SELECT_MESSAGE = "SELECT * FROM messages WHERE ? IN (fromUserId, toUserId) AND ? IN (fromUserId, toUserId)";
	private String SELECT_ALL_USERS = "SELECT userID, username, email, imageString, status FROM users_account";
	
	private String UPDATE_STATUS = "UPDATE users_account SET status = ? WHERE userId = ?";
    
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
                message.setData(new UserAccountData(userID, data.getUserName(), data.getEmail(), "", false));
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
	
	public void saveToDB(SendMessageData msg) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(SAVE_TO_DB);
		stmt.setInt(1, msg.getFromUserID());
		stmt.setInt(2, msg.getToUserID());
		stmt.setString(3, msg.getText());
		stmt.execute();
		stmt.close();
	}
	
	public List<SendMessageData> getMessage(RequestChatData data) throws SQLException {
		List<SendMessageData> listMessage = new ArrayList<>();
		PreparedStatement stmt = con.prepareStatement(SELECT_MESSAGE);
		stmt.setInt(1, data.getFromUserId());
		stmt.setInt(2, data.getToUserId());
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
            int messageId = resultSet.getInt(1);
            int fromUserId = resultSet.getInt(2);
            int toUserId = resultSet.getInt(3);
            String message = resultSet.getString(4);
//            System.out.println(email);
            listMessage.add(new SendMessageData(fromUserId, toUserId, message));
        }
		return listMessage;
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
            Boolean status = resultSet.getBoolean(5);
            data = new UserAccountData(userID, userName, email, image, status);
            
            stmt = con.prepareStatement(UPDATE_STATUS);
            stmt.setBoolean(1, true);
            stmt.setInt(2, userID);
            stmt.execute();
        }
        resultSet.close();
        stmt.close();
        return data;
    }
	
	public void setDisconnect(int userId) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_STATUS);
        stmt.setBoolean(1, false);
        stmt.setInt(2, userId);
        stmt.execute();
	}
	
	public List<UserAccountData> getUsers(int userReqID) throws SQLException {
		List<UserAccountData> list = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement(SELECT_USERS);
        stmt.setInt(1, userReqID);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            int userID = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String email = resultSet.getString(3);
            String image = resultSet.getString(4);
            Boolean status = resultSet.getBoolean(5);
            System.out.println(email);
            list.add(new UserAccountData(userID, userName, email, image, status));
        }
        resultSet.close();
        stmt.close();
        return list;
	}
	
	public List<UserAccountData> getAllUsers() throws SQLException {
		List<UserAccountData> list = new ArrayList<>();
        PreparedStatement stmt = con.prepareStatement(SELECT_ALL_USERS);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            int userID = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String email = resultSet.getString(3);
            String image = resultSet.getString(4);
            Boolean status = resultSet.getBoolean(5);
            System.out.println(email);
            list.add(new UserAccountData(userID, userName, email, image, status));
        }
        resultSet.close();
        stmt.close();
        return list;
	}
}
