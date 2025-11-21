package com.xguerrerov.venues.domain.ports.out;

import com.xguerrerov.venues.domain.model.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepositoryPort {

    Event save(Event event);

    Optional<Event> findById(Long id);

    List<Event> findAll();

    Event update(Event event);

    void deleteById(Long id);

    List<Event> findByCategory(String category, int page, int size);

    List<Event> findByDateBegin(LocalDate dateBegin);

    List<Event> findByVenueId(Long venueId);

    Optional<Event> findByName(String name);

}
