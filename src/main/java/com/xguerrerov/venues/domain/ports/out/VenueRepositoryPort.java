package com.xguerrerov.venues.domain.ports.out;

import com.xguerrerov.venues.domain.model.Venue;

import java.util.List;
import java.util.Optional;


public interface VenueRepositoryPort {
    Venue save(Venue venue);

    Optional<Venue> findById(Long id);

    List<Venue> findAll();

    Venue update(Venue venue);

    void deleteById(Long id);

    List<Venue> getByCity(String city);

    Optional<Venue> findByName (String name);

}
