package com.xguerrerov.venues.domain.ports.out;

import com.xguerrerov.venues.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findByEmail(String email);
}
