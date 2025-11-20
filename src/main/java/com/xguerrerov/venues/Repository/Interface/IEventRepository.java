package com.xguerrerov.venues.Repository.Interface;
import com.xguerrerov.venues.Entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IEventRepository extends JpaRepository <EventEntity, Long> {
    Optional<EventEntity> findByName(String name);
    List<EventEntity> findByCategory(String category);
    List<EventEntity> findByDateBegin(java.time.LocalDate dateBegin);
    List<EventEntity> findByVenueId(Long venueId);
    Page<EventEntity> findByCategory(String category, Pageable pageable);
}