package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patientId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;
    private String emergencyContact;
    private Integer insuranceId;
    private String status;
    private Date registrationDate;
    private Date lastVisitDate;
    private String medicalHistory;
    private String medicalReport;
    private String medicine;
    private String Age;

    public Patient() {
    }

    public Patient(String patientId, String firstName, String lastName, Date dateOfBirth, String gender,
            String contactNumber, String email, String address, String emergencyContact,
            Integer insuranceId, String status, Date registrationDate, Date lastVisitDate,
            String medicalHistory, String medicalReport, String medicine) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.insuranceId = insuranceId;
        this.status = status;
        this.registrationDate = registrationDate != null ? new Date(registrationDate.getTime()) : new Date();
        this.lastVisitDate = lastVisitDate != null ? new Date(lastVisitDate.getTime()) : new Date();
        this.medicalHistory = medicalHistory;
        this.medicalReport = medicalReport;
        this.medicine = medicine;
        this.Age = Age;
    }

    
    
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth != null ? new Date(dateOfBirth.getTime()) : null;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth != null ? new Date(dateOfBirth.getTime()) : null;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getMobile() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRegistrationDate() {
        return registrationDate != null ? new Date(registrationDate.getTime()) : null;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate != null ? new Date(registrationDate.getTime()) : null;
    }

    public Date getLastVisitDate() {
        return lastVisitDate != null ? new Date(lastVisitDate.getTime()) : null;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate != null ? new Date(lastVisitDate.getTime()) : null;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getMedicalReport() {
        return medicalReport;
    }

    public void setMedicalReport(String medicalReport) {
        this.medicalReport = medicalReport;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    // Helper methods
    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) {
            fullName.append(firstName);
        }
        if (lastName != null) {
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            fullName.append(lastName);
        }
        return fullName.toString();
    }

    public int getAge() {
        if (dateOfBirth != null) {
            Date now = new Date();
            long diffInMillies = now.getTime() - dateOfBirth.getTime();
            return (int) (diffInMillies / (1000L * 60 * 60 * 24 * 365));
        }
        return 0;
    }

    public boolean isActive() {
        return "Active".equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return "Patient{"
                + "patient_id='" + patientId + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", dateOfBirth=" + dateOfBirth
                + ", gender='" + gender + '\''
                + ", contactNumber='" + contactNumber + '\''
                + ", email='" + email + '\''
                + ", address='" + address + '\''
                + ", emergencyContact='" + emergencyContact + '\''
                + ", insuranceId=" + insuranceId
                + ", status='" + status + '\''
                + ", registrationDate=" + registrationDate
                + ", lastVisitDate=" + lastVisitDate
                + ", medicalHistory='" + medicalHistory + '\''
                + ", medicalReport='" + medicalReport + '\''
                + ", medicine='" + medicine + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        return Objects.equals(patientId, patient.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId);
    }

    /**
     * @param Age the Age to set
     */
    public void setAge(int Age) {
        this.Age = null;
    }
}
