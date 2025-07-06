-- ====================================
-- Clean Slate (Delete existing records)
-- ====================================
DELETE FROM technician_specialties;
DELETE FROM actions;
DELETE FROM requests;
DELETE FROM users;
DELETE FROM request_categories;
DELETE FROM specialties;
DELETE FROM customers;

-- ================================
-- Insert Specialties
-- ================================
INSERT INTO specialties (id, specialty_type) VALUES (1000, 'Connectivity');
INSERT INTO specialties (id, specialty_type) VALUES (1001, 'Pricing');
INSERT INTO specialties (id, specialty_type) VALUES (1002, 'Equipment');
INSERT INTO specialties (id, specialty_type) VALUES (1003, 'Offers');

-- =======================================================
-- Insert Request Categories
-- =======================================================
INSERT INTO request_categories (id, category_type, specialty_id) VALUES (2001, 'Internet Connection Problem', 1000);
INSERT INTO request_categories (id, category_type, specialty_id) VALUES (2002, 'Telephone Connection Problem', 1000);
INSERT INTO request_categories (id, category_type, specialty_id) VALUES (2003, 'Pricing Problem', 1001);
INSERT INTO request_categories (id, category_type, specialty_id) VALUES (2004, 'Contract Information Problem', 1001);
INSERT INTO request_categories (id, category_type, specialty_id) VALUES (2005, 'First Setup of Equipment Problem', 1002);
INSERT INTO request_categories (id, category_type, specialty_id) VALUES (2006, 'Router Malfunction Problem', 1002);
INSERT INTO request_categories (id, category_type, specialty_id) VALUES (2007, 'Router Change Problem', 1002);
INSERT INTO request_categories (id, category_type) VALUES (2008, 'Other');

-- ===========================
-- Insert Customer Support Users
-- ===========================
INSERT INTO Users (id, user_type, user_name, password, employee_code,
                   first_name, last_name, telephone_number, email_address,
                   birth_date, street, street_number, city, zip_code)
VALUES (3001, 'CUSTOMERSUPPORT', 'csupport1', 'securePass123', 'EMP001',
        'John', 'Doe', '1234567890', 'johndoe@example.com',
        '1990-05-15', 'Main Street', '12A', 'New York', '10001');

INSERT INTO Users (id, user_type, user_name, password, employee_code,
                   first_name, last_name, telephone_number, email_address,
                   birth_date, street, street_number, city, zip_code)
VALUES (3002, 'CUSTOMERSUPPORT', 'csupport2', 'strongPass456', 'EMP002',
        'Alice', 'Smith', '9876543210', 'alice.smith@example.com',
        '1988-07-22', 'Broadway', '45B', 'Los Angeles', '90001');

INSERT INTO Users (id, user_type, user_name, password, employee_code,
                   first_name, last_name, telephone_number, email_address,
                   birth_date, street, street_number, city, zip_code)
VALUES (3003, 'CUSTOMERSUPPORT', 'csupport3', 'safePass789', 'EMP003',
        'Michael', 'Johnson', '5551237890', 'michael.j@example.com',
        '1992-03-10', '5th Avenue', '78C', 'Chicago', '60601');

-- ====================
-- Insert Technicians
-- ====================
INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birth_date, street, street_number, city, zip_code)
VALUES (4001, 'TECHNICIAN', 'tech1', 'passTech123', 'TECH001',
        'David', 'Brown', '3216549870', 'david.brown@example.com',
        '1985-09-15', 'Elm Street', '10', 'San Francisco', '94102');

INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birth_date, street, street_number, city, zip_code)
VALUES (4002, 'TECHNICIAN', 'tech2', 'passTech456', 'TECH002',
        'Emma', 'Wilson', '7894561230', 'emma.wilson@example.com',
        '1991-06-23', 'Sunset Boulevard', '55A', 'Los Angeles', '90028');

INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birth_date, street, street_number, city, zip_code)
VALUES (4003, 'TECHNICIAN', 'tech3', 'passTech789', 'TECH003',
        'James', 'Taylor', '4567893210', 'james.taylor@example.com',
        '1988-11-05', 'Park Avenue', '23B', 'New York', '10022');

INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birth_date, street, street_number, city, zip_code)
VALUES (4004, 'TECHNICIAN', 'tech4', 'passTech012', 'TECH004',
        'Sophia', 'Anderson', '9632587410', 'sophia.anderson@example.com',
        '1993-02-14', 'Lakeshore Drive', '89C', 'Chicago', '60611');


-- ====================================
-- Assign Specialties to Technicians
-- ====================================
-- tech1: Connectivity (1000)
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4001, 1000);

-- tech2: Connectivity, Offers (1000, 1003)
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4002, 1000);
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4002, 1003);

-- tech3: Pricing (1001)
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4003, 1001);

-- tech4: Equipment (1002)
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4004, 1002);

-- ==================
-- Insert Customer
-- ==================
INSERT INTO Customers (id, customer_code, first_name, last_name, telephone_number,
                       email_address, birth_date, street, street_number, city, zip_code)
VALUES (5001, 'CUST005', 'Liam', 'Miller', '7123456789', 'liam.miller@example.com',
        '1995-08-12', 'Ocean Drive', '33B', 'Miami', '33139');

-- ===================
-- Insert Requests
-- ===================
-- Active request (no technician yet)
INSERT INTO requests (id, status, telephone_number, category_id, customer_id, customer_support_id, submission_date)
VALUES (6000, 'ACTIVE', '1234567890', 2001, 5001, 3001, '2025-03-16');

-- Closed request with assigned technician
INSERT INTO requests (id, status, telephone_number, category_id, customer_id, customer_support_id, techician_id, submission_date, close_date)
VALUES (6001, 'CLOSED', '1234567890', 2001, 5001, 3001, 4001, '2025-03-16', '2025-03-17');

-- Another active request
INSERT INTO requests (id, status, telephone_number, category_id, customer_id, customer_support_id, submission_date)
VALUES (6002, 'ACTIVE', '9876543210', 2001, 5001, 3001, '2025-03-18');


-- ==================
-- Insert Actions
-- ==================
-- Communication Action: Call with customer
INSERT INTO actions (id, action_type, title, description, submission_date, call_duration)
VALUES (7001, 'COMMUNICATION', 'Call Support', 'Customer called for help regarding internet issues', '2025-03-16', 15.5);

-- Technical Action: Fixing router issue
INSERT INTO actions (id, action_type, title, description, submission_date)
VALUES (7002, 'TECHNICAL', 'Fix Router Issue', 'Replaced faulty router with a new one', '2025-03-16');