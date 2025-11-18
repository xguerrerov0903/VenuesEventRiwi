package com.xguerrerov.venues.Repository.Interface;
import com.xguerrerov.venues.Entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEventRepository extends JpaRepository <EventEntity, Long> {
    // IEventRepository

}