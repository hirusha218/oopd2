/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.Stack;

/**
 *
 * @author Hiruw
 */
public class LoginCaretaker {

    private Stack<LoginMemento> history = new Stack<>();

    public void addMemento(LoginMemento memento) {
        history.push(memento);
    }

    public LoginMemento getLastMemento() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }

    public void save(LoginMemento loginMemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
