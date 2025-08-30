-- Complete Database Setup Script for HealthLink360
-- This script creates the database and all tables according to your schema

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
    status VARCHAR(20) DEFAULT 'Active',
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_visit_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (insurance_id) REFERENCES insurance_providers(insurance_id)
);

-- Staff table
CREATE TABLE IF NOT EXISTS staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role_id INT,
    department VARCHAR(100),
    contact_number VARCHAR(20),
    email VARCHAR(100),
    status VARCHAR(20) DEFAULT 'Active',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    staff_id INT,
    role_id INT,
    status VARCHAR(20) DEFAULT 'Active',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Stock/Inventory table (added for inventory management)
CREATE TABLE IF NOT EXISTS stock (
    stock_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(100),
    quantity INT DEFAULT 0,
    unit_price DECIMAL(10,2) DEFAULT 0.00,
    expiry_date DATE,
    status VARCHAR(20) DEFAULT 'Active',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Appointments table
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    staff_id INT,
    appointment_date DATE,
    appointment_time TIME,
    status VARCHAR(20) DEFAULT 'Scheduled',
    notes TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);

-- Billing table (enhanced with additional fields)
CREATE TABLE IF NOT EXISTS billing (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    appointment_id INT,
    total_amount DECIMAL(10,2),
    payment_status VARCHAR(20) DEFAULT 'Pending',
    bill_date DATE DEFAULT (CURRENT_DATE),
    due_date DATE,
    bill_type VARCHAR(100),
    description TEXT,
    notes TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);

-- Insurance Claims table
CREATE TABLE IF NOT EXISTS insurance_claims (
    claim_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    bill_id INT,
    insurance_id INT,
    claim_status VARCHAR(20) DEFAULT 'Pending',
    claim_date DATE DEFAULT (CURRENT_DATE),
    remarks TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
    record_date DATE DEFAULT (CURRENT_DATE),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- Reports table
CREATE TABLE IF NOT EXISTS reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    staff_id INT,
    report_type VARCHAR(100),
    report_content TEXT,
    generated_date DATE DEFAULT (CURRENT_DATE),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);

-- Insert sample data for testing

-- Insert roles
INSERT INTO roles (role_name) VALUES 
('Admin'),
('Doctor'),
('Nurse'),
('Pharmacist'),
('Receptionist');

-- Insert insurance providers
INSERT INTO insurance_providers (provider_name, contact_number, email, address) VALUES 
('Blue Cross Blue Shield', '+1-800-555-0123', 'info@bcbs.com', '123 Insurance St, City, State'),
('Aetna', '+1-800-555-0124', 'info@aetna.com', '456 Health Ave, City, State'),
('Cigna', '+1-800-555-0125', 'info@cigna.com', '789 Care Blvd, City, State');

-- Insert sample staff
INSERT INTO staff (first_name, last_name, role_id, department, contact_number, email) VALUES 
('John', 'Doe', 1, 'Administration', '+1-555-0101', 'john.doe@healthlink360.com'),
('Jane', 'Smith', 2, 'Cardiology', '+1-555-0102', 'jane.smith@healthlink360.com'),
('Mike', 'Johnson', 3, 'Emergency', '+1-555-0103', 'mike.johnson@healthlink360.com'),
('Sarah', 'Williams', 4, 'Pharmacy', '+1-555-0104', 'sarah.williams@healthlink360.com');

-- Insert sample users
INSERT INTO users (username, password_hash, staff_id, role_id, status) VALUES 
('admin', 'password123', 1, 1, 'Active'),
('johndoe', 'password123', 1, 1, 'Active'),
('janesmith', 'password123', 2, 2, 'Active'),
('mikejohnson', 'password123', 3, 3, 'Active'),
('sarahwilliams', 'password123', 4, 4, 'Active');

-- Insert sample patients
INSERT INTO patients (first_name, last_name, dob, gender, contact_number, email, address, emergency_contact, insurance_id, status) VALUES 
('Alice', 'Brown', '1985-03-15', 'Female', '+1-555-0201', 'alice.brown@email.com', '123 Patient St, City, State', 'Bob Brown +1-555-0202', 1, 'Active'),
('Charlie', 'Davis', '1990-07-22', 'Male', '+1-555-0203', 'charlie.davis@email.com', '456 Health Ave, City, State', 'Diana Davis +1-555-0204', 2, 'Active'),
('Eva', 'Wilson', '1978-11-08', 'Female', '+1-555-0205', 'eva.wilson@email.com', '789 Care Blvd, City, State', 'Frank Wilson +1-555-0206', 3, 'Active');

-- Insert sample stock items
INSERT INTO stock (name, category, quantity, unit_price, expiry_date, status) VALUES 
('Paracetamol 500mg', 'Medication', 100, 0.50, '2025-12-31', 'Active'),
('Bandages 3x3', 'Medical Supplies', 200, 2.00, '2026-06-30', 'Active'),
('Syringes 10ml', 'Medical Equipment', 150, 1.50, '2025-09-30', 'Active'),
('Antibiotic Cream', 'Medication', 75, 8.00, '2025-03-31', 'Active'),
('Gauze Pads', 'Medical Supplies', 300, 1.25, '2026-12-31', 'Active');

-- Insert sample appointments
INSERT INTO appointments (patient_id, staff_id, appointment_date, appointment_time, status, notes) VALUES 
(1, 2, '2024-01-15', '09:00:00', 'Completed', 'Regular checkup'),
(2, 2, '2024-01-16', '10:00:00', 'Scheduled', 'Follow-up appointment'),
(3, 3, '2024-01-17', '14:00:00', 'Scheduled', 'Emergency consultation');

-- Insert sample billing
INSERT INTO billing (patient_id, appointment_id, total_amount, payment_status, bill_date, due_date, bill_type, description) VALUES 
(1, 1, 150.00, 'Paid', '2024-01-15', '2024-01-22', 'Consultation', 'Regular checkup and consultation'),
(2, 2, 200.00, 'Pending', '2024-01-16', '2024-01-23', 'Consultation', 'Follow-up appointment'),
(3, 3, 300.00, 'Pending', '2024-01-17', '2024-01-24', 'Emergency', 'Emergency consultation fee');

-- Create indexes for better performance
CREATE INDEX idx_staff_username ON users(username);
CREATE INDEX idx_staff_role ON staff(role_id);
CREATE INDEX idx_staff_department ON staff(department);
CREATE INDEX idx_patient_name ON patients(first_name, last_name);
CREATE INDEX idx_patient_insurance ON patients(insurance_id);
CREATE INDEX idx_stock_category ON stock(category);
CREATE INDEX idx_stock_status ON stock(status);
CREATE INDEX idx_billing_patient ON billing(patient_id);
CREATE INDEX idx_billing_status ON billing(payment_status);
CREATE INDEX idx_appointment_date ON appointments(appointment_date);
CREATE INDEX idx_appointment_patient ON appointments(patient_id);

-- Display table information
SELECT 'Database setup completed successfully!' as status;
SELECT COUNT(*) as roles_count FROM roles;
SELECT COUNT(*) as staff_count FROM staff;
SELECT COUNT(*) as patients_count FROM patients;
SELECT COUNT(*) as stock_count FROM stock;
SELECT COUNT(*) as billing_count FROM billing; 