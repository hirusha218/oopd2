package service;

import java.awt.Component;
import java.awt.HeadlessException;
import utils.DBConnection;
import utils.DashboardConstants;
import javax.swing.*;
import java.sql.Connection;


public class RefactoredAdminDashboard extends JFrame {
    
    private DashboardMediator mediator;
    private JTabbedPane tabbedPane;
    
    public RefactoredAdminDashboard() {
        initializeDashboard();
        setupUI();
        setupEventHandlers();
    }
    
    private void initializeDashboard() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            mediator = new DashboardMediator(connection);
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
        
        // Create main layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel);
        
        // Tabbed content
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        setupTabs();
        add(tabbedPane);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        
        JLabel titleLabel = new JLabel("GlobeMed");
        titleLabel.setFont(new java.awt.Font(
            DashboardConstants.DEFAULT_FONT_FAMILY, 
            java.awt.Font.BOLD, 
            DashboardConstants.TITLE_FONT_SIZE
        ));
        
        JLabel subtitleLabel = new JLabel("Admin Dashboard");
        subtitleLabel.setFont(new java.awt.Font(
            DashboardConstants.DEFAULT_FONT_FAMILY, 
            java.awt.Font.PLAIN, 
            DashboardConstants.SUBTITLE_FONT_SIZE
        ));
        
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(subtitleLabel);
        
        return headerPanel;
    }
    
    private void setupTabs() {
        // Stock Management Tab
        tabbedPane.addTab(
            DashboardConstants.STOCK_MANAGEMENT_TAB,
            null,
            mediator.getStockPanel(),
            "Manage inventory and stock items"
        );
        
        // Staff Management Tab
        tabbedPane.addTab(DashboardConstants.STAFF_MANAGEMENT_TAB,
            null, (Component) mediator.getStaffPanel(),
            "Manage staff members and roles"
        );
        
        // Patient Records Tab
        tabbedPane.addTab(
            DashboardConstants.PATIENT_RECORDS_TAB,
            null,
            mediator.getPatientPanel(),
            "View and manage patient records"
        );
        
        // Billing & Claims Tab
        tabbedPane.addTab(
            DashboardConstants.BILLING_CLAIMS_TAB,
            null,
            mediator.getBillingPanel(),
            "Manage billing and payment records"
        );
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
        
        JButton refreshAllButton = new JButton("Refresh All Data");
        refreshAllButton.addActionListener(e -> refreshAllData());
        
        JButton exportDataButton = new JButton("Export Data");
        exportDataButton.addActionListener(e -> exportData());
        
        footerPanel.add(refreshAllButton);
        footerPanel.add(Box.createHorizontalGlue());
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
                mediator.handleStockUpdate();
            case 1 -> // Staff Management
                mediator.handleStaffUpdate();
            case 2 -> // Patient Records
                mediator.handlePatientUpdate();
            case 3 -> // Billing & Claims
                mediator.handleBillingUpdate();
        }
    }
    
    private void refreshAllData() {
        try {
            mediator.refreshAllData();
            JOptionPane.showMessageDialog(this, 
                "All data refreshed successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException e) {
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
    

    public DashboardMediator getMediator() {
        return mediator;
    }
    

} 