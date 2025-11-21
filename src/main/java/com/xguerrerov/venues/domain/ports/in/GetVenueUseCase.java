package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Venue;

import java.util.List;

public interface GetVenueUseCase {

    Venue findById(Long id);

    List<Venue> findAll();

    List<Venue> findByCity(String city);
}