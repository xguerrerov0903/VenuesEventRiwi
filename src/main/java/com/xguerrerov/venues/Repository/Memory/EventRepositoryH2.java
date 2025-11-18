package com.xguerrerov.venues.Repository.Memory;

import com.xguerrerov.venues.Entity.EventEntity;
import com.xguerrerov.venues.Repository.Interface.IEventRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Repository
public class EventRepositoryH2 implements IEventRepository {

    private final List<EventEntity> events = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public <S extends EventEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<EventEntity> findAll() {
        return new ArrayList<>(events);
    }

    @Override
    public List<EventEntity> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Optional<EventEntity> findById(Long id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
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
    public void delete(EventEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends EventEntity> entities) {

    }

    @Override
    public void deleteAll() {

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

    @Override
    public void flush() {

    }

    @Override
    public <S extends EventEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends EventEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<EventEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public EventEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public EventEntity getById(Long aLong) {
        return null;
    }

    @Override
    public EventEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends EventEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends EventEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends EventEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends EventEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends EventEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends EventEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends EventEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<EventEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<EventEntity> findAll(Pageable pageable) {
        return null;
    }
}