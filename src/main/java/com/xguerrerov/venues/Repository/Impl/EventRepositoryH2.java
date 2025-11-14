package com.xguerrerov.venues.Repository.Impl;

import com.xguerrerov.venues.Entity.EventEntity;
import com.xguerrerov.venues.Repository.Interface.IEventRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepositoryH2 implements IEventRepository {

    private final List<EventEntity> events = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public List<EventEntity> findAll() {
        return new ArrayList<>(events);
    }

    @Override
    public Optional<EventEntity> findById(Long id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public EventEntity save(EventEntity event) {
        if (event.getId() == null) {
            event.setId(nextId.getAndIncrement());
            events.add(event);
        } else {
            throw new RuntimeException("Cannot save event: Event already exists and this method is incorrectly configured for update.");

        }
        return event;
    }


    @Override
    public void deleteById(Long id) {
        events.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public void updateById (Long id, EventEntity event) {
        findById(id).ifPresent(existing -> {
            existing.setName(event.getName());
            existing.setDate(event.getDate());
            existing.setDescription(event.getDescription());
            existing.setVenueId(event.getVenueId());
        });
    }
}