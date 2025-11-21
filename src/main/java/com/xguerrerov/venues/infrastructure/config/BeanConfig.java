package com.xguerrerov.venues.infrastructure.config;

import com.xguerrerov.venues.aplication.usecase.EventService;
import com.xguerrerov.venues.aplication.usecase.VenueService;
import com.xguerrerov.venues.domain.ports.in.*;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    // ====== EVENT ======

    @Bean
    public EventService eventService(EventRepositoryPort eventRepositoryPort,
                                     VenueRepositoryPort venueRepositoryPort) {
        return new EventService(eventRepositoryPort, venueRepositoryPort);
    }


    // ====== VENUE ======

    @Bean
    public VenueService venueService(VenueRepositoryPort venueRepositoryPort) {
        return new VenueService(venueRepositoryPort);
    }
}

