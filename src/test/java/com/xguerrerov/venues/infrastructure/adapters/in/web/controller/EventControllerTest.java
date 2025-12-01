package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.model.State;
import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.EventDtoMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        })
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private CreateEventUseCase createUseCase;
    @MockBean private UpdateEventUseCase updateUseCase;
    @MockBean private DeleteEventUseCase deleteUseCase;
    @MockBean private GetEventUseCase getUseCase;

    @MockBean private EventDtoMapper mapper;


    // ----------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------
    private Event createDomainEvent() {
        Event e = new Event();
        e.setId(1L);
        e.setName("Test Event");
        e.setDateBegin(LocalDate.now().plusDays(1));
        e.setDateEnd(LocalDate.now().plusDays(8));
        e.setCategory(Category.NORMAL);
        e.setState(State.ACTIVE);

        Venue v = new Venue();
        v.setId(10L);
        v.setName("Venue One");
        v.setCity("MEDELLIN");
        e.setVenue(v);

        return e;
    }

    private EventDto createDto() {
        return EventDto.builder()
                .id(1L)
                .name("Test Event")
                .dateBegin(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(8))
                .category("NORMAL")
                .state("ACTIVE")
                .venueId(10L)
                .build();
    }


    // ----------------------------------------------------------
    // TEST: GET ALL
    // ----------------------------------------------------------
    @Test
    void findAll_shouldReturnListOfEvents() throws Exception {
        Event domain = createDomainEvent();
        EventDto dto = createDto();

        when(getUseCase.findAll()).thenReturn(List.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }


    // ----------------------------------------------------------
    // TEST: GET BY ID
    // ----------------------------------------------------------
    @Test
    void findById_shouldReturnSingleEvent() throws Exception {
        Event domain = createDomainEvent();
        EventDto dto = createDto();

        when(getUseCase.findById(1L)).thenReturn(domain);
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Event"));
    }


    // ----------------------------------------------------------
    // TEST: CREATE EVENT
    // ----------------------------------------------------------
    @Test
    void create_shouldReturnSavedEvent() throws Exception {
        EventDto requestDto = createDto();
        requestDto.setId(null);

        Event domainFromMapper = createDomainEvent();
        domainFromMapper.setId(null);

        Event saved = createDomainEvent();
        EventDto responseDto = createDto();

        when(mapper.toDomain(any(EventDto.class))).thenReturn(domainFromMapper);
        when(createUseCase.create(any(Event.class), eq(10L))).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(responseDto);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }


    // ----------------------------------------------------------
    // TEST: UPDATE EVENT
    // ----------------------------------------------------------
    @Test
    void update_shouldReturnUpdatedEvent() throws Exception {

        EventDto requestDto = createDto();
        requestDto.setName("Updated Event");

        Event domainToUpdate = createDomainEvent();
        domainToUpdate.setName("Updated Event");

        Event updated = createDomainEvent();
        updated.setName("Updated Event");

        EventDto responseDto = createDto();
        responseDto.setName("Updated Event");

        when(mapper.toDomain(any(EventDto.class))).thenReturn(domainToUpdate);
        when(updateUseCase.update(eq(1L), any(Event.class), eq(10L))).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(responseDto);

        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Event"));
    }


    // ----------------------------------------------------------
    // TEST: DELETE EVENT
    // ----------------------------------------------------------
    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(deleteUseCase).delete(1L);

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNoContent());
    }


    // ----------------------------------------------------------
    // TEST: GET BY NAME
    // ----------------------------------------------------------
    @Test
    void findByName_shouldReturnEvent() throws Exception {
        Event domain = createDomainEvent();
        EventDto dto = createDto();

        when(getUseCase.findByName("Test Event")).thenReturn(Optional.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events/by-name")
                        .param("name", "Test Event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
