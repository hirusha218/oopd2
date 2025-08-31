package service;

import dao.StaffDAO;
import dao.StockDAO;
import dao.PatientDAO;
import dao.BillingDAO;
import service.StaffManagementPanel;
import service.StockManagementPanel;
import service.PatientManagementPanel;
import service.BillingManagementPanel;
import utils.DBConnection;
import utils.DashboardConstants;
import javax.swing.*;
import java.sql.Connection;
import java.awt.*;



public class AdminDashboard extends JFrame {
    
    private StaffDAO staffDAO;
    private StockDAO stockDAO;
    private PatientDAO patientDAO;
    private BillingDAO billingDAO;
    
    private StaffManagementPanel staffPanel;
    private StockManagementPanel stockPanel;
    private PatientManagementPanel patientPanel;
    private BillingManagementPanel billingPanel;
    
    private JTabbedPane tabbedPane;
    
    public AdminDashboard() {
        initializeDashboard();
        setupUI();
        setupEventHandlers();
    }
    
    private void initializeDashboard() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            staffDAO = new StaffDAO(connection);
            stockDAO = new StockDAO(connection);
            patientDAO = new PatientDAO(connection);
            billingDAO = new BillingDAO(connection);
            
            staffPanel = new StaffManagementPanel(staffDAO);
            stockPanel = new StockManagementPanel(stockDAO);
            patientPanel = new PatientManagementPanel(patientDAO);
            billingPanel = new BillingManagementPanel(billingDAO);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error initializing dashboard: " + e.getMessage(), 
                DashboardConstants.ERROR_GENERAL, 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupUI() {
        setTitle("GlobeMed - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        setupTabs();
        add(tabbedPane, BorderLayout.CENTER);
        
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("GlobeMed");
        titleLabel.setFont(new Font(
            DashboardConstants.DEFAULT_FONT_FAMILY, 
            Font.BOLD, 
            DashboardConstants.TITLE_FONT_SIZE
        ));
        
        JLabel subtitleLabel = new JLabel("Admin Dashboard");
        subtitleLabel.setFont(new Font(
            DashboardConstants.DEFAULT_FONT_FAMILY, 
            Font.PLAIN, 
            DashboardConstants.SUBTITLE_FONT_SIZE
        ));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private void setupTabs() {
        tabbedPane.addTab(
            DashboardConstants.STOCK_MANAGEMENT_TAB,
            null,
            stockPanel,
            "Manage inventory and stock items"
        );
        
        // Staff Management Tab
        tabbedPane.addTab(
            DashboardConstants.STAFF_MANAGEMENT_TAB,
            null,
            staffPanel,
            "Manage staff members and roles"
        );
        
        // Patient Records Tab
        tabbedPane.addTab(
            DashboardConstants.PATIENT_RECORDS_TAB,
            null,
            patientPanel,
            "View and manage patient records"
        );
        
        // Billing & Claims Tab
        tabbedPane.addTab(
            DashboardConstants.BILLING_CLAIMS_TAB,
            null,
            billingPanel,
            "Manage billing and payment records"
        );
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton refreshAllButton = new JButton("Refresh All Data");
        refreshAllButton.addActionListener(e -> refreshAllData());
        
        JButton exportDataButton = new JButton("Export Data");
        exportDataButton.addActionListener(e -> exportData());
        
        footerPanel.add(refreshAllButton);
        footerPanel.add(exportDataButton);
        
        return footerPanel;
    }
    
    private void setupEventHandlers() {
        // Add tab change listener to refresh data when switching tabs
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            if (selectedIndex >= 0) {
                refreshCurrentTabData(selectedIndex);
            }
        });
    }
    
    private void refreshCurrentTabData(int tabIndex) {
        switch (tabIndex) {
            case 0 -> // Stock Management
                stockPanel.refreshData();
            case 1 -> // Staff Management
                staffPanel.refreshData();
            case 2 -> // Patient Records
                patientPanel.refreshData();
            case 3 -> // Billing & Claims
                billingPanel.refreshData();
        }
    }
    
    private void refreshAllData() {
        try {
            stockPanel.refreshData();
            staffPanel.refreshData();
            patientPanel.refreshData();
            billingPanel.refreshData();
            
            JOptionPane.showMessageDialog(this, 
                "All data refreshed successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error refreshing data: " + e.getMessage(), 
                DashboardConstants.ERROR_GENERAL, 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportData() {
        // This would implement data export functionality
        JOptionPane.showMessageDialog(this, 
            "Export functionality to be implemented", 
            "Information", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    

} 