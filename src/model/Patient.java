package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Hiruw
 */
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    private int patientId;
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

    // Default constructor
    public Patient() {
    }

    // Parameterized constructor
    public Patient(int patientId, String firstName, String lastName, Date dateOfBirth, String gender,
                   String contactNumber, String email, String address, String emergencyContact, 
                   Integer insuranceId, String status) {
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
        this.registrationDate = new Date();
        this.lastVisitDate = new Date();
    }

    // Getters and Setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
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
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
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
                + "patientId=" + patientId
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", dateOfBirth=" + dateOfBirth
                + ", gender='" + gender + '\''
                + ", contactNumber='" + contactNumber + '\''
                + ", email='" + email + '\''
                + ", status='" + status + '\''
                + '}';
    }
} 