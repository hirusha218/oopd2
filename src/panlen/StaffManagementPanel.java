package panlen;

import dao.StaffDAO;
import model.Staff;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * StaffManagementPanel - Implements the Single Responsibility Principle
 * 
 * This panel is responsible solely for staff management operations,
 * including CRUD operations, search functionality, and form validation.
 * It encapsulates all staff-related UI logic and business rules,
 * ensuring that changes to staff management don't affect other system components.
 */
public class StaffManagementPanel extends JPanel {
    
    private final StaffDAO staffDAO;
    private DefaultTableModel staffTableModel;
    private Staff selectedStaff;
    
    // UI Components
    private JTable staffTable;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField roleField;
    private JTextField mobileField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField departmentField;
    private JTextField emailField;
    private JTextField searchField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    
    public StaffManagementPanel(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
        initializeComponents();
        setupLayout();
        loadStaffData();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Initialize table model
        staffTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Define column names
        String[] columnNames = {"Staff ID", "First Name", "Last Name", "Role", "Contact Number", "Username", "Department", "Email", "Status"};
        for (String columnName : columnNames) {
            staffTableModel.addColumn(columnName);
        }

        // Initialize UI components
        staffTable = new JTable(staffTableModel);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        roleField = new JTextField(20);
        mobileField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JTextField(20);
        departmentField = new JTextField(20);
        emailField = new JTextField(20);
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
        JScrollPane scrollPane = new JScrollPane(staffTable);
        add(scrollPane);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        // First row
        JPanel row1 = new JPanel();
        row1.add(new JLabel("First Name:"));
        row1.add(firstNameField);
        row1.add(new JLabel("Last Name:"));
        row1.add(lastNameField);
        formPanel.add(row1);
        
        // Second row
        JPanel row2 = new JPanel();
        row2.add(new JLabel("Role:"));
        row2.add(roleField);
        row2.add(new JLabel("Mobile:"));
        row2.add(mobileField);
        formPanel.add(row2);
        
        // Third row
        JPanel row3 = new JPanel();
        row3.add(new JLabel("Username:"));
        row3.add(usernameField);
        row3.add(new JLabel("Password:"));
        row3.add(passwordField);
        formPanel.add(row3);
        
        // Fourth row
        JPanel row4 = new JPanel();
        row4.add(new JLabel("Department:"));
        row4.add(departmentField);
        row4.add(new JLabel("Email:"));
        row4.add(emailField);
        formPanel.add(row4);
        
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
        staffTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableSelection();
            }
        });
        
        // Search field listener
        searchField.addActionListener(e -> performSearch());
        
        // Button action listeners
        addButton.addActionListener(e -> handleAddStaff());
        updateButton.addActionListener(e -> handleUpdateStaff());
        deleteButton.addActionListener(e -> handleDeleteStaff());
    }
    
    private void loadStaffData() {
        try {
            List<Staff> staffList = staffDAO.getAllStaff();
            staffTableModel.setRowCount(0); // Clear existing rows
            
            for (Staff staff : staffList) {
                Object[] row = {
                    staff.getStaffId(),
                    staff.getFirstName(),
                    staff.getLastName(),
                    staff.getRoleName(),
                    staff.getContactNumber(),
                    staff.getUsername(),
                    staff.getDepartment(),
                    staff.getEmail(),
                    staff.getStatus()
                };
                staffTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading staff data: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleTableSelection() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int staffId = (Integer) staffTableModel.getValueAt(selectedRow, 0);
                selectedStaff = staffDAO.getStaffById(staffId);
                if (selectedStaff != null) {
                    populateFormFields(selectedStaff);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading staff details: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateFormFields(Staff staff) {
        firstNameField.setText(staff.getFirstName());
        lastNameField.setText(staff.getLastName());
        roleField.setText(staff.getRoleName());
        mobileField.setText(staff.getContactNumber());
        usernameField.setText(staff.getUsername());
        passwordField.setText(""); // Don't populate password for security
        departmentField.setText(staff.getDepartment());
        emailField.setText(staff.getEmail());
    }
    
    private void clearFormFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        roleField.setText("");
        mobileField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        departmentField.setText("");
        emailField.setText("");
        selectedStaff = null;
    }
    
    private Staff getStaffFromForm() {
        Staff staff = new Staff();
        if (selectedStaff != null) {
            staff.setStaffId(selectedStaff.getStaffId());
        }
        staff.setFirstName(firstNameField.getText().trim());
        staff.setLastName(lastNameField.getText().trim());
        
        // Convert role name to role ID
        try {
            int roleId = staffDAO.getRoleIdByName(roleField.getText().trim());
            if (roleId == -1) {
                throw new IllegalArgumentException("Invalid role: " + roleField.getText().trim());
            }
            staff.setRoleId(roleId);
        } catch (SQLException ex) {
            throw new RuntimeException("Error getting role ID: " + ex.getMessage());
        }
        
        staff.setContactNumber(mobileField.getText().trim());
        staff.setUsername(usernameField.getText().trim());
        staff.setPassword(passwordField.getText().trim());
        staff.setDepartment(departmentField.getText().trim());
        staff.setEmail(emailField.getText().trim());
        staff.setStatus("Active");
        return staff;
    }
    
    private boolean validateForm() {
        if (firstNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter First Name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            firstNameField.requestFocus();
            return false;
        }
        if (lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Last Name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            lastNameField.requestFocus();
            return false;
        }
        if (roleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Role", "Validation Error", JOptionPane.WARNING_MESSAGE);
            roleField.requestFocus();
            return false;
        }
        if (usernameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Username", "Validation Error", JOptionPane.WARNING_MESSAGE);
            usernameField.requestFocus();
            return false;
        }
        if (passwordField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Password", "Validation Error", JOptionPane.WARNING_MESSAGE);
            passwordField.requestFocus();
            return false;
        }
        return true;
    }
    
    private void handleAddStaff() {
        if (!validateForm()) {
            return;
        }
        
        try {
            // Check if username already exists
            if (staffDAO.isUsernameExists(usernameField.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", 
                                            "Validation Error", JOptionPane.WARNING_MESSAGE);
                usernameField.requestFocus();
                return;
            }
            
            Staff newStaff = getStaffFromForm();
            String password = passwordField.getText().trim();
            if (staffDAO.createStaff(newStaff, password)) {
                JOptionPane.showMessageDialog(this, "Staff member added successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadStaffData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add staff member.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdateStaff() {
        if (selectedStaff == null) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to update.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            Staff updatedStaff = getStaffFromForm();
            
            // Check if username changed and if new username already exists
            if (!updatedStaff.getUsername().equals(selectedStaff.getUsername()) && 
                staffDAO.isUsernameExists(updatedStaff.getUsername())) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", 
                                            "Validation Error", JOptionPane.WARNING_MESSAGE);
                usernameField.requestFocus();
                return;
            }
            
            String password = passwordField.getText().trim();
            if (staffDAO.updateStaff(updatedStaff, password)) {
                JOptionPane.showMessageDialog(this, "Staff member updated successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadStaffData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update staff member.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleDeleteStaff() {
        if (selectedStaff == null) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete staff member: " + selectedStaff.getFullName() + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (staffDAO.deleteStaff(selectedStaff.getStaffId())) {
                    JOptionPane.showMessageDialog(this, "Staff member deleted successfully!", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFormFields();
                    loadStaffData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete staff member.", 
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
            loadStaffData(); // Load all data if search is empty
            return;
        }
        
        try {
            List<Staff> searchResults = staffDAO.searchStaff(searchTerm);
            staffTableModel.setRowCount(0); // Clear existing rows
            
            for (Staff staff : searchResults) {
                Object[] row = {
                    staff.getStaffId(),
                    staff.getFirstName(),
                    staff.getLastName(),
                    staff.getRoleName(),
                    staff.getContactNumber(),
                    staff.getUsername(),
                    staff.getDepartment(),
                    staff.getEmail(),
                    staff.getStatus()
                };
                staffTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No staff members found matching: " + searchTerm, 
                                            "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshData() {
        loadStaffData();
    }
} 