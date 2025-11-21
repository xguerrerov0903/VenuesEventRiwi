package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.CreateVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.DeleteVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.GetVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.UpdateVenueUseCase;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;

import java.util.List;

public class VenueService implements
        CreateVenueUseCase,
        UpdateVenueUseCase,
        DeleteVenueUseCase,
        GetVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public VenueService(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public Venue create(Venue venue) {
        return venueRepositoryPort.save(venue);
    }

    @Override
    public Venue update(Long id, Venue venue) {
        // podrías validar que exista primero
        venue.setId(id);
        return venueRepositoryPort.save(venue);
    }

    @Override
    public void delete(Long id) {
        venueRepositoryPort.deleteById(id);
    }

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
    public List<Venue> findByCity(String city) {
        return venueRepositoryPort.findByCity(city);
    }
}
