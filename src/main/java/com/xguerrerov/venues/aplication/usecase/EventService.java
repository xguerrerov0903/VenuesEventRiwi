package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService implements
        CreateEventUseCase,
        UpdateEventUseCase,
        DeleteEventUseCase,
        GetEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;
    private final VenueRepositoryPort venueRepositoryPort;

    public EventService(EventRepositoryPort eventRepositoryPort,
                        VenueRepositoryPort venueRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
        this.venueRepositoryPort = venueRepositoryPort;
    }

    // ================= CREATE =================
    @Override
    public Event create(Event event, Long venueId) {

        Venue venue = venueRepositoryPort.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        event.setVenue(venue);

        return eventRepositoryPort.save(event);
    }

    // ================= UPDATE =================
    @Override
    public Event update(Long id, Event event, Long venueId) {

        Event existing = eventRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Venue venue = venueRepositoryPort.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        event.setId(id);
        event.setVenue(venue);

        return eventRepositoryPort.save(event);
    }

    // ================= DELETE =================
    @Override
    public void delete(Long id) {
        eventRepositoryPort.deleteById(id);
    }

    // ================= GET =================
    @Override
    public Event findById(Long id) {
        return eventRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public List<Event> findAll() {
        return eventRepositoryPort.findAll();
    }

    @Override
    public List<Event> findByVenue(Long venueId) {
        return eventRepositoryPort.findByVenueId(venueId);
    }

    @Override
    public List<Event> findByCategory(String category, int page, int size) {
        return eventRepositoryPort.findByCategory(category, page, size);
    }

    @Override
    public List<Event> findByDateBegin(LocalDate dateBegin) {
        return eventRepositoryPort.findByDateBegin(dateBegin);
    }
}
