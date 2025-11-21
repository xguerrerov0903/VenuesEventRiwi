package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface GetEventUseCase {

    Event findById(Long id);

    List<Event> findAll();

    List<Event> findByCategory(String category, int page, int size);

    List<Event> findByDateBegin(LocalDate dateBegin);

    List<Event> findByVenue(Long venueId);
}
