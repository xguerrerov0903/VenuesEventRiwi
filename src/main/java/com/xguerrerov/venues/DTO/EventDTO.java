package com.xguerrerov.venues.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;

    @NotBlank(message = "Event name cannot be empty")
    private String name;

    @NotNull(message = "Event date is required")
    private LocalDate date;

    private String description;

    @NotNull(message = "Venue ID is required for the event")
    private Long venueId;
}