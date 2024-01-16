package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }


    public void connectToDatabase() throws SQLException {
        String ip = "localhost";
        String port = "3306";
        String database = "chatapp";
        String userName = "root";
        String password = "Krisktan5436*****";
        connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, userName, password);
        System.out.println("ConnectToDatabase" + connection);
    }

    public Connection getConnection() {
    	System.out.println("getConnection()"+this.connection);
        return this.connection;
    }
    
    public void closeConnection() throws SQLException {
    	connection.close();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
