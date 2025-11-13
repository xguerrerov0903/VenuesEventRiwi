package com.xguerrerov.venues.Repository.Interface;
import com.xguerrerov.venues.Entity.VenueEntity;
import java.util.List;
import java.util.Optional;

public interface IVenueRepository { // IVenueRepository
    List<VenueEntity> findAll();
    Optional<VenueEntity> findById(Long id);
    VenueEntity save(VenueEntity venue);
    void deleteById(Long id);
}
