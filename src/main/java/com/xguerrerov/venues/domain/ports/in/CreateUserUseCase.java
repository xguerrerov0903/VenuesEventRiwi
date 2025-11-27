package com.xguerrerov.venues.domain.ports.in;

import com.xguerrerov.venues.domain.model.User;

public interface CreateUserUseCase {
    User register(User user);
}
