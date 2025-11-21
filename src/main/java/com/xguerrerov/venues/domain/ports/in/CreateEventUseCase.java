package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Event;

public interface CreateEventUseCase {
    Event create(Event event);
}
