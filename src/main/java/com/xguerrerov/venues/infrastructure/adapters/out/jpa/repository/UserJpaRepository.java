package com.xguerrerov.venues.infrastructure.adapters.out.jpa.repository;

import com.xguerrerov.venues.infrastructure.adapters.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
