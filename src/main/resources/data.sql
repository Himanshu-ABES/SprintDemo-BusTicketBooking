-- =============================================
-- Initial seed data for Bus Ticket Booking System
-- Compatible with PostgreSQL
-- Run this ONCE via pgAdmin or set spring.sql.init.mode=always for first run
-- =============================================

-- Seed Bus Routes
INSERT INTO bus_route (src, dest) VALUES ('Delhi', 'Jaipur') ON CONFLICT DO NOTHING;
INSERT INTO bus_route (src, dest) VALUES ('Delhi', 'Agra') ON CONFLICT DO NOTHING;
INSERT INTO bus_route (src, dest) VALUES ('Mumbai', 'Pune') ON CONFLICT DO NOTHING;
INSERT INTO bus_route (src, dest) VALUES ('Bangalore', 'Chennai') ON CONFLICT DO NOTHING;
INSERT INTO bus_route (src, dest) VALUES ('Hyderabad', 'Vizag') ON CONFLICT DO NOTHING;

-- Seed Customers
INSERT INTO customer (cust_name, phone_no, password) VALUES ('Rahul Sharma', '9876543210', 'pass123') ON CONFLICT (phone_no) DO NOTHING;
INSERT INTO customer (cust_name, phone_no, password) VALUES ('Priya Singh', '9876543211', 'pass456') ON CONFLICT (phone_no) DO NOTHING;
INSERT INTO customer (cust_name, phone_no, password) VALUES ('Amit Kumar', '9876543212', 'pass789') ON CONFLICT (phone_no) DO NOTHING;

-- Seed Route Schedules
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
