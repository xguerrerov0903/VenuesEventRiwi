package com.xguerrerov.venues.DTO;

import com.xguerrerov.venues.Entity.Category;
import com.xguerrerov.venues.Utils.ValueOfEnum;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
public class EventDTO {
    private Long id;

    @NotBlank(message = "Event name cannot be empty")
    @Size(min = 3, max = 100, message = "The event name need at least 3 characters and a maximum of 100 characters")
    private String name;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDate dateBegin;

    private String description;

    @NotBlank(message = "Event category cannot be empty")
    @ValueOfEnum(enumClass = Category.class, message = "La categoría debe ser: NORMAL, CRAZY o BORING")
    private String category;

    @NotNull(message = "Venue ID is required for the event")
    private Long venueId;
}