package com.xguerrerov.venues.Service.Impl;

import com.xguerrerov.venues.DTO.EventDTO;
import com.xguerrerov.venues.Entity.EventEntity;
import com.xguerrerov.venues.Entity.VenueEntity;
import com.xguerrerov.venues.Exception.DuplicateResourceException;
import com.xguerrerov.venues.Exception.NotFoundException;
import com.xguerrerov.venues.Repository.Interface.IEventRepository;
import com.xguerrerov.venues.Mapper.EventMapper;
import com.xguerrerov.venues.Repository.Interface.IVenueRepository;
import com.xguerrerov.venues.Service.Interface.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository repository;
    private final EventMapper mapper;
    private final IVenueRepository venueRepository;


    @Override
    public EventDTO create(EventDTO dto) {
        repository.findByName(dto.getName())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Event with name '" + dto.getName() + "' already exists"
                    );
                });
        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new NotFoundException(
                        "Venue with id " + dto.getVenueId() + " not found"
                ));
        EventEntity entity = mapper.toEntity(dto);
        entity.setVenue(venue);

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
    public EventDTO update(Long id, EventDTO dto) {

        EventEntity existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Event with id " + id + " not found for update"
                ));

        repository.findByName(dto.getName())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new DuplicateResourceException(
                            "Event with name '" + dto.getName() + "' already exists"
                    );
                });
        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new NotFoundException(
                        "Venue with id " + dto.getVenueId() + " not found"
                ));
        existing.setName(dto.getName());
        existing.setDateBegin(dto.getDateBegin());
        existing.setDescription(dto.getDescription());
        existing.setCategory(dto.getCategory());
        existing.setVenue(venue);

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

    @Override
    public List<EventDTO> getEventsByDateBegin(java.time.LocalDate dateBegin) {
        List<EventEntity> events = repository.findByDateBegin(dateBegin);
        if (events.isEmpty()) {
            throw new NotFoundException("No events found for date " + dateBegin);
        }
        return events
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EventDTO> getAllPaged(Pageable pageable) {
        var page = repository.findAll(pageable);

        if (page.isEmpty()) {
            throw new NotFoundException("No events found");
        }

        return page.map(mapper::toDto);
    }

    @Override
    public Page<EventDTO> getEventsByCategory(String category, Pageable pageable) {
        var page = repository.findByCategory(category, pageable);
        if (page.isEmpty()) {
            throw new NotFoundException("No events found for category " + category);
        }
        return page.map(mapper::toDto);
    }


}