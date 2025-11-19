package com.xguerrerov.venues.Controller;

import com.xguerrerov.venues.DTO.EventDTO;
import com.xguerrerov.venues.Service.Interface.IEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "name": "Gaming Convention",
                                          "date": "2025-03-15",
                                          "venueId": 2
                                        }
                                        """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Event created",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 3,
                                          "name": "Gaming Convention",
                                          "date": "2025-03-15",
                                          "venueId": 2
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid event data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDto) {
        EventDTO created = service.create(eventDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    @Operation(summary = "Get all events")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of events",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        [
                                          {
                                            "id": 1,
                                            "name": "Rock Concert",
                                            "date": "2025-01-20",
                                            "venueId": 1
                                          },
                                          {
                                            "id": 2,
                                            "name": "Tech Expo",
                                            "date": "2025-02-05",
                                            "venueId": 2
                                          }
                                        ]
                                        """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get an event by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Event found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 1,
                                          "name": "Rock Concert",
                                          "date": "2025-01-20",
                                          "venueId": 1
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        try {
            EventDTO event = service.getById(id);
            return ResponseEntity.ok(event);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Update an existing event",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "name": "Updated Concert",
                                          "date": "2025-04-10",
                                          "venueId": 1
                                        }
                                        """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Event updated",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 1,
                                          "name": "Updated Concert",
                                          "date": "2025-04-10",
                                          "venueId": 1
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDto) {
        try {
            EventDTO updated = service.update(id, eventDto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an event by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}