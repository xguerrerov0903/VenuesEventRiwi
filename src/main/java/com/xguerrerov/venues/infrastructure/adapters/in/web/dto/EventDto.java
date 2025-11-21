package com.xguerrerov.venues.infrastructure.adapters.in.web.dto;

import com.xguerrerov.venues.Utils.ValueOfEnum;
import com.xguerrerov.venues.domain.model.Category;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private Long id;

    @NotBlank(message = "Event name cannot be empty")
    @Size(min = 3, max = 100, message = "The event name need at least 3 characters and a maximum of 100 characters")
    private String name;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDate dateBegin;

    private String description;

    @NotBlank(message = "Event category cannot be empty")
    @ValueOfEnum(enumClass = Category.class, message = "The category should be: NORMAL, CRAZY o BORING")
    private String category;

    @NotNull(message = "Venue ID is required for the event")
    private Long venueId;
}
