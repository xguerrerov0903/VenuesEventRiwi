package com.xguerrerov.venues.infrastructure.adapters.in.web.mapper;

import com.xguerrerov.venues.domain.model.Category;
import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",
        imports = { Category.class })   // ← CLAVE
public interface EventDtoMapper {

    @Mappings({
            @Mapping(target = "venue", ignore = true),
            @Mapping(target = "category",
                    expression = "java(Category.valueOf(dto.getCategory()))")
    })
    Event toDomain(EventDto dto);

    @Mappings({
            @Mapping(target = "venueId", source = "venue.id"),
            @Mapping(target = "category",
                    expression = "java(event.getCategory().name())")
    })
    EventDto toDto(Event event);
}
