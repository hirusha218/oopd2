# Updated Staff Management System - HealthLink360

## Overview
The Staff Management system has been updated to work with your actual database schema. The system now properly handles the relationship between `users` and `staff` tables, with proper foreign key constraints and role management.

## Database Schema Changes

### Original vs. Updated Structure
- **Before**: Single `staff` table with username/password fields
- **After**: Separate `users` and `staff` tables with proper relationships

### Key Tables
1. **`roles`** - Defines available staff roles (Admin, Doctor, Nurse, etc.)
2. **`staff`** - Contains staff personal information (name, contact, department)
3. **`users`** - Contains authentication information (username, password, status)

### Table Relationships
```
roles (role_id) ← staff (role_id)
staff (staff_id) ← users (staff_id)
```

## Features

### 1. **Add New Staff Member**
- Creates records in both `staff` and `users` tables
- Validates username uniqueness
- Automatically assigns role ID based on role name
- Transaction-safe operations

### 2. **Update Existing Staff Member**
- Updates both `staff` and `users` tables
- Optional password update
- Maintains referential integrity
- Transaction-safe operations

### 3. **Delete Staff Member**
- Removes from both `users` and `staff` tables
- Handles foreign key constraints properly
- Transaction-safe operations

### 4. **Search Functionality**
- Search across name, role, and department
- Real-time filtering
- Case-insensitive search

### 5. **Role Management**
- Dynamic role loading from database
- Role name to ID conversion
- Validation of role assignments

## Database Setup

### 1. **Run the Setup Script**
```sql
-- Execute the database_setup.sql file
source database_setup.sql;
```

### 2. **Verify Tables Created**
```sql
USE globemed1;
SHOW TABLES;
```

### 3. **Check Sample Data**
```sql
SELECT s.staff_id, s.first_name, s.last_name, r.role_name, u.username, u.status
FROM staff s 
JOIN roles r ON s.role_id = r.role_id 
JOIN users u ON s.staff_id = u.staff_id
ORDER BY s.staff_id;
```

## Usage Instructions

### Adding a New Staff Member
1. **Fill Required Fields**:
   - First Name
   - Last Name
   - Role (must match existing role names)
   - Username (must be unique)
   - Password
   - Department

2. **Fill Optional Fields**:
   - Contact Number
   - Email

3. **Click "Add" Button**
4. **Verify Success Message**

### Updating Staff Information
1. **Select Staff Member** from the table
2. **Modify Desired Fields**
3. **Update Password** (optional - leave blank to keep current)
4. **Click "Update" Button**
5. **Verify Success Message**

### Deleting Staff Member
1. **Select Staff Member** from the table
2. **Click "Delete" Button**
3. **Confirm Deletion** in dialog
4. **Verify Success Message**

### Searching Staff Records
1. **Use Search Fields** (Name, Role, Username)
2. **Type Search Term**
3. **Press Enter** or click outside field
4. **View Filtered Results**
5. **Clear Search** to show all records

## Form Field Mapping

### Required Fields
- **First Name** → `staff.first_name`
- **Last Name** → `staff.last_name`
- **Role** → `staff.role_id` (converted from role name)
- **Username** → `users.username`
- **Password** → `users.password_hash`

### Optional Fields
- **Contact Number** → `staff.contact_number`
- **Department** → `staff.department`
- **Email** → `staff.email`

## Database Operations

### Create Staff Member
```sql
-- 1. Insert into staff table
INSERT INTO staff (first_name, last_name, role_id, department, contact_number, email) 
VALUES (?, ?, ?, ?, ?, ?);

-- 2. Insert into users table
INSERT INTO users (username, password_hash, staff_id, role_id, status) 
VALUES (?, ?, ?, ?, ?);
```

### Update Staff Member
```sql
-- 1. Update staff table
UPDATE staff SET first_name=?, last_name=?, role_id=?, department=?, contact_number=?, email=? 
WHERE staff_id=?;

-- 2. Update users table
UPDATE users SET username=?, role_id=?, status=? WHERE staff_id=?;

-- 3. Update password if provided
UPDATE users SET password_hash=? WHERE staff_id=?;
```

### Delete Staff Member
```sql
-- 1. Delete from users table first (due to foreign key)
DELETE FROM users WHERE staff_id=?;

-- 2. Delete from staff table
DELETE FROM staff WHERE staff_id=?;
```

## Error Handling

### Common Error Scenarios
1. **Username Already Exists**
   - Error: "Username already exists"
   - Solution: Choose different username

2. **Invalid Role**
   - Error: "Invalid role: [role_name]"
   - Solution: Use existing role from database

3. **Database Connection Issues**
   - Error: "Database connection error"
   - Solution: Check database connection

4. **Foreign Key Constraint Violations**
   - Error: "Cannot delete or update a parent row"
   - Solution: Remove dependent records first

### Validation Rules
- **First Name**: Required, non-empty
- **Last Name**: Required, non-empty
- **Role**: Must exist in `roles` table
- **Username**: Required, unique across all users
- **Password**: Required, non-empty
- **Department**: Optional
- **Contact Number**: Optional
- **Email**: Optional

## Security Features

### Password Handling
- Passwords are stored as plain text in demo (NOT for production)
- In production, implement proper hashing (BCrypt recommended)
- Password field is cleared after operations for security

### Data Validation
- Input sanitization
- SQL injection prevention with PreparedStatements
- Transaction safety for data integrity

### Access Control
- Admin-only access through dashboard
- Role-based permissions (future enhancement)

## Performance Considerations

### Database Indexes
- `idx_staff_role_id` on `staff(role_id)`
- `idx_staff_department` on `staff(department)`
- `idx_users_username` on `users(username)`
- `idx_users_staff_id` on `users(staff_id)`

### Query Optimization
- Efficient JOIN operations
- Proper WHERE clauses
- Transaction management

## Testing

### Run Test Class
```bash
# Compile and run the test class
javac -cp "lib/*" src/test/StaffManagementTest.java
java -cp "lib/*:src" test.StaffManagementTest
```

### Test Scenarios
1. **Database Connection**
2. **Role Retrieval**
3. **Staff Creation**
4. **Username Validation**
5. **Staff Listing**
6. **Search Functionality**
7. **Role ID Lookup**

## Troubleshooting

### Common Issues
1. **Table Not Found**
   - Ensure database_setup.sql was executed
   - Check database name and connection

2. **Foreign Key Errors**
   - Verify roles table has data
   - Check table structure matches schema

3. **Connection Errors**
   - Verify DBConnection configuration
   - Check database server status

4. **Data Not Displaying**
   - Check table relationships
   - Verify JOIN queries

### Debug Steps
1. **Check Database Connection**
2. **Verify Table Structure**
3. **Test Individual Queries**
4. **Check Error Logs**
5. **Validate Data Integrity**

## Future Enhancements

### Planned Features
- **Password Encryption** (BCrypt implementation)
- **Role-based Permissions**
- **Audit Trail Logging**
- **Bulk Import/Export**
- **Advanced Search Filters**

### Technical Improvements
- **Real-time Updates**
- **API Integration**
- **Mobile Responsiveness**
- **Performance Monitoring**

## Support

For technical support or questions about the updated Staff Management system:
1. Check this README for common solutions
2. Review the database schema
3. Test with the provided test class
4. Contact the development team

## Version History

- **v1.0**: Initial implementation with single table
- **v2.0**: Updated for proper database schema with users/staff separation
- **v2.1**: Enhanced error handling and validation
- **v2.2**: Improved transaction safety and performance

---

**Note**: This system is designed to work with your specific database schema. Ensure the database_setup.sql script is executed before using the Staff Management functionality. 