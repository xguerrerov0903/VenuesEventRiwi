-- =========================
-- VENUES (28 registros)
-- =========================

INSERT INTO venues (name, city) VALUES ('GRAND ARENA', 'NEW YORK');
INSERT INTO venues (name, city) VALUES ('SUNSET HALL', 'LOS ANGELES');
INSERT INTO venues (name, city) VALUES ('CENTRAL PAVILION', 'CHICAGO');
INSERT INTO venues (name, city) VALUES ('RIVERFRONT CENTER', 'MIAMI');
INSERT INTO venues (name, city) VALUES ('MAPLE CONVENTION HALL', 'TORONTO');
INSERT INTO venues (name, city) VALUES ('PACIFIC EXPO CENTER', 'SAN FRANCISCO');
INSERT INTO venues (name, city) VALUES ('GALAXY DOME', 'HOUSTON');
INSERT INTO venues (name, city) VALUES ('DIAMOND VENUE', 'LAS VEGAS');
INSERT INTO venues (name, city) VALUES ('ROYAL SQUARE', 'LONDON');
INSERT INTO venues (name, city) VALUES ('TECH HUB ARENA', 'SEATTLE');
INSERT INTO venues (name, city) VALUES ('EAST SIDE AUDITORIUM', 'BOSTON');
INSERT INTO venues (name, city) VALUES ('WEST VALLEY CENTER', 'DENVER');
INSERT INTO venues (name, city) VALUES ('HARBOR PLACE', 'BALTIMORE');
INSERT INTO venues (name, city) VALUES ('NORTH POINT STAGE', 'DETROIT');
INSERT INTO venues (name, city) VALUES ('SKYLINE EVENT HALL', 'ATLANTA');
INSERT INTO venues (name, city) VALUES ('ECLIPSE EXPO', 'PHOENIX');
INSERT INTO venues (name, city) VALUES ('CRYSTAL HALL', 'PHILADELPHIA');
INSERT INTO venues (name, city) VALUES ('EMERALD HALL', 'PORTLAND');
INSERT INTO venues (name, city) VALUES ('NOVA ARENA', 'AUSTIN');
INSERT INTO venues (name, city) VALUES ('COSMOS CONVENTION HALL', 'VANCOUVER');
INSERT INTO venues (name, city) VALUES ('SOLSTICE CENTER', 'SAN DIEGO');
INSERT INTO venues (name, city) VALUES ('IGNITE HALL', 'SAN ANTONIO');
INSERT INTO venues (name, city) VALUES ('TITAN EXPO', 'MINNEAPOLIS');
INSERT INTO venues (name, city) VALUES ('MOMENTUM DOME', 'KANSAS CITY');
INSERT INTO venues (name, city) VALUES ('SPARK VENUE', 'ORLANDO');
INSERT INTO venues (name, city) VALUES ('QUANTUM HALL', 'DALLAS');
INSERT INTO venues (name, city) VALUES ('INFINITE SQUARE', 'COLUMBUS');
INSERT INTO venues (name, city) VALUES ('STARLIGHT PAVILION', 'CHARLOTTE');

-- =========================
-- EVENTS (76 registros)
-- =========================
-- Todos con columna venue_id (NO venueId)
-- nombres únicos para respetar UNIQUE(name)

INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 01', '2026-01-10', 'PRELOADED EVENT 01', 'NORMAL', 1);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 02', '2026-01-11', 'PRELOADED EVENT 02', 'CRAZY', 2);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 03', '2026-01-12', 'PRELOADED EVENT 03', 'BORING', 3);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 04', '2026-01-13', 'PRELOADED EVENT 04', 'NORMAL', 4);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 05', '2026-01-14', 'PRELOADED EVENT 05', 'CRAZY', 5);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 06', '2026-01-15', 'PRELOADED EVENT 06', 'BORING', 6);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 07', '2026-01-16', 'PRELOADED EVENT 07', 'NORMAL', 7);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 08', '2026-01-17', 'PRELOADED EVENT 08', 'CRAZY', 8);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 09', '2026-01-18', 'PRELOADED EVENT 09', 'BORING', 9);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 10', '2026-01-19', 'PRELOADED EVENT 10', 'NORMAL', 10);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 11', '2026-01-20', 'PRELOADED EVENT 11', 'CRAZY', 11);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 12', '2026-01-21', 'PRELOADED EVENT 12', 'BORING', 12);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 13', '2026-01-22', 'PRELOADED EVENT 13', 'NORMAL', 13);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 14', '2026-01-23', 'PRELOADED EVENT 14', 'CRAZY', 14);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 15', '2026-01-24', 'PRELOADED EVENT 15', 'BORING', 15);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 16', '2026-01-25', 'PRELOADED EVENT 16', 'NORMAL', 16);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 17', '2026-01-26', 'PRELOADED EVENT 17', 'CRAZY', 17);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 18', '2026-01-27', 'PRELOADED EVENT 18', 'BORING', 18);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 19', '2026-01-28', 'PRELOADED EVENT 19', 'NORMAL', 19);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 20', '2026-01-29', 'PRELOADED EVENT 20', 'CRAZY', 20);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 21', '2026-01-30', 'PRELOADED EVENT 21', 'BORING', 21);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 22', '2026-01-31', 'PRELOADED EVENT 22', 'NORMAL', 22);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 23', '2026-02-01', 'PRELOADED EVENT 23', 'CRAZY', 23);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 24', '2026-02-02', 'PRELOADED EVENT 24', 'BORING', 24);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 25', '2026-02-03', 'PRELOADED EVENT 25', 'NORMAL', 25);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 26', '2026-02-04', 'PRELOADED EVENT 26', 'CRAZY', 26);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 27', '2026-02-05', 'PRELOADED EVENT 27', 'BORING', 27);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 28', '2026-02-06', 'PRELOADED EVENT 28', 'NORMAL', 28);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 29', '2026-02-07', 'PRELOADED EVENT 29', 'CRAZY', 1);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 30', '2026-02-08', 'PRELOADED EVENT 30', 'BORING', 2);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 31', '2026-02-09', 'PRELOADED EVENT 31', 'NORMAL', 3);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 32', '2026-02-10', 'PRELOADED EVENT 32', 'CRAZY', 4);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 33', '2026-02-11', 'PRELOADED EVENT 33', 'BORING', 5);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 34', '2026-02-12', 'PRELOADED EVENT 34', 'NORMAL', 6);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 35', '2026-02-13', 'PRELOADED EVENT 35', 'CRAZY', 7);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 36', '2026-02-14', 'PRELOADED EVENT 36', 'BORING', 8);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 37', '2026-02-15', 'PRELOADED EVENT 37', 'NORMAL', 9);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 38', '2026-02-16', 'PRELOADED EVENT 38', 'CRAZY', 10);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 39', '2026-02-17', 'PRELOADED EVENT 39', 'BORING', 11);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 40', '2026-02-18', 'PRELOADED EVENT 40', 'NORMAL', 12);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 41', '2026-02-19', 'PRELOADED EVENT 41', 'CRAZY', 13);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 42', '2026-02-20', 'PRELOADED EVENT 42', 'BORING', 14);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 43', '2026-02-21', 'PRELOADED EVENT 43', 'NORMAL', 15);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 44', '2026-02-22', 'PRELOADED EVENT 44', 'CRAZY', 16);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 45', '2026-02-23', 'PRELOADED EVENT 45', 'BORING', 17);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 46', '2026-02-24', 'PRELOADED EVENT 46', 'NORMAL', 18);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 47', '2026-02-25', 'PRELOADED EVENT 47', 'CRAZY', 19);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 48', '2026-02-26', 'PRELOADED EVENT 48', 'BORING', 20);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 49', '2026-02-27', 'PRELOADED EVENT 49', 'NORMAL', 21);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 50', '2026-02-28', 'PRELOADED EVENT 50', 'CRAZY', 22);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 51', '2026-03-01', 'PRELOADED EVENT 51', 'BORING', 23);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 52', '2026-03-02', 'PRELOADED EVENT 52', 'NORMAL', 24);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 53', '2026-03-03', 'PRELOADED EVENT 53', 'CRAZY', 25);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 54', '2026-03-04', 'PRELOADED EVENT 54', 'BORING', 26);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 55', '2026-03-05', 'PRELOADED EVENT 55', 'NORMAL', 27);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 56', '2026-03-06', 'PRELOADED EVENT 56', 'CRAZY', 28);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 57', '2026-03-07', 'PRELOADED EVENT 57', 'BORING', 1);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 58', '2026-03-08', 'PRELOADED EVENT 58', 'NORMAL', 2);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 59', '2026-03-09', 'PRELOADED EVENT 59', 'CRAZY', 3);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 60', '2026-03-10', 'PRELOADED EVENT 60', 'BORING', 4);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 61', '2026-03-11', 'PRELOADED EVENT 61', 'NORMAL', 5);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 62', '2026-03-12', 'PRELOADED EVENT 62', 'CRAZY', 6);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 63', '2026-03-13', 'PRELOADED EVENT 63', 'BORING', 7);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 64', '2026-03-14', 'PRELOADED EVENT 64', 'NORMAL', 8);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 65', '2026-03-15', 'PRELOADED EVENT 65', 'CRAZY', 9);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 66', '2026-03-16', 'PRELOADED EVENT 66', 'BORING', 10);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 67', '2026-03-17', 'PRELOADED EVENT 67', 'NORMAL', 11);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 68', '2026-03-18', 'PRELOADED EVENT 68', 'CRAZY', 12);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 69', '2026-03-19', 'PRELOADED EVENT 69', 'BORING', 13);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 70', '2026-03-20', 'PRELOADED EVENT 70', 'NORMAL', 14);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 71', '2026-03-21', 'PRELOADED EVENT 71', 'CRAZY', 15);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 72', '2026-03-22', 'PRELOADED EVENT 72', 'BORING', 16);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 73', '2026-03-23', 'PRELOADED EVENT 73', 'NORMAL', 17);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 74', '2026-03-24', 'PRELOADED EVENT 74', 'CRAZY', 18);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 75', '2026-03-25', 'PRELOADED EVENT 75', 'BORING', 19);
INSERT INTO events (name, date_begin, description, category, venue_id) VALUES ('SEED EVENT 76', '2026-03-26', 'PRELOADED EVENT 76', 'NORMAL', 20);
