package RTDRestaurant.Controller.Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

// Kết nối tới cơ sở dữ liệu của hệ thống

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    // Đảm bảo chỉ có một instance của DatabaseConnection
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {
        // Private constructor để ngăn tạo đối tượng ngoài lớp
    }

    // Kết nối tới Database
    public void connectToDatabase() throws SQLException {
        final String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; // Đảm bảo URL đúng
        final String username = "Phat"; // Tên người dùng Oracle của bạn
        final String password = "123";  // Mật khẩu người dùng

        // Cố gắng kết nối tới cơ sở dữ liệu Oracle
        connection = DriverManager.getConnection(url, username, password);
    }

    // Kiểm tra xem kết nối đã được thiết lập chưa
    public Connection getConnection() {
        if (connection == null) {
            try {
                // Nếu chưa có kết nối, gọi lại phương thức kết nối
                connectToDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;  // Nếu kết nối không thành công, trả về null
            }
        }
        return connection;
    }

    // Đảm bảo kết nối được đóng khi không sử dụng
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;  // Đảm bảo kết nối được đóng
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức main để kiểm tra kết nối
    public static void main(String[] args) {
        try {
            DatabaseConnection db = DatabaseConnection.getInstance();  // Lấy đối tượng duy nhất
            db.connectToDatabase();  // Kết nối tới database
            System.out.println("Kết nối DB thành công!");

            // Đảm bảo lấy kết nối thành công
            Connection con = db.getConnection();
            if (con != null) {
                System.out.println("Kết nối cơ sở dữ liệu đã được thiết lập.");
            }

            // Đóng kết nối sau khi sử dụng
            db.closeConnection();

        } catch (SQLException e) {
            System.out.println("Không thể kết nối cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
