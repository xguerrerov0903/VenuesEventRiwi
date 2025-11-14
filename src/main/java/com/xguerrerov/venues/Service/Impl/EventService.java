package com.xguerrerov.venues.Service.Impl;

import com.xguerrerov.venues.DTO.EventDTO;
import com.xguerrerov.venues.Entity.EventEntity;
import com.xguerrerov.venues.Exception.NotFoundException;
import com.xguerrerov.venues.Repository.Interface.IEventRepository;
import com.xguerrerov.venues.Mapper.EventMapper;
import com.xguerrerov.venues.Service.Interface.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository repository;
    private final EventMapper mapper;

    @Override
    public EventDTO create(EventDTO eventDTO) {
        EventEntity entity = mapper.toEntity(eventDTO);
        EventEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<EventDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found"));
    }

    @Override
    public EventDTO update(Long id, EventDTO eventDTO) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found for update"));
        EventEntity entityToUpdate = mapper.toEntity(eventDTO);
        entityToUpdate.setId(id);
        EventEntity updatedEntity = repository.save(entityToUpdate);
        return mapper.toDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found for delete"));
        repository.deleteById(id);
    }
}