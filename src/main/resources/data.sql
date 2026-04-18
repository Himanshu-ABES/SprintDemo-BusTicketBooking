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

-- Seed Users (passwords are BCrypt-encoded)
-- rahul_s / Pass123
INSERT INTO users (username, password, enabled) VALUES ('rahul_s', '$2a$10$DvHTFlRw1LKy0grLLKqPie.uwiRJy2Gj3/v28jvtBRaXynMK1rut.', true) ON CONFLICT (username) DO NOTHING;
-- priya_s / Pass456
INSERT INTO users (username, password, enabled) VALUES ('priya_s', '$2a$10$KNzxNT8mZOD23jnsXduNcebKZAs.IeTl.KVsFRZcKTVOumVFNs.vG', true) ON CONFLICT (username) DO NOTHING;
-- amit_k / Pass789
INSERT INTO users (username, password, enabled) VALUES ('amit_k', '$2a$10$1LkPOwINnZqK.vWjfg.rUODdXPysim/4o2z9e/NamRCG5CtGewRbq', true) ON CONFLICT (username) DO NOTHING;
-- admin / Admin123
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$bKDytM0Eclh3iqss0zM5K.rL8kCApAa/B.KEKrKoXBOwN4wsW/R7K', true) ON CONFLICT (username) DO NOTHING;

-- Seed Authorities (Roles)
INSERT INTO authorities (username, authority) VALUES ('rahul_s', 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO authorities (username, authority) VALUES ('priya_s', 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO authorities (username, authority) VALUES ('amit_k', 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN') ON CONFLICT DO NOTHING;

-- Seed Customers
INSERT INTO customer (username, cust_name, email, phone_no) VALUES ('rahul_s', 'Rahul Sharma', 'rahul@example.com', '9876543210') ON CONFLICT (username) DO NOTHING;
INSERT INTO customer (username, cust_name, email, phone_no) VALUES ('priya_s', 'Priya Singh', 'priya@example.com', '9876543211') ON CONFLICT (username) DO NOTHING;
INSERT INTO customer (username, cust_name, email, phone_no) VALUES ('amit_k', 'Amit Kumar', 'amit@example.com', '9876543212') ON CONFLICT (username) DO NOTHING;

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
