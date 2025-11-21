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
        DeleteVenueUseCase,
        GetVenueUseCase,
        UpdateVenueUseCase {

    private final VenueRepositoryPort venueRepositoryPort;

    public VenueService(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }
    @Override
    public Venue create(Venue venue) {
        return venueRepositoryPort.save(venue);
    }

    @Override
    public List<Venue> findAll() {
        return venueRepositoryPort.findAll();
    }


    @Override
    public Venue findById(Long id) {
        return venueRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
    }

    @Override
    public Venue update(Long id, Venue venue) {
        venue.setId(id);
        return venueRepositoryPort.update(venue);
    }

    @Override
    public void delete(Long id) {
        venueRepositoryPort.deleteById(id);
    }

    @Override
    public List<Venue> findByCity(String city) {
        return venueRepositoryPort.findByCity(city);
    }
}
