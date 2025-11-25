package com.xguerrerov.venues.infrastructure.adapters.out.jpa.repository;

import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.State;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByName(String name);

    @Query("SELECT e FROM EventEntity e WHERE e.category = :category")
    Page<EventEntity> getByCategory(@Param("category") Category category, Pageable pageable);

    @Query("SELECT e FROM EventEntity e WHERE e.dateBegin = :dateBegin")
    List<EventEntity> getByDateBegin(@Param("dateBegin") LocalDate dateBegin);

    @Query("SELECT e FROM EventEntity e WHERE e.dateEnd = :dateEnd")
    List<EventEntity> getByDateEnd(@Param("dateEnd") LocalDate dateEnd);

    @Query("SELECT e FROM EventEntity e WHERE e.venue.id = :venueId")
    List<EventEntity> getByVenueId(@Param("venueId") Long venueId);

    @Query("SELECT e FROM EventEntity e WHERE e.state = :state")
    List<EventEntity> getByState(@Param("state") State state);
}
