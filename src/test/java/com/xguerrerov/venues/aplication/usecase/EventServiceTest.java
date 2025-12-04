package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.model.State;
import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import com.xguerrerov.venues.infrastructure.metrics.EventsMetrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {


    @Mock
    private EventsMetrics metrics;

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private EventService eventService;



    // =========================================================
    // CREATE
    // =========================================================
    @Test
    void create_shouldSetVenueDateEndAndStateAndSave() {
        Event event = new Event();
        event.setName("My event");
        LocalDate begin = LocalDate.now().plusDays(1);
        event.setDateBegin(begin);

        Venue venue = new Venue();
        venue.setId(10L);
        venue.setName("Main Hall");

        when(venueRepositoryPort.findById(10L)).thenReturn(Optional.of(venue));
        when(eventRepositoryPort.save(any(Event.class))).thenAnswer(invocation -> {
            Event e = invocation.getArgument(0);
            e.setId(1L);
            return e;
        });

        Event result = eventService.create(event, 10L);

        assertNotNull(result.getId());
        assertEquals(venue, result.getVenue());
        assertEquals(begin.plusDays(7), result.getDateEnd());
        assertEquals(State.ACTIVE, result.getState());

        verify(venueRepositoryPort).findById(10L);
        verify(eventRepositoryPort).save(any(Event.class));
    }

    @Test
    void create_shouldThrowWhenVenueNotFound() {
        Event event = new Event();
        event.setDateBegin(LocalDate.now().plusDays(1));

        when(venueRepositoryPort.findById(10L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> eventService.create(event, 10L));

        verify(eventRepositoryPort, never()).save(any());
    }

    // =========================================================
    // UPDATE
    // =========================================================
    @Test
    void update_shouldUpdateEventAndRecalculateDateEnd() {
        Long eventId = 1L;
        Long venueId = 20L;

        Event existing = new Event();
        existing.setId(eventId);

        Event updateData = new Event();
        LocalDate newBegin = LocalDate.of(2025, 1, 1);
        updateData.setDateBegin(newBegin);

        Venue venue = new Venue();
        venue.setId(venueId);

        when(eventRepositoryPort.findById(eventId)).thenReturn(Optional.of(existing));
        when(venueRepositoryPort.findById(venueId)).thenReturn(Optional.of(venue));
        when(eventRepositoryPort.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event result = eventService.update(eventId, updateData, venueId);

        assertEquals(eventId, result.getId());
        assertEquals(venue, result.getVenue());
        assertEquals(newBegin.plusDays(7), result.getDateEnd());

        verify(eventRepositoryPort).findById(eventId);
        verify(venueRepositoryPort).findById(venueId);
        verify(eventRepositoryPort).save(any(Event.class));
    }

    @Test
    void update_shouldThrowWhenEventNotFound() {
        when(eventRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> eventService.update(1L, new Event(), 10L));

        verify(eventRepositoryPort, never()).save(any());
    }

    // =========================================================
    // DELETE
    // =========================================================
    @Test
    void delete_shouldDeleteWhenExists() {
        when(eventRepositoryPort.findById(1L)).thenReturn(Optional.of(new Event()));

        eventService.delete(1L);

        verify(eventRepositoryPort).deleteById(1L);
    }

    @Test
    void delete_shouldThrowWhenNotFound() {
        when(eventRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> eventService.delete(1L));

        verify(eventRepositoryPort, never()).deleteById(anyLong());
    }

    // =========================================================
    // FIND BY ID / ALL
    // =========================================================
    @Test
    void findById_shouldReturnEvent() {
        Event event = new Event();
        event.setId(5L);
        when(eventRepositoryPort.findById(5L)).thenReturn(Optional.of(event));

        Event result = eventService.findById(5L);

        assertEquals(5L, result.getId());
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(eventRepositoryPort.findById(5L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> eventService.findById(5L));
    }

    @Test
    void findAll_shouldReturnList() {
        when(eventRepositoryPort.findAll()).thenReturn(List.of(new Event(), new Event()));

        List<Event> result = eventService.findAll();

        assertEquals(2, result.size());
        verify(eventRepositoryPort).findAll();
    }

    // =========================================================
    // OTROS GETTERS
    // =========================================================
    @Test
    void getByCategory_shouldDelegateToRepository() {
        when(eventRepositoryPort.getByCategory("NORMAL", 0, 10))
                .thenReturn(List.of(new Event()));

        List<Event> result = eventService.getByCategory("NORMAL", 0, 10);

        assertEquals(1, result.size());
        verify(eventRepositoryPort).getByCategory("NORMAL", 0, 10);
    }

    @Test
    void getByDateBegin_shouldDelegateToRepository() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        when(eventRepositoryPort.getByDateBegin(date))
                .thenReturn(List.of(new Event()));

        List<Event> result = eventService.getByDateBegin(date);

        assertEquals(1, result.size());
        verify(eventRepositoryPort).getByDateBegin(date);
    }

    @Test
    void getByVenueId_shouldDelegateToRepository() {
        when(eventRepositoryPort.getByVenueId(10L))
                .thenReturn(List.of(new Event()));

        List<Event> result = eventService.getByVenueId(10L);

        assertEquals(1, result.size());
        verify(eventRepositoryPort).getByVenueId(10L);
    }

    @Test
    void findByName_shouldReturnOptionalFromRepository() {
        Event event = new Event();
        event.setName("Concert");

        when(eventRepositoryPort.findByName("Concert"))
                .thenReturn(Optional.of(event));

        Optional<Event> result = eventService.findByName("Concert");

        assertTrue(result.isPresent());
        assertEquals("Concert", result.get().getName());
    }
}
