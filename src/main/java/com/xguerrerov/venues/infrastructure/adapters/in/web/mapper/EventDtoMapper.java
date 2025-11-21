package com.xguerrerov.venues.infrastructure.adapters.in.web.mapper;

import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.Event;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EventDtoMapper {

    // DTO → Domain
    @Mappings({
            @Mapping(target = "venue", ignore = true),
            @Mapping(target = "category", expression = "java( Category.valueOf(dto.getCategory()) )")
    })
    Event toDomain(EventDto dto);

    // Domain → DTO
    @Mappings({
            @Mapping(target = "venueId", source = "venue.id"),
            @Mapping(target = "category", expression = "java( event.getCategory().name() )")
    })
    EventDto toDto(Event event);
}
