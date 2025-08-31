package gui.Dashboard;

import dao.StaffDAO;
import dao.StockDAO;
import dao.PatientDAO;
import dao.BillingDAO;
import java.math.BigDecimal;
import model.Staff;
import model.Stock;
import model.Patient;
import model.Billing;
import utils.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hiruw
 */
public class Admin_Dashboard extends javax.swing.JFrame {
    
    private StaffDAO staffDAO;
    private StockDAO stockDAO;
    private PatientDAO patientDAO;
    private BillingDAO billingDAO;
    
    private DefaultTableModel staffTableModel;
    private DefaultTableModel stockTableModel;
    private DefaultTableModel patientTableModel;
    private DefaultTableModel billingTableModel;
    
    private Staff selectedStaff;
    private Stock selectedStock;
    private Patient selectedPatient;
    private Billing selectedBilling;
    
    private boolean isEditMode = false;
    
    public Admin_Dashboard() {
        initComponents();
        initializeAllPanels();
    }
    
    private void initializeAllPanels() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            staffDAO = new StaffDAO(connection);
            stockDAO = new StockDAO(connection);
            patientDAO = new PatientDAO(connection);
            billingDAO = new BillingDAO(connection);
            
            initializeStaffManagement();
            initializeStockManagement();
            initializePatientManagement();
            initializeBillingManagement();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error initializing panels: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeStaffManagement() {
        try {
            staffTableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; 
                }
            };
            
            String[] columnNames = {"Staff ID", "First Name", "Last Name", "Role", "Contact Number", "Username", "Department", "Email", "Status"};
            for (String columnName : columnNames) {
                staffTableModel.addColumn(columnName);
            }
            
            Staff_table.setModel(staffTableModel);
            
            loadStaffData();
            
