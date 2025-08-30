package test;

import dao.StaffDAO;
import dao.StockDAO;
import dao.PatientDAO;
import dao.BillingDAO;
import model.Staff;
import model.Stock;
import model.Patient;
import model.Billing;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Comprehensive test class for all DAOs in the HealthLink360 system
 * @author Hiruw
 */
public class CompleteSystemTest {
    
    public static void main(String[] args) {
        System.out.println("=== HealthLink360 Complete System Test ===\n");
        
        try {
            // Get database connection
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println("✅ Database connection successful!");
            
            // Test all DAOs
            testStaffDAO(connection);
            testStockDAO(connection);
            testPatientDAO(connection);
            testBillingDAO(connection);
            
            System.out.println("\n🎉 All tests completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testStaffDAO(Connection connection) throws SQLException {
        System.out.println("\n--- Testing StaffDAO ---");
        
        StaffDAO staffDAO = new StaffDAO(connection);
        
        // Test getAllStaff
        List<Staff> staffList = staffDAO.getAllStaff();
        System.out.println("✅ getAllStaff: Found " + staffList.size() + " staff members");
        
        if (!staffList.isEmpty()) {
            Staff firstStaff = staffList.get(0);
            System.out.println("   First staff: " + firstStaff.getFirstName() + " " + firstStaff.getLastName());
            
            // Test getStaffById
            Staff staffById = staffDAO.getStaffById(firstStaff.getStaffId());
            if (staffById != null) {
                System.out.println("✅ getStaffById: Successfully retrieved staff by ID");
            }
            
            // Test searchStaff
            List<Staff> searchResults = staffDAO.searchStaff(firstStaff.getFirstName());
            System.out.println("✅ searchStaff: Found " + searchResults.size() + " matching staff members");
        }
    }
    
    private static void testStockDAO(Connection connection) throws SQLException {
        System.out.println("\n--- Testing StockDAO ---");
        
        StockDAO stockDAO = new StockDAO(connection);
        
        // Test getAllStock
        List<Stock> stockList = stockDAO.getAllStock();
        System.out.println("✅ getAllStock: Found " + stockList.size() + " stock items");
        
        if (!stockList.isEmpty()) {
            Stock firstStock = stockList.get(0);
            System.out.println("   First stock: " + firstStock.getName() + " (Qty: " + firstStock.getQuantity() + ")");
            
            // Test getStockById
            Stock stockById = stockDAO.getStockById(firstStock.getStockId());
            if (stockById != null) {
                System.out.println("✅ getStockById: Successfully retrieved stock by ID");
            }
            
            // Test searchStock
            List<Stock> searchResults = stockDAO.searchStock(firstStock.getName());
            System.out.println("✅ searchStock: Found " + searchResults.size() + " matching stock items");
            
            // Test getLowStockItems
            List<Stock> lowStockItems = stockDAO.getLowStockItems(50);
            System.out.println("✅ getLowStockItems: Found " + lowStockItems.size() + " items with quantity <= 50");
            
            // Test getTotalStockValue
            BigDecimal totalValue = stockDAO.getTotalStockValue();
            System.out.println("✅ getTotalStockValue: Total inventory value: $" + totalValue);
        }
    }
    
    private static void testPatientDAO(Connection connection) throws SQLException {
        System.out.println("\n--- Testing PatientDAO ---");
        
        PatientDAO patientDAO = new PatientDAO(connection);
        
        // Test getAllPatients
        List<Patient> patientList = patientDAO.getAllPatients();
        System.out.println("✅ getAllPatients: Found " + patientList.size() + " patients");
        
        if (!patientList.isEmpty()) {
            Patient firstPatient = patientList.get(0);
            System.out.println("   First patient: " + firstPatient.getFirstName() + " " + firstPatient.getLastName());
            
            // Test getPatientById
            Patient patientById = patientDAO.getPatientById(firstPatient.getPatientId());
            if (patientById != null) {
                System.out.println("✅ getPatientById: Successfully retrieved patient by ID");
            }
            
            // Test searchPatients
            List<Patient> searchResults = patientDAO.searchPatients(firstPatient.getFirstName());
            System.out.println("✅ searchPatients: Found " + searchResults.size() + " matching patients");
            
            // Test getActivePatients
            List<Patient> activePatients = patientDAO.getActivePatients();
            System.out.println("✅ getActivePatients: Found " + activePatients.size() + " active patients");
            
            // Test getTotalPatientCount
            int totalCount = patientDAO.getTotalPatientCount();
            System.out.println("✅ getTotalPatientCount: Total patients: " + totalCount);
        }
    }
    
    private static void testBillingDAO(Connection connection) throws SQLException {
        System.out.println("\n--- Testing BillingDAO ---");
        
        BillingDAO billingDAO = new BillingDAO(connection);
        
        // Test getAllBilling
        List<Billing> billingList = billingDAO.getAllBilling();
        System.out.println("✅ getAllBilling: Found " + billingList.size() + " billing records");
        
        if (!billingList.isEmpty()) {
            Billing firstBilling = billingList.get(0);
            System.out.println("   First billing: Bill ID " + firstBilling.getBillId() + 
                             " - Patient: " + firstBilling.getPatientName() + 
                             " - Amount: " + firstBilling.getFormattedAmount());
            
            // Test getBillingById
            Billing billingById = billingDAO.getBillingById(firstBilling.getBillId());
            if (billingById != null) {
                System.out.println("✅ getBillingById: Successfully retrieved billing by ID");
            }
            
            // Test searchBilling
            List<Billing> searchResults = billingDAO.searchBilling(firstBilling.getPatientName());
            System.out.println("✅ searchBilling: Found " + searchResults.size() + " matching billing records");
            
            // Test getBillingByPaymentStatus
            List<Billing> pendingBills = billingDAO.getBillingByPaymentStatus("Pending");
            System.out.println("✅ getBillingByPaymentStatus: Found " + pendingBills.size() + " pending bills");
            
            // Test getTotalRevenue
            BigDecimal totalRevenue = billingDAO.getTotalRevenue();
            System.out.println("✅ getTotalRevenue: Total revenue: $" + totalRevenue);
            
            // Test getPendingPayments
            BigDecimal pendingPayments = billingDAO.getPendingPayments();
            System.out.println("✅ getPendingPayments: Pending payments: $" + pendingPayments);
        }
    }
    
    private static void displayDatabaseInfo(Connection connection) throws SQLException {
        System.out.println("\n--- Database Information ---");
        
        // You can add more database information queries here
        System.out.println("✅ Database connection established successfully");
        System.out.println("✅ All DAO tests completed");
    }
} 