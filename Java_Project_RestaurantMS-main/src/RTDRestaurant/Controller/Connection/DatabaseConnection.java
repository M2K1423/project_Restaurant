
package RTDRestaurant.Controller.Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

// Kết nối tới DataBase của hệ thống

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {

    }
    //Thực hiện kết nối tới Database
    public void connectToDatabase() throws SQLException {
        final String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; // ← đúng với Docker image
        final String username = "Phat"; // ← đúng với APP_USER
        final String password = "123";  // ← đúng với APP_USER_PASSWORD

        connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public static void main(String[] args) {
        try {
            DatabaseConnection db = DatabaseConnection.getInstance();
            db.connectToDatabase();
            System.out.println("Kết nối DB thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

