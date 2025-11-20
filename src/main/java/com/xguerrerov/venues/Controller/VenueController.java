package com.xguerrerov.venues.Controller;

import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Service.Interface.IVenueService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
@Tag(name = "Venues", description = "Operations related to venue management")
public class VenueController {

    private final IVenueService service;

    @Operation(
            summary = "Create a new venue",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Create Venue Example",
                                    value = """
                                        {
                                          "name": "Expo Center",
                                          "city": "Medellín"
                                        }
                                        """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Venue created",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 3,
                                          "name": "Expo Center",
                                          "city": "Medellín"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid venue data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VenueDTO> createVenue(@Valid @RequestBody VenueDTO venueDTO) {
        VenueDTO created = service.create(venueDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all venues")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of venues",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        [
                                          {
                                            "id": 1,
                                            "name": "Main Hall",
                                            "city": "Bogotá"
                                          },
                                          {
                                            "id": 2,
                                            "name": "North Arena",
                                            "city": "Cali"
                                          }
                                        ]
                                        """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get a venue by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venue found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 1,
                                          "name": "Main Hall",
                                          "city": "Bogotá"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        VenueDTO venue = service.getById(id);
        return ResponseEntity.ok(venue);
    }

    @Operation(
            summary = "Update an existing venue",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "name": "Updated Hall",
                                          "city": "Medellín"
                                        }
                                        """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venue updated",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "id": 1,
                                          "name": "Updated Hall",
                                          "city": "Medellín"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDTO venueDTO) {
        VenueDTO updated = service.update(id, venueDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a venue by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue deleted"),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all venues with pagination",
            description = "Returns a paginated list of venues. Default page size is 5, sorted by id."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Page of venues",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        {
                                          "content": [
                                            {
                                              "id": 1,
                                              "name": "Main Hall",
                                              "city": "Bogotá"
                                            },
                                            {
                                              "id": 2,
                                              "name": "North Arena",
                                              "city": "Cali"
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
    public ResponseEntity<Page<VenueDTO>> getAllVenuesPaged(
            @PageableDefault(size = 5, sort = "id") Pageable pageable
    ) {
        Page<VenueDTO> page = service.getAllPaged(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(
            summary = "Get venues by city",
            description = "Returns all venues located in the given city (case-sensitive or case-insensitive depending on implementation)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of venues in the given city",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                        [
                                          {
                                            "id": 1,
                                            "name": "Main Hall",
                                            "city": "Bogotá"
                                          },
                                          {
                                            "id": 4,
                                            "name": "Downtown Theater",
                                            "city": "Bogotá"
                                          }
                                        ]
                                        """
                            )
                    )
            )
    })
    @GetMapping("/by-city")
    public ResponseEntity<List<VenueDTO>> getVenuesByCity(@RequestParam String city) {
        List<VenueDTO> venues = service.getVenueByCity(city);
        return ResponseEntity.ok(venues);
    }
}
