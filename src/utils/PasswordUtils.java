/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Hiruw
 */
public class PasswordUtils {
   
    public static void setPlaceholder(JPasswordField passwordField, String placeholder) {
        passwordField.setEchoChar((char)0); 
        passwordField.setForeground(Color.GRAY);
        passwordField.setText(placeholder);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.WHITE); 
                    passwordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char)0);
                }
            }
        });
    }

    public static void enableShowHide(JPasswordField passwordField, JButton toggleButton) {
        toggleButton.setText("Show");
        toggleButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == 0) {
                passwordField.setEchoChar('•');
                toggleButton.setText("👁");
            } else {
                passwordField.setEchoChar((char)0);
                toggleButton.setText("👁");
            }
        });
    }
}