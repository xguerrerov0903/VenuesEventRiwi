package com.xguerrerov.venues.infrastructure.adapters.out.jpa.mapper;

import com.xguerrerov.venues.domain.model.Role;
import com.xguerrerov.venues.domain.model.User;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = "spring",
        imports = {Role.class}   // 🔥 ESTA ES LA CLAVE
)
public interface UserJpaMapper {

    @Mappings({
            @Mapping(target = "role",
                    expression = "java(Role.valueOf(entity.getRole()))")
    })
    User toDomain(UserEntity entity);

    @Mappings({
            @Mapping(target = "role",
                    expression = "java(user.getRole().name())")
    })
    UserEntity toEntity(User user);
}
