package com.xguerrerov.venues.Controller;


import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Service.Interface.IVenueService;
import io.swagger.v3.oas.annotations.Operation;
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
            description = "Creates a new venue in the in-memory catalog"
    )
    @PostMapping
    public ResponseEntity<VenueDTO> createVenue(@Valid @RequestBody VenueDTO venueDTO) {
        VenueDTO created = service.create(venueDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all venues",
            description = "Returns all venues stored in memory"
    )
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Get venue by ID",
            description = "Returns a single venue given its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        try {
            VenueDTO venue = service.findById(id);
            return ResponseEntity.ok(venue);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Update venue by ID",
            description = "Updates an existing venue if it exists"
    )
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueDTO venueDTO) {
        try {
            VenueDTO updated = service.update(id, venueDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Delete venue by ID",
            description = "Deletes a venue if it exists"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
