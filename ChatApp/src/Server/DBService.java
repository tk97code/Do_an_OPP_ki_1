package Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Login.MessageData;
import Login.RegisterData;

public class DBService {
	private Connection con;
	
	private String INSERT_USER = "INSERT INTO users (userName, email, pass) VALUES (?, ?, ?)";
    private String CHECK_USER = "SELECT userID FROM users WHERE userName = ? LIMIT 1";
    private String CHECK_EMAIL = "SELECT userID FROM users WHERE email = ? LIMIT 1";
	
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
}
