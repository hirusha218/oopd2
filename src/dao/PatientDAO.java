package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Patient;
import java.util.Date;

/**
 * PatientDAO - Implements the DAO (Data Access Object) Pattern
 * 
 * This class encapsulates all database operations for Patient entities,
 * providing a clean separation between business logic and data access.
 * It handles CRUD operations, search functionality, and patient
 * management while abstracting the underlying database implementation details.
 */
public class PatientDAO {

    private final Connection connection;

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    // Create new patient
    public boolean createPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, contact_number, email, address, emergency_contact, insurance_id, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setDate(3, new java.sql.Date(patient.getDateOfBirth() != null ? patient.getDateOfBirth().getTime() : new Date().getTime()));
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getContactNumber());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getEmergencyContact());
            pstmt.setObject(9, patient.getInsuranceId());
            pstmt.setString(10, patient.getStatus() != null ? patient.getStatus() : "Active");
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get all patients
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setContactNumber(rs.getString("contact_number"));
                patient.setEmail(rs.getString("email"));
                patient.setAddress(rs.getString("address"));
                patient.setEmergencyContact(rs.getString("emergency_contact"));
                Integer insuranceId = rs.getInt("insurance_id");
                patient.setInsuranceId(rs.wasNull() ? null : insuranceId);
                patient.setStatus(rs.getString("status"));
                patient.setRegistrationDate(rs.getDate("registration_date"));
                patient.setLastVisitDate(rs.getDate("last_visit_date"));
                patientList.add(patient);
            }
        }
        return patientList;
    }

    // Get patient by ID
    public Patient getPatientById(int patientId) throws SQLException {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setPatientId(rs.getInt("patient_id"));
                    patient.setFirstName(rs.getString("first_name"));
                    patient.setLastName(rs.getString("last_name"));
                    patient.setDateOfBirth(rs.getDate("date_of_birth"));
                    patient.setGender(rs.getString("gender"));
                    patient.setContactNumber(rs.getString("contact_number"));
                    patient.setEmail(rs.getString("email"));
                    patient.setAddress(rs.getString("address"));
                    patient.setEmergencyContact(rs.getString("emergency_contact"));
                    Integer insuranceId = rs.getInt("insurance_id");
                    patient.setInsuranceId(rs.wasNull() ? null : insuranceId);
                    patient.setStatus(rs.getString("status"));
                    patient.setRegistrationDate(rs.getDate("registration_date"));
                    patient.setLastVisitDate(rs.getDate("last_visit_date"));
                    return patient;
                }
            }
        }
        return null;
    }

    // Update patient
    public boolean updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET first_name=?, last_name=?, date_of_birth=?, gender=?, contact_number=?, email=?, address=?, emergency_contact=?, insurance_id=?, status=? WHERE patient_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setDate(3, new java.sql.Date(patient.getDateOfBirth() != null ? patient.getDateOfBirth().getTime() : new Date().getTime()));
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getContactNumber());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getEmergencyContact());
            pstmt.setObject(9, patient.getInsuranceId());
            pstmt.setString(10, patient.getStatus() != null ? patient.getStatus() : "Active");
            pstmt.setInt(11, patient.getPatientId());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete patient
    public boolean deletePatient(int patientId) throws SQLException {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Search patients by name, contact, or email
    public List<Patient> searchPatients(String searchTerm) throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE first_name LIKE ? OR last_name LIKE ? OR contact_number LIKE ? OR email LIKE ? ORDER BY patient_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Patient patient = new Patient();
                    patient.setPatientId(rs.getInt("patient_id"));
                    patient.setFirstName(rs.getString("first_name"));
                    patient.setLastName(rs.getString("last_name"));
                    patient.setDateOfBirth(rs.getDate("date_of_birth"));
                    patient.setGender(rs.getString("gender"));
                    patient.setContactNumber(rs.getString("contact_number"));
                    patient.setEmail(rs.getString("email"));
                    patient.setAddress(rs.getString("address"));
                    patient.setEmergencyContact(rs.getString("emergency_contact"));
                    Integer insuranceId = rs.getInt("insurance_id");
                    patient.setInsuranceId(rs.wasNull() ? null : insuranceId);
                    patient.setStatus(rs.getString("status"));
                    patient.setRegistrationDate(rs.getDate("registration_date"));
                    patient.setLastVisitDate(rs.getDate("last_visit_date"));
                    patientList.add(patient);
                }
            }
        }
        return patientList;
    }

    // Get patients by status
    public List<Patient> getPatientsByStatus(String status) throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE status = ? ORDER BY patient_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Patient patient = new Patient();
                    patient.setPatientId(rs.getInt("patient_id"));
                    patient.setFirstName(rs.getString("first_name"));
                    patient.setLastName(rs.getString("last_name"));
                    patient.setDateOfBirth(rs.getDate("date_of_birth"));
                    patient.setGender(rs.getString("gender"));
                    patient.setContactNumber(rs.getString("contact_number"));
                    patient.setEmail(rs.getString("email"));
                    patient.setAddress(rs.getString("address"));
                    patient.setEmergencyContact(rs.getString("emergency_contact"));
                    Integer insuranceId = rs.getInt("insurance_id");
                    patient.setInsuranceId(rs.wasNull() ? null : insuranceId);
                    patient.setStatus(rs.getString("status"));
                    patient.setRegistrationDate(rs.getDate("registration_date"));
                    patient.setLastVisitDate(rs.getDate("last_visit_date"));
                    patientList.add(patient);
                }
            }
        }
        return patientList;
    }

    // Get total patient count
    public int getTotalPatientCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Get active patient count
    public int getActivePatientCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients WHERE status = 'Active'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Patient> getActivePatients() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
} 