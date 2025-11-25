package com.xguerrerov.venues.infrastructure.adapters.in.web.mapper;

import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.domain.model.State;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = { Category.class, State.class })
public interface EventDtoMapper {

    // ============================================================
    // DTO → DOMAIN
    // ============================================================
    @Mappings({
            @Mapping(target = "venue", ignore = true),   // Se asigna en el UseCase
            @Mapping(
                    target = "category",
                    expression = "java(Category.valueOf(dto.getCategory().toUpperCase()))"
            ),
            @Mapping(
                    target = "state",
                    expression = "java(State.valueOf(dto.getState().toUpperCase()))"
            )
    })
    Event toDomain(EventDto dto);

    // ============================================================
    // DOMAIN → DTO
    // ============================================================
    @Mappings({
            @Mapping(target = "venueId", source = "venue.id"),
            @Mapping(
                    target = "category",
                    expression = "java(event.getCategory().name())"
            ),
            @Mapping(
                    target = "state",
                    expression = "java(event.getState().name())"
            )
    })
    EventDto toDto(Event event);
}
