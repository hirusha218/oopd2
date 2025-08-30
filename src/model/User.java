/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Hiruw
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lname;
    private String fname;
    private int userId;
    private String username;
    private String passwordHash;
    private Integer staffId; // Nullable for admin users
    private int roleId;
    private String status;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(int userId, String username, String passwordHash, Integer staffId, int roleId, String status) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.staffId = staffId;
        this.roleId = roleId;
        this.status = status;
        this.fname = fname;
        this.lname = lname;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Optional: ToString (avoid printing password)
    @Override
    public String toString() {
        return "User{"
                + "userId=" + userId
                + ", username='" + username + '\''
                + ", staffId=" + staffId
                + ", roleId=" + roleId
                + ", status='" + status + '\''
                + '}';
    }

    
    public String getLname() {
        return lname;
    }

    public String getFname() {
        return fname;
    }
}
