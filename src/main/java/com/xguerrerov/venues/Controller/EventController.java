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

    // ----------------------------------------------------------------------
    // CREATE EVENT
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Create a new event",
            description = """
                Creates a new event associated with an existing venue.
                
                Example URL:
                - POST /events
                """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Create Event Example",
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
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid event data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDto) {
        EventDTO created = service.create(eventDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ----------------------------------------------------------------------
    // GET ALL EVENTS
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get all events",
            description = """
                Retrieves the full list of events.
                
                Example URL:
                - GET /events
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of events retrieved successfully",
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

    // ----------------------------------------------------------------------
    // GET EVENT BY ID
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get an event by ID",
            description = """
                Retrieves a single event using its unique ID.
                
                Example URL:
                - GET /events/1
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO event = service.getById(id);
        return ResponseEntity.ok(event);
    }

    // ----------------------------------------------------------------------
    // UPDATE EVENT
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Update an existing event",
            description = """
                Updates an event by its ID.
                
                Example URL:
                - PUT /events/1
                """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Update Event Example",
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
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDto) {
        EventDTO updated = service.update(id, eventDto);
        return ResponseEntity.ok(updated);
    }

    // ----------------------------------------------------------------------
    // DELETE EVENT
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Delete an event by ID",
            description = """
                Deletes an event from the system.
                
                Example URL:
                - DELETE /events/1
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------------------------------------
    // PAGINATION
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get paginated events",
            description = """
                Returns events using pagination.
                
                Default settings:
                - page = 0
                - size = 5
                - sort = id,asc
                
                URL format:
                - /events/paged?page={page}&size={size}&sort={field},{direction}
                
                Examples:
                - /events/paged
                - /events/paged?page=1&size=10
                - /events/paged?sort=dateBegin,desc
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated events retrieved successfully")
    })
    @GetMapping("/paged")
    public ResponseEntity<Page<EventDTO>> getAllEventsPaged(
            @PageableDefault(size = 5, sort = "id") Pageable pageable
    ) {
        Page<EventDTO> page = service.getAllPaged(pageable);
        return ResponseEntity.ok(page);
    }

    // ----------------------------------------------------------------------
    // FILTER BY DATE
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get events by start date",
            description = """
                Filters events by the exact dateBegin value.
                
                URL format:
                - /events/by-date?dateBegin=YYYY-MM-DD
                
                Examples:
                - /events/by-date?dateBegin=2030-06-01
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Events retrieved for the given date")
    })
    @GetMapping("/by-date")
    public ResponseEntity<List<EventDTO>> getEventsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateBegin
    ) {
        List<EventDTO> events = service.getEventsByDateBegin(dateBegin);
        return ResponseEntity.ok(events);
    }

    // ----------------------------------------------------------------------
    // FILTER BY CATEGORY + PAGINATION
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get events by category with pagination",
            description = """
                Filters events by category and returns a paginated result.

                Valid categories:
                - NORMAL
                - CRAZY
                - BORING
                
                URL format:
                - /events/by-category?category={value}&page={page}&size={size}&sort={field},{direction}
                
                Examples:
                - /events/by-category?category=CRAZY
                - /events/by-category?category=NORMAL&page=1
                - /events/by-category?category=BORING&sort=dateBegin,desc
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paginated filtered events retrieved successfully")
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
