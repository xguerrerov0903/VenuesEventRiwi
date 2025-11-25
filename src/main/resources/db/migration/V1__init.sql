-- ============================
-- V1__init.sql
-- Create base tables
-- ============================

CREATE TABLE venues (
    venue_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    city VARCHAR(100) NOT NULL
);

CREATE TABLE events (
    event_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    date_begin DATE NOT NULL,
    date_end DATE NOT NULL,
    category VARCHAR(20) NOT NULL,
    description TEXT,
    state VARCHAR(20) NOT NULL,
    venue_id BIGINT NOT NULL
);
