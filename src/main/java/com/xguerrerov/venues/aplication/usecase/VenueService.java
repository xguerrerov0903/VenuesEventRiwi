package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.CreateVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.DeleteVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.GetVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.UpdateVenueUseCase;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VenueService implements
        CreateVenueUseCase,
        UpdateVenueUseCase,
        DeleteVenueUseCase,
        GetVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    // ============================================================
    // CREATE VENUE
    // ============================================================
    @Override
    @Transactional
    public Venue create(Venue venue) {

        // Normalizar nombre
        venue.setName(venue.getName().toUpperCase().trim());
        venue.setCity(venue.getCity().toUpperCase().trim());

        return venueRepositoryPort.save(venue);
    }

    // ============================================================
    // UPDATE VENUE
    // ============================================================
    @Override
    @Transactional
    public Venue update(Long id, Venue venue) {

        Venue existing = venueRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue with id " + id + " not found"));

        venue.setId(id);

        venue.setName(venue.getName().toUpperCase().trim());
        venue.setCity(venue.getCity().toUpperCase().trim());

        return venueRepositoryPort.save(venue);
    }

    // ============================================================
    // DELETE VENUE
    // ============================================================
    @Override
    @Transactional
    public void delete(Long id) {

        Venue existing = venueRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue with id " + id + " not found"));

        venueRepositoryPort.deleteById(id);
    }

    // ============================================================
    // GETTERS
    // ============================================================
    @Override
    public Venue findById(Long id) {
        return venueRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue with id " + id + " not found"));
    }

    @Override
    public List<Venue> findAll() {
        return venueRepositoryPort.findAll();
    }

    @Override
    public List<Venue> getByCity(String city) {
        return venueRepositoryPort.getByCity(city.toUpperCase());
    }

    @Override
    public Optional<Venue> findByName(String name) {
        return venueRepositoryPort.findByName(name.toUpperCase());
    }
}
