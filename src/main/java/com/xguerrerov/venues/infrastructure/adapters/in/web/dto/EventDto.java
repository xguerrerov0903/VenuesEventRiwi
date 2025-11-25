package com.xguerrerov.venues.infrastructure.adapters.in.web.dto;

import com.xguerrerov.venues.Utils.ValueOfEnum;
import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.State;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {

    private Long id;

    @NotBlank(message = "Event name cannot be empty")
    @Size(min = 3, max = 100, message = "The event name needs at least 3 characters and a maximum of 100 characters")
    private String name;

    @NotNull(message = "Event start date is required")
    @Future(message = "Event start date must be in the future")
    private LocalDate dateBegin;

    @NotNull(message = "Event end date is required")
    @Future(message = "Event end date must be in the future")
    private LocalDate dateEnd;

    private String description;

    @NotBlank(message = "Event category cannot be empty")
    @ValueOfEnum(enumClass = Category.class, message = "Category must be NORMAL, CRAZY or BORING")
    private String category;

    @NotBlank(message = "Event state cannot be empty")
    @ValueOfEnum(enumClass = State.class, message = "State must be: DRAFT, ACTIVE or CANCELLED")
    private String state;

    @NotNull(message = "Venue ID is required for the event")
    private Long venueId;
}
