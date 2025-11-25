package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Venue;

import java.util.List;
import java.util.Optional;

public interface GetVenueUseCase {

    Venue findById(Long id);

    List<Venue> findAll();

    List<Venue> getByCity(String city);

    Optional<Venue> findByName (String name);


}