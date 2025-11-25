package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GetEventUseCase {

    Event findById(Long id);

    List<Event> findAll();

    Optional<Event> findByName (String name);

    List<Event> getByCategory(String category, int page, int size);

    List<Event> getByDateBegin(LocalDate dateBegin);

    List<Event> getByDateEnd(LocalDate dateEnd);

    List<Event> getByState(String State);

    List<Event> getByVenueId(Long venueId);
}
