package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenuesServiceTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private VenueService venueService;

    // Helper para crear un Venue básico
    private Venue createVenue() {
        Venue v = new Venue();
        v.setId(1L);
        v.setName("TEATRO");
        v.setCity("MEDELLIN");
        v.setEvents(null);
        return v;
    }

    // =========================================================
    // CREATE
    // =========================================================
    @Test
    void create_shouldNormalizeNameAndCityAndSave() {

        Venue input = new Venue();
        input.setName("  Mi Venue ");
        input.setCity(" medellin ");

        // Resultado que el mock devolverá
        Venue saved = new Venue();
        saved.setId(10L);
        saved.setName("MI VENUE");
        saved.setCity("MEDELLIN");

        when(venueRepositoryPort.save(any(Venue.class))).thenReturn(saved);

        Venue result = venueService.create(input);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("MI VENUE", result.getName());
        assertEquals("MEDELLIN", result.getCity());

        // Capturar lo que se envió al repositorio
        ArgumentCaptor<Venue> captor = ArgumentCaptor.forClass(Venue.class);
        verify(venueRepositoryPort).save(captor.capture());

        Venue sent = captor.getValue();
        assertEquals("MI VENUE", sent.getName());
        assertEquals("MEDELLIN", sent.getCity());
    }

    // =========================================================
    // UPDATE
    // =========================================================
    @Test
    void update_shouldUpdateExistingVenue() {
        Long id = 1L;
        Venue existing = createVenue();

        when(venueRepositoryPort.findById(id)).thenReturn(Optional.of(existing));

        Venue update = new Venue();
        update.setName("  Nuevo lugar ");
        update.setCity("  bogota ");

        Venue updated = new Venue();
        updated.setId(id);
        updated.setName("NUEVO LUGAR");
        updated.setCity("BOGOTA");

        when(venueRepositoryPort.save(any(Venue.class))).thenReturn(updated);

        Venue result = venueService.update(id, update);

        assertEquals(id, result.getId());
        assertEquals("NUEVO LUGAR", result.getName());
        assertEquals("BOGOTA", result.getCity());
    }

    @Test
    void update_shouldThrowIfNotFound() {
        when(venueRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                venueService.update(99L, new Venue())
        );

        assertTrue(ex.getMessage().contains("Venue with id 99 not found"));
    }

    // =========================================================
    // DELETE
    // =========================================================
    @Test
    void delete_shouldDeleteIfExists() {
        when(venueRepositoryPort.findById(1L)).thenReturn(Optional.of(createVenue()));

        venueService.delete(1L);

        verify(venueRepositoryPort).deleteById(1L);
    }

    @Test
    void delete_shouldThrowIfNotFound() {
        when(venueRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                venueService.delete(1L)
        );

        assertTrue(ex.getMessage().contains("Venue with id 1 not found"));
        verify(venueRepositoryPort, never()).deleteById(anyLong());
    }

    // =========================================================
    // FIND BY ID
    // =========================================================
    @Test
    void findById_shouldReturnVenue() {
        Venue venue = createVenue();
        when(venueRepositoryPort.findById(1L)).thenReturn(Optional.of(venue));

        Venue result = venueService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("TEATRO", result.getName());
    }

    @Test
    void findById_shouldThrowIfNotFound() {
        when(venueRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> venueService.findById(1L)
        );

        assertTrue(ex.getMessage().contains("Venue with id 1 not found"));
    }

    // =========================================================
    // FIND ALL
    // =========================================================
    @Test
    void findAll_shouldReturnList() {
        when(venueRepositoryPort.findAll()).thenReturn(List.of(createVenue()));

        List<Venue> result = venueService.findAll();

        assertEquals(1, result.size());
        verify(venueRepositoryPort).findAll();
    }

    // =========================================================
    // GET BY CITY / FIND BY NAME
    // =========================================================
    @Test
    void getByCity_shouldUppercaseCityBeforeDelegating() {
        when(venueRepositoryPort.getByCity("MEDELLIN"))
                .thenReturn(List.of(createVenue()));

        List<Venue> result = venueService.getByCity("medellin");

        assertEquals(1, result.size());
        verify(venueRepositoryPort).getByCity("MEDELLIN");
    }

    @Test
    void findByName_shouldUppercaseNameBeforeDelegating() {
        Venue venue = createVenue();
        when(venueRepositoryPort.findByName("TEATRO"))
                .thenReturn(Optional.of(venue));

        var result = venueService.findByName("teatro");

        assertTrue(result.isPresent());
        assertEquals("TEATRO", result.get().getName());
        verify(venueRepositoryPort).findByName("TEATRO");
    }
}
