package com.xguerrerov.venues.aplication.usecase;

import com.xguerrerov.venues.domain.model.Role;
import com.xguerrerov.venues.domain.model.User;
import com.xguerrerov.venues.domain.ports.in.CreateUserUseCase;
import com.xguerrerov.venues.domain.ports.in.LoginUserUseCase;
import com.xguerrerov.venues.domain.ports.out.UserRepositoryPort;
import com.xguerrerov.venues.infrastructure.config.security.JwtService;
import com.xguerrerov.venues.infrastructure.config.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, LoginUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // por defecto todos USER, pero puedes cambiarlo por ADMIN manualmente en la DB
        if(user.getRole() == null) {
            user.setRole(Role.USER);
        }

        return userRepositoryPort.save(user);
    }

    @Override
    public String login(String email, String password) {

        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        UserDetails securityUser = new UserSecurity(user);

        return jwtService.generateToken(securityUser);
    }


}
