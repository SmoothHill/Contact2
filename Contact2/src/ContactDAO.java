import java.sql.*;

public class ContactDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/contacts?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=yes&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addContact(String name, String address, String phone) throws SQLException {
        String query = "INSERT INTO man (name, address, phone) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.executeUpdate();
        }
    }

    public void modifyContact(String name, String address, String phone) throws SQLException {
        String query = "UPDATE man SET address = ?, phone = ? WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, address);
            stmt.setString(2, phone);
            stmt.setString(3, name);
            stmt.executeUpdate();
        }
    }

    public void deleteContact(String name) throws SQLException {
        String query = "DELETE FROM man WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public ResultSet viewContacts() throws SQLException {
        String query = "SELECT * FROM man";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
}
