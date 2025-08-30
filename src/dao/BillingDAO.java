package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Billing;
import java.math.BigDecimal;
import java.util.Date;

/**
 * BillingDAO - Implements the DAO (Data Access Object) Pattern
 * 
 * This class encapsulates all database operations for Billing entities,
 * providing a clean separation between business logic and data access.
 * It handles CRUD operations, search functionality, and financial
 * record management while abstracting the underlying database implementation details.
 */
public class BillingDAO {

    private final Connection connection;

    public BillingDAO(Connection connection) {
        this.connection = connection;
    }

    // Create new billing record
    public boolean createBilling(Billing billing) throws SQLException {
        String sql = "INSERT INTO billing (patient_id, appointment_id, total_amount, payment_status, bill_date, due_date, bill_type, description, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, billing.getPatientId());
            if (billing.getAppointmentId() != null) {
                pstmt.setInt(2, billing.getAppointmentId());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            pstmt.setBigDecimal(3, billing.getTotalAmount());
            pstmt.setString(4, billing.getPaymentStatus() != null ? billing.getPaymentStatus() : "Pending");
            if (billing.getBillDate() != null) {
                pstmt.setDate(5, new java.sql.Date(billing.getBillDate().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }
            if (billing.getDueDate() != null) {
                pstmt.setDate(6, new java.sql.Date(billing.getDueDate().getTime()));
            } else {
                pstmt.setNull(6, java.sql.Types.DATE);
            }
            pstmt.setString(7, billing.getBillType());
            pstmt.setString(8, billing.getDescription());
            pstmt.setString(9, billing.getNotes());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get all billing records with patient and doctor information
    public List<Billing> getAllBilling() throws SQLException {
        List<Billing> billingList = new ArrayList<>();
        String sql = "SELECT b.*, p.first_name, p.last_name, s.first_name as doctor_first_name, s.last_name as doctor_last_name, " +
                     "a.appointment_date FROM billing b " +
                     "LEFT JOIN patients p ON b.patient_id = p.patient_id " +
                     "LEFT JOIN appointments a ON b.appointment_id = a.appointment_id " +
                     "LEFT JOIN staff s ON a.staff_id = s.staff_id " +
                     "ORDER BY b.bill_id";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Billing billing = new Billing();
                billing.setBillId(rs.getInt("bill_id"));
                billing.setPatientId(rs.getInt("patient_id"));
                int appointmentId = rs.getInt("appointment_id");
                billing.setAppointmentId(rs.wasNull() ? null : appointmentId);
                billing.setTotalAmount(rs.getBigDecimal("total_amount"));
                billing.setPaymentStatus(rs.getString("payment_status"));
                billing.setBillDate(rs.getDate("bill_date"));
                Date dueDate = rs.getDate("due_date");
                billing.setDueDate(rs.wasNull() ? null : dueDate);
                billing.setBillType(rs.getString("bill_type"));
                billing.setDescription(rs.getString("description"));
                billing.setNotes(rs.getString("notes"));
                
                // Set display fields
                billing.setPatientName(rs.getString("first_name") + " " + rs.getString("last_name"));
                String doctorFirstName = rs.getString("doctor_first_name");
                String doctorLastName = rs.getString("doctor_last_name");
                if (doctorFirstName != null && doctorLastName != null) {
                    billing.setDoctorName(doctorFirstName + " " + doctorLastName);
                } else {
                    billing.setDoctorName("N/A");
                }
                
                Date appointmentDate = rs.getDate("appointment_date");
                if (appointmentDate != null) {
                    billing.setAppointmentDate(appointmentDate.toString());
                } else {
                    billing.setAppointmentDate("N/A");
                }
                
                billingList.add(billing);
            }
        }
        return billingList;
    }

    // Get billing by ID
    public Billing getBillingById(int billId) throws SQLException {
        String sql = "SELECT b.*, p.first_name, p.last_name, s.first_name as doctor_first_name, s.last_name as doctor_last_name, " +
                     "a.appointment_date FROM billing b " +
                     "LEFT JOIN patients p ON b.patient_id = p.patient_id " +
                     "LEFT JOIN appointments a ON b.appointment_id = a.appointment_id " +
                     "LEFT JOIN staff s ON a.staff_id = s.staff_id " +
                     "WHERE b.bill_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, billId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Billing billing = new Billing();
                    billing.setBillId(rs.getInt("bill_id"));
                    billing.setPatientId(rs.getInt("patient_id"));
                    int appointmentId = rs.getInt("appointment_id");
                    billing.setAppointmentId(rs.wasNull() ? null : appointmentId);
                    billing.setTotalAmount(rs.getBigDecimal("total_amount"));
                    billing.setPaymentStatus(rs.getString("payment_status"));
                    billing.setBillDate(rs.getDate("bill_date"));
                    Date dueDate = rs.getDate("due_date");
                    billing.setDueDate(rs.wasNull() ? null : dueDate);
                    billing.setBillType(rs.getString("bill_type"));
                    billing.setDescription(rs.getString("description"));
                    billing.setNotes(rs.getString("notes"));
                    
                    // Set display fields
                    billing.setPatientName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    String doctorFirstName = rs.getString("doctor_first_name");
                    String doctorLastName = rs.getString("doctor_last_name");
                    if (doctorFirstName != null && doctorLastName != null) {
                        billing.setDoctorName(doctorFirstName + " " + doctorLastName);
                    } else {
                        billing.setDoctorName("N/A");
                    }
                    
                    Date appointmentDate = rs.getDate("appointment_date");
                    if (appointmentDate != null) {
                        billing.setAppointmentDate(appointmentDate.toString());
                    } else {
                        billing.setAppointmentDate("N/A");
                    }
                    
                    return billing;
                }
            }
        }
        return null;
    }

    // Update billing record
    public boolean updateBilling(Billing billing) throws SQLException {
        String sql = "UPDATE billing SET patient_id=?, appointment_id=?, total_amount=?, payment_status=?, " +
                     "bill_date=?, due_date=?, bill_type=?, description=?, notes=? WHERE bill_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, billing.getPatientId());
            if (billing.getAppointmentId() != null) {
                pstmt.setInt(2, billing.getAppointmentId());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }
            pstmt.setBigDecimal(3, billing.getTotalAmount());
            pstmt.setString(4, billing.getPaymentStatus() != null ? billing.getPaymentStatus() : "Pending");
            if (billing.getBillDate() != null) {
                pstmt.setDate(5, new java.sql.Date(billing.getBillDate().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }
            if (billing.getDueDate() != null) {
                pstmt.setDate(6, new java.sql.Date(billing.getDueDate().getTime()));
            } else {
                pstmt.setNull(6, java.sql.Types.DATE);
            }
            pstmt.setString(7, billing.getBillType());
            pstmt.setString(8, billing.getDescription());
            pstmt.setString(9, billing.getNotes());
            pstmt.setInt(10, billing.getBillId());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Delete billing record
    public boolean deleteBilling(int billId) throws SQLException {
        String sql = "DELETE FROM billing WHERE bill_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, billId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Search billing by patient name, doctor name, or payment status
    public List<Billing> searchBilling(String searchTerm) throws SQLException {
        List<Billing> billingList = new ArrayList<>();
        String sql = "SELECT b.*, p.first_name, p.last_name, s.first_name as doctor_first_name, s.last_name as doctor_last_name, " +
                     "a.appointment_date FROM billing b " +
                     "LEFT JOIN patients p ON b.patient_id = p.patient_id " +
                     "LEFT JOIN appointments a ON b.appointment_id = a.appointment_id " +
                     "LEFT JOIN staff s ON a.staff_id = s.staff_id " +
                     "WHERE p.first_name LIKE ? OR p.last_name LIKE ? OR s.first_name LIKE ? OR s.last_name LIKE ? " +
                     "OR b.payment_status LIKE ? OR b.bill_type LIKE ? " +
                     "ORDER BY b.bill_id";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            pstmt.setString(5, searchPattern);
            pstmt.setString(6, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Billing billing = new Billing();
                    billing.setBillId(rs.getInt("bill_id"));
                    billing.setPatientId(rs.getInt("patient_id"));
                    int appointmentId = rs.getInt("appointment_id");
                    billing.setAppointmentId(rs.wasNull() ? null : appointmentId);
                    billing.setTotalAmount(rs.getBigDecimal("total_amount"));
                    billing.setPaymentStatus(rs.getString("payment_status"));
                    billing.setBillDate(rs.getDate("bill_date"));
                    Date dueDate = rs.getDate("due_date");
                    billing.setDueDate(rs.wasNull() ? null : dueDate);
                    billing.setBillType(rs.getString("bill_type"));
                    billing.setDescription(rs.getString("description"));
                    billing.setNotes(rs.getString("notes"));
                    
                    // Set display fields
                    billing.setPatientName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    String doctorFirstName = rs.getString("doctor_first_name");
                    String doctorLastName = rs.getString("doctor_last_name");
                    if (doctorFirstName != null && doctorLastName != null) {
                        billing.setDoctorName(doctorFirstName + " " + doctorLastName);
                    } else {
                        billing.setDoctorName("N/A");
                    }
                    
                    Date appointmentDate = rs.getDate("appointment_date");
                    if (appointmentDate != null) {
                        billing.setAppointmentDate(appointmentDate.toString());
                    } else {
                        billing.setAppointmentDate("N/A");
                    }
                    
                    billingList.add(billing);
                }
            }
        }
        return billingList;
    }

    // Get billing by patient ID
    public List<Billing> getBillingByPatientId(int patientId) throws SQLException {
        List<Billing> billingList = new ArrayList<>();
        String sql = "SELECT b.*, p.first_name, p.last_name, s.first_name as doctor_first_name, s.last_name as doctor_last_name, " +
                     "a.appointment_date FROM billing b " +
                     "LEFT JOIN patients p ON b.patient_id = p.patient_id " +
                     "LEFT JOIN appointments a ON b.appointment_id = a.appointment_id " +
                     "LEFT JOIN staff s ON a.staff_id = s.staff_id " +
                     "WHERE b.patient_id = ? ORDER BY b.bill_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Billing billing = new Billing();
                    billing.setBillId(rs.getInt("bill_id"));
                    billing.setPatientId(rs.getInt("patient_id"));
                    int appointmentId = rs.getInt("appointment_id");
                    billing.setAppointmentId(rs.wasNull() ? null : appointmentId);
                    billing.setTotalAmount(rs.getBigDecimal("total_amount"));
                    billing.setPaymentStatus(rs.getString("payment_status"));
                    billing.setBillDate(rs.getDate("bill_date"));
                    Date dueDate = rs.getDate("due_date");
                    billing.setDueDate(rs.wasNull() ? null : dueDate);
                    billing.setBillType(rs.getString("bill_type"));
                    billing.setDescription(rs.getString("description"));
                    billing.setNotes(rs.getString("notes"));
                    
                    // Set display fields
                    billing.setPatientName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    String doctorFirstName = rs.getString("doctor_first_name");
                    String doctorLastName = rs.getString("doctor_last_name");
                    if (doctorFirstName != null && doctorLastName != null) {
                        billing.setDoctorName(doctorFirstName + " " + doctorLastName);
                    } else {
                        billing.setDoctorName("N/A");
                    }
                    
                    Date appointmentDate = rs.getDate("appointment_date");
                    if (appointmentDate != null) {
                        billing.setAppointmentDate(appointmentDate.toString());
                    } else {
                        billing.setAppointmentDate("N/A");
                    }
                    
                    billingList.add(billing);
                }
            }
        }
        return billingList;
    }

    // Get billing by payment status
    public List<Billing> getBillingByPaymentStatus(String paymentStatus) throws SQLException {
        List<Billing> billingList = new ArrayList<>();
        String sql = "SELECT b.*, p.first_name, p.last_name, s.first_name as doctor_first_name, s.last_name as doctor_last_name, " +
                     "a.appointment_date FROM billing b " +
                     "LEFT JOIN patients p ON b.patient_id = p.patient_id " +
                     "LEFT JOIN appointments a ON b.appointment_id = a.appointment_id " +
                     "LEFT JOIN staff s ON a.staff_id = s.staff_id " +
                     "WHERE b.payment_status = ? ORDER BY b.bill_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paymentStatus);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Billing billing = new Billing();
                    billing.setBillId(rs.getInt("bill_id"));
                    billing.setPatientId(rs.getInt("patient_id"));
                    int appointmentId = rs.getInt("appointment_id");
                    billing.setAppointmentId(rs.wasNull() ? null : appointmentId);
                    billing.setTotalAmount(rs.getBigDecimal("total_amount"));
                    billing.setPaymentStatus(rs.getString("payment_status"));
                    billing.setBillDate(rs.getDate("bill_date"));
                    Date dueDate = rs.getDate("due_date");
                    billing.setDueDate(rs.wasNull() ? null : dueDate);
                    billing.setBillType(rs.getString("bill_type"));
                    billing.setDescription(rs.getString("description"));
                    billing.setNotes(rs.getString("notes"));
                    
                    // Set display fields
                    billing.setPatientName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    String doctorFirstName = rs.getString("doctor_first_name");
                    String doctorLastName = rs.getString("doctor_last_name");
                    if (doctorFirstName != null && doctorLastName != null) {
                        billing.setDoctorName(doctorFirstName + " " + doctorLastName);
                    } else {
                        billing.setDoctorName("N/A");
                    }
                    
                    Date appointmentDate = rs.getDate("appointment_date");
                    if (appointmentDate != null) {
                        billing.setAppointmentDate(appointmentDate.toString());
                    } else {
                        billing.setAppointmentDate("N/A");
                    }
                    
                    billingList.add(billing);
                }
            }
        }
        return billingList;
    }

    // Update payment status
    public boolean updatePaymentStatus(int billId, String paymentStatus) throws SQLException {
        String sql = "UPDATE billing SET payment_status = ? WHERE bill_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paymentStatus);
            pstmt.setInt(2, billId);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Get total revenue
    public BigDecimal getTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(total_amount) as total_revenue FROM billing WHERE payment_status = 'Paid'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("total_revenue");
                return total != null ? total : BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    // Get pending payments
    public BigDecimal getPendingPayments() throws SQLException {
        String sql = "SELECT SUM(total_amount) as pending_amount FROM billing WHERE payment_status != 'Paid'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("pending_amount");
                return total != null ? total : BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    // Get billing count by status
    public int getBillingCountByStatus(String paymentStatus) throws SQLException {
        String sql = "SELECT COUNT(*) FROM billing WHERE payment_status = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, paymentStatus);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
} 