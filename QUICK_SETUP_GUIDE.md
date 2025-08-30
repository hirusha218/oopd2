# Quick Setup Guide - HealthLink360

## 🚀 Quick Start

### 1. Database Setup
```bash
# Connect to MySQL
mysql -u your_username -p

# Run the complete database script
source database_setup_complete.sql;
```

### 2. Verify Database
```sql
USE globemed1;
SHOW TABLES;
SELECT COUNT(*) FROM roles;
SELECT COUNT(*) FROM staff;
SELECT COUNT(*) FROM patients;
SELECT COUNT(*) FROM stock;
SELECT COUNT(*) FROM billing;
```

### 3. Test the System
```bash
# Compile the application
javac -cp "lib/*" src/**/*.java

# Run the comprehensive test
java -cp "lib/*:src" test.CompleteSystemTest

# Run the main application
java -cp "lib/*:src" gui.Dashboard.Admin_Dashboard
```

## 🔧 What Was Fixed

### Database Schema Alignment
- ✅ **Added missing `stock` table** to your schema
- ✅ **Enhanced existing tables** with proper defaults and timestamps
- ✅ **Added sample data** for testing all functionality
- ✅ **Created proper indexes** for performance

### DAO Fixes
- ✅ **PatientDAO**: Fixed null handling for dates and insurance IDs
- ✅ **BillingDAO**: Fixed null handling for dates and payment status
- ✅ **StockDAO**: Fixed null handling for expiry dates and status
- ✅ **All DAOs**: Added proper error handling and validation

### Key Improvements
- **Null Safety**: All DAOs now handle null values properly
- **Default Values**: Database provides sensible defaults
- **Data Integrity**: Foreign key constraints properly enforced
- **Performance**: Added indexes for better query performance

## 📊 Database Structure

```
globemed1/
├── roles (5 records - Admin, Doctor, Nurse, Pharmacist, Receptionist)
├── insurance_providers (3 records - BCBS, Aetna, Cigna)
├── staff (4 records - John Doe, Jane Smith, Mike Johnson, Sarah Williams)
├── users (5 records - linked to staff)
├── patients (3 records - Alice Brown, Charlie Davis, Eva Wilson)
├── stock (5 records - medications and supplies)
├── appointments (3 records - linked to patients and staff)
├── billing (3 records - linked to patients and appointments)
├── insurance_claims
├── medical_history
└── reports
```

## 🧪 Testing

### Run Complete System Test
```bash
java -cp "lib/*:src" test.CompleteSystemTest
```

This will test:
- ✅ Database connection
- ✅ StaffDAO operations
- ✅ StockDAO operations  
- ✅ PatientDAO operations
- ✅ BillingDAO operations

### Expected Output
```
=== HealthLink360 Complete System Test ===

✅ Database connection successful!

--- Testing StaffDAO ---
✅ getAllStaff: Found 4 staff members
   First staff: John Doe
✅ getStaffById: Successfully retrieved staff by ID
✅ searchStaff: Found 1 matching staff members

--- Testing StockDAO ---
✅ getAllStock: Found 5 stock items
   First stock: Paracetamol 500mg (Qty: 100)
✅ getStockById: Successfully retrieved stock by ID
✅ searchStock: Found 1 matching stock items
✅ getLowStockItems: Found 0 items with quantity <= 50
✅ getTotalStockValue: Total inventory value: $1237.50

--- Testing PatientDAO ---
✅ getAllPatients: Found 3 patients
   First patient: Alice Brown
✅ getPatientById: Successfully retrieved patient by ID
✅ searchPatients: Found 1 matching patients
✅ getActivePatients: Found 3 active patients
✅ getTotalPatientCount: Total patients: 3

--- Testing BillingDAO ---
✅ getAllBilling: Found 3 billing records
   First billing: Bill ID 1 - Patient: Alice Brown - Amount: $150.00
✅ getBillingById: Successfully retrieved billing by ID
✅ searchBilling: Found 1 matching billing records
✅ getBillingByPaymentStatus: Found 2 pending bills
✅ getTotalRevenue: Total revenue: $150.00
✅ getPendingPayments: Pending payments: $500.00

🎉 All tests completed successfully!
```

## 🚨 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   ```bash
   # Check MySQL service
   sudo systemctl status mysql
   
   # Verify credentials in DBConnection.java
   # Test connection manually
   mysql -u your_username -p
   ```

2. **Table Not Found**
   ```sql
   -- Verify database
   SHOW DATABASES;
   USE globemed1;
   SHOW TABLES;
   
   -- Re-run setup script if needed
   source database_setup_complete.sql;
   ```

3. **Compilation Errors**
   ```bash
   # Check Java version
   java -version
   
   # Ensure all dependencies are in lib/ folder
   ls -la lib/
   
   # Clean and recompile
   rm -rf build/
   javac -cp "lib/*" src/**/*.java
   ```

### Database Connection Issues
```java
// Check DBConnection.java
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/globemed1";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";
}
```

## 📁 File Structure

```
HealthLink360/
├── database_setup_complete.sql          # Complete database setup
├── src/
│   ├── model/                          # Data models
│   │   ├── Staff.java
│   │   ├── Stock.java
│   │   ├── Patient.java
│   │   └── Billing.java
│   ├── dao/                            # Data access objects
│   │   ├── StaffDAO.java
│   │   ├── StockDAO.java
│   │   ├── PatientDAO.java
│   │   └── BillingDAO.java
│   ├── gui/Dashboard/
│   │   └── Admin_Dashboard.java        # Main application
│   └── test/
│       └── CompleteSystemTest.java     # System test
├── lib/                                # Dependencies
└── QUICK_SETUP_GUIDE.md               # This file
```

## 🎯 Next Steps

1. **Run the database setup script**
2. **Test the system with CompleteSystemTest**
3. **Launch the Admin Dashboard**
4. **Verify all panels work correctly**
5. **Start using the system!**

## 📞 Support

If you encounter any issues:
1. Check this guide first
2. Run the CompleteSystemTest to identify problems
3. Verify database connection and table structure
4. Check error messages in the console

---

**Happy coding! 🎉** 