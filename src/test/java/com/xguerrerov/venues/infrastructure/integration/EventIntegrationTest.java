package com.xguerrerov.venues.infrastructure.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xguerrerov.venues.IntegrationTestBase;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EventIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateAndFindEvent() throws Exception {

        EventDto dto = EventDto.builder()
                .name("Integration Event")
                .category("NORMAL")
                .state("ACTIVE")
                .dateBegin(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(3))
                .venueId(1L)
                .build();

        // CREATE EVENT
        var result = mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("INTEGRATION EVENT"))
                .andReturn();

        // Convert response to EventDto to extract ID
        EventDto created = mapper.readValue(
                result.getResponse().getContentAsString(),
                EventDto.class
        );

        // GET CREATED EVENT BY ID
        mockMvc.perform(get("/events/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("INTEGRATION EVENT"));
    }
}
