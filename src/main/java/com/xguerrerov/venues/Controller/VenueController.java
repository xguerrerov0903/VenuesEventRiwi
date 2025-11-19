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
                                          "location": "South District",
                                          "capacity": 900
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
                                          "location": "South District",
                                          "capacity": 900
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
                                            "location": "Downtown",
                                            "capacity": 500
                                          },
                                          {
                                            "id": 2,
                                            "name": "North Arena",
                                            "location": "Industrial Zone",
                                            "capacity": 1200
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
                                          "location": "Downtown",
                                          "capacity": 500
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        try {
            VenueDTO venue = service.getById(id);
            return ResponseEntity.ok(venue);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
                                          "location": "Central Park",
                                          "capacity": 700
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
                                          "location": "Central Park",
                                          "capacity": 700
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDTO venueDTO) {
        try {
            VenueDTO updated = service.update(id, venueDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
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
}
