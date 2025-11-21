package com.xguerrerov.venues.domain.ports.in;


import com.xguerrerov.venues.domain.model.Event;

import java.util.List;

public interface GetEventUseCase {

    Event findById(Long id);

    List<Event> findAll();

    List<Event> findByCategory(String category);

    List<Event> findByVenue(Long venueId);
}