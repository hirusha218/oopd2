package panlen;

import dao.PatientDAO;
import model.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * PatientManagementPanel - Implements the Single Responsibility Principle
 * 
 * This panel is responsible solely for patient management operations,
 * including CRUD operations, search functionality, and patient record handling.
 * It encapsulates all patient-related UI logic and business rules,
 * ensuring that changes to patient management don't affect other system components.
 */
public class PatientManagementPanel extends JPanel {
    
    private final PatientDAO patientDAO;
    private DefaultTableModel patientTableModel;
    private Patient selectedPatient;
    
    // UI Components
    private JTable patientTable;
    private JTextField patientIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField genderField;
    private JTextField mobileField;
    private JTextField emailField;
    private JTextField searchField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    
    public PatientManagementPanel(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
        initializeComponents();
        setupLayout();
        loadPatientData();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        // Initialize table model
        patientTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Define column names
        String[] columnNames = {"Patient ID", "First Name", "Last Name", "Gender", "Contact Number", "Email", "Status"};
        for (String columnName : columnNames) {
            patientTableModel.addColumn(columnName);
        }

        // Initialize UI components
        patientTable = new JTable(patientTableModel);
        patientIdField = new JTextField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        genderField = new JTextField(20);
        mobileField = new JTextField(20);
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
        JScrollPane scrollPane = new JScrollPane(patientTable);
        add(scrollPane);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        // First row
        JPanel row1 = new JPanel();
        row1.add(new JLabel("Patient ID:"));
        row1.add(patientIdField);
        row1.add(new JLabel("First Name:"));
        row1.add(firstNameField);
        formPanel.add(row1);
        
        // Second row
        JPanel row2 = new JPanel();
        row2.add(new JLabel("Last Name:"));
        row2.add(lastNameField);
        row2.add(new JLabel("Gender:"));
        row2.add(genderField);
        formPanel.add(row2);
        
        // Third row
        JPanel row3 = new JPanel();
        row3.add(new JLabel("Mobile:"));
        row3.add(mobileField);
        row3.add(new JLabel("Email:"));
        row3.add(emailField);
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
        patientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleTableSelection();
            }
        });
        
        // Search field listener
        searchField.addActionListener(e -> performSearch());
        
        // Button action listeners
        addButton.addActionListener(e -> handleAddPatient());
        updateButton.addActionListener(e -> handleUpdatePatient());
        deleteButton.addActionListener(e -> handleDeletePatient());
    }
    
    private void loadPatientData() {
        try {
            List<Patient> patientList = patientDAO.getAllPatients();
            patientTableModel.setRowCount(0); // Clear existing rows
            
            for (Patient patient : patientList) {
                Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getEmail(),
                    patient.getStatus()
                };
                patientTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading patient data: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleTableSelection() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int patientId = (Integer) patientTableModel.getValueAt(selectedRow, 0);
                selectedPatient = patientDAO.getPatientById(patientId);
                if (selectedPatient != null) {
                    populateFormFields(selectedPatient);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading patient details: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateFormFields(Patient patient) {
        patientIdField.setText(String.valueOf(patient.getPatientId()));
        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        genderField.setText(patient.getGender());
        mobileField.setText(patient.getContactNumber());
        emailField.setText(patient.getEmail());
    }
    
    private void clearFormFields() {
        patientIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        genderField.setText("");
        mobileField.setText("");
        emailField.setText("");
        selectedPatient = null;
    }
    
    private Patient getPatientFromForm() {
        Patient patient = new Patient();
        if (selectedPatient != null) {
            patient.setPatientId(selectedPatient.getPatientId());
        }
        patient.setFirstName(firstNameField.getText().trim());
        patient.setLastName(lastNameField.getText().trim());
        patient.setGender(genderField.getText().trim());
        patient.setContactNumber(mobileField.getText().trim());
        patient.setEmail(emailField.getText().trim());
        patient.setStatus("Active");
        return patient;
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
        if (mobileField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Mobile Number", "Validation Error", JOptionPane.WARNING_MESSAGE);
            mobileField.requestFocus();
            return false;
        }
        return true;
    }
    
    private void handleAddPatient() {
        if (!validateForm()) {
            return;
        }
        
        try {
            Patient newPatient = getPatientFromForm();
            if (patientDAO.createPatient(newPatient)) {
                JOptionPane.showMessageDialog(this, "Patient added successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadPatientData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add patient.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleUpdatePatient() {
        if (selectedPatient == null) {
            JOptionPane.showMessageDialog(this, "Please select a patient to update.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            Patient updatedPatient = getPatientFromForm();
            if (patientDAO.updatePatient(updatedPatient)) {
                JOptionPane.showMessageDialog(this, "Patient updated successfully!", 
                                            "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadPatientData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update patient.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleDeletePatient() {
        if (selectedPatient == null) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete patient: " + selectedPatient.getFirstName() + " " + selectedPatient.getLastName() + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (patientDAO.deletePatient(selectedPatient.getPatientId())) {
                    JOptionPane.showMessageDialog(this, "Patient deleted successfully!", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFormFields();
                    loadPatientData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete patient.", 
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
            loadPatientData();
            return;
        }
        
        try {
            List<Patient> searchResults = patientDAO.searchPatients(searchTerm);
            patientTableModel.setRowCount(0);
            
            for (Patient patient : searchResults) {
                Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getEmail(),
                    patient.getStatus()
                };
                patientTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No patients found matching: " + searchTerm, 
                                            "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshData() {
        loadPatientData();
    }
} 