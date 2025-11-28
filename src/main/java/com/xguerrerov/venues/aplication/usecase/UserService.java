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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, LoginUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User register(User user) {

        log.info("Intentando registrar usuario con email: {}", user.getEmail());

        // Log previa validación
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            log.warn("Intento de registro fallido: contraseña vacía para email {}", user.getEmail());
            throw new RuntimeException("Password cannot be empty");
        }

        // Log del proceso de cifrado
        log.debug("Cifrando contraseña para usuario: {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Asignación de rol Default
        if (user.getRole() == null) {
            log.debug("No se recibió rol para el usuario {}, asignando rol USER por defecto", user.getEmail());
            user.setRole(Role.USER);
        }

        // Guardado en base de datos
        log.debug("Guardando usuario {} en base de datos…", user.getEmail());
        User saved = userRepositoryPort.save(user);

        log.info("Usuario registrado exitosamente con email: {}", saved.getEmail());

        return saved;
    }



    @Override
    public String login(String email, String password) {

        log.info("Buscando usuario con email: {}", email);

        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado: {}", email);
                    return new RuntimeException("User not found");
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Contraseña incorrecta para usuario: {}", email);
            throw new RuntimeException("Invalid credentials");
        }

        log.info("Usuario autenticado correctamente: {}", email);

        UserDetails securityUser = new UserSecurity(user);
        return jwtService.generateToken(securityUser);
    }

}
