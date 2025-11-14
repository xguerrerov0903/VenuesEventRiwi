package com.xguerrerov.venues.Controller;

import com.xguerrerov.venues.DTO.EventDTO;
import com.xguerrerov.venues.Service.Interface.IEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Events", description = "Operations related to event management")
public class EventController {

    private final IEventService service;

    @Operation(
            summary = "Create a new event",
            description = "Creates a new event linked to a venue stored in memory"
    )
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDto) {
        EventDTO created = service.create(eventDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all events",
            description = "Returns all events stored in the in-memory repository"
    )
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Get event by ID",
            description = "Searches for an event using its unique ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        try {
            EventDTO event = service.findById(id);
            return ResponseEntity.ok(event);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Update an event",
            description = "Updates an event by its ID if it exists"
    )
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDto) {
        try {
            EventDTO updated = service.update(id, eventDto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Delete an event",
            description = "Deletes an existing event given its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}