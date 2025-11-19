package com.xguerrerov.venues.Repository.Interface;

import com.xguerrerov.venues.Entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVenueRepository  extends JpaRepository<VenueEntity, Long> { // IVenueRepository
    Optional<VenueEntity> findByName(String name);
}
