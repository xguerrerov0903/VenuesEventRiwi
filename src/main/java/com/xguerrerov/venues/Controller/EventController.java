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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
                                          "dateBegin": "2030-03-15",
                                          "description": "Video game expo with tournaments and talks",
                                          "category": "CRAZY",
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
                                          "dateBegin": "2030-03-15",
                                          "description": "Video game expo with tournaments and talks",
                                          "category": "CRAZY",
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
                                            "dateBegin": "2030-01-20",
                                            "description": "Classic rock live show",
                                            "category": "NORMAL",
                                            "venueId": 1
                                          },
                                          {
                                            "id": 2,
                                            "name": "Tech Expo",
                                            "dateBegin": "2030-02-05",
                                            "description": "Technology and innovation fair",
                                            "category": "BORING",
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
                                          "dateBegin": "2030-01-20",
                                          "description": "Classic rock live show",
                                          "category": "NORMAL",
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
        EventDTO event = service.getById(id);
        return ResponseEntity.ok(event);
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
                                          "dateBegin": "2030-04-10",
                                          "description": "Updated description with new artists",
                                          "category": "NORMAL",
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
                                          "dateBegin": "2030-04-10",
                                          "description": "Updated description with new artists",
                                          "category": "NORMAL",
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
        EventDTO updated = service.update(id, eventDto);
        return ResponseEntity.ok(updated);
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

    @Operation(
            summary = "Get all events with pagination",
            description = "Returns a paginated list of events. Default page size is 5, sorted by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Page of events",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "content": [
                                            {
                                              "id": 1,
                                              "name": "Rock Concert",
                                              "dateBegin": "2030-01-20",
                                              "description": "Classic rock live show",
                                              "category": "NORMAL",
                                              "venueId": 1
                                            },
                                            {
                                              "id": 2,
                                              "name": "Tech Expo",
                                              "dateBegin": "2030-02-05",
                                              "description": "Technology and innovation fair",
                                              "category": "BORING",
                                              "venueId": 2
                                            }
                                          ],
                                          "pageable": {
                                            "pageNumber": 0,
                                            "pageSize": 5
                                          },
                                          "totalElements": 2,
                                          "totalPages": 1,
                                          "last": true,
                                          "first": true
                                        }
                                        """
                            )
                    )
            )
    })
    @GetMapping("/paged")
    public ResponseEntity<Page<EventDTO>> getAllEventsPaged(
            @PageableDefault(size = 5, sort = "id") Pageable pageable
    ) {
        Page<EventDTO> page = service.getAllPaged(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Get events by start date",
            description = "Returns all events whose dateBegin matches the given date."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of events for the given start date",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        [
                                          {
                                            "id": 5,
                                            "name": "Indie Festival",
                                            "dateBegin": "2030-06-01",
                                            "description": "Local indie bands all day long",
                                            "category": "CRAZY",
                                            "venueId": 3
                                          }
                                        ]
                                        """
                            )
                    )
            )
    })
    @GetMapping("/by-date")
    public ResponseEntity<List<EventDTO>> getEventsByDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateBegin
    ) {
        List<EventDTO> events = service.getEventsByDateBegin(dateBegin);
        return ResponseEntity.ok(events);
    }

    @Operation(
            summary = "Get events by category with pagination",
            description = "Returns a paginated list of events filtered by category. Valid values: NORMAL, CRAZY, BORING."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Page of events for the given category",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "content": [
                                            {
                                              "id": 10,
                                              "name": "Frontend Conference",
                                              "dateBegin": "2030-09-10",
                                              "description": "Talks and workshops about modern frontend",
                                              "category": "BORING",
                                              "venueId": 4
                                            }
                                          ],
                                          "pageable": {
                                            "pageNumber": 0,
                                            "pageSize": 5
                                          },
                                          "totalElements": 1,
                                          "totalPages": 1,
                                          "last": true,
                                          "first": true
                                        }
                                        """
                            )
                    )
            )
    })
    @GetMapping("/by-category")
    public ResponseEntity<Page<EventDTO>> getEventsByCategory(
            @RequestParam String category,
            @PageableDefault(size = 5, sort = "dateBegin") Pageable pageable
    ) {
        Page<EventDTO> page = service.getEventsByCategory(category, pageable);
        return ResponseEntity.ok(page);
    }
}
