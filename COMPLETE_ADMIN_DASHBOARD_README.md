# Complete Admin Dashboard - HealthLink360

## Overview
The Admin Dashboard is a comprehensive management system for HealthLink360 that includes four main panels:
1. **Staff Management** - Manage hospital staff and user accounts
2. **Stock Management** - Manage inventory and medical supplies
3. **Patient Records** - Manage patient information and records
4. **Billing & Claims** - Manage billing, payments, and insurance claims

## System Architecture

### Database Schema
The system uses a relational database with the following key tables:
- **`roles`** - Defines staff roles (Admin, Doctor, Nurse, etc.)
- **`staff`** - Contains staff personal information
- **`users`** - Contains authentication information
- **`stock`** - Inventory management
- **`patients`** - Patient records
- **`billing`** - Billing and payment records
- **`appointments`** - Patient appointments
- **`insurance_providers`** - Insurance company information

### Application Layers
- **Model Classes** - Data entities (Staff, Stock, Patient, Billing)
- **DAO Classes** - Data Access Objects for database operations
- **GUI Layer** - Java Swing interface with tabbed panels

## Panel 1: Staff Management

### Features
- **Add New Staff Member**: Create staff accounts with roles and permissions
- **Update Staff Information**: Modify existing staff details
- **Delete Staff Member**: Remove staff from the system
- **Search Functionality**: Find staff by name, role, or department
- **Role Management**: Dynamic role assignment and validation

### Form Fields
- **Required**: First Name, Last Name, Role, Username, Password
- **Optional**: Contact Number, Department, Email

### Database Operations
- Creates records in both `staff` and `users` tables
- Maintains referential integrity with foreign keys
- Transaction-safe operations for data consistency

## Panel 2: Stock Management

### Features
- **Add New Stock Item**: Add inventory items with details
- **Update Stock Information**: Modify quantity, price, expiry dates
- **Delete Stock Item**: Remove items from inventory
- **Stock Monitoring**: Track quantities and expiry dates
- **Category Management**: Organize items by category

### Form Fields
- **Required**: Name, Category, Quantity, Unit Price
- **Optional**: Expiry Date, Status

### Stock Features
- **Low Stock Alerts**: Automatic detection of items below threshold
- **Expiry Date Tracking**: Monitor items approaching expiration
- **Value Calculation**: Total inventory value calculation
- **Category Filtering**: Filter items by category

### Database Operations
- CRUD operations for stock items
- Quantity updates and status management
- Expiry date validation and alerts

## Panel 3: Patient Records

### Features
- **Add New Patient**: Register new patients
- **Update Patient Information**: Modify patient details
- **Delete Patient Record**: Remove patient from system
- **Patient Search**: Find patients by name, contact, or email
- **Status Management**: Track patient status (Active/Inactive)

### Form Fields
- **Required**: First Name, Last Name, Date of Birth, Gender
- **Optional**: Contact Number, Email, Address, Emergency Contact, Insurance ID

### Patient Features
- **Age Calculation**: Automatic age calculation from date of birth
- **Insurance Integration**: Link patients to insurance providers
- **Emergency Contact**: Store emergency contact information
- **Registration Tracking**: Track registration and last visit dates

### Database Operations
- Patient record management
- Insurance provider linking
- Contact information updates
- Status tracking and updates

## Panel 4: Billing & Claims

### Features
- **Create New Bill**: Generate bills for patients
- **Update Billing Information**: Modify bill details and amounts
- **Delete Billing Record**: Remove billing entries
- **Payment Status Tracking**: Monitor payment status
- **Search Functionality**: Find bills by patient, doctor, or status

### Form Fields
- **Required**: Patient ID, Total Amount, Payment Status
- **Optional**: Appointment ID, Due Date, Bill Type, Description, Notes

### Billing Features
- **Payment Status Management**: Track Paid, Pending, Overdue status
- **Revenue Reporting**: Total revenue and pending payments
- **Patient-Doctor Linking**: Connect bills to appointments and staff
- **Due Date Tracking**: Monitor payment deadlines

### Database Operations
- Billing record management
- Payment status updates
- Revenue calculations
- Patient and appointment linking

## Technical Implementation

### Model Classes
```java
// Staff Management
public class Staff {
    private int staffId;
    private String firstName, lastName;
    private int roleId;
    private String department, contactNumber, email;
    private String username, password, status;
}

// Stock Management
public class Stock {
    private int stockId;
    private String name, category, status;
    private int quantity;
    private BigDecimal unitPrice;
    private Date expiryDate;
}

// Patient Management
public class Patient {
    private int patientId;
    private String firstName, lastName, gender;
    private Date dateOfBirth;
    private String contactNumber, email, address;
    private String emergencyContact, status;
    private Integer insuranceId;
}

// Billing Management
public class Billing {
    private int billId, patientId;
    private Integer appointmentId;
    private BigDecimal totalAmount;
    private String paymentStatus, billType;
    private Date billDate, dueDate;
    private String description, notes;
}
```

