package com.xguerrerov.venues.infrastructure.adapters.out.jpa.mapper;

import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public interface VenueJpaMapper {
    @Mapping(target = "events", ignore = true)
    EventEntity toEntity(Event domain);

    Event toDomain(EventEntity entity);
}

