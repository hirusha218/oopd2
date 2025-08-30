package model;

import java.io.Serializable;

/**
 *
 * @author Hiruw
 */
public class Staff implements Serializable {

        private static final long serialVersionUID = 1L;

    private int staffId;
    private String firstName;
    private String lastName;
    private int roleId;
    private String department;
    private String contactNumber;
    private String email;
    private String username;
    private String password;
    private String status;

    // Default constructor
    public Staff() {
    }

    // Parameterized constructor
    public Staff(int staffId, String firstName, String lastName, int roleId, String department, 
                 String contactNumber, String email, String username, String password, String status) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleId = roleId;
        this.department = department;
        this.contactNumber = contactNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ToString method (avoid printing password)
    @Override
    public String toString() {
        return "Staff{"
                + "staffId=" + staffId
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", roleId=" + roleId
                + ", department='" + department + '\''
                + ", contactNumber='" + contactNumber + '\''
                + ", email='" + email + '\''
                + ", username='" + username + '\''
                + ", status='" + status + '\''
                + '}';
    }


    // Helper method to get role name (will be populated from database)
    private String roleName;
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Object getMobile() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
} 