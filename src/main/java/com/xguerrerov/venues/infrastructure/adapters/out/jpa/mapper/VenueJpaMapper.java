package com.xguerrerov.venues.infrastructure.adapters.out.jpa.mapper;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.VenueEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VenueJpaMapper {

    @Mapping(target = "events", ignore = true)
    VenueEntity toEntity(Venue domain);
    @Mapping(target = "events", ignore = true)
    Venue toDomain(VenueEntity entity);
}
