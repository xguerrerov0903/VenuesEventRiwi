package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.EventDtoMapper;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) {
        Event event = mapper.toDomain(dto);
        Event saved = createUseCase.create(event, dto.getVenueId());
        return ResponseEntity.ok(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable Long id, @RequestBody EventDto dto) {
        Event event = mapper.toDomain(dto);
        Event updated = updateUseCase.update(id, event, dto.getVenueId());
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> findById(@PathVariable Long id) {
        Event event = getUseCase.findById(id);
        return ResponseEntity.ok(mapper.toDto(event));
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> findAll() {
        List<EventDto> response = getUseCase.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
