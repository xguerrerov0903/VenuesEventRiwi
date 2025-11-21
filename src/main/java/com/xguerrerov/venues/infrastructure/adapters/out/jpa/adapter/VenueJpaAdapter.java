package com.xguerrerov.venues.infrastructure.adapters.out.jpa.adapter;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.VenueEntity;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.mapper.VenueJpaMapper;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.repository.VenueJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VenueJpaAdapter implements VenueRepositoryPort {

    private final VenueJpaMapper mapper;
    private final VenueJpaRepository repository;

    @Override
    public Venue save(Venue venue) {
        VenueEntity entity = mapper.toEntity(venue);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Venue> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Venue update(Venue venue) {
        VenueEntity entity = mapper.toEntity(venue);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Venue> findByCity(String city) {
        return repository.findByCity(city).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
