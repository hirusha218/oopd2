-- Database Setup Script for HealthLink360 Staff Management System
-- This script creates the database and tables according to your schema

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS globemed1;

-- Step 2: Use the new database
USE globemed1;

-- Step 3: Create the tables

-- Roles table
CREATE TABLE IF NOT EXISTS roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Insurance Providers table
CREATE TABLE IF NOT EXISTS insurance_providers (
    insurance_id INT AUTO_INCREMENT PRIMARY KEY,
    provider_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255)
);

-- Patients table
CREATE TABLE IF NOT EXISTS patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    dob DATE,
    gender VARCHAR(10),
    contact_number VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255),
    emergency_contact VARCHAR(100),
    insurance_id INT,
    FOREIGN KEY (insurance_id) REFERENCES insurance_providers(insurance_id)
);

-- Staff table
CREATE TABLE IF NOT EXISTS staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role_id INT NOT NULL,
    department VARCHAR(100),
    contact_number VARCHAR(20),
    email VARCHAR(100),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    staff_id INT,
    role_id INT NOT NULL,
    status VARCHAR(20) DEFAULT 'Active',
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Appointments table
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    staff_id INT,
    appointment_date DATE,
    appointment_time TIME,
    status VARCHAR(20),
    notes TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);

-- Billing table
CREATE TABLE IF NOT EXISTS billing (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    appointment_id INT,
    total_amount DECIMAL(10,2),
    payment_status VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);

-- Insurance Claims table
CREATE TABLE IF NOT EXISTS insurance_claims (
    claim_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    bill_id INT,
    insurance_id INT,
    claim_status VARCHAR(20),
    claim_date DATE,
    remarks TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (bill_id) REFERENCES billing(bill_id),
    FOREIGN KEY (insurance_id) REFERENCES insurance_providers(insurance_id)
);

-- Medical History table
CREATE TABLE IF NOT EXISTS medical_history (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    diagnosis VARCHAR(255),
    treatment VARCHAR(255),
    allergies VARCHAR(255),
    notes TEXT,
    record_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- Reports table
CREATE TABLE IF NOT EXISTS reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    staff_id INT,
    report_type VARCHAR(100),
    report_content TEXT,
    generated_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);

-- Insert sample data

-- Insert roles
INSERT INTO roles (role_name) VALUES 
('Admin'),
('Doctor'),
('Nurse'),
('Pharmacist'),
('Receptionist'),
('Lab Technician');

-- Insert sample insurance providers
INSERT INTO insurance_providers (provider_name, contact_number, email, address) VALUES
('Blue Cross Blue Shield', '+1-800-555-0123', 'info@bcbs.com', '123 Insurance St, City, State'),
('Aetna', '+1-800-555-0124', 'info@aetna.com', '456 Health Ave, City, State'),
('Cigna', '+1-800-555-0125', 'info@cigna.com', '789 Care Blvd, City, State');

-- Insert sample staff members
INSERT INTO staff (first_name, last_name, role_id, department, contact_number, email) VALUES
('John', 'Doe', 2, 'Cardiology', '+1-555-0101', 'john.doe@healthlink360.com'),
('Jane', 'Smith', 3, 'Emergency', '+1-555-0102', 'jane.smith@healthlink360.com'),
('Mike', 'Johnson', 4, 'Pharmacy', '+1-555-0103', 'mike.johnson@healthlink360.com'),
('Sarah', 'Williams', 1, 'Administration', '+1-555-0104', 'sarah.williams@healthlink360.com'),
('David', 'Brown', 2, 'Neurology', '+1-555-0105', 'david.brown@healthlink360.com'),
('Lisa', 'Davis', 3, 'Pediatrics', '+1-555-0106', 'lisa.davis@healthlink360.com');

-- Insert sample users (passwords are plain text for demo - use hashed passwords in production)
INSERT INTO users (username, password_hash, staff_id, role_id, status) VALUES
('johndoe', 'password123', 1, 2, 'Active'),
('janesmith', 'password123', 2, 3, 'Active'),
('mikejohnson', 'password123', 3, 4, 'Active'),
('sarahwilliams', 'password123', 4, 1, 'Active'),
('davidbrown', 'password123', 5, 2, 'Active'),
('lisadavis', 'password123', 6, 3, 'Active');

-- Insert sample patients
INSERT INTO patients (first_name, last_name, dob, gender, contact_number, email, address, emergency_contact, insurance_id) VALUES
('Robert', 'Wilson', '1985-03-15', 'Male', '+1-555-0201', 'robert.wilson@email.com', '123 Patient St, City, State', 'Mary Wilson +1-555-0202', 1),
('Emily', 'Johnson', '1990-07-22', 'Female', '+1-555-0203', 'emily.johnson@email.com', '456 Health Ave, City, State', 'John Johnson +1-555-0204', 2),
('Michael', 'Brown', '1978-11-08', 'Male', '+1-555-0205', 'michael.brown@email.com', '789 Care Blvd, City, State', 'Lisa Brown +1-555-0206', 3);

-- Create indexes for better performance
CREATE INDEX idx_staff_role_id ON staff(role_id);
CREATE INDEX idx_staff_department ON staff(department);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_staff_id ON users(staff_id);
CREATE INDEX idx_users_role_id ON users(role_id);
CREATE INDEX idx_patients_insurance_id ON patients(insurance_id);

-- Display the created data
SELECT 'Database setup completed successfully!' as Status;

-- Show sample staff data
SELECT 'Sample Staff Data:' as Info;
SELECT s.staff_id, s.first_name, s.last_name, r.role_name, s.department, u.username, u.status
FROM staff s 
JOIN roles r ON s.role_id = r.role_id 
JOIN users u ON s.staff_id = u.staff_id
ORDER BY s.staff_id; 