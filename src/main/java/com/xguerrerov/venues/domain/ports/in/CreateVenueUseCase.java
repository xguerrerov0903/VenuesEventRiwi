package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Venue;

public interface CreateVenueUseCase {
    Venue create(Venue venue);
}