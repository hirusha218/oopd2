package panlen;

import dao.BillingDAO;
import model.Billing;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * BillingManagementPanel - Implements the Single Responsibility Principle
 * 
 * This panel is responsible solely for billing management operations,
 * including CRUD operations, search functionality, and financial record handling.
 * It encapsulates all billing-related UI logic and business rules,
 * ensuring that changes to billing management don't affect other system components.
 */
public class BillingManagementPanel extends JPanel {
    
    private BillingDAO billingDAO;
    private DefaultTableModel billingTableModel;
    private Billing selectedBilling;
    
    // UI Components
    private JTable billingTable;
    private JTextField billingIdField;
    private JTextField patientIdField;
    private JTextField patientNameField;
    private JTextField doctorNameField;
    private JTextField amountField;
    private JTextField statusField;
    private JTextField searchField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    
    public BillingManagementPanel(BillingDAO billingDAO) {
        this.billingDAO = billingDAO;
        initializeComponents();
        setupLayout();
        loadBillingData();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Initialize table model
        billingTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Define column names
        String[] columnNames = {"Bill ID", "Patient Name", "Doctor Name", "Total Amount", "Payment Status", "Bill Date"};
        for (String columnName : columnNames) {
            billingTableModel.addColumn(columnName);
        }

        // Initialize UI components
        billingTable = new JTable(billingTableModel);
        billingIdField = new JTextField(20);
        patientIdField = new JTextField(20);
        patientNameField = new JTextField(20);
        doctorNameField = new JTextField(20);
        amountField = new JTextField(20);
        statusField = new JTextField(20);
        searchField = new JTextField(20);
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
    }
    
    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        add(searchPanel);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(billingTable);
        add(scrollPane);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        // First row
        JPanel row1 = new JPanel();
        row1.add(new JLabel("Billing ID:"));
        row1.add(billingIdField);
        row1.add(new JLabel("Patient ID:"));
        row1.add(patientIdField);
        formPanel.add(row1);
        
        // Second row
        JPanel row2 = new JPanel();
        row2.add(new JLabel("Patient Name:"));
        row2.add(patientNameField);
        row2.add(new JLabel("Doctor Name:"));
        row2.add(doctorNameField);
        formPanel.add(row2);
        
        // Third row
        JPanel row3 = new JPanel();
        row3.add(new JLabel("Amount:"));
        row3.add(amountField);
        row3.add(new JLabel("Status:"));
        row3.add(statusField);
        formPanel.add(row3);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        formPanel.add(buttonPanel);
        
        add(formPanel);
    }
    
    private void setupEventHandlers() {
        // Table selection listener
        billingTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableSelection();
            }
        });
        
        // Search field listener
        searchField.addActionListener(e -> performSearch());
        
        // Button action listeners
        addButton.addActionListener(e -> handleAddBilling());
        updateButton.addActionListener(e -> handleUpdateBilling());
        deleteButton.addActionListener(e -> handleDeleteBilling());
    }
    
    private void loadBillingData() {
        try {
            List<Billing> billingList = billingDAO.getAllBilling();
            billingTableModel.setRowCount(0); // Clear existing rows
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Billing billing : billingList) {
                Object[] row = {
                    billing.getBillId(),
                    billing.getPatientName(),
                    billing.getDoctorName(),
                    billing.getFormattedAmount(),
                    billing.getPaymentStatus(),
                    billing.getBillDate() != null ? dateFormat.format(billing.getBillDate()) : "N/A"
                };
                billingTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading billing data: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleTableSelection() {
        int selectedRow = billingTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int billId = (Integer) billingTableModel.getValueAt(selectedRow, 0);
                selectedBilling = billingDAO.getBillingById(billId);
                if (selectedBilling != null) {
                    populateFormFields(selectedBilling);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading billing details: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateFormFields(Billing billing) {
        billingIdField.setText(String.valueOf(billing.getBillId()));
        patientIdField.setText(String.valueOf(billing.getPatientId()));
        patientNameField.setText(billing.getPatientName());
        doctorNameField.setText(billing.getDoctorName());
        amountField.setText(billing.getTotalAmount() != null ? billing.getTotalAmount().toString() : "");
        statusField.setText(billing.getPaymentStatus());
    }
    
    private void clearFormFields() {
        billingIdField.setText("");
        patientIdField.setText("");
        patientNameField.setText("");
        doctorNameField.setText("");
        amountField.setText("");
        statusField.setText("");
        selectedBilling = null;
    }
    
    private Billing getBillingFromForm() {
        Billing billing = new Billing();
        if (selectedBilling != null) {
            billing.setBillId(selectedBilling.getBillId());
        }
        
        try {
            billing.setPatientId(Integer.parseInt(patientIdField.getText().trim()));
        } catch (NumberFormatException e) {
            billing.setPatientId(0);
        }
        
        billing.setPatientName(patientNameField.getText().trim());
        billing.setDoctorName(doctorNameField.getText().trim());
        
        try {
            billing.setTotalAmount(new java.math.BigDecimal(amountField.getText().trim()));
        } catch (NumberFormatException e) {
            billing.setTotalAmount(java.math.BigDecimal.ZERO);
        }
        
        billing.setPaymentStatus(statusField.getText().trim());
        billing.setBillDate(new java.util.Date());
        billing.setBillType("General");
        billing.setDescription("Billing record");
        billing.setNotes("");
        
        return billing;
    }
    
    private boolean validateForm() {
        if (patientIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Patient ID", "Validation Error", JOptionPane.WARNING_MESSAGE);
            patientIdField.requestFocus();
            return false;
        }
        if (patientNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Patient Name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            patientNameField.requestFocus();
            return false;
        }
        if (amountField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Amount", "Validation Error", JOptionPane.WARNING_MESSAGE);
            amountField.requestFocus();
            return false;
        }
        if (statusField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Status", "Validation Error", JOptionPane.WARNING_MESSAGE);
            statusField.requestFocus();
            return false;
        }
        return true;
    }
    
    private void handleAddBilling() {
        if (!validateForm()) {
            return;
        }
        
        try {
            Billing newBilling = getBillingFromForm();
            if (billingDAO.createBilling(newBilling)) {
                JOptionPane.showMessageDialog(this, "Billing record added successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadBillingData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add billing record.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateBilling() {
        if (selectedBilling == null) {
            JOptionPane.showMessageDialog(this, "Please select a billing record to update.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            Billing updatedBilling = getBillingFromForm();
            if (billingDAO.updateBilling(updatedBilling)) {
                JOptionPane.showMessageDialog(this, "Billing record updated successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadBillingData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update billing record.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleDeleteBilling() {
        if (selectedBilling == null) {
            JOptionPane.showMessageDialog(this, "Please select a billing record to delete.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete billing record: " + selectedBilling.getBillId() + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (billingDAO.deleteBilling(selectedBilling.getBillId())) {
                    JOptionPane.showMessageDialog(this, "Billing record deleted successfully!", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFormFields();
                    loadBillingData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete billing record.", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
            }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadBillingData();
            return;
        }
        
        try {
            List<Billing> searchResults = billingDAO.searchBilling(searchTerm);
            billingTableModel.setRowCount(0);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Billing billing : searchResults) {
                Object[] row = {
                    billing.getBillId(),
                    billing.getPatientName(),
                    billing.getDoctorName(),
                    billing.getFormattedAmount(),
                    billing.getPaymentStatus(),
                    billing.getBillDate() != null ? dateFormat.format(billing.getBillDate()) : "N/A"
                };
                billingTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No billing records found matching: " + searchTerm, 
                                            "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshData() {
        loadBillingData();
    }
} 