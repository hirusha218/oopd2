package gui.Dashboard;

import dao.AppointmentDAO;
import dao.PatientDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.Appointment;
import model.Patient;
import utils.DBConnection;

/**
 *
 * @author Hiruw
 */
public class Doctor_Dashboard extends javax.swing.JFrame {

   
    
    private JTable patientTable;
    private JTable appointmentTable;
    private JTextField patient_idField;
    private JTextField patient_nameField;
    private JTextField mobileField;
    private JTextField timeField;
    private JTextField dateField;
    
    private Connection conn;

    public Doctor_Dashboard() {
        try {
            initializeDatabaseConnection();
            
            initComponents();
            
            patientTable = patient_Table;
            appointmentTable = jTable2;
            patient_idField = patient_id_2;
            patient_nameField = patient_name_2;
            mobileField = mobile_2;
            timeField = time_1;
            dateField = date_1;

            loadAllAppointments();
            loadAllPatients();
            setupLiveSearch();
            setupPatientLiveSearch();
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error initializing Doctor Dashboard", ex);
            JOptionPane.showMessageDialog(this, "Error initializing dashboard: " + ex.getMessage(), 
                                        "Initialization Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeDatabaseConnection() throws SQLException {
        try {
            conn = DBConnection.getInstance().getConnection();
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Unable to establish database connection");
            }
            
            try (java.sql.PreparedStatement testStmt = conn.prepareStatement("SELECT 1")) {
                testStmt.executeQuery();
            }
            
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.INFO, "Database connection established successfully");
        } catch (Exception ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Database connection error", ex);
            throw new SQLException("Database connection failed: " + ex.getMessage());
        }
    }
    
 
    private boolean isDatabaseConnectionValid() {
        try {
            return conn != null && !conn.isClosed() && conn.isValid(5);
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error checking database connection validity", ex);
            return false;
        }
    }
    
  
    private boolean reconnectToDatabase() {
        try {
            closeDatabaseConnection();
            initializeDatabaseConnection();
            return isDatabaseConnectionValid();
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Failed to reconnect to database", ex);
            JOptionPane.showMessageDialog(this, "Failed to reconnect to database: " + ex.getMessage(), 
                                        "Reconnection Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        patient_id_2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        patient_name_2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        mobile_2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        patient_Table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        time_1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        date_1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDatabaseConnection();
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("GlobeMed");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Doctor Dashboard ");
        
        // Add reconnection button
        javax.swing.JButton reconnectBtn = new javax.swing.JButton("Reconnect DB");
        reconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (reconnectToDatabase()) {
                    JOptionPane.showMessageDialog(Doctor_Dashboard.this, "Database reconnected successfully!", 
                                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshData();
                }
            }
        });

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel7.setText("Patient ID");

        jLabel8.setText("Patient Name");

        patient_name_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patient_name_2ActionPerformed(evt);
            }
        });

        jLabel9.setText("Mobile");

        mobile_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobile_2ActionPerformed(evt);
            }
        });
        
        // Add refresh button for patients
        javax.swing.JButton refreshPatientsBtn = new javax.swing.JButton("Refresh");
        refreshPatientsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAllPatients();
            }
        });
        
        // Add clear button for patients
        javax.swing.JButton clearPatientsBtn = new javax.swing.JButton("Clear");
        clearPatientsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patient_idField.setText("");
                patient_nameField.setText("");
                mobileField.setText("");
                loadAllPatients();
                updateSearchStatusLabel("Ready to search...");
                updateSearchCountLabel(0);
            }
        });
        
        // Add status label for search results
        javax.swing.JLabel searchStatusLabel = new javax.swing.JLabel("Ready to search...");
        searchStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 12));
        searchStatusLabel.setForeground(new java.awt.Color(100, 100, 100));
        
        // Add search count label
        javax.swing.JLabel searchCountLabel = new javax.swing.JLabel("0 patients");
        searchCountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        searchCountLabel.setForeground(new java.awt.Color(0, 100, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(patient_id_2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(patient_name_2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mobile_2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(501, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patient_id_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patient_name_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mobile_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        patient_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Patient ID", "Patient Name", "Mobile", "Medical history ", "Medical Report ", "Medicine"
            }
        ));
        jScrollPane1.setViewportView(patient_Table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Patient Records", jPanel1);

        jLabel11.setText("Time");

        time_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                time_1ActionPerformed(evt);
            }
        });

        jLabel12.setText("Date");

        date_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_1ActionPerformed(evt);
            }
        });
        
        // Add refresh button for appointments
        javax.swing.JButton refreshAppointmentsBtn = new javax.swing.JButton("Refresh");
        refreshAppointmentsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAllAppointments();
            }
        });
        
        // Add clear button for appointments
        javax.swing.JButton clearAppointmentsBtn = new javax.swing.JButton("Clear");
        clearAppointmentsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeField.setText("");
                dateField.setText("");
                loadAllAppointments();
                updateAppointmentSearchStatus("Ready to search appointments...", 0);
            }
        });
        
        // Add status label for appointment search results
        javax.swing.JLabel appointmentSearchStatusLabel = new javax.swing.JLabel("Ready to search appointments...");
        appointmentSearchStatusLabel.setFont(new java.awt.Font("Segoe UI", 0, 12));
        appointmentSearchStatusLabel.setForeground(new java.awt.Color(100, 100, 100));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(time_1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date_1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(644, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Patient Name", "Date", "Time"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Appointment", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(242, 242, 242)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void patient_name_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patient_name_2ActionPerformed
    }//GEN-LAST:event_patient_name_2ActionPerformed

    private void time_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_time_1ActionPerformed
                                             

    }//GEN-LAST:event_time_1ActionPerformed

    private void mobile_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobile_2ActionPerformed
                                                  
    }//GEN-LAST:event_mobile_2ActionPerformed

    private void date_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_1ActionPerformed
    }//GEN-LAST:event_date_1ActionPerformed


    
    private void loadAllAppointments() {
        try {
            if (!isDatabaseConnectionValid()) {
                JOptionPane.showMessageDialog(this, "Database connection not established!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            AppointmentDAO dao = new AppointmentDAO(conn);
            List<Appointment> appointments = dao.getAllAppointments();
            updateAppointmentTable(appointments);
            updateAppointmentSearchStatus("Showing all appointments", appointments.size());
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error loading appointments", ex);
            JOptionPane.showMessageDialog(this, "Error loading appointments: " + ex.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupLiveSearch() {
        // Add search delay to prevent too many database calls while typing
        javax.swing.Timer searchTimer = new javax.swing.Timer(300, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                performAppointmentSearch();
            }
        });
        searchTimer.setRepeats(false);
        
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTimer.restart();
            }
        };

        dateField.getDocument().addDocumentListener(listener);
        timeField.getDocument().addDocumentListener(listener);
        
        appointmentTable.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    displaySelectedAppointmentDetails();
                }
            }
        });
    }
    
    private void performAppointmentSearch() {
        try {
            String dateInput = dateField.getText().trim();
            String timeInput = timeField.getText().trim();

            if (!isDatabaseConnectionValid()) {
                JOptionPane.showMessageDialog(Doctor_Dashboard.this, "Database connection not established!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            AppointmentDAO dao = new AppointmentDAO(conn);
            List<Appointment> appointments;
            
            String formattedDate = validateAndFormatDate(dateInput);
            String formattedTime = validateAndFormatTime(timeInput);
            
            if (dateInput.isEmpty() && timeInput.isEmpty()) {
                appointments = dao.getAllAppointments();
                updateAppointmentSearchStatus("Showing all appointments", appointments.size());
            } else {
                appointments = dao.searchAppointments(formattedDate, formattedTime);
                showAppointmentSearchStatus(appointments.size(), dateInput, timeInput);
            }
            
            updateAppointmentTable(appointments);
            
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error searching appointments", ex);
            JOptionPane.showMessageDialog(Doctor_Dashboard.this, "Error searching appointments: " + ex.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String validateAndFormatDate(String dateInput) {
        if (dateInput.isEmpty()) {
            return "";
        }
        
        try {
            if (dateInput.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                String[] parts = dateInput.split("/");
                return String.format("%04d-%02d-%02d", 
                    Integer.parseInt(parts[2]), 
                    Integer.parseInt(parts[0]), 
                    Integer.parseInt(parts[1]));
            } else if (dateInput.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                return dateInput;
            } else if (dateInput.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
                String[] parts = dateInput.split("-");
                return String.format("%04d-%02d-%02d", 
                    Integer.parseInt(parts[2]), 
                    Integer.parseInt(parts[0]), 
                    Integer.parseInt(parts[1]));
            } else {
                // Try to parse as today's date if just a number (day)
                if (dateInput.matches("\\d{1,2}")) {
                    java.time.LocalDate today = java.time.LocalDate.now();
                    return String.format("%04d-%02d-%02d", 
                        today.getYear(), 
                        today.getMonthValue(), 
                        Integer.parseInt(dateInput));
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.WARNING, "Invalid date format: " + dateInput, e);
        }
        
        return dateInput; 
    }
    
    private String validateAndFormatTime(String timeInput) {
        if (timeInput.isEmpty()) {
            return "";
        }
        
        try {
            if (timeInput.matches("\\d{1,2}:\\d{2}")) {
                return timeInput;
            } else if (timeInput.matches("\\d{1,2}:\\d{2}:\\d{2}")) {
                return timeInput;
            } else if (timeInput.matches("\\d{1,2}")) {
                return timeInput + ":00";
            } else if (timeInput.matches("\\d{1,2}\\s*[AaPp][Mm]")) {
                String time = timeInput.replaceAll("\\s", "").toUpperCase();
                if (time.endsWith("PM") && !time.startsWith("12")) {
                    int hour = Integer.parseInt(time.substring(0, time.length() - 2));
                    return String.format("%02d:00", hour + 12);
                } else if (time.endsWith("AM") && time.startsWith("12")) {
                    return "00:00";
                } else {
                    int hour = Integer.parseInt(time.substring(0, time.length() - 2));
                    return String.format("%02d:00", hour);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.WARNING, "Invalid time format: " + timeInput, e);
        }
        
        return timeInput; 
    }
    
    private String getTodayDate() {
        java.time.LocalDate today = java.time.LocalDate.now();
        return String.format("%04d-%02d-%02d", 
            today.getYear(), 
            today.getMonthValue(), 
            today.getDayOfMonth());
    }

    private String getCurrentTime() {
        java.time.LocalTime now = java.time.LocalTime.now();
        return String.format("%02d:%02d", now.getHour(), now.getMinute());
    }
    
    private void addQuickDateButtons() {
        javax.swing.JButton todayBtn = new javax.swing.JButton("Today");
        todayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateField.setText(getTodayDate());
            }
        });
        
        javax.swing.JButton tomorrowBtn = new javax.swing.JButton("Tomorrow");
        tomorrowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                java.time.LocalDate tomorrow = java.time.LocalDate.now().plusDays(1);
                dateField.setText(String.format("%04d-%02d-%02d", 
                    tomorrow.getYear(), 
                    tomorrow.getMonthValue(), 
                    tomorrow.getDayOfMonth()));
            }
        });
    }
    
    private void updateAppointmentTable(List<Appointment> appointments) {
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);
        
        if (appointments.isEmpty()) {
            model.addRow(new Object[]{"No appointments found", "", ""});
            return;
        }
        
        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                a.getPatientName(),
                a.getAppointmentDate(),
                a.getAppointmentTime()
            });
        }
    }
    
    private void showAppointmentSearchStatus(int resultCount, String date, String time) {
        StringBuilder statusMessage = new StringBuilder();
        
        statusMessage.append("Search results: ");
        if (!date.isEmpty()) statusMessage.append("Date: '").append(date).append("' ");
        if (!time.isEmpty()) statusMessage.append("Time: '").append(time).append("' ");
        
        statusMessage.append(" - Found ").append(resultCount).append(" appointment(s)");
        
        updateAppointmentSearchStatus(statusMessage.toString(), resultCount);
    }
    

    private void updateAppointmentSearchStatus(String status, int count) {
        for (java.awt.Component comp : jPanel9.getComponents()) {
            if (comp instanceof javax.swing.JLabel && 
                ((javax.swing.JLabel) comp).getText().contains("Ready to search appointments")) {
                ((javax.swing.JLabel) comp).setText(status);
                break;
            }
        }
        
        System.out.println(status + " - Count: " + count);
    }
    

    private void displaySelectedAppointmentDetails() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            String patientName = appointmentTable.getValueAt(selectedRow, 0).toString();
            String date = appointmentTable.getValueAt(selectedRow, 1).toString();
            String time = appointmentTable.getValueAt(selectedRow, 2).toString();
            
            try {
                if (date != null && !date.isEmpty()) {
                    dateField.setText(date);
                }
                if (time != null && !time.isEmpty()) {
                    timeField.setText(time);
                }
            } catch (Exception ex) {
                Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.WARNING, "Error parsing appointment data", ex);
            }
        }
    }

    private void loadAllPatients() {
        try {
            if (!isDatabaseConnectionValid()) {
                JOptionPane.showMessageDialog(this, "Database connection not established!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PatientDAO dao = new PatientDAO(conn);
            List<Patient> patients = dao.getAllPatients();
            updatePatientTable(patients);
            updateSearchCountLabel(patients.size());
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error loading patients", ex);
            JOptionPane.showMessageDialog(this, "Error loading patients: " + ex.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupPatientLiveSearch() {
        javax.swing.Timer searchTimer = new javax.swing.Timer(300, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                performLiveSearch();
            }

            private void performLiveSearch() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        searchTimer.setRepeats(false);
        
        searchTimer.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateSearchStatusLabel("Searching...");
            }
        });
        
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchTimer.restart();
            }

            private void performLiveSearch() {
                try {
                    String id = patient_idField.getText().trim();
                    String name = patient_nameField.getText().trim();
                    String mobile = mobileField.getText().trim();

                    if (!isDatabaseConnectionValid()) {
                        JOptionPane.showMessageDialog(Doctor_Dashboard.this, "Database connection not established!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    PatientDAO dao = new PatientDAO(conn);
                    List<Patient> patients;
                    
                    if (id.isEmpty() && name.isEmpty() && mobile.isEmpty()) {
                        patients = dao.getAllPatients();
                        updateSearchStatusLabel("Showing all patients");
                    } else {
                        patients = dao.searchPatients(id, name, mobile);
                        showSearchStatus(patients.size(), id, name, mobile);
                    }
                    
                    updatePatientTable(patients);
                    
                    updateSearchCountLabel(patients.size());
                    
                } catch (SQLException ex) {
                    Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error searching patients", ex);
                    JOptionPane.showMessageDialog(Doctor_Dashboard.this, "Error searching patients: " + ex.getMessage(), 
                                                "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        patient_idField.getDocument().addDocumentListener(listener);
        patient_nameField.getDocument().addDocumentListener(listener);
        mobileField.getDocument().addDocumentListener(listener);
        
        patientTable.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    displaySelectedPatientDetails();
                }
            }
        });
    }
    

    private void updatePatientTable(List<Patient> patients) {
        DefaultTableModel model = (DefaultTableModel) patientTable.getModel();
        model.setRowCount(0);
        
        if (patients.isEmpty()) {
            model.addRow(new Object[]{"No results found", "", "", "", "", ""});
            return;
        }
        
        for (Patient p : patients) {
            model.addRow(new Object[]{
                p.getPatientId(),
                p.getFullName(),
                p.getContactNumber(),
                p.getMedicalHistory() != null ? p.getMedicalHistory() : "",
                p.getMedicalReport() != null ? p.getMedicalReport() : "",
                p.getMedicine() != null ? p.getMedicine() : ""
            });
        }
    }
    

    private void showSearchStatus(int resultCount, String id, String name, String mobile) {
        StringBuilder statusMessage = new StringBuilder();
        
        if (id.isEmpty() && name.isEmpty() && mobile.isEmpty()) {
            statusMessage.append("Showing all patients");
        } else {
            statusMessage.append("Search results: ");
            if (!id.isEmpty()) statusMessage.append("ID contains '").append(id).append("' ");
            if (!name.isEmpty()) statusMessage.append("Name contains '").append(name).append("' ");
            if (!mobile.isEmpty()) statusMessage.append("Mobile contains '").append(mobile).append("' ");
        }
        
        statusMessage.append(" - Found ").append(resultCount).append(" patient(s)");
        
        updateSearchStatusLabel(statusMessage.toString());
    }
    

    private void updateSearchStatusLabel(String status) {
        for (java.awt.Component comp : jPanel8.getComponents()) {
            if (comp instanceof javax.swing.JLabel && 
                ((javax.swing.JLabel) comp).getText().contains("Ready to search")) {
                ((javax.swing.JLabel) comp).setText(status);
                break;
            }
        }
    }
    

    private void updateSearchCountLabel(int count) {
        for (java.awt.Component comp : jPanel8.getComponents()) {
            if (comp instanceof javax.swing.JLabel && 
                ((javax.swing.JLabel) comp).getText().contains("patients")) {
                ((javax.swing.JLabel) comp).setText(count + " patient(s)");
                break;
            }
        }
    }

    private void displaySelectedPatientDetails() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            String patientId = patientTable.getValueAt(selectedRow, 0).toString();
            String patientName = patientTable.getValueAt(selectedRow, 1).toString();
            String mobile = patientTable.getValueAt(selectedRow, 2).toString();
            
            patient_idField.setText(patientId);
            patient_nameField.setText(patientName);
            mobileField.setText(mobile);
        }
    }
    

    public void refreshData() {
        try {
            loadAllPatients();
            loadAllAppointments();
        } catch (Exception ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error refreshing data", ex);
            JOptionPane.showMessageDialog(this, "Error refreshing data: " + ex.getMessage(), 
                                        "Refresh Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void closeDatabaseConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.INFO, "Database connection closed");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error closing database connection", ex);
        }
    }
    

    public void exportPatientData() {
        try {
            if (!isDatabaseConnectionValid()) {
                JOptionPane.showMessageDialog(this, "Database connection not established!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            PatientDAO dao = new PatientDAO(conn);
            List<Patient> patients = dao.getAllPatients();
            
            StringBuilder exportData = new StringBuilder();
            exportData.append("Patient ID,Patient Name,Mobile,Medical History,Medical Report,Medicine\n");
            
            for (Patient p : patients) {
                exportData.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                    p.getPatientId(),
                    p.getFullName(),
                    p.getContactNumber(),
                    p.getMedicalHistory() != null ? p.getMedicalHistory() : "",
                    p.getMedicalReport() != null ? p.getMedicalReport() : "",
                    p.getMedicine() != null ? p.getMedicine() : ""
                ));
            }
            
            javax.swing.JTextArea textArea = new javax.swing.JTextArea(exportData.toString());
            textArea.setEditable(false);
            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Patient Data Export", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(Doctor_Dashboard.class.getName()).log(Level.SEVERE, "Error exporting patient data", ex);
            JOptionPane.showMessageDialog(this, "Error exporting data: " + ex.getMessage(), 
                                        "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField date_1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField mobile_2;
    private javax.swing.JTable patient_Table;
    private javax.swing.JTextField patient_id_2;
    private javax.swing.JTextField patient_name_2;
    private javax.swing.JTextField time_1;
    // End of variables declaration//GEN-END:variables

}