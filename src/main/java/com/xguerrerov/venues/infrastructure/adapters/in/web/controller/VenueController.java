package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.VenueDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.VenueDtoMapper;
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

    // CREATE
    @PostMapping
    public ResponseEntity<VenueDto> create(@RequestBody VenueDto dto) {
        Venue venue = mapper.toDomain(dto);
        Venue saved = createUseCase.create(venue);
        return ResponseEntity.ok(mapper.toDto(saved));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<VenueDto> update(@PathVariable Long id, @RequestBody VenueDto dto) {
        Venue venue = mapper.toDomain(dto);
        Venue updated = updateUseCase.update(id, venue);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    // FIND BY ID
    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> findById(@PathVariable Long id) {
        Venue venue = getUseCase.findById(id);
        return ResponseEntity.ok(mapper.toDto(venue));
    }

    // FIND ALL
    @GetMapping
    public ResponseEntity<List<VenueDto>> findAll() {
        List<VenueDto> response = getUseCase.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    // FIND BY CITY
    @GetMapping("/city/{city}")
    public ResponseEntity<List<VenueDto>> findByCity(@PathVariable String city) {
        List<VenueDto> response = getUseCase.findByCity(city)
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }
}
