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

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final CreateVenueUseCase createUseCase;
    private final UpdateVenueUseCase updateUseCase;
    private final DeleteVenueUseCase deleteUseCase;
    private final GetVenueUseCase getUseCase;
    private final VenueDtoMapper mapper;

    // ----------------------------------------------------------------------
    // CREATE VENUE
    // ----------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<VenueDto> create(@Valid @RequestBody VenueDto dto) {

        log.info("Solicitud para crear venue: {}", dto.getName());
        log.debug("Datos recibidos para creación: {}", dto);

        Venue venue = mapper.toDomain(dto);
        Venue saved = createUseCase.create(venue);

        log.info("Venue creado exitosamente con ID: {}", saved.getId());

        return ResponseEntity.ok(mapper.toDto(saved));
    }

    // ----------------------------------------------------------------------
    // UPDATE VENUE
    // ----------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<VenueDto> update(@PathVariable Long id, @Valid @RequestBody VenueDto dto) {

        log.info("Solicitud para actualizar venue con ID: {}", id);
        log.debug("Datos recibidos para actualización: {}", dto);

        Venue venue = mapper.toDomain(dto);
        Venue updated = updateUseCase.update(id, venue);

        log.info("Venue actualizado exitosamente con ID: {}", id);

        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // ----------------------------------------------------------------------
    // DELETE VENUE
    // ----------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Solicitud para eliminar venue con ID: {}", id);

        deleteUseCase.delete(id);

        log.info("Venue eliminado exitosamente con ID: {}", id);

        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------------------------------------
    // GET VENUE BY ID
    // ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> findById(@PathVariable Long id) {

        log.info("Buscando venue con ID: {}", id);

        Venue venue = getUseCase.findById(id);

        log.debug("Venue encontrado: {}", venue.getName());

        return ResponseEntity.ok(mapper.toDto(venue));
    }

    // ----------------------------------------------------------------------
    // GET ALL VENUES
    // ----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<VenueDto>> findAll() {

        log.info("Solicitando lista completa de venues");

        List<VenueDto> response = getUseCase.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Total de venues encontrados: {}", response.size());

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET VENUES BY CITY
    // ----------------------------------------------------------------------
    @GetMapping("/city/{city}")
    public ResponseEntity<List<VenueDto>> getByCity(@PathVariable String city) {

        log.info("Buscando venues por ciudad: {}", city);

        List<VenueDto> response = getUseCase.getByCity(city)
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Venues encontrados en ciudad {}: {}", city, response.size());

        return ResponseEntity.ok(response);
    }
}
