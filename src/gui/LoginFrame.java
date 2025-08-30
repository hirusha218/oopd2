package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import gui.Dashboard.Admin_Dashboard;
import gui.Dashboard.Doctor_Dashboard;
import gui.Dashboard.Nurse_Dashboard;
import gui.Dashboard.Pharmacist_Dashboard;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.LoginCaretaker;
import utils.LoginMemento;
import utils.PasswordUtils;
import utils.PlaceholderSupport;

/**
 *
 * @author Hiruw
 */
public final class LoginFrame extends javax.swing.JFrame {

    public LoginFrame() {
        initComponents();
        customizeComponents();

    }

    private void customizeComponents() {

        setSize(new Dimension(1200, 700));

        Password.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        Log_in_username.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 36));
        User_roles.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        PlaceholderSupport.addPlaceholder(Log_in_username, "Enter your Username");
        PasswordUtils.setPlaceholder(Password, "Enter your Password");

        Log_in_username.setForeground(new Color(255, 255, 255, 128));

        getContentPane().setBackground(new Color(43, 43, 43));

        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ComboBox.arc", 10);
        UIManager.put("Button.arc", 10);

        Password.setForeground(Color.WHITE);
        Log_in_username.setForeground(Color.WHITE);
        User_roles.setForeground(Color.WHITE);
        jLabel1.setForeground(Color.WHITE);

        Password.setBackground(new Color(60, 63, 65));
        Log_in_username.setBackground(new Color(60, 63, 65));
        User_roles.setBackground(new Color(60, 63, 65));

        Log_in_btn.setBounds(50, 150, 250, 35);
        Log_in_btn.setForeground(Color.BLACK);
        Log_in_btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        Log_in_btn.setFocusPainted(false);
        add(Log_in_btn);

        setVisible(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Log_in_username = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        User_roles = new javax.swing.JComboBox<>();
        Password = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Log_in_btn = new javax.swing.JButton();
        Registration = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);
        setFocusable(false);
        setLocationByPlatform(true);
        setType(java.awt.Window.Type.UTILITY);

        Log_in_username.setDoubleBuffered(true);
        Log_in_username.setDragEnabled(true);
        Log_in_username.setFocusCycleRoot(true);
        Log_in_username.setFocusTraversalPolicyProvider(true);
        Log_in_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Log_in_usernameActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Log In");

        User_roles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Doctor", "Nurse", "Receptionist", "Billing Officer", "Patient" }));

        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Walcome Back");

        jLabel3.setBackground(new java.awt.Color(38, 177, 157));
        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Hiruw\\Downloads\\img_188.png")); // NOI18N

        Log_in_btn.setBackground(new java.awt.Color(38, 239, 198));
        Log_in_btn.setText("Log In");
        Log_in_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Log_in_btnActionPerformed(evt);
            }
        });

        Registration.setText("Other Detils Use Manuval");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel4.setText("GlobeMed");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addComponent(User_roles, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Log_in_username)
                                    .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Log_in_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(Registration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(86, 86, 86)))
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(57, 57, 57)
                        .addComponent(jLabel4)
                        .addGap(169, 169, 169)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(User_roles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(Log_in_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Log_in_btn)
                            .addComponent(Registration)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1216, 709));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordActionPerformed

    }//GEN-LAST:event_PasswordActionPerformed

    private final LoginCaretaker caretaker = new LoginCaretaker();

    private void saveCurrentState() {
        String username = Log_in_username.getText();
        String password = new String(Password.getPassword());
        String role = (String) User_roles.getSelectedItem();

        caretaker.save(new LoginMemento(username, password, role));
    }

    private void Log_in_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Log_in_btnActionPerformed

        String username = Log_in_username.getText().trim();
        String password = new String(Password.getPassword());
        String role = (String) User_roles.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Username and Password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT u.user_id, u.username, u.password_hash, u.role_id, r.role_name "
                + "FROM users u "
                + "JOIN roles r ON u.role_id = r.role_id "
                + "WHERE u.username = ? AND u.password_hash = ? AND r.role_name = ? AND u.status = 'Active'";

        try (java.sql.Connection con = java.sql.DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/globemed1", "root", "9#Jc4$kB2ED"); java.sql.PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);

            try (java.sql.ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    switch (role) {
                        case "Admin" -> new Admin_Dashboard().setVisible(true);
                        case "Doctor" -> new Doctor_Dashboard().setVisible(true);
                        case "Nurse" -> new Nurse_Dashboard().setVisible(true);
                        case "Pharmacist" -> new Pharmacist_Dashboard().setVisible(true);
                        default -> JOptionPane.showMessageDialog(this, "Role Dashboard not implemented yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }

                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials or role!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_Log_in_btnActionPerformed

    private void Log_in_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Log_in_usernameActionPerformed
    }//GEN-LAST:event_Log_in_usernameActionPerformed
public static void main(String[] args) {
    try {
        // Set FlatLaf Look and Feel first
        UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());

        // Optional customization for rounded components
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("ComboBox.arc", 10);
        UIManager.put("Button.arc", 10);

    } catch (UnsupportedLookAndFeelException e) {
        System.err.println("Failed to initialize FlatLaf: " + e.getMessage());
        // Fallback to system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Could not set system look and feel: " + ex.getMessage());
        }
    }

    // Launch the UI on Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
        new LoginFrame().setVisible(true);
    });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Log_in_btn;
    private javax.swing.JTextField Log_in_username;
    private javax.swing.JPasswordField Password;
    private javax.swing.JLabel Registration;
    private javax.swing.JComboBox<String> User_roles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables

}
