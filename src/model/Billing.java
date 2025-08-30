package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Hiruw
 */
public class Billing implements Serializable {

    private static final long serialVersionUID = 1L;

    private int billId;
    private int patientId;
    private Integer appointmentId;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private Date billDate;
    private Date dueDate;
    private String billType;
    private String description;
    private String notes;
    private Date createdDate;
    private Date updatedDate;

    // Additional fields for display purposes
    private String patientName;
    private String doctorName;
    private String appointmentDate;

    // Default constructor
    public Billing() {
    }

    // Parameterized constructor
    public Billing(int billId, int patientId, Integer appointmentId, BigDecimal totalAmount,
                   String paymentStatus, Date billDate, Date dueDate, String billType, 
                   String description, String notes) {
        this.billId = billId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.billDate = billDate;
        this.dueDate = dueDate;
        this.billType = billType;
        this.description = description;
        this.notes = notes;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    // Display fields
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    // Helper methods
    public boolean isPaid() {
        return "Paid".equalsIgnoreCase(paymentStatus);
    }

    public boolean isOverdue() {
        if (dueDate != null && !isPaid()) {
            return new Date().after(dueDate);
        }
        return false;
    }

    public String getStatusColor() {
        if (isPaid()) {
            return "Green";
        } else if (isOverdue()) {
            return "Red";
        } else {
            return "Orange";
        }
    }

    public String getFormattedAmount() {
        if (totalAmount != null) {
            return "$" + totalAmount.toString();
        }
        return "$0.00";
    }

    @Override
    public String toString() {
        return "Billing{"
                + "billId=" + billId
                + ", patientId=" + patientId
                + ", appointmentId=" + appointmentId
                + ", totalAmount=" + totalAmount
                + ", paymentStatus='" + paymentStatus + '\''
                + ", billDate=" + billDate
                + ", dueDate=" + dueDate
                + ", billType='" + billType + '\''
                + '}';
    }
} 