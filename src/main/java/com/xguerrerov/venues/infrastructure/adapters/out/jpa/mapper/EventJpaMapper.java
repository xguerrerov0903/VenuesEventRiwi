package com.xguerrerov.venues.infrastructure.adapters.out.jpa.mapper;


import com.xguerrerov.venues.domain.model.Event;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventJpaMapper {
    @Mapping(target = "venue", ignore = false)
    EventEntity toEntity(Event domain);
    Event toDomain(EventEntity entity);
}

