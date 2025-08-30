package panlen;

import dao.StaffDAO;
import dao.StockDAO;
import dao.PatientDAO;
import dao.BillingDAO;
import java.sql.Connection;

public class DashboardMediator {
    
    private StaffDAO staffDAO;
    private StockDAO stockDAO;
    private PatientDAO patientDAO;
    private BillingDAO billingDAO;
    
    private StaffManagementPanel staffPanel;
    private StockManagementPanel stockPanel;
    private PatientManagementPanel patientPanel;
    private BillingManagementPanel billingPanel;
    
    public DashboardMediator(Connection connection) {
        initializeDAOs(connection);
        initializePanels();
    }
    
    private void initializeDAOs(Connection connection) {
        this.staffDAO = new StaffDAO(connection);
        this.stockDAO = new StockDAO(connection);
        this.patientDAO = new PatientDAO(connection);
        this.billingDAO = new BillingDAO(connection);
    }
    
    private void initializePanels() {
        this.staffPanel = new StaffManagementPanel(staffDAO);
        this.stockPanel = new StockManagementPanel(stockDAO);
        this.patientPanel = new PatientManagementPanel(patientDAO);
        this.billingPanel = new BillingManagementPanel(billingDAO);
    }
    
    /**
     * Refresh all data across all panels
     */
    public void refreshAllData() {
        staffPanel.refreshData();
        stockPanel.refreshData();
        patientPanel.refreshData();
        billingPanel.refreshData();
    }
    
    /**
     * Handle staff updates and refresh related data
     */
    public void handleStaffUpdate() {
        // Refresh staff data
        staffPanel.refreshData();
        
        // Could trigger updates to related data
        // For example, if staff role changes, might need to update billing records
        // This demonstrates the mediator's role in coordinating updates
    }
    
    /**
     * Handle stock updates and refresh related data
     */
    public void handleStockUpdate() {
        stockPanel.refreshData();
        
        // Could trigger updates to related data
        // For example, if stock quantity changes, might need to update billing records
    }
    
    /**
     * Handle patient updates and refresh related data
     */
    public void handlePatientUpdate() {
        patientPanel.refreshData();
        
        // Could trigger updates to related data
        // For example, if patient status changes, might need to update billing records
    }
    
    /**
     * Handle billing updates and refresh related data
     */
    public void handleBillingUpdate() {
        billingPanel.refreshData();
        
        // Could trigger updates to related data
        // For example, if payment status changes, might need to update patient records
    }
    
    /**
     * Get staff management panel
     */
    public StaffManagementPanel getStaffPanel() {
        return staffPanel;
    }
    
    /**
     * Get stock management panel
     * @return 
     */
    public StockManagementPanel getStockPanel() {
        return stockPanel;
    }


    public PatientManagementPanel getPatientPanel() {
        return patientPanel;
    }
    
    /**
     * Get billing management panel
     */
    public BillingManagementPanel getBillingPanel() {
        return billingPanel;
    }
    
    /**
     * Get staff DAO for direct operations if needed
     */
    public StaffDAO getStaffDAO() {
        return staffDAO;
    }
    
    /**
     * Get stock DAO for direct operations if needed
     */
    public StockDAO getStockDAO() {
        return stockDAO;
    }
    
    /**
     * Get patient DAO for direct operations if needed
     */
    public PatientDAO getPatientDAO() {
        return patientDAO;
    }
    
    /**
     * Get billing DAO for direct operations if needed
     */
    public BillingDAO getBillingDAO() {
        return billingDAO;
    }
} 