package com.xguerrerov.venues.infrastructure.adapters.out.jpa.adapter;


import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.EventEntity;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.mapper.EventJpaMapper;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.repository.EventJpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class EventJpaAdapter implements EventRepositoryPort {

    private final EventJpaMapper mapper;
    private final EventJpaRepository eventJpaRepository;


    @Override
    public Event save(Event event) {
        EventEntity entity = mapper.toEntity(event);
        return mapper.toDomain(eventJpaRepository.save(entity));
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Event> findByName(String name) {
        return eventJpaRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<Event> findAll() {
        return eventJpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Event update(Event event) {
        EventEntity entity = mapper.toEntity(event);
        return mapper.toDomain(eventJpaRepository.save(entity));
    }


    @Override
    public List<Event> findByCategory(String category, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EventEntity> result = eventJpaRepository.findByCategory(category, pageable);

        return result.getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> findByDateBegin(LocalDate dateBegin) {
        return eventJpaRepository.findByDateBegin(dateBegin)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> findByVenueId(Long venueId) {
        return eventJpaRepository.findByVenueId(venueId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        eventJpaRepository.deleteById(id);
    }
}
