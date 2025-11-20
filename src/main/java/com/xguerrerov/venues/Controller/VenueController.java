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
                    description = "Venue created successfully",
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

    @Operation(
            summary = "Get all venues",
            description = """
                Returns the complete list of venues.

                Example URL:
                - /venues
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of venues retrieved successfully",
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

    @Operation(
            summary = "Get a venue by ID",
            description = """
                Retrieves a venue using its unique ID.

                Example URL:
                - /venues/1
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Venue found successfully",
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
            description = """
                Updates a venue by its unique ID.

                Example URL:
                - /venues/1
                """,
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
                    description = "Venue updated successfully",
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

    @Operation(
            summary = "Delete a venue by ID",
            description = """
                Deletes a venue by its unique ID.

                Example URL:
                - /venues/1
                """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Venue deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get paginated venues",
            description = """
                Returns a paginated list of venues.

                Default pagination:
                - page = 0
                - size = 5
                - sort = id,asc

                URL format:
                - /venues/paged?page={page}&size={size}&sort={field},{direction}

                Examples:
                - /venues/paged
                - /venues/paged?page=0&size=5
                - /venues/paged?page=1&size=10&sort=name,asc
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Paginated venues retrieved successfully",
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
            description = """
                Filters venues by the specified city.

                URL format:
                - /venues/by-city?city={cityName}

                Examples:
                - /venues/by-city?city=Bogotá
                - /venues/by-city?city=Medellín
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filtered venues retrieved successfully",
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
