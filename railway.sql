CREATE DATABASE railway_db;

-- Create the Employees table
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL
);

-- Insert sample data into the Employees table
INSERT INTO employees (name, role) 
VALUES 
    ('John Doe', 'Engineer'),
    ('Jane Smith', 'Conductor'),
    ('Alice Johnson', 'Ticket Collector'),
    ('Bob Brown', 'Station Manager');

-- Create the Passengers table
CREATE TABLE passengers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL
);

INSERT INTO passengers (name, age, gender) 
VALUES 
    ('Wei Zhang', 28, 'Male'),
    ('Li Wei', 24, 'Male'),
    ('Mei Li', 26, 'Female'),
    ('Xia Zhang', 22, 'Female'),
    ('Hiroshi Tanaka', 32, 'Male');

-- Create the Trains table
CREATE TABLE trains (
    train_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    available_tickets INT NOT NULL
);

-- Insert sample data into the Trains table
INSERT INTO trains (name, source, destination, available_tickets) 
VALUES 
    ('Express 101', 'New York', 'Chicago', 150),
    ('Superfast 202', 'Los Angeles', 'San Francisco', 100),
    ('Shatabdi 303', 'Delhi', 'Mumbai', 200),
    ('Rajdhani 404', 'Kolkata', 'Chennai', 180);

-- Create the Trips table
CREATE TABLE trips (
    trip_id INT AUTO_INCREMENT PRIMARY KEY,
    trip_date DATE NOT NULL,
    train_id INT,
    FOREIGN KEY (train_id) REFERENCES trains(train_id) ON DELETE CASCADE
);

-- Insert sample data into the Trips table
INSERT INTO trips (trip_date, train_id) 
VALUES 
    ('2025-01-10', 1),
    ('2025-01-11', 2),
    ('2025-01-12', 3),
    ('2025-01-13', 4);


    -- Create the Tickets table
CREATE TABLE tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    train_id INT,
    passenger_id INT,
    FOREIGN KEY (train_id) REFERENCES trains(train_id) ON DELETE CASCADE,
    FOREIGN KEY (passenger_id) REFERENCES passengers(id) ON DELETE CASCADE
);

-- Insert sample data into the Tickets table
INSERT INTO tickets (train_id, passenger_id)
VALUES
    (1, 101),
    (2, 102),
    (3, 103),
    (4, 104);