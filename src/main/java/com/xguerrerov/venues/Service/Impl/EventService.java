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
        if (repository.findByName(entity.getName()).isPresent()){
            throw new IllegalArgumentException("Event with name " + entity.getName() + " already exists");
        }
        EventEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<EventDTO> getAll() {
        List<EventEntity> events = repository.findAll();
        if (events.isEmpty()) {
            throw new NotFoundException("No events found");
        }
        return events
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public EventDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found"));
    }

    @Override
    public EventDTO update(Long id, EventDTO eventDTO) {
        EventEntity existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found for update"));
        repository.findByName(eventDTO.getName())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new IllegalArgumentException("Event with name " + eventDTO.getName() + " already exists");
                });
        existing.setName(eventDTO.getName());
        existing.setDate(eventDTO.getDate());
        existing.setDescription(eventDTO.getDescription());
        existing.setVenueId(eventDTO.getVenueId());
        EventEntity updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id " + id + " not found for deletion"));
        repository.deleteById(id);
    }

    @Override
    public List<EventDTO> getEventsByVenueId(Long venueId) {
        List<EventEntity> events = repository.findByVenueId(venueId);
        if (events.isEmpty()) {
            throw new NotFoundException("No events found for venue id " + venueId);
        }
        return events
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }




}