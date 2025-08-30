package gui.Dashboard;

import dao.StaffDAO;
import dao.StockDAO;
import dao.PatientDAO;
import dao.BillingDAO;
import panlen.StaffManagementPanel;
import panlen.StockManagementPanel;
import panlen.PatientManagementPanel;
import panlen.BillingManagementPanel;
import java.sql.Connection;

/**
 * Dashboard Mediator - Implements the Mediator Pattern
 * 
 * This class acts as a mediator between different management panels,
 * reducing direct dependencies and tight coupling between components.
 * It centralizes the communication logic and provides a single point
 * of control for coordinating panel interactions.
 */
public class DashboardMediator {
    
    private final StaffDAO staffDAO;
    private final StockDAO stockDAO;
    private final PatientDAO patientDAO;
    private final BillingDAO billingDAO;
    
    private StaffManagementPanel staffPanel;
    private StockManagementPanel stockPanel;
    private PatientManagementPanel patientPanel;
    private BillingManagementPanel billingPanel;
    
    public DashboardMediator(Connection connection) {
        // Initialize DAOs
        this.staffDAO = new StaffDAO(connection);
        this.stockDAO = new StockDAO(connection);
        this.patientDAO = new PatientDAO(connection);
        this.billingDAO = new BillingDAO(connection);
        
        // Initialize panels
        initializePanels();
    }
    
    private void initializePanels() {
        this.staffPanel = new StaffManagementPanel(staffDAO);
        this.stockPanel = new StockManagementPanel(stockDAO);
        this.patientPanel = new PatientManagementPanel(patientDAO);
        this.billingPanel = new BillingManagementPanel(billingDAO);
    }
    
    // Getter methods for panels
    public StaffManagementPanel getStaffPanel() {
        return staffPanel;
    }
    
    public StockManagementPanel getStockPanel() {
        return stockPanel;
    }
    
    public PatientManagementPanel getPatientPanel() {
        return patientPanel;
    }
    
    public BillingManagementPanel getBillingPanel() {
        return billingPanel;
    }
    
    // Handle updates from different panels
    public void handleStaffUpdate() {
        if (staffPanel != null) {
            staffPanel.refreshData();
        }
    }
    
    public void handleStockUpdate() {
        if (stockPanel != null) {
            stockPanel.refreshData();
        }
    }
    
    public void handlePatientUpdate() {
        if (patientPanel != null) {
            patientPanel.refreshData();
        }
    }
    
    public void handleBillingUpdate() {
        if (billingPanel != null) {
            billingPanel.refreshData();
        }
    }
    
    // Refresh all data across all panels
    public void refreshAllData() {
        handleStaffUpdate();
        handleStockUpdate();
        handlePatientUpdate();
        handleBillingUpdate();
    }
    
    // Get DAOs for external access if needed
    public StaffDAO getStaffDAO() {
        return staffDAO;
    }
    
    public StockDAO getStockDAO() {
        return stockDAO;
    }
    
    public PatientDAO getPatientDAO() {
        return patientDAO;
    }
    
    public BillingDAO getBillingDAO() {
        return billingDAO;
    }
} 