### DAO Classes
Each panel has its corresponding DAO class:
- **`StaffDAO`** - Staff and user management operations
- **`StockDAO`** - Inventory management operations
- **`PatientDAO`** - Patient record operations
- **`BillingDAO`** - Billing and payment operations

### Key Features
- **Transaction Safety**: All database operations use transactions
- **Error Handling**: Comprehensive error handling and user feedback
- **Data Validation**: Form validation and business rule enforcement
- **Search Capabilities**: Advanced search across multiple fields
- **Real-time Updates**: Immediate data refresh after operations

## Usage Instructions

### Adding New Records
1. **Fill Required Fields** in the appropriate form
2. **Click "Add" Button** to create the record
3. **Verify Success Message** and check the table
4. **Clear Form** using the clear functionality

### Updating Records
1. **Select Record** from the table
2. **Modify Desired Fields** in the form
3. **Click "Update" Button** to save changes
4. **Verify Success Message** and updated data

### Deleting Records
1. **Select Record** from the table
2. **Click "Delete" Button**
3. **Confirm Deletion** in the dialog
4. **Verify Success Message** and removed data

### Searching Records
1. **Use Search Fields** (varies by panel)
2. **Type Search Term** and press Enter
3. **View Filtered Results** in the table
4. **Clear Search** to show all records

## Database Setup

### 1. Run Database Script
```sql
source database_setup.sql;
```

### 2. Verify Tables
```sql
USE globemed1;
SHOW TABLES;
```

### 3. Check Sample Data
```sql
-- Staff
SELECT s.staff_id, s.first_name, s.last_name, r.role_name, u.username, u.status
FROM staff s 
JOIN roles r ON s.role_id = r.role_id 
JOIN users u ON s.staff_id = u.staff_id
ORDER BY s.staff_id;

-- Stock
SELECT * FROM stock ORDER BY stock_id;

-- Patients
SELECT * FROM patients ORDER BY patient_id;

-- Billing
SELECT b.*, p.first_name, p.last_name FROM billing b
JOIN patients p ON b.patient_id = p.patient_id
ORDER BY b.bill_id;
```

## Error Handling

### Common Error Scenarios
1. **Database Connection Issues**
   - Check database server status
   - Verify connection configuration

2. **Validation Errors**
   - Required field validation
   - Data format validation
   - Business rule validation

3. **Foreign Key Constraint Violations**
   - Check dependent records
   - Verify table relationships

4. **Duplicate Entry Errors**
   - Username uniqueness (Staff)
   - Stock item validation
   - Patient record conflicts

### Debug Steps
1. **Check Error Messages** in dialog boxes
2. **Verify Database Connection** and table structure
3. **Test Individual Operations** to isolate issues
4. **Check Data Integrity** and relationships
5. **Review Error Logs** for detailed information

## Performance Considerations

### Database Optimization
- **Indexes**: Proper indexing on frequently queried fields
- **Query Optimization**: Efficient JOIN operations and WHERE clauses
- **Transaction Management**: Appropriate transaction boundaries

### UI Performance
- **Table Models**: Efficient data loading and display
- **Event Handling**: Optimized table selection listeners
- **Memory Management**: Proper cleanup of resources

## Security Features

### Data Protection
- **Input Validation**: Sanitize user inputs
- **SQL Injection Prevention**: Use PreparedStatements
- **Access Control**: Admin-only access through dashboard

### Password Security
- **Password Hashing**: Secure password storage (BCrypt recommended)
- **Session Management**: Proper user session handling
- **Access Logging**: Track user actions and changes

## Future Enhancements

### Planned Features
- **Advanced Reporting**: Comprehensive reporting and analytics
- **User Permissions**: Role-based access control
- **Audit Trail**: Complete change tracking and logging
- **Data Export**: CSV/Excel export functionality
- **Real-time Notifications**: Stock alerts and payment reminders

### Technical Improvements
- **API Integration**: RESTful API for external systems
- **Mobile Support**: Responsive design for mobile devices
- **Performance Monitoring**: Real-time performance metrics
- **Backup and Recovery**: Automated backup systems
- **Multi-language Support**: Internationalization features

## Testing

### Test Scenarios
1. **Database Operations**: CRUD operations for all entities
2. **Form Validation**: Input validation and error handling
3. **Search Functionality**: Search across all panels
4. **Data Relationships**: Foreign key constraints and joins
5. **Error Handling**: Exception handling and user feedback

### Test Execution
```bash
# Compile the application
javac -cp "lib/*" src/**/*.java

# Run the main application
java -cp "lib/*:src" gui.Dashboard.Admin_Dashboard

# Run test classes
java -cp "lib/*:src" test.StaffManagementTest
```

## Support and Maintenance

### Troubleshooting
1. **Check README Files** for common solutions
2. **Review Database Schema** and relationships
3. **Test Individual Components** to isolate issues
4. **Check Error Logs** for detailed information
5. **Verify Data Integrity** and constraints

### Maintenance Tasks
- **Regular Database Backups**
- **Performance Monitoring**
- **Security Updates**
- **Data Archiving**
- **System Updates**

---

**Note**: This system provides a comprehensive healthcare management solution. Ensure proper training for users and regular maintenance for optimal performance. 