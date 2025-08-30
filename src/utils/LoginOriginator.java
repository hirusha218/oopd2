/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Hiruw
 */
public class LoginOriginator {
        private String username;
    private String password;
    private String role;

    public void setState(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public LoginMemento saveState() {
        return new LoginMemento(username, password, role);
    }

    public void restoreState(LoginMemento memento) {
        this.username = memento.getUsername();
        this.password = memento.getPassword();
        this.role = memento.getRole();
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}
