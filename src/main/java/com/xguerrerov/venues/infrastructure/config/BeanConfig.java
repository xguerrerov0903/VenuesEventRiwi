package com.xguerrerov.venues.infrastructure.config;

import com.xguerrerov.venues.aplication.usecase.EventService;
import com.xguerrerov.venues.aplication.usecase.VenueService;
import com.xguerrerov.venues.domain.ports.out.EventRepositoryPort;
import com.xguerrerov.venues.domain.ports.out.VenueRepositoryPort;
import com.xguerrerov.venues.infrastructure.metrics.EventsMetrics;
import com.xguerrerov.venues.infrastructure.metrics.VenuesMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public EventService eventService(
            EventsMetrics eventsMetrics,
            EventRepositoryPort eventRepositoryPort,
            VenueRepositoryPort venueRepositoryPort
    ) {
        return new EventService(eventsMetrics, eventRepositoryPort, venueRepositoryPort);
    }

    @Bean
    public VenueService venueService(
            VenuesMetrics venuesMetrics,
            VenueRepositoryPort venueRepositoryPort
    ) {
        return new VenueService(venuesMetrics, venueRepositoryPort);
    }
}
