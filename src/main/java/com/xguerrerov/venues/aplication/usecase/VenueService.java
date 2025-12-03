package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.CreateVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.DeleteVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.GetVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.UpdateVenueUseCase;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import com.xguerrerov.venues.infrastructure.metrics.EventsMetrics;
import com.xguerrerov.venues.infrastructure.metrics.VenuesMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VenueService implements
        CreateVenueUseCase,
        UpdateVenueUseCase,
        DeleteVenueUseCase,
        GetVenueUseCase {

    private final VenuesMetrics metrics;
    private final VenueRepositoryPort venueRepositoryPort;

    // ============================================================
    // CREATE VENUE
    // ============================================================
    @Override
    @Transactional
    public Venue create(Venue venue) {

        log.info("Intentando crear venue: {}", venue.getName());

        // Normalizar datos
        log.debug("Normalizando datos de venue antes de guardar");
        venue.setName(venue.getName().toUpperCase().trim());
        venue.setCity(venue.getCity().toUpperCase().trim());

        Venue saved = venueRepositoryPort.save(venue);

        log.info("Venue creado exitosamente con ID: {}", saved.getId());
        metrics.incrementCreated();
        return saved;
    }

    // ============================================================
    // UPDATE VENUE
    // ============================================================
    @Override
    @Transactional
    public Venue update(Long id, Venue venue) {

        log.info("Intentando actualizar venue con ID: {}", id);

        Venue existing = venueRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró venue con ID: {}", id);
                    return new RuntimeException("Venue with id " + id + " not found");
                });

        log.debug("Venue encontrado. Procediendo a actualizar: {}", existing.getName());

        venue.setId(id);
        venue.setName(venue.getName().toUpperCase().trim());
        venue.setCity(venue.getCity().toUpperCase().trim());

        Venue updated = venueRepositoryPort.save(venue);

        log.info("Venue actualizado exitosamente con ID: {}", updated.getId());
        metrics.incrementUpdated();
        return updated;
    }

    // ============================================================
    // DELETE VENUE
    // ============================================================
    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Intentando eliminar venue con ID: {}", id);

        Venue existing = venueRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró venue con ID: {}", id);
                    return new RuntimeException("Venue with id " + id + " not found");
                });

        log.debug("Venue encontrado. Procediendo a eliminar: {}", existing.getName());

        venueRepositoryPort.deleteById(id);

        log.info("Venue eliminado exitosamente con ID: {}", id);
        metrics.incrementDeleted();
    }

    // ============================================================
    // GETTERS
    // ============================================================
    @Override
    public Venue findById(Long id) {

        log.info("Buscando venue por ID: {}", id);

        return venueRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.warn("No se encontró venue con ID: {}", id);
                    return new RuntimeException("Venue with id " + id + " not found");
                });
    }

    @Override
    public List<Venue> findAll() {
        log.info("Obteniendo lista completa de venues");
        return venueRepositoryPort.findAll();
    }

    @Override
    public List<Venue> getByCity(String city) {
        log.info("Buscando venues por ciudad: {}", city);
        return venueRepositoryPort.getByCity(city.toUpperCase());
    }

    @Override
    public Optional<Venue> findByName(String name) {
        log.info("Buscando venue por nombre: {}", name);
        return venueRepositoryPort.findByName(name.toUpperCase());
    }
}
