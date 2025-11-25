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

    List<Event> getByCategory(String category, int page, int size);

    List<Event> getByDateBegin(LocalDate dateBegin);

    List<Event> getByDateEnd(LocalDate dateEnd);

    List<Event> getByVenueId(Long venueId);

    List<Event> getByState(String state);

    Optional<Event> findByName(String name);

}
