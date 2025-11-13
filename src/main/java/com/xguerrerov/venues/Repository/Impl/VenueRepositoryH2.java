package com.xguerrerov.venues.Repository.Impl;

import com.xguerrerov.venues.Entity.VenueEntity;
import com.xguerrerov.venues.Repository.Interface.IVenueRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VenueRepositoryH2 implements IVenueRepository {

    private final List<VenueEntity> venues = new ArrayList<>();

    private final AtomicLong nextId = new AtomicLong(1);


    @Override
    public List<VenueEntity> findAll() {
        return new ArrayList<>(venues);
    }


    @Override
    public Optional<VenueEntity> findById(Long id) {
        return venues.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst();
    }

    @Override
    public VenueEntity save(VenueEntity venue) {
        if (venue.getId() == null) {
            venue.setId(nextId.getAndIncrement());
            venues.add(venue);
        } else {
            findById(venue.getId()).ifPresent(existing -> {
                existing.setName(venue.getName());
            });
        }
        return venue;
    }

    @Override
    public void deleteById(Long id) {
        venues.removeIf(v -> v.getId().equals(id));
    }
}