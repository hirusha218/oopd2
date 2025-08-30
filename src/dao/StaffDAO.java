package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Staff;

/**
 * StaffDAO - Implements the DAO (Data Access Object) Pattern
 * 
 * This class encapsulates all database operations for Staff entities,
 * providing a clean separation between business logic and data access.
 * It handles CRUD operations, search functionality, and data validation
 * while abstracting the underlying database implementation details.
 */
public class StaffDAO {

    private final Connection connection;

    public StaffDAO(Connection connection) {
        this.connection = connection;
    }


    public boolean createStaff(Staff staff, String plainPassword) throws SQLException {

        if (isUsernameExists(staff.getUsername())) {
            throw new SQLException("Username already exists");
        }

        connection.setAutoCommit(false);
        try {
    
            String staffSql = "INSERT INTO staff (first_name, last_name, role_id, department, contact_number, email) VALUES (?, ?, ?, ?, ?, ?)";
            int staffId;
            try (PreparedStatement pstmt = connection.prepareStatement(staffSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, staff.getFirstName());
                pstmt.setString(2, staff.getLastName());
                pstmt.setInt(3, staff.getRoleId());
                pstmt.setString(4, staff.getDepartment());
                pstmt.setString(5, staff.getContactNumber());
                pstmt.setString(6, staff.getEmail());
                pstmt.executeUpdate();
                
                ResultSet rs = pstmt.getGeneratedKeys();
                if (!rs.next()) {
                    throw new SQLException("Failed to retrieve staff_id");
                }
                staffId = rs.getInt(1);
            }


            String userSql = "INSERT INTO users (username, password_hash, staff_id, role_id, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(userSql)) {
                pstmt.setString(1, staff.getUsername());
                pstmt.setString(2, hashPassword(plainPassword));
                pstmt.setInt(3, staffId);
                pstmt.setInt(4, staff.getRoleId());
                pstmt.setString(5, staff.getStatus());
                pstmt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Get all staff members
    public List<Staff> getAllStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT s.staff_id, s.first_name, s.last_name, s.role_id, s.department, " +
                     "s.contact_number, s.email, u.username, u.status, r.role_name " +
                     "FROM staff s " +
                     "JOIN users u ON s.staff_id = u.staff_id " +
                     "JOIN roles r ON s.role_id = r.role_id " +
                     "ORDER BY s.staff_id";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setFirstName(rs.getString("first_name"));
                staff.setLastName(rs.getString("last_name"));
                staff.setRoleId(rs.getInt("role_id"));
                staff.setDepartment(rs.getString("department"));
                staff.setContactNumber(rs.getString("contact_number"));
                staff.setEmail(rs.getString("email"));
                staff.setUsername(rs.getString("username"));
                staff.setStatus(rs.getString("status"));
                staff.setRoleName(rs.getString("role_name"));
                staffList.add(staff);
            }
        }
        return staffList;
    }

    // Get staff by ID
    public Staff getStaffById(int staffId) throws SQLException {
        String sql = "SELECT s.staff_id, s.first_name, s.last_name, s.role_id, s.department, " +
                     "s.contact_number, s.email, u.username, u.status, r.role_name " +
                     "FROM staff s " +
                     "JOIN users u ON s.staff_id = u.staff_id " +
                     "JOIN roles r ON s.role_id = r.role_id " +
                     "WHERE s.staff_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, staffId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff();
                    staff.setStaffId(rs.getInt("staff_id"));
                    staff.setFirstName(rs.getString("first_name"));
                    staff.setLastName(rs.getString("last_name"));
                    staff.setRoleId(rs.getInt("role_id"));
                    staff.setDepartment(rs.getString("department"));
                    staff.setContactNumber(rs.getString("contact_number"));
                    staff.setEmail(rs.getString("email"));
                    staff.setUsername(rs.getString("username"));
                    staff.setStatus(rs.getString("status"));
                    staff.setRoleName(rs.getString("role_name"));
                    return staff;
                }
            }
        }
        return null;
    }

    // Get staff by username
    public Staff getStaffByUsername(String username) throws SQLException {
        String sql = "SELECT s.staff_id, s.first_name, s.last_name, s.role_id, s.department, " +
                     "s.contact_number, s.email, u.username, u.status, r.role_name " +
                     "FROM staff s " +
                     "JOIN users u ON s.staff_id = u.staff_id " +
                     "JOIN roles r ON s.role_id = r.role_id " +
                     "WHERE u.username = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Staff staff = new Staff();
                    staff.setStaffId(rs.getInt("staff_id"));
                    staff.setFirstName(rs.getString("first_name"));
                    staff.setLastName(rs.getString("last_name"));
                    staff.setRoleId(rs.getInt("role_id"));
                    staff.setDepartment(rs.getString("department"));
                    staff.setContactNumber(rs.getString("contact_number"));
                    staff.setEmail(rs.getString("email"));
                    staff.setUsername(rs.getString("username"));
                    staff.setStatus(rs.getString("status"));
                    staff.setRoleName(rs.getString("role_name"));
                    return staff;
                }
            }
        }
        return null;
    }

    // Update staff member
    public boolean updateStaff(Staff staff, String plainPassword) throws SQLException {

        if (isUsernameExistsForOtherStaff(staff.getUsername(), staff.getStaffId())) {
            throw new SQLException("Username already exists for another staff member");
        }

        // Start transaction
        connection.setAutoCommit(false);
        try {

            String staffSql = "UPDATE staff SET first_name = ?, last_name = ?, role_id = ?, " +
                             "department = ?, contact_number = ?, email = ? WHERE staff_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(staffSql)) {
                pstmt.setString(1, staff.getFirstName());
                pstmt.setString(2, staff.getLastName());
                pstmt.setInt(3, staff.getRoleId());
                pstmt.setString(4, staff.getDepartment());
                pstmt.setString(5, staff.getContactNumber());
                pstmt.setString(6, staff.getEmail());
                pstmt.setInt(7, staff.getStaffId());
                pstmt.executeUpdate();
            }


            String userSql = "UPDATE users SET username = ?, role_id = ?, status = ? WHERE staff_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(userSql)) {
                pstmt.setString(1, staff.getUsername());
                pstmt.setInt(2, staff.getRoleId());
                pstmt.setString(3, staff.getStatus());
                pstmt.setInt(4, staff.getStaffId());
                pstmt.executeUpdate();
            }


            if (plainPassword != null && !plainPassword.isEmpty()) {
                String passwordSql = "UPDATE users SET password_hash = ? WHERE staff_id = ?";
                try (PreparedStatement pstmt = connection.prepareStatement(passwordSql)) {
                    pstmt.setString(1, hashPassword(plainPassword));
                    pstmt.setInt(2, staff.getStaffId());
                    pstmt.executeUpdate();
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Delete staff member
    public boolean deleteStaff(int staffId) throws SQLException {
        // Start transaction
        connection.setAutoCommit(false);
        try {
            // Delete from users table first (due to foreign key constraint)
            String userSql = "DELETE FROM users WHERE staff_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(userSql)) {
                pstmt.setInt(1, staffId);
                pstmt.executeUpdate();
            }

            // Delete from staff table
            String staffSql = "DELETE FROM staff WHERE staff_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(staffSql)) {
                pstmt.setInt(1, staffId);
                boolean success = pstmt.executeUpdate() > 0;
                connection.commit();
                return success;
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Search staff by name, role, or department
    public List<Staff> searchStaff(String searchTerm) throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT s.staff_id, s.first_name, s.last_name, s.role_id, s.department, " +
                     "s.contact_number, s.email, u.username, u.status, r.role_name " +
                     "FROM staff s " +
                     "JOIN users u ON s.staff_id = u.staff_id " +
                     "JOIN roles r ON s.role_id = r.role_id " +
                     "WHERE s.first_name LIKE ? OR s.last_name LIKE ? OR r.role_name LIKE ? OR s.department LIKE ? " +
                     "ORDER BY s.staff_id";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setStaffId(rs.getInt("staff_id"));
                    staff.setFirstName(rs.getString("first_name"));
                    staff.setLastName(rs.getString("last_name"));
                    staff.setRoleId(rs.getInt("role_id"));
                    staff.setDepartment(rs.getString("department"));
                    staff.setContactNumber(rs.getString("contact_number"));
                    staff.setEmail(rs.getString("email"));
                    staff.setUsername(rs.getString("username"));
                    staff.setStatus(rs.getString("status"));
                    staff.setRoleName(rs.getString("role_name"));
                    staffList.add(staff);
                }
            }
        }
        return staffList;
    }

    // Check if username already exists
    public boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Check if username exists for other staff
    private boolean isUsernameExistsForOtherStaff(String username, int staffId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND staff_id != ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, staffId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Get all available roles
    public List<String> getAllRoles() throws SQLException {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT role_name FROM roles ORDER BY role_name";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                roles.add(rs.getString("role_name"));
            }
        }
        return roles;
    }

    // Get role ID by role name
    public int getRoleIdByName(String roleName) throws SQLException {
        String sql = "SELECT role_id FROM roles WHERE role_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, roleName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("role_id");
                }
            }
        }
        return -1;
    }

    // Simple password hashing (in production, use BCrypt or similar)
    private String hashPassword(String plainPassword) {
        // This is a simple hash for demonstration
        // In production, use BCrypt.hashpw(plainPassword, BCrypt.gensalt())
        return plainPassword; // For now, just return as-is
    }

    public boolean isEmailExists(String email) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}