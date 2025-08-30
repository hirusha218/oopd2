
package dao;


import dto.UserLoginDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class UserDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean validateLogin(UserLoginDTO loginDTO) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password_hash = ? AND status = 'Active' AND role_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, loginDTO.getUsername());
            pstmt.setString(2, loginDTO.getPassword()); 
            pstmt.setInt(3,   loginDTO.getRoleId());                    
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); 
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT user_id, username, password_hash, staff_id, role_id, status FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setStaffId(rs.getObject("staff_id") != null ? rs.getInt("staff_id") : null);
                user.setRoleId(rs.getInt("role_id"));
                user.setStatus(rs.getString("status"));
                return user;
            }
        }
        return null;
    }

    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, staff_id, role_id, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash()); // store as plain text
            if (user.getStaffId() != null) {
                pstmt.setInt(3, user.getStaffId());
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.setInt(4, user.getRoleId());
            pstmt.setString(5, user.getStatus());
            return pstmt.executeUpdate() > 0;
        }
    }
}


