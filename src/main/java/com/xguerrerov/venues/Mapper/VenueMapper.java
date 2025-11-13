package com.xguerrerov.venues.Mapper;

import com.xguerrerov.venues.DTO.VenueDTO;
import com.xguerrerov.venues.Entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VenueMapper {

    VenueMapper mapper = Mappers.getMapper(VenueMapper.class);

    @Mapping(target = "id", source = "venueEntity.id")
    @Mapping(target = "name", source = "venueEntity.name")
    VenueDTO toDto(VenueEntity venueEntity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "venueDTO.name")
    VenueEntity toEntity(VenueDTO venueDTO);
}