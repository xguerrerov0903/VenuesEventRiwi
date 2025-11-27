package com.xguerrerov.venues.infrastructure.adapters.out.jpa.adapter;

import com.xguerrerov.venues.domain.model.User;
import com.xguerrerov.venues.domain.ports.out.UserRepositoryPort;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.UserEntity;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.mapper.UserJpaMapper;
import com.xguerrerov.venues.infrastructure.adapters.out.jpa.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaRepository repository;
    private final UserJpaMapper mapper;

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

}
