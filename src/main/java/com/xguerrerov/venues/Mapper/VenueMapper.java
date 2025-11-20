package com.xguerrerov.venues.Mapper;

import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface VenueMapper {

    VenueMapper mapper = Mappers.getMapper(VenueMapper.class);

    @Mapping(target = "id", source = "venueEntity.id")
    @Mapping(target = "name", source = "venueEntity.name")
    @Mapping(target = "city", source = "venueEntity.city")
    VenueDTO toDto(VenueEntity venueEntity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "venueDTO.name")
    @Mapping(target = "city", source = "venueDTO.city")
    @Mapping(target = "events", ignore = true)
    VenueEntity toEntity(VenueDTO venueDTO);
}