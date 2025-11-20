/* package com.xguerrerov.venues.Config;

import com.xguerrerov.venues.DTO.EventDTO;
import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Service.Interface.IEventService;
import com.xguerrerov.venues.Service.Interface.IVenueService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer {

    private final IVenueService venueService;
    private final IEventService eventService;

    public DataInitializer(IVenueService venueService, IEventService eventService) {
        this.venueService = venueService;
        this.eventService = eventService;
    }

    @PostConstruct
    public void initData() {

        // -------- VENUES --------
        VenueDTO v1 = venueService.create(new VenueDTO(null, "Auditorio Central"));
        VenueDTO v2 = venueService.create(new VenueDTO(null, "Coliseo Norte"));
        VenueDTO v3 = venueService.create(new VenueDTO(null, "Teatro Riwi"));
        VenueDTO v4 = venueService.create(new VenueDTO(null, "Centro Cultural Sur"));
        VenueDTO v5 = venueService.create(new VenueDTO(null, "Estadio Pequeño"));
        VenueDTO v6 = venueService.create(new VenueDTO(null, "Salón Comunal Este"));

        // -------- EVENTS --------
        eventService.create(new EventDTO(
                null,
                "Rock al Parque",
                LocalDate.now().plusDays(10),
                "Festival de música rock",
                v1.getId()
        ));

        eventService.create(new EventDTO(
                null,
                "Conferencia Java",
                LocalDate.now().plusDays(20),
                "Evento tecnológico para devs",
                v2.getId()
        ));

        eventService.create(new EventDTO(
                null,
                "Obra Romeo y Julieta",
                LocalDate.now().plusDays(5),
                "Clásico de teatro",
                v3.getId()
        ));

        eventService.create(new EventDTO(
                null,
                "Feria del Libro",
                LocalDate.now().plusDays(15),
                "Evento cultural y literario",
                v4.getId()
        ));

        eventService.create(new EventDTO(
                null,
                "Copa Local de Fútbol",
                LocalDate.now().plusDays(30),
                "Torneo deportivo regional",
                v5.getId()
        ));

        eventService.create(new EventDTO(
                null,
                "Taller de Programación",
                LocalDate.now().plusDays(3),
                "Curso intensivo de programación",
                v6.getId()
        ));

        System.out.println("🟢 Datos iniciales (venues + events) cargados en memoria.");
    }
}
*/