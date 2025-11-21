package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Venue;

public interface UpdateVenueUseCase {
    Venue update(Long id, Venue venue);
}