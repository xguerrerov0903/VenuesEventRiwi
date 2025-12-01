package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.VenueDto;
import com.xguerrerov.venues.infrastructure.adapters.in.web.mapper.VenueDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VenueController.class)
@AutoConfigureMockMvc(addFilters = false)
class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private CreateVenueUseCase createUseCase;
    @MockBean private UpdateVenueUseCase updateUseCase;
    @MockBean private DeleteVenueUseCase deleteUseCase;
    @MockBean private GetVenueUseCase getUseCase;
    @MockBean private VenueDtoMapper mapper;

    private Venue createVenue() {
        Venue v = new Venue();
        v.setId(1L);
        v.setName("My Venue");
        v.setCity("New York");
        return v;
    }


    private VenueDto createVenueDto() {
        return VenueDto.builder()
                .id(1L)
                .name("Test Venue")
                .city("Medellin")
                .build();
    }

    @Test
    void testCreateVenue() throws Exception {
        VenueDto dto = createVenueDto();
        Venue domain = createVenue();

        when(mapper.toDomain(dto)).thenReturn(domain);
        when(createUseCase.create(domain)).thenReturn(domain);
        when(mapper.toDto(domain)).thenReturn(dto);

        mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Venue"));
    }

    @Test
    void testFindById() throws Exception {
        Venue venue = createVenue();
        VenueDto dto = createVenueDto();

        when(getUseCase.findById(1L)).thenReturn(venue);
        when(mapper.toDto(venue)).thenReturn(dto);

        mockMvc.perform(get("/venues/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Medellin"));
    }

    @Test
    void testFindAll() throws Exception {
        Venue venue = createVenue();
        VenueDto dto = createVenueDto();

        when(getUseCase.findAll()).thenReturn(List.of(venue));
        when(mapper.toDto(venue)).thenReturn(dto);

        mockMvc.perform(get("/venues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Venue"));
    }

    @Test
    void testUpdateVenue() throws Exception {
        VenueDto dto = createVenueDto();
        Venue venue = createVenue();

        when(mapper.toDomain(dto)).thenReturn(venue);
        when(updateUseCase.update(1L, venue)).thenReturn(venue);
        when(mapper.toDto(venue)).thenReturn(dto);

        mockMvc.perform(put("/venues/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Venue"));
    }

    @Test
    void testDeleteVenue() throws Exception {
        mockMvc.perform(delete("/venues/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetByCity() throws Exception {
        Venue venue = createVenue();
        VenueDto dto = createVenueDto();

        when(getUseCase.getByCity("Medellin")).thenReturn(List.of(venue));
        when(mapper.toDto(venue)).thenReturn(dto);

        mockMvc.perform(get("/venues/city/Medellin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Medellin"));
    }
}
