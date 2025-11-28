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
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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
    @PostMapping
    public ResponseEntity<EventDto> create(@Valid @RequestBody EventDto dto) {

        log.info("Solicitud para crear evento: {}", dto.getName());
        log.debug("Datos recibidos para creación: {}", dto);

        Event event = mapper.toDomain(dto);
        Event saved = createUseCase.create(event, dto.getVenueId());

        log.info("Evento creado exitosamente con ID: {}", saved.getId());

        return ResponseEntity.ok(mapper.toDto(saved));
    }

    // ----------------------------------------------------------------------
    // UPDATE EVENT
    // ----------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable Long id, @Valid @RequestBody EventDto dto) {

        log.info("Solicitud para actualizar evento con ID: {}", id);
        log.debug("Datos recibidos para actualización: {}", dto);

        Event event = mapper.toDomain(dto);
        Event updated = updateUseCase.update(id, event, dto.getVenueId());

        log.info("Evento actualizado exitosamente con ID: {}", id);

        return ResponseEntity.ok(mapper.toDto(updated));
    }

    // ----------------------------------------------------------------------
    // GET EVENT BY ID
    // ----------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> findById(@PathVariable Long id) {

        log.info("Buscando evento con ID: {}", id);

        Event event = getUseCase.findById(id);

        log.debug("Evento encontrado: {}", event.getName());

        return ResponseEntity.ok(mapper.toDto(event));
    }

    // ----------------------------------------------------------------------
    // GET ALL EVENTS
    // ----------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<EventDto>> findAll() {

        log.info("Solicitando lista completa de eventos");

        List<EventDto> response = getUseCase.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Total de eventos encontrados: {}", response.size());

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // DELETE EVENT
    // ----------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        log.info("Solicitud para eliminar evento con ID: {}", id);

        deleteUseCase.delete(id);

        log.info("Evento eliminado exitosamente con ID: {}", id);

        return ResponseEntity.noContent().build();
    }

    // ----------------------------------------------------------------------
    // GET BY CATEGORY
    // ----------------------------------------------------------------------
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDto>> getByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Buscando eventos por categoría: {}", category);
        log.debug("Parámetros: page={}, size={}", page, size);

        List<EventDto> response = getUseCase.getByCategory(category, page, size)
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Eventos encontrados por categoría {}: {}", category, response.size());

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY DATE BEGIN
    // ----------------------------------------------------------------------
    @GetMapping("/date-begin")
    public ResponseEntity<List<EventDto>> getByDateBegin(@RequestParam("date") String date) {

        log.info("Buscando eventos con fecha inicio: {}", date);

        List<EventDto> response = getUseCase.getByDateBegin(LocalDate.parse(date))
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Eventos encontrados con dateBegin {}: {}", date, response.size());

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY DATE END
    // ----------------------------------------------------------------------
    @GetMapping("/date-end")
    public ResponseEntity<List<EventDto>> getByDateEnd(@RequestParam("date") String date) {

        log.info("Buscando eventos con fecha fin: {}", date);

        List<EventDto> response = getUseCase.getByDateEnd(LocalDate.parse(date))
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Eventos encontrados con dateEnd {}: {}", date, response.size());

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY VENUE ID
    // ----------------------------------------------------------------------
    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<EventDto>> getByVenueId(@PathVariable Long venueId) {

        log.info("Buscando eventos asociados a venue ID: {}", venueId);

        List<EventDto> response = getUseCase.getByVenueId(venueId)
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Eventos encontrados para venue {}: {}", venueId, response.size());

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // GET BY STATE
    // ----------------------------------------------------------------------
    @GetMapping("/state/{state}")
    public ResponseEntity<List<EventDto>> getByState(@PathVariable String state) {

        log.info("Buscando eventos por estado: {}", state);

        List<EventDto> response = getUseCase.getByState(state)
                .stream()
                .map(mapper::toDto)
                .toList();

        log.debug("Eventos encontrados con estado {}: {}", state, response.size());

        return ResponseEntity.ok(response);
    }

    // ----------------------------------------------------------------------
    // FIND BY NAME
    // ----------------------------------------------------------------------
    @GetMapping("/by-name")
    public ResponseEntity<EventDto> findByName(@RequestParam String name) {

        log.info("Buscando evento por nombre: {}", name);

        Event event = getUseCase.findByName(name)
                .orElseThrow(() -> {
                    log.warn("No se encontró evento con nombre: {}", name);
                    return new RuntimeException("Event not found");
                });

        log.debug("Evento encontrado: {}", event.getName());

        return ResponseEntity.ok(mapper.toDto(event));
    }
}
