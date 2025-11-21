package com.xguerrerov.venues.infrastructure.adapters.in.web.mapper;

import com.xguerrerov.venues.domain.model.Venue;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.VenueDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VenueDtoMapper {

    // DTO → Domain
    Venue toDomain(VenueDto dto);

    // Domain → DTO
    VenueDto toDto(Venue venue);
}
