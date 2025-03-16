delete from technician_specialties;
delete from actions;
delete from requests;
delete from users;
delete from request_categories;
delete from specialties;
delete from customers;

insert into specialties (id, specialtyType) values (1000, 'Connectivity');
insert into specialties (id, specialtyType) values (1001, 'Pricing');
insert into specialties (id, specialtyType) values (1002, 'Equipment');
insert into specialties (id, specialtyType) values (1003, 'Offers');

insert into request_categories (id, categoryType, specialty_id) values (2001, 'Internet Connection Problem', 1000);
insert into request_categories (id, categoryType, specialty_id) values (2002, 'Telephone Connection Problem', 1000);
insert into request_categories (id, categoryType, specialty_id) values (2003, 'Pricing Problem', 1001);
insert into request_categories (id, categoryType, specialty_id) values (2004, 'Contract Information Problem', 1001);
insert into request_categories (id, categoryType, specialty_id) values (2005, 'First Setup of Equipment Problem', 1002);
insert into request_categories (id, categoryType, specialty_id) values (2006, 'Router Malfunction Problem', 1002);
insert into request_categories (id, categoryType, specialty_id) values (2007, 'Router Change Problem', 1002);
insert into request_categories (id, categoryType) values (2008, 'Other');

-- Customer Support
INSERT INTO Users (id, user_type, user_name, password, employee_code,
                   first_name, last_name, telephone_number, email_address,
                   birthdate, street, number, city, zip_code)
VALUES (3001, 'CUSTOMERSUPPORT', 'csupport1', 'securePass123', 'EMP001',
        'John', 'Doe', '1234567890', 'johndoe@example.com',
        '1990-05-15', 'Main Street', '12A', 'New York', '10001');
INSERT INTO Users (id, user_type, user_name, password, employee_code,
                   first_name, last_name, telephone_number, email_address,
                   birthdate, street, number, city, zip_code)
VALUES (3002, 'CUSTOMERSUPPORT', 'csupport2', 'strongPass456', 'EMP002',
        'Alice', 'Smith', '9876543210', 'alice.smith@example.com',
        '1988-07-22', 'Broadway', '45B', 'Los Angeles', '90001');

INSERT INTO Users (id, user_type, user_name, password, employee_code,
                   first_name, last_name, telephone_number, email_address,
                   birthdate, street, number, city, zip_code)
VALUES (3003, 'CUSTOMERSUPPORT', 'csupport3', 'safePass789', 'EMP003',
        'Michael', 'Johnson', '5551237890', 'michael.j@example.com',
        '1992-03-10', '5th Avenue', '78C', 'Chicago', '60601');

-- Technician
INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birthdate, street, number, city, zip_code)
VALUES (4001, 'TECHNICIAN', 'tech1', 'passTech123', 'TECH001',
        'David', 'Brown', '3216549870', 'david.brown@example.com',
        '1985-09-15', 'Elm Street', '10', 'San Francisco', '94102');

INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birthdate, street, number, city, zip_code)
VALUES (4002, 'TECHNICIAN', 'tech2', 'passTech456', 'TECH002',
        'Emma', 'Wilson', '7894561230', 'emma.wilson@example.com',
        '1991-06-23', 'Sunset Boulevard', '55A', 'Los Angeles', '90028');

INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birthdate, street, number, city, zip_code)
VALUES (4003, 'TECHNICIAN', 'tech3', 'passTech789', 'TECH003',
        'James', 'Taylor', '4567893210', 'james.taylor@example.com',
        '1988-11-05', 'Park Avenue', '23B', 'New York', '10022');

INSERT INTO Users (id, user_type, user_name, password, technician_code,
                   first_name, last_name, telephone_number, email_address,
                   birthdate, street, number, city, zip_code)
VALUES (4004, 'TECHNICIAN', 'tech4', 'passTech012', 'TECH004',
        'Sophia', 'Anderson', '9632587410', 'sophia.anderson@example.com',
        '1993-02-14', 'Lakeshore Drive', '89C', 'Chicago', '60611');


-- (tech1) -> Specialty 1000
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4001, 1000);

-- (tech2) -> Specialty 1000, 1003
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4002, 1000);
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4002, 1003);

-- (tech3) -> Specialty 1001
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4003, 1001);

-- (tech4) -> Specialty 1002
INSERT INTO technician_specialties (technician_id, specialty_id) VALUES (4004, 1002);
