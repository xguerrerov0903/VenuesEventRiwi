package com.xguerrerov.venues.infrastructure.adapters.out.jpa.adapter;


import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.model.State;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.EventEntity;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
    public void deleteById(Long id) {
        eventJpaRepository.deleteById(id);
    }


    @Override
    public List<Event> getByCategory(String category, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<EventEntity> pageResult =
                eventJpaRepository.getByCategory(Category.valueOf(category.toUpperCase()), pageable);

        return pageResult
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> getByDateBegin(LocalDate dateBegin) {
        return eventJpaRepository.getByDateBegin(dateBegin)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> getByDateEnd(LocalDate dateEnd) {
        return eventJpaRepository.getByDateEnd(dateEnd)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> getByVenueId(Long venueId) {
        return eventJpaRepository.getByVenueId(venueId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> getByState(String state) {

        State stateEnum = State.valueOf(state.toUpperCase());

        return eventJpaRepository.getByState(stateEnum)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
