package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.EventDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) {
        Event event = mapper.toDomain(dto);
        Event saved = createUseCase.create(event, dto.getVenueId());
        return ResponseEntity.ok(mapper.toDto(saved));
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
    public ResponseEntity<EventDto> update(@PathVariable Long id, @RequestBody EventDto dto) {
        Event event = mapper.toDomain(dto);
        Event updated = updateUseCase.update(id, event, dto.getVenueId());
        return ResponseEntity.ok(mapper.toDto(updated));
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
    public ResponseEntity<EventDto> findById(@PathVariable Long id) {
        Event event = getUseCase.findById(id);
        return ResponseEntity.ok(mapper.toDto(event));
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
    public ResponseEntity<List<EventDto>> findAll() {
        List<EventDto> response = getUseCase.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDto>> getByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<EventDto> response = getUseCase.getByCategory(category, page, size)
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY DATE BEGIN
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get events by start date",
            description = """
                Retrieves all events that begin on a specific date.
                
                Example URL:
                - /events/date-begin?date=2030-03-15
                """
    )
    @GetMapping("/date-begin")
    public ResponseEntity<List<EventDto>> getByDateBegin(@RequestParam("date") String date) {

        List<EventDto> response = getUseCase.getByDateBegin(LocalDate.parse(date))
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY DATE END
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get events by end date",
            description = """
                Retrieves all events that end on a specific date.
                
                Example URL:
                - /events/date-end?date=2030-03-20
                """
    )
    @GetMapping("/date-end")
    public ResponseEntity<List<EventDto>> getByDateEnd(@RequestParam("date") String date) {

        List<EventDto> response = getUseCase.getByDateEnd(LocalDate.parse(date))
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY VENUE ID
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get events by venue ID",
            description = """
                Retrieves all events assigned to a specific venue.
                
                Example URL:
                - /events/venue/2
                """
    )
    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<EventDto>> getByVenueId(@PathVariable Long venueId) {

        List<EventDto> response = getUseCase.getByVenueId(venueId)
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY STATE
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Get events by state",
            description = """
                Retrieves events filtered by their state (ACTIVE, CANCELLED, POSTPONED, etc.).
                
                Example URL:
                - /events/state/ACTIVE
                """
    )
    @GetMapping("/state/{state}")
    public ResponseEntity<List<EventDto>> getByState(@PathVariable String state) {

        List<EventDto> response = getUseCase.getByState(state)
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // FIND BY NAME
    // ----------------------------------------------------------------------
    @Operation(
            summary = "Find an event by name",
            description = """
                Retrieves a single event by its name.
                
                Example URL:
                - /events/by-name?name=Rock Concert
                """
    )
    @GetMapping("/by-name")
    public ResponseEntity<EventDto> findByName(@RequestParam String name) {

        Event event = getUseCase.findByName(name)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return ResponseEntity.ok(mapper.toDto(event));
    }


}
