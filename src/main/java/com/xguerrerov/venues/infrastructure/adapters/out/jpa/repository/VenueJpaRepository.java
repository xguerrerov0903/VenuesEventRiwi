package com.xguerrerov.venues.infrastructure.adapters.out.jpa.repository;

import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenueJpaRepository extends JpaRepository <VenueEntity, Long> {
    Optional<VenueEntity> findByName(String name);

    @Query("SELECT v FROM VenueEntity v WHERE UPPER(v.city) = UPPER(:city)")
    List<VenueEntity> getByCity(@Param("city") String city);

}
