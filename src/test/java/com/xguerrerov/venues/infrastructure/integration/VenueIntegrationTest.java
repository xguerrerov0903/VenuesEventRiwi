package com.xguerrerov.venues.infrastructure.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xguerrerov.venues.IntegrationTestBase;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.VenueDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VenueIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateAndRetrieveVenue() throws Exception {

        VenueDto dto = VenueDto.builder()
                .name("Integration Venue")
                .city("Medellin")
                .build();

        // CREATE VENUE
        var result = mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("INTEGRATION VENUE"))
                .andReturn();

        VenueDto created = mapper.readValue(
                result.getResponse().getContentAsString(),
                VenueDto.class
        );

        // GET VENUE BY ID
        mockMvc.perform(get("/venues/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("MEDELLIN"))
                .andExpect(jsonPath("$.name").value("INTEGRATION VENUE"));
    }
}
