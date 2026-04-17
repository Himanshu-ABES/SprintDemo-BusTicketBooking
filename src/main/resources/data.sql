-- =============================================
-- Initial seed data for Bus Ticket Booking System
-- This script runs AFTER Hibernate DDL because
-- spring.jpa.defer-datasource-initialization=true
-- =============================================

-- Seed Bus Routes
INSERT INTO bus_route (id, src, dest) VALUES (1, 'Delhi', 'Jaipur');
INSERT INTO bus_route (id, src, dest) VALUES (2, 'Delhi', 'Agra');
INSERT INTO bus_route (id, src, dest) VALUES (3, 'Mumbai', 'Pune');
INSERT INTO bus_route (id, src, dest) VALUES (4, 'Bangalore', 'Chennai');
INSERT INTO bus_route (id, src, dest) VALUES (5, 'Hyderabad', 'Vizag');

-- Seed Customers
INSERT INTO customer (cust_id, cust_name, phone_no, password) VALUES (1, 'Rahul Sharma', '9876543210', 'pass123');
INSERT INTO customer (cust_id, cust_name, phone_no, password) VALUES (2, 'Priya Singh', '9876543211', 'pass456');
INSERT INTO customer (cust_id, cust_name, phone_no, password) VALUES (3, 'Amit Kumar', '9876543212', 'pass789');

-- Seed Route Schedules
INSERT INTO route_schedule (id, route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (1, 1, '08:00:00', '2026-04-20', 40, 40, 'ACTIVE');

INSERT INTO route_schedule (id, route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (2, 1, '14:00:00', '2026-04-20', 40, 40, 'ACTIVE');

INSERT INTO route_schedule (id, route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (3, 2, '09:30:00', '2026-04-20', 35, 35, 'ACTIVE');

INSERT INTO route_schedule (id, route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (4, 3, '07:00:00', '2026-04-21', 45, 45, 'ACTIVE');

INSERT INTO route_schedule (id, route_id, departure_time, schedule_dt, avl_seats, tot_seats, sch_status)
VALUES (5, 4, '22:00:00', '2026-04-21', 50, 50, 'ACTIVE');
