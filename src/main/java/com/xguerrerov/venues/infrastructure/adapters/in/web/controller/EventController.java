package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.EventDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final CreateEventUseCase createUseCase;
    private final UpdateEventUseCase updateUseCase;
    private final DeleteEventUseCase deleteUseCase;
    private final GetEventUseCase getUseCase;
    private final EventDtoMapper mapper;

    // CREATE
    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) {
        Event event = mapper.toDomain(dto);
        Event saved = createUseCase.create(event);
        return ResponseEntity.ok(mapper.toDto(saved));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable Long id, @RequestBody EventDto dto) {
        Event event = mapper.toDomain(dto);
        Event updated = updateUseCase.update(id, event);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    // FIND BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> findById(@PathVariable Long id) {
        Event event = getUseCase.findById(id);
        return ResponseEntity.ok(mapper.toDto(event));
    }

    // FIND ALL
    @GetMapping
    public ResponseEntity<List<EventDto>> findAll() {
        List<EventDto> response = getUseCase.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    // FIND BY CATEGORY (with pagination)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDto>> findByCategory(
            @PathVariable String category,
            @RequestParam int page,
            @RequestParam int size) {

        List<EventDto> response = getUseCase.findByCategory(category, page, size)
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // FIND BY VENUE
    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<EventDto>> findByVenue(@PathVariable Long venueId) {
        List<EventDto> response = getUseCase.findByVenue(venueId)
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }
}
