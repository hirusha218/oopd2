package dao;

import model.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PatientDAO {

    private final Connection connection;

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    
    public boolean createPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (patient_id, first_name, last_name, dateOfBirth, gender, contact_number, " +
                     "email, address, emergency_contact, insurance_id, status, registration_date, last_visit_date, " +
                     "medical_history, medical_report, medicine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient.getPatientId());
            pstmt.setString(2, patient.getFirstName());
            pstmt.setString(3, patient.getLastName());
            pstmt.setDate(4, patient.getDateOfBirth() != null ? new java.sql.Date(patient.getDateOfBirth().getTime()) : null);
            pstmt.setString(5, patient.getGender());
            pstmt.setString(6, patient.getContactNumber());
            pstmt.setString(7, patient.getEmail());
            pstmt.setString(8, patient.getAddress());
            pstmt.setString(9, patient.getEmergencyContact());
            pstmt.setObject(10, patient.getInsuranceId(), java.sql.Types.INTEGER);
            pstmt.setString(11, patient.getStatus() != null ? patient.getStatus() : "Active");
            pstmt.setDate(12, patient.getRegistrationDate() != null ? new java.sql.Date(patient.getRegistrationDate().getTime()) : null);
            pstmt.setDate(13, patient.getLastVisitDate() != null ? new java.sql.Date(patient.getLastVisitDate().getTime()) : null);
            pstmt.setString(14, patient.getMedicalHistory());
            pstmt.setString(15, patient.getMedicalReport());
            pstmt.setString(16, patient.getMedicine());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients ORDER BY patient_id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                patientList.add(mapResultSetToPatient(rs));
            }
        }
        return patientList;
    }


    public Patient getPatientById(int patient_id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                }
            }
        }
        return null;
    }

  
    public boolean updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET first_name=?, last_name=?, dateOfBirth=?, gender=?, contact_number=?, " +
                     "email=?, address=?, emergency_contact=?, insurance_id=?, status=?, registration_date=?, " +
                     "last_visit_date=?, medical_history=?, medical_report=?, medicine=? WHERE patient_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient.getFirstName());
            pstmt.setString(2, patient.getLastName());
            pstmt.setDate(3, patient.getDateOfBirth() != null ? new java.sql.Date(patient.getDateOfBirth().getTime()) : null);
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getContactNumber());
            pstmt.setString(6, patient.getEmail());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getEmergencyContact());
            pstmt.setObject(9, patient.getInsuranceId(), java.sql.Types.INTEGER);
            pstmt.setString(10, patient.getStatus() != null ? patient.getStatus() : "Active");
            pstmt.setDate(11, patient.getRegistrationDate() != null ? new java.sql.Date(patient.getRegistrationDate().getTime()) : null);
            pstmt.setDate(12, patient.getLastVisitDate() != null ? new java.sql.Date(patient.getLastVisitDate().getTime()) : null);
            pstmt.setString(13, patient.getMedicalHistory());
            pstmt.setString(14, patient.getMedicalReport());
            pstmt.setString(15, patient.getMedicine());
            pstmt.setString(16, patient.getPatientId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deletePatient(int patient_id) throws SQLException {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient_id);
            return pstmt.executeUpdate() > 0;
        }
    }

  
    public List<Patient> searchPatients(String id, String name, String mobile) throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM patients WHERE 1=1");
        if (!id.isEmpty()) sql.append(" AND patient_id LIKE ?");
        if (!name.isEmpty()) sql.append(" AND CONCAT(first_name, ' ', last_name) LIKE ?");
        if (!mobile.isEmpty()) sql.append(" AND contact_number LIKE ?");
        sql.append(" ORDER BY patient_id");

        try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (!id.isEmpty()) pstmt.setString(paramIndex++, "%" + id + "%");
            if (!name.isEmpty()) pstmt.setString(paramIndex++, "%" + name + "%");
            if (!mobile.isEmpty()) pstmt.setString(paramIndex++, "%" + mobile + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    patientList.add(mapResultSetToPatient(rs));
                }
            }
        }
        return patientList;
    }

    public List<Patient> getPatientsByGender(String gender) throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE gender = ? ORDER BY first_name, last_name";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, gender);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    patientList.add(mapResultSetToPatient(rs));
                }
            }
        }
        return patientList;
    }

 
    public List<Patient> getPatientsByInsurance(Integer insuranceId) throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE insurance_id = ? ORDER BY first_name, last_name";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, insuranceId, java.sql.Types.INTEGER);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    patientList.add(mapResultSetToPatient(rs));
                }
            }
        }
        return patientList;
    }


    public List<Patient> getActivePatients() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE status = 'Active' ORDER BY first_name, last_name";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                patientList.add(mapResultSetToPatient(rs));
            }
        }
        return patientList;
    }

    public boolean updatePatientStatus(String patientId, String status) throws SQLException {
        String sql = "UPDATE patients SET status = ? WHERE patient_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, patientId);
            return pstmt.executeUpdate() > 0;
        }
    }

  
    public int getPatientCountByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients WHERE status = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
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

    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getString("patient_id"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setDateOfBirth(rs.getDate("dateOfBirth"));
        patient.setGender(rs.getString("gender"));
        patient.setContactNumber(rs.getString("contact_number"));
        patient.setEmail(rs.getString("email"));
        patient.setAddress(rs.getString("address"));
        patient.setEmergencyContact(rs.getString("emergency_contact"));
        patient.setInsuranceId(rs.getObject("insurance_id") != null ? rs.getInt("insurance_id") : null);
        patient.setStatus(rs.getString("status"));
        patient.setRegistrationDate(rs.getDate("registration_date"));
        patient.setLastVisitDate(rs.getDate("last_visit_date"));
        patient.setMedicalHistory(rs.getString("medical_history"));
        patient.setMedicalReport(rs.getString("medical_report"));
        patient.setMedicine(rs.getString("medicine"));
        return patient;
    }

    public List<Patient> searchPatients(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean addPatient(Patient patient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}