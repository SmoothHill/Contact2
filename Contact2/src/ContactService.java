import java.sql.*;

public class ContactService {
    private static final String URL = "jdbc:mysql://localhost:3306/contacts?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=yes&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("MySQL JDBC Driver not found.");
        }
    }

    public String addContact(String name, String address, String phone) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO man (name, address, phone) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setInt(3, Integer.parseInt(phone)); // 假设电话是整数
            stmt.executeUpdate();
            return "联系人添加成功";
        } catch (SQLException e) {
            return "添加联系人失败: " + e.getMessage();
        }
    }

    public String modifyContact(String name, String address, String phone) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE man SET address = ?, phone = ? WHERE name = ?")) {
            stmt.setString(1, address);
            stmt.setInt(2, Integer.parseInt(phone)); // 假设电话是整数
            stmt.setString(3, name);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "联系人信息已更改" : "未找到联系人";
        } catch (SQLException e) {
            return "更改联系人失败: " + e.getMessage();
        }
    }

    public String deleteContact(String name) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM man WHERE name = ?")) {
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "联系人已删除" : "未找到联系人";
        } catch (SQLException e) {
            return "删除联系人失败: " + e.getMessage();
        }
    }

    public ResultSet viewContacts() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM man");
    }
}