            Staff_table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    handleTableSelection();
                }
            });
            
            // Setup search listeners for all three search fields
            setupStaffSearchListeners();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing staff management: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeStockManagement() {
        try {
            stockTableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };

            String[] columnNames = {"Stock ID", "Name", "Category", "Quantity", "Unit Price", "Expiry Date", "Status"};
            for (String columnName : columnNames) {
                stockTableModel.addColumn(columnName);
            }

            stok_table.setModel(stockTableModel);

            loadStockData();

            stok_table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    handleStockTableSelection();
                }
            });
            
            // Setup search listeners for stock search fields
            setupStockSearchListeners();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing stock management: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializePatientManagement() {
        try {
            patientTableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };
            String[] columnNames = {"Patient ID", "First Name", "Last Name", "Gender", "Contact Number", "Email", "Status"};
            for (String columnName : columnNames) {
                patientTableModel.addColumn(columnName);
            }
            
            patient_table.setModel(patientTableModel);
            
            loadPatientData();
            
            patient_table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    handlePatientTableSelection();
                }
            });
            
            // Setup search listeners for patient search fields
            setupPatientSearchListeners();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing patient management: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initializeBillingManagement() {
        try {
            billingTableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };
            
            String[] columnNames = {"Bill ID", "Patient Name", "Doctor Name", "Total Amount", "Payment Status", "Bill Date"};
            for (String columnName : columnNames) {
                billingTableModel.addColumn(columnName);
            }
            
            bill_table.setModel(billingTableModel);
            
            loadBillingData();
            
            bill_table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    handleBillingTableSelection();
                }
            });
            
            // Setup search listeners for billing search fields
            setupBillingSearchListeners();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing billing management: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        catagory = new javax.swing.JTextField();
        u_name = new javax.swing.JTextField();
        u_price = new javax.swing.JTextField();
        u_ex_date = new javax.swing.JTextField();
        u_update = new javax.swing.JButton();
        u_add = new javax.swing.JButton();
        u_delete = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        u_status = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        u_id = new javax.swing.JTextField();
        catagory1 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        qty1 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        u_name1 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        u_ex_date1 = new javax.swing.JTextField();
        u_status1 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        stok_table = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        s_name = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        s_role1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        s_username1 = new javax.swing.JTextField();
        clearSearchBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Staff_table = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        s_lname1 = new javax.swing.JTextField();
        s_role = new javax.swing.JTextField();
        s_mobile = new javax.swing.JTextField();
        s_department = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        s_btn_1 = new javax.swing.JButton();
        s_btn_2 = new javax.swing.JButton();
        s_btn_3 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        s_username = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        s_password = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        s_fname1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        s_department1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        patient_id_2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        patient_name_2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        mobile_2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        patient_table = new javax.swing.JTable();
        jLabel51 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        billing_id = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        bill_p_name = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        bil_d_name = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        bil_amount = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        bill_p_stutas = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        bill_date_form = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        bill_date_to = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        bill_table = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        billing_id_1 = new javax.swing.JTextField();
        bill_p_name_1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        bil_d_name_1 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        bil_amount_1 = new javax.swing.JTextField();
        bill_p_stutas_1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        b_btn_1 = new javax.swing.JButton();
        b_btn_2 = new javax.swing.JButton();
        b_btn_3 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setFocusTraversalPolicyProvider(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setInheritsPopupMenu(true);

        jLabel36.setText("Name ");

        jLabel37.setText("Catagory");

        jLabel38.setText("Qty");

        jLabel39.setText("Unit Price");

        jLabel40.setText("Ex_Date");

        qty.setText(" ");

        u_price.setText(" ");
        u_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_priceActionPerformed(evt);
            }
        });

        u_ex_date.setText(" ");

        u_update.setText("Update");
        u_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_updateActionPerformed(evt);
            }
        });

        u_add.setText("Add");
        u_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_addActionPerformed(evt);
            }
        });

        u_delete.setText("Delete");
        u_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_deleteActionPerformed(evt);
            }
        });

        jLabel42.setText("Status");

        u_status.setText(" ");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(u_status, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jLabel42)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(u_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(u_update, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(u_add, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel36)
                    .addComponent(u_name, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(catagory)
                    .addComponent(qty)
                    .addComponent(u_price)
                    .addComponent(u_ex_date))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(u_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addGap(21, 21, 21)
                .addComponent(catagory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38)
                .addGap(18, 18, 18)
                .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addGap(18, 18, 18)
                .addComponent(u_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel40)
                .addGap(21, 21, 21)
                .addComponent(u_ex_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel42)
                .addGap(18, 18, 18)
                .addComponent(u_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(u_add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(u_update)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(u_delete)
                .addGap(32, 32, 32))
        );

        jLabel41.setText("ID");

        u_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_idActionPerformed(evt);
            }
        });

        jLabel43.setText("Catagory");

        jLabel44.setText("Qty");

        qty1.setText(" ");

        jLabel45.setText("Name");

        u_name1.setText(" ");
        u_name1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_name1ActionPerformed(evt);
            }
        });

        jLabel46.setText("Ex_Date");

        u_ex_date1.setText(" ");
        u_ex_date1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_ex_date1ActionPerformed(evt);
            }
        });

        u_status1.setText(" ");

        jLabel47.setText("Status");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addGap(21, 21, 21)))
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(u_ex_date1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(u_name1))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(18, 18, 18)
                        .addComponent(u_id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel43)
                        .addGap(21, 21, 21)
                        .addComponent(catagory1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel44)
                        .addGap(21, 21, 21)
                        .addComponent(qty1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addGap(21, 21, 21)
                        .addComponent(u_status1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(u_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(catagory1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(qty1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(u_name1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel46)
                        .addComponent(u_ex_date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel47)
                        .addComponent(u_status1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        stok_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Name", "Catagory", "Qty", "Unit Price", "Ex_Date", "Status"
            }
        ));
        jScrollPane4.setViewportView(stok_table);

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel48.setText("Stok Managemnt");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel48)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel48)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4)
                        .addGap(35, 35, 35))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Stok Managemnt", jPanel5);

        jLabel10.setText("Name");
        jPanel9.add(jLabel10);

        s_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_nameActionPerformed(evt);
            }
        });
        jPanel9.add(s_name);

        jLabel11.setText("Role");
        jPanel9.add(jLabel11);

        s_role1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_role1ActionPerformed(evt);
            }
        });
        jPanel9.add(s_role1);

        jLabel12.setText("Username");
        jPanel9.add(jLabel12);
        jPanel9.add(s_username1);

        clearSearchBtn.setText("Clear Search");
        clearSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSearchBtnActionPerformed(evt);
            }
        });
        jPanel9.add(clearSearchBtn);

        Staff_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Full Name ", "Role", "Mobile", "Username", "Department", "Passowrd"
            }
        ));
        jScrollPane2.setViewportView(Staff_table);

        s_lname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_lname1ActionPerformed(evt);
            }
        });

        s_department.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_departmentActionPerformed(evt);
            }
        });

        jLabel13.setText("Last Name");

        jLabel14.setText("Role");

        jLabel15.setText("Mobile");

        jLabel16.setText("Department");

        s_btn_1.setText("Add");
        s_btn_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_btn_1ActionPerformed(evt);
            }
        });

        s_btn_2.setText("Update ");
        s_btn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_btn_2ActionPerformed(evt);
            }
        });

        s_btn_3.setText("Delete");
        s_btn_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_btn_3ActionPerformed(evt);
            }
        });

        jLabel17.setText("Username");

        jLabel18.setText("Passowrd");

        s_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_passwordActionPerformed(evt);
            }
        });

        jLabel24.setText("First_Name");

        jLabel25.setText("Email");

        s_department1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_department1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(s_btn_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(s_btn_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(s_btn_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap(10, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(s_lname1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                .addComponent(s_role)
                                .addComponent(s_mobile)
                                .addComponent(jLabel14))
                            .addComponent(jLabel17)
                            .addComponent(s_username, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(s_password, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel25)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(s_fname1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                                        .addComponent(jLabel24))
                                    .addComponent(s_department, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)))
                            .addComponent(s_department1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_fname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_lname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_role, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_department, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s_department1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 18, Short.MAX_VALUE)
                .addComponent(s_btn_1)
                .addGap(16, 16, 16)
                .addComponent(s_btn_2)
                .addGap(18, 18, 18)
                .addComponent(s_btn_3)
                .addGap(12, 12, 12))
        );

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel49.setText("Staff Management");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel49)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab(" Staff Management", jPanel6);

        jPanel2.setAutoscrolls(true);
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setVerifyInputWhenFocusTarget(false);

        jLabel7.setText("Patient ID");

        jLabel8.setText("Patient Name");

        patient_name_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patient_name_2ActionPerformed(evt);
            }
        });

        jLabel9.setText("Mobile");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(patient_id_2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(patient_name_2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mobile_2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(443, Short.MAX_VALUE))
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

        patient_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Patient ID", "Mobile", "Adderss"
            }
        ));
        jScrollPane1.setViewportView(patient_table);

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel51.setText("Patient Records");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addGap(5, 5, 5)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jTabbedPane1.addTab("Patient Records", jPanel2);
        jPanel2.getAccessibleContext().setAccessibleDescription("");

        jLabel19.setText("Billing Id");

        jLabel20.setText("Patient Name");

        bill_p_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_p_nameActionPerformed(evt);
            }
        });

        jLabel21.setText("Docter Name");

        jLabel22.setText("Total Amounet");

        jLabel23.setText("Payment Stutas");

        jLabel33.setText("Date");

        jLabel34.setText("Form");

        jLabel35.setText("To");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(bill_p_stutas, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(billing_id, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(bill_p_name, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(75, 75, 75)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bil_d_name, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bil_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel33)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bill_date_form, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel35)
                        .addGap(18, 18, 18)
                        .addComponent(bill_date_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(billing_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bill_p_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bil_d_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bil_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bill_p_stutas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(bill_date_form, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(bill_date_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bill_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Billing Id", "Patient Name", "Docter Name", "Total Amounet", "Payment Stutas"
            }
        ));
        jScrollPane3.setViewportView(bill_table);

        jLabel28.setText("Billing Id");

        bill_p_name_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_p_name_1ActionPerformed(evt);
            }
        });

        jLabel29.setText("Patient Name");

        jLabel30.setText("Docter Name");

        jLabel31.setText("Total Amounet");

        jLabel32.setText("Payment Stutas");

        b_btn_1.setText("Add");
        b_btn_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_btn_1ActionPerformed(evt);
            }
        });

        b_btn_2.setText("Update");
        b_btn_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_btn_2ActionPerformed(evt);
            }
        });

        b_btn_3.setText("Delete");
        b_btn_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_btn_3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(bill_p_name_1)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bil_d_name_1)
                            .addComponent(billing_id_1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bill_p_stutas_1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bil_amount_1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(b_btn_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_btn_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(b_btn_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addComponent(billing_id_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bill_p_name_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bil_d_name_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bill_p_stutas_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bil_amount_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(b_btn_1)
                .addGap(18, 18, 18)
                .addComponent(b_btn_2)
                .addGap(18, 18, 18)
                .addComponent(b_btn_3)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel52.setText("Billing & Claims");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel52)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Billing & Claims", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1220, 650));
        jTabbedPane1.getAccessibleContext().setAccessibleName("Dashborad");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("GlobeMed");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Admin Dashboard ");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 22, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_btn_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_btn_3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b_btn_3ActionPerformed

    private void bill_p_name_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_p_name_1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bill_p_name_1ActionPerformed

    private void bill_p_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_p_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bill_p_nameActionPerformed

    private void patient_name_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patient_name_2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_patient_name_2ActionPerformed

    private void s_departmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_departmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_departmentActionPerformed

    private void s_role1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_role1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_role1ActionPerformed

    private void u_ex_date1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_ex_date1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_ex_date1ActionPerformed

    private void u_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_priceActionPerformed

    private void s_department1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_department1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_department1ActionPerformed

    private void s_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_passwordActionPerformed

    private void s_lname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_lname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_lname1ActionPerformed

    private void s_btn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_btn_1ActionPerformed
        
        if (!validateForm()) {
            return;
        }
        
        try {
            if (staffDAO.isUsernameExists(s_username.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                s_username.requestFocus();
                return;
            }
            
            Staff newStaff = getStaffFromForm();
            String password = s_password.getText().trim();
            if (staffDAO.createStaff(newStaff, password)) {
                JOptionPane.showMessageDialog(this, "Staff member added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadStaffData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add staff member.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_s_btn_1ActionPerformed

    private void s_btn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_btn_2ActionPerformed
        if (selectedStaff == null) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to update.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            Staff updatedStaff = getStaffFromForm();

            if (!updatedStaff.getUsername().equals(selectedStaff.getUsername())
                    && staffDAO.isUsernameExists(updatedStaff.getUsername())) {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                s_username.requestFocus();
                return;
            }
            
            String password = s_password.getText().trim();
            if (staffDAO.updateStaff(updatedStaff, password)) {
                JOptionPane.showMessageDialog(this, "Staff member updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadStaffData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update staff member.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_s_btn_2ActionPerformed

    private void s_btn_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_btn_3ActionPerformed
        
        if (selectedStaff == null) {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete staff member: " + selectedStaff.getFullName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (staffDAO.deleteStaff(selectedStaff.getStaffId())) {
                    JOptionPane.showMessageDialog(this, "Staff member deleted successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFormFields();
                    loadStaffData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete staff member.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_s_btn_3ActionPerformed

    private void s_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_nameActionPerformed
        performSearch();
    }//GEN-LAST:event_s_nameActionPerformed

    private void clearSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSearchBtnActionPerformed
        clearSearchFields();
    }//GEN-LAST:event_clearSearchBtnActionPerformed

    private void clearStockSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearStockSearchBtnActionPerformed
        clearStockSearchFields();
    }//GEN-LAST:event_clearStockSearchBtnActionPerformed

    private void clearBillingSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBillingSearchBtnActionPerformed
        clearBillingSearchFields();
    }//GEN-LAST:event_clearBillingSearchBtnActionPerformed

    private void u_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_deleteActionPerformed
        if (selectedStock == null) {
            JOptionPane.showMessageDialog(this, "Please select a stock item to delete.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete stock item: " + selectedStock.getName() + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (stockDAO.deleteStock(selectedStock.getStockId())) {
                    JOptionPane.showMessageDialog(this, "Stock item deleted successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearStockFormFields();
                    loadStockData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete stock item.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }    }//GEN-LAST:event_u_deleteActionPerformed

    private void b_btn_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_btn_1ActionPerformed
        try {
            List<Billing> unpaidBills = billingDAO.searchBilling("Unpaid");
            billingTableModel.setRowCount(0);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Billing billing : unpaidBills) {
                billingTableModel.addRow(new Object[]{
                    billing.getBillId(),
                    billing.getPatientName(),
                    billing.getDoctorName(),
                    billing.getFormattedAmount(),
                    billing.getPaymentStatus(),
                    billing.getBillDate() != null ? dateFormat.format(billing.getBillDate()) : "N/A"
                });
            }
            JOptionPane.showMessageDialog(this, "Filtered Unpaid Bills", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error filtering bills: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_b_btn_1ActionPerformed

    private void b_btn_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_btn_2ActionPerformed
        if (selectedBilling == null) {
            JOptionPane.showMessageDialog(this, "Please select a billing record to update.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateForm()) {
            return;
        }
        
        try {
            Billing updatedBilling = billingDAO.getBillingById(WIDTH);
            if (billingDAO.updateBilling(updatedBilling)) {
                JOptionPane.showMessageDialog(this, "Billing record updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFormFields();
                loadBillingData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update billing record.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_b_btn_2ActionPerformed

    private void u_name1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_name1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_name1ActionPerformed

    private void u_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_idActionPerformed

    // Staff Management Helper Methods
    private void loadStaffData() {
        try {
            List<Staff> staffList = staffDAO.getAllStaff();
            staffTableModel.setRowCount(0); // Clear existing rows

            for (Staff staff : staffList) {
                Object[] row = {
                    staff.getStaffId(),
                    staff.getFirstName(),
                    staff.getLastName(),
                    staff.getRoleName(),
                    staff.getContactNumber(),
                    staff.getUsername(),
                    staff.getDepartment(),
                    staff.getEmail(),
                    staff.getStatus()
                };
                staffTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading staff data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleTableSelection() {
        int selectedRow = Staff_table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedStaff = new Staff();
            selectedStaff.setStaffId((Integer) staffTableModel.getValueAt(selectedRow, 0));
            selectedStaff.setFirstName((String) staffTableModel.getValueAt(selectedRow, 1));
            selectedStaff.setLastName((String) staffTableModel.getValueAt(selectedRow, 2));
            try {
                Staff fullStaff = staffDAO.getStaffById(selectedStaff.getStaffId());
                if (fullStaff != null) {
                    selectedStaff = fullStaff;
                    populateFormFields(selectedStaff);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading staff details: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateFormFields(Staff staff) {
        s_fname1.setText(staff.getFirstName());
        s_lname1.setText(staff.getLastName());
        s_role.setText(staff.getRoleName()); 
        s_mobile.setText(staff.getContactNumber());
        s_username.setText(staff.getUsername());
        s_password.setText(""); 
        s_department.setText(staff.getDepartment());
        s_department1.setText(staff.getEmail());
    }
    
    private void clearFormFields() {
        s_fname1.setText("");
        s_lname1.setText("");
        s_role.setText("");
        s_mobile.setText("");
        s_username.setText("");
        s_password.setText("");
        s_department.setText("");
        s_department1.setText("");
        selectedStaff = null;
        isEditMode = false;
    }
    
    private Staff getStaffFromForm() {
        Staff staff = new Staff();
        if (selectedStaff != null) {
            staff.setStaffId(selectedStaff.getStaffId());
        }
        staff.setFirstName(s_fname1.getText().trim());
        staff.setLastName(s_lname1.getText().trim());
        // Convert role name to role ID
        try {
            int roleId = staffDAO.getRoleIdByName(s_role.getText().trim());
            if (roleId == -1) {
                throw new IllegalArgumentException("Invalid role: " + s_role.getText().trim());
            }
            staff.setRoleId(roleId);
        } catch (SQLException ex) {
            throw new RuntimeException("Error getting role ID: " + ex.getMessage());
        }
        staff.setContactNumber(s_mobile.getText().trim());
        staff.setUsername(s_username.getText().trim());
        staff.setPassword(s_password.getText().trim());
        staff.setDepartment(s_department.getText().trim());
        staff.setEmail(s_department1.getText().trim());
        staff.setStatus("Active");
        return staff;
    }
    
    private boolean validateForm() {
        if (s_fname1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter First Name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            s_fname1.requestFocus();
            return false;
        }
        if (s_lname1.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Last Name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            s_lname1.requestFocus();
            return false;
        }
        if (s_role.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Role", "Validation Error", JOptionPane.WARNING_MESSAGE);
            s_role.requestFocus();
            return false;
        }
        if (s_username.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Username", "Validation Error", JOptionPane.WARNING_MESSAGE);
            s_username.requestFocus();
            return false;
        }
        if (s_password.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Password", "Validation Error", JOptionPane.WARNING_MESSAGE);
            s_password.requestFocus();
            return false;
        }
        return true;
    }

    // Search functionality
    private void performSearch() {
        String nameSearch = s_name.getText().trim();
        String roleSearch = s_role1.getText().trim();
        String usernameSearch = s_username1.getText().trim();
        
        // If all search fields are empty, load all staff
        if (nameSearch.isEmpty() && roleSearch.isEmpty() && usernameSearch.isEmpty()) {
            loadStaffData(); 
            return;
        }
        
        try {
            List<Staff> searchResults = staffDAO.searchStaffAdvanced(nameSearch, roleSearch, usernameSearch);
            staffTableModel.setRowCount(0); 

            for (Staff staff : searchResults) {
                Object[] row = {
                    staff.getStaffId(),
                    staff.getFirstName(),
                    staff.getLastName(),
                    staff.getRoleName(),
                    staff.getContactNumber(),
                    staff.getUsername(),
                    staff.getDepartment(),
                    staff.getEmail(),
                    staff.getStatus()
                };
                staffTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No staff members found matching the search criteria.",
                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
//                JOptionPane.showMessageDialog(this, "Found " + searchResults.size() + " staff member(s) matching the search criteria.",
//                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Setup search listeners for all three search fields
    private void setupStaffSearchListeners() {
        // Add document listeners to all search fields
        s_name.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
        
        s_role1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
        
        s_username1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
    }
    
    // Clear search fields
    private void clearSearchFields() {
        s_name.setText("");
        s_role1.setText("");
        s_username1.setText("");
        loadStaffData(); // Reload all data when search is cleared
    }
    
    // Stock Management Search Methods
    private void setupStockSearchListeners() {
        // Add document listeners to all stock search fields
        u_name1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
        });
        
        u_id.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
        });
        
        catagory1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
        });
        
        qty1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
        });
        
        u_ex_date1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
        });
        
        u_status1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performStockSearch(); }
        });
    }
    
    private void performStockSearch() {
        String nameSearch = u_name1.getText().trim();
        String idSearch = u_id.getText().trim();
        String categorySearch = catagory1.getText().trim();
        String qtySearch = qty1.getText().trim();
        String dateSearch = u_ex_date1.getText().trim();
        String statusSearch = u_status1.getText().trim();
        
        // If all search fields are empty, load all stock
        if (nameSearch.isEmpty() && idSearch.isEmpty() && categorySearch.isEmpty() && 
            qtySearch.isEmpty() && dateSearch.isEmpty() && statusSearch.isEmpty()) {
            loadStockData();
            return;
        }
        
        try {
            List<Stock> searchResults = stockDAO.searchStockAdvanced(nameSearch, idSearch, categorySearch, 
                                                                   qtySearch, dateSearch, statusSearch);
            stockTableModel.setRowCount(0);

            for (Stock stock : searchResults) {
                Object[] row = {
                    stock.getStockId(),
                    stock.getName(),
                    stock.getCategory(),
                    stock.getQuantity(),
                    stock.getUnitPrice() != null ? "$" + stock.getUnitPrice().toString() : "$0.00",
                    stock.getExpiryDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(stock.getExpiryDate()) : "N/A",
                    stock.getStatus()
                };
                stockTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No stock items found matching the search criteria.",
                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // JOptionPane.showMessageDialog(this, "Found " + searchResults.size() + " stock item(s) matching the search criteria.",
                //         "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Clear stock search fields
    private void clearStockSearchFields() {
        u_name1.setText("");
        u_id.setText("");
        catagory1.setText("");
        qty1.setText("");
        u_ex_date1.setText("");
        u_status1.setText("");
        loadStockData(); // Reload all data when search is cleared
    }
    
    // Patient Management Search Methods
    private void setupPatientSearchListeners() {
        // Add document listeners to all patient search fields
        patient_id_2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
        });
        
        patient_name_2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
        });
        
        mobile_2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performPatientSearch(); }
        });
    }
    
    private void performPatientSearch() {
        String idSearch = patient_id_2.getText().trim();
        String nameSearch = patient_name_2.getText().trim();
        String mobileSearch = mobile_2.getText().trim();
        
        // If all search fields are empty, load all patients
        if (idSearch.isEmpty() && nameSearch.isEmpty() && mobileSearch.isEmpty()) {
            loadPatientData();
            return;
        }
        
        try {
            List<Patient> searchResults = patientDAO.searchPatients(idSearch, nameSearch, mobileSearch);
            patientTableModel.setRowCount(0);

            for (Patient patient : searchResults) {
                Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getEmail(),
                    patient.getStatus()
                };
                patientTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No patients found matching the search criteria.",
                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Clear patient search fields
    private void clearPatientSearchFields() {
        patient_id_2.setText("");
        patient_name_2.setText("");
        mobile_2.setText("");
        loadPatientData(); // Reload all data when search is cleared
    }
    
    // Billing Management Search Methods
    private void setupBillingSearchListeners() {
        // Add document listeners to all billing search fields
        billing_id.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
        });
        
        bill_p_stutas.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
        });
        
        bill_p_name.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
        });
        
        bil_d_name.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
        });
        
        bill_date_form.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
        });
        
        bill_date_to.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
        });
        
        bil_amount.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performBillingSearch(); }
        });
    }
    
    private void performBillingSearch() {
        String idSearch = billing_id.getText().trim();
        String statusSearch = bill_p_stutas.getText().trim();
        String patientNameSearch = bill_p_name.getText().trim();
        String doctorNameSearch = bil_d_name.getText().trim();
        String dateFromSearch = bill_date_form.getText().trim();
        String dateToSearch = bill_date_to.getText().trim();
        String amountSearch = bil_amount.getText().trim();
        
        // If all search fields are empty, load all billing data
        if (idSearch.isEmpty() && statusSearch.isEmpty() && patientNameSearch.isEmpty() && 
            doctorNameSearch.isEmpty() && dateFromSearch.isEmpty() && dateToSearch.isEmpty() && 
            amountSearch.isEmpty()) {
            loadBillingData();
            return;
        }
        
        try {
            List<Billing> searchResults = billingDAO.searchBillingAdvanced(idSearch, statusSearch, patientNameSearch, 
                                                                         doctorNameSearch, dateFromSearch, dateToSearch, amountSearch);
            billingTableModel.setRowCount(0);

            for (Billing billing : searchResults) {
                Object[] row = {
                    billing.getBillId(),
                    billing.getPatientName(),
                    billing.getDoctorName(),
                    billing.getFormattedAmount(),
                    billing.getPaymentStatus(),
                    billing.getBillDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(billing.getBillDate()) : "N/A"
                };
                billingTableModel.addRow(row);
            }
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No billing records found matching the search criteria.",
                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Clear billing search fields
    private void clearBillingSearchFields() {
        billing_id.setText("");
        bill_p_stutas.setText("");
        bill_p_name.setText("");
        bil_d_name.setText("");
        bill_date_form.setText("");
        bill_date_to.setText("");
        bil_amount.setText("");
        loadBillingData(); // Reload all data when search is cleared
    }

    // ==================== STOCK MANAGEMENT METHODS ====================
    private void loadStockData() {
        try {
            List<Stock> stockList = stockDAO.getAllStock();
            stockTableModel.setRowCount(0); // Clear existing rows

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            for (Stock stock : stockList) {
                Object[] row = {
                    stock.getStockId(),
                    stock.getName(),
                    stock.getCategory(),
                    stock.getQuantity(),
                    stock.getUnitPrice() != null ? "$" + stock.getUnitPrice().toString() : "$0.00",
                    stock.getExpiryDate() != null ? dateFormat.format(stock.getExpiryDate()) : "N/A",
                    stock.getStatus()
                };
                stockTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading stock data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleStockTableSelection() {
        int selectedRow = stok_table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int stockId = (Integer) stockTableModel.getValueAt(selectedRow, 0);
                selectedStock = stockDAO.getStockById(stockId);
                if (selectedStock != null) {
                    populateStockFormFields(selectedStock);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading stock details: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateStockFormFields(Stock stock) {
        u_name.setText(stock.getName());
        catagory.setText(stock.getCategory());
        qty.setText(String.valueOf(stock.getQuantity()));
        u_price.setText(stock.getUnitPrice() != null ? stock.getUnitPrice().toString() : "");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        u_ex_date.setText(stock.getExpiryDate() != null ? dateFormat.format(stock.getExpiryDate()) : "");
        u_status.setText(stock.getStatus());
    }
    
    private void clearStockFormFields() {
        u_name.setText("");
        catagory.setText("");
        qty.setText("");
        u_price.setText("");
        u_ex_date.setText("");
        u_status.setText("");
        selectedStock = null;
    }
    
    private Stock getStockFromForm() {
        Stock stock = new Stock();
        if (selectedStock != null) {
            stock.setStockId(selectedStock.getStockId());
        }
        stock.setName(u_name.getText().trim());
        stock.setCategory(catagory.getText().trim());
        try {
            stock.setQuantity(Integer.parseInt(qty.getText().trim()));
        } catch (NumberFormatException e) {
            stock.setQuantity(0);
        }
        try {
            stock.setUnitPrice(new BigDecimal(u_price.getText().trim()));
        } catch (NumberFormatException e) {
            stock.setUnitPrice(BigDecimal.ZERO);
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            stock.setExpiryDate(dateFormat.parse(u_ex_date.getText().trim()));
        } catch (Exception e) {
            stock.setExpiryDate(new Date());
        }
        stock.setStatus(u_status.getText().trim());
        return stock;
    }
    
    private boolean validateStockForm() {
        if (u_name.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Name", "Validation Error", JOptionPane.WARNING_MESSAGE);
            u_name.requestFocus();
            return false;
        }
        if (catagory.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Category", "Validation Error", JOptionPane.WARNING_MESSAGE);
            catagory.requestFocus();
            return false;
        }
        if (qty.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Quantity", "Validation Error", JOptionPane.WARNING_MESSAGE);
            qty.requestFocus();
            return false;
        }
        if (u_price.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Unit Price", "Validation Error", JOptionPane.WARNING_MESSAGE);
            u_price.requestFocus();
            return false;
        }
        return true;
    }

    // ==================== PATIENT MANAGEMENT METHODS ====================
    private void loadPatientData() {
        try {
            List<Patient> patientList = patientDAO.getAllPatients();
            patientTableModel.setRowCount(0); // Clear existing rows

            for (Patient patient : patientList) {
                Object[] row = {
                    patient.getPatientId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getGender(),
                    patient.getContactNumber(),
                    patient.getEmail(),
                    patient.getStatus()
                };
                patientTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading patient data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handlePatientTableSelection() {
        int selectedRow = patient_table.getSelectedRow();
        if (selectedRow >= 0) {
            int patientId = (Integer) patientTableModel.getValueAt(selectedRow, 0);
            selectedPatient = patientDAO.getPatientById(patientId);
            if (selectedPatient != null) {
                populatePatientFormFields(selectedPatient);
            }
        }
    }
    
    private void populatePatientFormFields(Patient patient) {
        patient_id_2.setText(String.valueOf(patient.getPatientId()));
        patient_name_2.setText(patient.getFirstName() + " " + patient.getLastName());
        mobile_2.setText(patient.getContactNumber());
    }
    
    private void clearPatientFormFields() {
        patient_id_2.setText("");
        patient_name_2.setText("");
        mobile_2.setText("");
        selectedPatient = null;
    }

    // ==================== BILLING MANAGEMENT METHODS ====================
    private void loadBillingData() {
        try {
            List<Billing> billingList = billingDAO.getAllBilling();
            billingTableModel.setRowCount(0); // Clear existing rows

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Billing billing : billingList) {
                Object[] row = {
                    billing.getBillId(),
                    billing.getPatientName(),
                    billing.getDoctorName(),
                    billing.getFormattedAmount(),
                    billing.getPaymentStatus(),
                    billing.getBillDate() != null ? dateFormat.format(billing.getBillDate()) : "N/A"
                };
                billingTableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading billing data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleBillingTableSelection() {
        int selectedRow = bill_table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int billId = (Integer) billingTableModel.getValueAt(selectedRow, 0);
                selectedBilling = billingDAO.getBillingById(billId);
                if (selectedBilling != null) {
                    populateBillingFormFields(selectedBilling);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading billing details: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateBillingFormFields(Billing billing) {
        billing_id_1.setText(String.valueOf(billing.getBillId()));
        bill_p_name_1.setText(billing.getPatientName());
        bil_d_name_1.setText(billing.getDoctorName());
        bil_amount_1.setText(billing.getTotalAmount() != null ? billing.getTotalAmount().toString() : "");
        bill_p_stutas_1.setText(billing.getPaymentStatus());
    }
    
    private void clearBillingFormFields() {
        billing_id_1.setText("");
        bill_p_name_1.setText("");
        bil_d_name_1.setText("");
        bil_amount_1.setText("");
        bill_p_stutas_1.setText("");
        selectedBilling = null;
    }

    // ==================== STOCK MANAGEMENT ACTION METHODS ====================
    private void u_addActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validateStockForm()) {
            return;
        }
        
        try {
            Stock newStock = getStockFromForm();
            if (stockDAO.createStock(newStock)) {
                JOptionPane.showMessageDialog(this, "Stock item added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearStockFormFields();
                loadStockData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add stock item.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void u_updateActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedStock == null) {
            JOptionPane.showMessageDialog(this, "Please select a stock item to update.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateStockForm()) {
            return;
        }
        
        try {
            Stock updatedStock = getStockFromForm();
            if (stockDAO.updateStock(updatedStock)) {
                JOptionPane.showMessageDialog(this, "Stock item updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearStockFormFields();
                loadStockData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update stock item.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Staff_table;
    private javax.swing.JButton b_btn_1;
    private javax.swing.JButton b_btn_2;
    private javax.swing.JButton b_btn_3;
    private javax.swing.JTextField bil_amount;
    private javax.swing.JTextField bil_amount_1;
    private javax.swing.JTextField bil_d_name;
    private javax.swing.JTextField bil_d_name_1;
    private javax.swing.JTextField bill_date_form;
    private javax.swing.JTextField bill_date_to;
    private javax.swing.JTextField bill_p_name;
    private javax.swing.JTextField bill_p_name_1;
    private javax.swing.JTextField bill_p_stutas;
    private javax.swing.JTextField bill_p_stutas_1;
    private javax.swing.JTable bill_table;
    private javax.swing.JTextField billing_id;
    private javax.swing.JTextField billing_id_1;
    private javax.swing.JTextField catagory;
    private javax.swing.JTextField catagory1;
    private javax.swing.JButton clearSearchBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField mobile_2;
    private javax.swing.JTextField patient_id_2;
    private javax.swing.JTextField patient_name_2;
    private javax.swing.JTable patient_table;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField qty1;
    private javax.swing.JButton s_btn_1;
    private javax.swing.JButton s_btn_2;
    private javax.swing.JButton s_btn_3;
    private javax.swing.JTextField s_department;
    private javax.swing.JTextField s_department1;
    private javax.swing.JTextField s_fname1;
    private javax.swing.JTextField s_lname1;
    private javax.swing.JTextField s_mobile;
    private javax.swing.JTextField s_name;
    private javax.swing.JTextField s_password;
    private javax.swing.JTextField s_role;
    private javax.swing.JTextField s_role1;
    private javax.swing.JTextField s_username;
    private javax.swing.JTextField s_username1;
    private javax.swing.JTable stok_table;
    private javax.swing.JButton u_add;
    private javax.swing.JButton u_delete;
    private javax.swing.JTextField u_ex_date;
    private javax.swing.JTextField u_ex_date1;
    private javax.swing.JTextField u_id;
    private javax.swing.JTextField u_name;
    private javax.swing.JTextField u_name1;
    private javax.swing.JTextField u_price;
    private javax.swing.JTextField u_status;
    private javax.swing.JTextField u_status1;
    private javax.swing.JButton u_update;
    private javax.swing.JButton clearStockSearchBtn;
    private javax.swing.JButton clearBillingSearchBtn;
    // End of variables declaration//GEN-END:variables
}
