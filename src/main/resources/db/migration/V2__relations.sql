-- ================================
-- V2__relations.sql
-- Foreign keys + constraints
-- ================================

ALTER TABLE events
ADD CONSTRAINT fk_event_venue
    FOREIGN KEY (venue_id)
    REFERENCES venues (venue_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
