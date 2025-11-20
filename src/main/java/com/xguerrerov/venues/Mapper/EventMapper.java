package com.xguerrerov.venues.Mapper;

import com.xguerrerov.venues.DTO.EventDTO;
import com.xguerrerov.venues.Entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "venueId", source = "venue.id")
    EventDTO toDto(EventEntity entity);

    @Mapping(target = "venue", ignore = true) // se asigna en el service
    EventEntity toEntity(EventDTO dto);
}