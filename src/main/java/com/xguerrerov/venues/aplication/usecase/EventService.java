package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.ports.in.CreateEventUseCase;
import com.xguerrerov.venues.domain.ports.in.DeleteEventUseCase;
import com.xguerrerov.venues.domain.ports.in.GetEventUseCase;
import com.xguerrerov.venues.domain.ports.in.UpdateEventUseCase;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;

import java.time.LocalDate;
import java.util.List;

public class EventService implements
        CreateEventUseCase,
        DeleteEventUseCase,
        GetEventUseCase,
        UpdateEventUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public EventService(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public Event create(Event event) {
        return eventRepositoryPort.save(event);
    }

    @Override
    public List<Event> findAll() {
        return eventRepositoryPort.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public Event update(Long id, Event event) {
        event.setId(id);
        return eventRepositoryPort.update(event);
    }

    @Override
    public void delete(Long id) {
        eventRepositoryPort.deleteById(id);
    }

    @Override
    public List<Event> findByCategory(String category, int page, int size) {
        return eventRepositoryPort.findByCategory(category, page, size);
    }

    @Override
    public List<Event> findByDateBegin(LocalDate dateBegin) {
        return eventRepositoryPort.findByDateBegin(dateBegin);
    }

    @Override
    public List<Event> findByVenue(Long venueId) {
        return eventRepositoryPort.findByVenueId(venueId);
    }

}
