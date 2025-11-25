package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.VenueDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.VenueDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final CreateVenueUseCase createUseCase;
    private final UpdateVenueUseCase updateUseCase;
    private final DeleteVenueUseCase deleteUseCase;
    private final GetVenueUseCase getUseCase;
    private final VenueDtoMapper mapper;

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
    public ResponseEntity<VenueDto> create(@RequestBody VenueDto dto) {
        Venue venue = mapper.toDomain(dto);
        Venue saved = createUseCase.create(venue);
        return ResponseEntity.ok(mapper.toDto(saved));
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
    public ResponseEntity<VenueDto> update(@PathVariable Long id, @RequestBody VenueDto dto) {
        Venue venue = mapper.toDomain(dto);
        Venue updated = updateUseCase.update(id, venue);
        return ResponseEntity.ok(mapper.toDto(updated));
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.delete(id);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<VenueDto> findById(@PathVariable Long id) {
        Venue venue = getUseCase.findById(id);
        return ResponseEntity.ok(mapper.toDto(venue));
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
    public ResponseEntity<List<VenueDto>> findAll() {
        List<VenueDto> response = getUseCase.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
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
    @GetMapping("/city/{city}")
    public ResponseEntity<List<VenueDto>> getdByCity(@PathVariable String city) {
        List<VenueDto> response = getUseCase.getByCity(city)
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }
}
