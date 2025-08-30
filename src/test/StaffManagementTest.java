package test;

import dao.StaffDAO;
import model.Staff;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Test class for Staff Management functionality
 * @author Hiruw
 */
public class StaffManagementTest {
    
    public static void main(String[] args) {
        System.out.println("Testing Staff Management System...");
        
        try {
            // Get database connection
            Connection connection = DBConnection.getConnection();
            System.out.println("Database connection successful!");
            
            // Create StaffDAO instance
            StaffDAO staffDAO = new StaffDAO(connection);
            System.out.println("StaffDAO created successfully!");
            
            // Test getting all roles
            List<String> roles = staffDAO.getAllRoles();
            System.out.println("Available roles: " + roles);
            
            // Test creating a new staff member
            Staff testStaff = new Staff();
            testStaff.setFirstName("Test");
            testStaff.setLastName("User");
            testStaff.setRoleId(2); // Doctor role
            testStaff.setContactNumber("+1234567890");
            testStaff.setUsername("testuser");
            testStaff.setPassword("testpass");
            testStaff.setDepartment("Test Department");
            testStaff.setEmail("test@healthlink360.com");
            testStaff.setStatus("Active");
            
            System.out.println("Test staff object created: " + testStaff.getFullName());
            
            // Test username existence check
            boolean usernameExists = staffDAO.isUsernameExists("testuser");
            System.out.println("Username 'testuser' exists: " + usernameExists);
            
            // Test getting all staff
            List<Staff> allStaff = staffDAO.getAllStaff();
            System.out.println("Total staff members: " + allStaff.size());
            
            // Display all staff members
            for (Staff staff : allStaff) {
                System.out.println("Staff: " + staff.getFullName() + " - " + staff.getRoleName() + " - " + staff.getDepartment());
            }
            
            // Test search functionality
            List<Staff> searchResults = staffDAO.searchStaff("Doctor");
            System.out.println("Search results for 'Doctor': " + searchResults.size());
            
            // Test role ID lookup
            int doctorRoleId = staffDAO.getRoleIdByName("Doctor");
            System.out.println("Doctor role ID: " + doctorRoleId);
            
            System.out.println("All tests completed successfully!");
            
        } catch (SQLException ex) {
            System.err.println("Database error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println("General error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
} 