import java.sql.*;

public class DatabaseManager {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createUsersTable(String url, String user, String password) {
        URL = url;
        USER = user;
        PASSWORD = password;

        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "chat_id BIGINT UNIQUE NOT NULL, " +
                "username VARCHAR(255), " +
                "birthdate VARCHAR(255), " +
                "registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void addUser(Long chatId, String username, String birthdate) {
        String sql = "INSERT INTO users (chat_id, username, birthdate) " +
                "VALUES (?, ?, ?) ON CONFLICT (chat_id) DO NOTHING";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, chatId);
            pstmt.setString(2, username);
            pstmt.setString(3, birthdate);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    public boolean userExists(Long chatId) {
        String sql = "SELECT 1 FROM users WHERE chat_id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, chatId);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.out.println("Ошибка при проверке пользователя: " + e.getMessage());
            return false;
        }
    }
}
