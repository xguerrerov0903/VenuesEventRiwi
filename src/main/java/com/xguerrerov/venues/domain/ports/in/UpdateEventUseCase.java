package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Event;

public interface UpdateEventUseCase {
    Event update(Long id, Event event, Long venueId);
}