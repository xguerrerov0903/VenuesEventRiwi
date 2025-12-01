package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.model.State;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.EventDtoMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)   // 🔥 desactiva filtros de seguridad
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // 🔥 TODOS los casos de uso
    @MockBean private CreateEventUseCase createUseCase;
    @MockBean private UpdateEventUseCase updateUseCase;
    @MockBean private DeleteEventUseCase deleteUseCase;
    @MockBean private GetEventUseCase getUseCase;

    // 🔥 Mapper
    @MockBean private EventDtoMapper mapper;

    // -----------------------------------------------------------------------------
    // HELPERS
    // -----------------------------------------------------------------------------
    private Event createEventDomain() {
        Event e = new Event();
        e.setId(1L);
        e.setName("Test Event");
        e.setDateBegin(LocalDate.now().plusDays(1));
        e.setDateEnd(LocalDate.now().plusDays(8));
        e.setCategory(Category.NORMAL);
        e.setState(State.ACTIVE);
        e.setVenueId(10L);
        return e;
    }

    private EventDto createEventDto() {
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

    // -----------------------------------------------------------------------------
    // TEST: GET ALL
    // -----------------------------------------------------------------------------
    @Test
    void findAll_shouldReturnListOfEvents() throws Exception {
        Event domain = createEventDomain();
        EventDto dto = createEventDto();

        when(getUseCase.findAll()).thenReturn(List.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Event"));
    }

    // -----------------------------------------------------------------------------
    // TEST: GET BY ID
    // -----------------------------------------------------------------------------
    @Test
    void findById_shouldReturnSingleEvent() throws Exception {
        Event domain = createEventDomain();
        EventDto dto = createEventDto();

        when(getUseCase.findById(1L)).thenReturn(domain);
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Event"));
    }

    // -----------------------------------------------------------------------------
    // TEST: CREATE
    // -----------------------------------------------------------------------------
    @Test
    void create_shouldReturnSavedEvent() throws Exception {
        EventDto requestDto = createEventDto();
        requestDto.setId(null);  // en creación no se envía id

        Event domainReceived = createEventDomain();
        domainReceived.setId(null);

        Event savedDomain = createEventDomain();
        EventDto responseDto = createEventDto();

        when(mapper.toDomain(any(EventDto.class))).thenReturn(domainReceived);
        when(createUseCase.create(any(Event.class), eq(10L)))
                .thenReturn(savedDomain);
        when(mapper.toDto(savedDomain)).thenReturn(responseDto);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Event"));
    }

    // -----------------------------------------------------------------------------
    // TEST: UPDATE
    // -----------------------------------------------------------------------------
    @Test
    void update_shouldReturnUpdatedEvent() throws Exception {

        EventDto requestDto = createEventDto();
        requestDto.setName("Updated Event");

        Event domainToUpdate = createEventDomain();
        domainToUpdate.setName("Updated Event");

        Event updatedDomain = createEventDomain();
        updatedDomain.setName("Updated Event");

        EventDto responseDto = createEventDto();
        responseDto.setName("Updated Event");

        when(mapper.toDomain(any(EventDto.class))).thenReturn(domainToUpdate);
        when(updateUseCase.update(eq(1L), any(Event.class), eq(10L)))
                .thenReturn(updatedDomain);
        when(mapper.toDto(updatedDomain)).thenReturn(responseDto);

        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Event"));
    }

    // -----------------------------------------------------------------------------
    // TEST: DELETE
    // -----------------------------------------------------------------------------
    @Test
    void delete_shouldReturnNoContent() throws Exception {

        doNothing().when(deleteUseCase).delete(1L);

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNoContent());
    }

    // -----------------------------------------------------------------------------
    // TEST: GET BY CATEGORY
    // -----------------------------------------------------------------------------
    @Test
    void getByCategory_shouldReturnFilteredEvents() throws Exception {

        Event domain = createEventDomain();
        EventDto dto = createEventDto();

        when(getUseCase.getByCategory("NORMAL", 0, 10))
                .thenReturn(List.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events/category/NORMAL")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Event"));
    }

    // -----------------------------------------------------------------------------
    // TEST: GET BY DATE BEGIN
    // -----------------------------------------------------------------------------
    @Test
    void getByDateBegin_shouldReturnFilteredEvents() throws Exception {
        Event domain = createEventDomain();
        EventDto dto = createEventDto();

        String date = LocalDate.now().plusDays(1).toString();

        when(getUseCase.getByDateBegin(LocalDate.parse(date)))
                .thenReturn(List.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events/date-begin")
                        .param("date", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    // -----------------------------------------------------------------------------
    // TEST: GET BY NAME
    // -----------------------------------------------------------------------------
    @Test
    void findByName_shouldReturnEvent() throws Exception {

        Event domain = createEventDomain();
        EventDto dto = createEventDto();

        when(getUseCase.findByName("Test Event"))
                .thenReturn(Optional.of(domain));
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(get("/events/by-name")
                        .param("name", "Test Event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Event"));
    }
}
