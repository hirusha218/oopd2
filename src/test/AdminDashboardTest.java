package test;

import panlen.AdminDashboard;
import javax.swing.SwingUtilities;

/**
 * Test class for Admin Dashboard
 * @author Hiruw
 */
public class AdminDashboardTest {
    
    public static void main(String[] args) {
        System.out.println("Testing Admin Dashboard...");
        
        try {
            // Test the dashboard in a separate thread
            SwingUtilities.invokeLater(() -> {
                try {
                    AdminDashboard dashboard = new AdminDashboard();
                    dashboard.setVisible(true);
                    System.out.println("Admin Dashboard created and displayed successfully!");
                } catch (Exception e) {
                    System.err.println("Error creating Admin Dashboard: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
        } catch (Exception ex) {
            System.err.println("General error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
} 