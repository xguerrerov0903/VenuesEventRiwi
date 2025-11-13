package com.xguerrerov.venues.Repository.Interface;
import com.xguerrerov.venues.Entity.EventEntity;
import java.util.List;
import java.util.Optional;

public interface IEventRepository { // IEventRepository
    List<EventEntity> findAll();
    Optional<EventEntity> findById(Long id);
    EventEntity save(EventEntity event);
    void deleteById(Long id);
}