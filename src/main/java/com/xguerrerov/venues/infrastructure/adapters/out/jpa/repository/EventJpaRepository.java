package com.xguerrerov.venues.infrastructure.adapters.out.jpa.repository;

import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByName(String name);

    Page<EventEntity> findByCategory(String category, Pageable pageable);

    List<EventEntity> findByDateBegin(LocalDate dateBegin);

    List<EventEntity> findByVenueId(Long venueId);
}
