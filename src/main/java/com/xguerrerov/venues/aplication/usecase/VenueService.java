package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.CreateVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.DeleteVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.GetVenueUseCase;
import com.xguerrerov.venues.domain.ports.in.UpdateVenueUseCase;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public Venue create(Venue venue) {
        return venueRepositoryPort.save(venue);
    }

    @Override
    public Venue update(Long id, Venue venue) {
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
    public List<Venue> getByCity(String city) {
        return venueRepositoryPort.getByCity(city);
    }

    @Override
    public Optional<Venue> findByName(String name) {
        return venueRepositoryPort.findByName(name);
    }
}
