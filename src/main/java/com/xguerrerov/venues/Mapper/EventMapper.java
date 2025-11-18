package com.xguerrerov.venues.Mapper;

import com.xguerrerov.venues.DTO.EventDTO;
import com.xguerrerov.venues.Entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {


    @Mapping(target = "id", source = "eventEntity.id")
    @Mapping(target = "name", source = "eventEntity.name")
    @Mapping(target = "date", source = "eventEntity.date")
    @Mapping(target = "description", source = "eventEntity.description")
    @Mapping(target = "venueId", source = "eventEntity.venueId")
    EventDTO toDto(EventEntity eventEntity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "eventDTO.name")
    @Mapping(target = "date", source = "eventDTO.date")
    @Mapping(target = "description", source = "eventDTO.description")
    @Mapping(target = "venueId", source = "eventDTO.venueId")
    EventEntity toEntity(EventDTO eventDTO);
}