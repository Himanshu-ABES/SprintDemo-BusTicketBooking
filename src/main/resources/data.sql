-- =============================================
-- Initial seed data for Bus Ticket Booking System
-- This script runs AFTER Hibernate DDL because
-- spring.jpa.defer-datasource-initialization=true
-- =============================================

-- Seed Bus Routes (let H2 auto-generate id)
INSERT INTO bus_route (src, dest) VALUES ('Delhi', 'Jaipur');
INSERT INTO bus_route (src, dest) VALUES ('Delhi', 'Agra');
INSERT INTO bus_route (src, dest) VALUES ('Mumbai', 'Pune');
INSERT INTO bus_route (src, dest) VALUES ('Bangalore', 'Chennai');
INSERT INTO bus_route (src, dest) VALUES ('Hyderabad', 'Vizag');

-- Seed Customers (let H2 auto-generate cust_id to avoid primary key conflicts)
INSERT INTO customer (cust_name, phone_no, password) VALUES ('Rahul Sharma', '9876543210', 'pass123');
INSERT INTO customer (cust_name, phone_no, password) VALUES ('Priya Singh', '9876543211', 'pass456');
INSERT INTO customer (cust_name, phone_no, password) VALUES ('Amit Kumar', '9876543212', 'pass789');

-- Seed Route Schedules (let H2 auto-generate id)
INSERT INTO route_schedule (route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (1, '08:00:00', '2026-04-20', 40, 40, 'ACTIVE');

INSERT INTO route_schedule (route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (1, '14:00:00', '2026-04-20', 40, 40, 'ACTIVE');

INSERT INTO route_schedule (route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (2, '09:30:00', '2026-04-20', 35, 35, 'ACTIVE');

INSERT INTO route_schedule (route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (3, '07:00:00', '2026-04-21', 45, 45, 'ACTIVE');

INSERT INTO route_schedule (route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (4, '22:00:00', '2026-04-21', 50, 50, 'ACTIVE');
