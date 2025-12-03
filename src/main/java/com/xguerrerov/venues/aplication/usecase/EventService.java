package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import com.xguerrerov.venues.infrastructure.metrics.EventsMetrics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements
        CreateEventUseCase,
        UpdateEventUseCase,
        DeleteEventUseCase,
        GetEventUseCase {

    private final EventsMetrics metrics;
    private final EventRepositoryPort eventRepositoryPort;
    private final VenueRepositoryPort venueRepositoryPort;

    // ============================================================
    // CREATE EVENT
    // ============================================================
    @Override
    @Transactional
    public Event create(Event event, Long venueId) {

        log.info("Intentando crear evento con nombre: {} en venue ID: {}", event.getName(), venueId);

        Venue venue = venueRepositoryPort.findById(venueId)
                .orElseThrow(() -> {
                    log.warn("No se encontró venue con ID: {}", venueId);
                    return new RuntimeException("Venue not found");
                });

        log.debug("Venue encontrado: {}. Asociando al evento…", venue.getName());
        event.setVenue(venue);

        // DateEnd
        log.debug("Calculando fecha fin (7 días después de fecha inicio)");

        event.setDateEnd(event.getDateBegin().plusDays(7));

        // Estado inicial
        log.debug("Determinando estado inicial del evento…");
        event.setState(event.getDateBegin().isAfter(LocalDate.now())
                ? com.xguerrerov.venues.domain.model.State.ACTIVE
                : com.xguerrerov.venues.domain.model.State.INACTIVE);

        Event saved = eventRepositoryPort.save(event);
        log.info("Evento creado exitosamente con ID: {}", saved.getId());
        metrics.incrementCreated();

        return saved;
    }

    // ============================================================
    // UPDATE EVENT
    // ============================================================
    @Override
    @Transactional
    public Event update(Long id, Event event, Long venueId) {

        log.info("Intentando actualizar evento con ID: {}", id);

        Event existing = eventRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró evento con ID: {}", id);
                    return new RuntimeException("Event not found");
                });

        Venue venue = venueRepositoryPort.findById(venueId)
                .orElseThrow(() -> {
                    log.warn("No se encontró venue con ID: {}", venueId);
                    return new RuntimeException("Venue not found");
                });

        log.debug("Evento y venue encontrados. Procediendo con actualización.");

        event.setId(id);
        event.setVenue(venue);

        // Recalcular fecha fin si cambia la fecha inicio
        if (event.getDateBegin() != null) {
            log.debug("Recalculando fecha fin: nueva fecha inicio = {}", event.getDateBegin());
            event.setDateEnd(event.getDateBegin().plusDays(7));
        }

        Event updated = eventRepositoryPort.save(event);
        log.info("Evento actualizado exitosamente con ID: {}", updated.getId());
        metrics.incrementUpdated();

        return updated;
    }

    // ============================================================
    // DELETE EVENT
    // ============================================================
    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Intentando eliminar evento con ID: {}", id);

        boolean exists = eventRepositoryPort.findById(id).isPresent();

        if (!exists) {
            log.warn("No se puede eliminar. No existe evento con ID: {}", id);
            throw new RuntimeException("Event not found");
        }

        eventRepositoryPort.deleteById(id);
        log.info("Evento eliminado exitosamente con ID: {}", id);
        metrics.incrementDeleted();
    }

    // ============================================================
    // GETTERS
    // ============================================================
    @Override
    public Event findById(Long id) {

        log.info("Buscando evento por ID: {}", id);

        return eventRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró evento con ID: {}", id);
                    return new RuntimeException("Event not found");
                });
    }

    @Override
    public List<Event> findAll() {
        log.info("Obteniendo lista completa de eventos");
        return eventRepositoryPort.findAll();
    }

    @Override
    public List<Event> getByCategory(String category, int page, int size) {
        log.info("Buscando eventos por categoría: {}", category);
        return eventRepositoryPort.getByCategory(category, page, size);
    }

    @Override
    public List<Event> getByDateBegin(LocalDate dateBegin) {
        log.info("Buscando eventos con fecha inicio: {}", dateBegin);
        return eventRepositoryPort.getByDateBegin(dateBegin);
    }

    @Override
    public List<Event> getByDateEnd(LocalDate dateEnd) {
        log.info("Buscando eventos con fecha fin: {}", dateEnd);
        return eventRepositoryPort.getByDateEnd(dateEnd);
    }

    @Override
    public List<Event> getByVenueId(Long venueId) {
        log.info("Buscando eventos por venue ID: {}", venueId);
        return eventRepositoryPort.getByVenueId(venueId);
    }

    @Override
    public List<Event> getByState(String state) {
        log.info("Buscando eventos por estado: {}", state);
        return eventRepositoryPort.getByState(state);
    }

    @Override
    public Optional<Event> findByName(String name) {
        log.info("Buscando evento por nombre: {}", name);
        return eventRepositoryPort.findByName(name);
    }
}
