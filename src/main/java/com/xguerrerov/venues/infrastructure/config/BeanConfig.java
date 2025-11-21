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
    public EventService eventService(EventRepositoryPort eventRepositoryPort) {
        return new EventService(eventRepositoryPort);
    }

    @Bean
    public CreateEventUseCase createEventUseCase(EventService service) {
        return service;
    }

    @Bean
    public UpdateEventUseCase updateEventUseCase(EventService service) {
        return service;
    }

    @Bean
    public DeleteEventUseCase deleteEventUseCase(EventService service) {
        return service;
    }

    @Bean
    public GetEventUseCase getEventUseCase(EventService service) {
        return service;
    }

    // ====== VENUE ======

    @Bean
    public VenueService venueService(VenueRepositoryPort venueRepositoryPort) {
        return new VenueService(venueRepositoryPort);
    }

    @Bean
    public CreateVenueUseCase createVenueUseCase(VenueService service) {
        return service;
    }

    @Bean
    public UpdateVenueUseCase updateVenueUseCase(VenueService service) {
        return service;
    }

    @Bean
    public DeleteVenueUseCase deleteVenueUseCase(VenueService service) {
        return service;
    }

    @Bean
    public GetVenueUseCase getVenueUseCase(VenueService service) {
        return service;
    }
}
