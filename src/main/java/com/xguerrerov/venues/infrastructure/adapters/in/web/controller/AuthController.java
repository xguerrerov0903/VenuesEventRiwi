package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.xguerrerov.venues.aplication.usecase.UserService;
import com.xguerrerov.venues.domain.model.User;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.AuthRequest;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // ========================= REGISTER =========================

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        log.info("Intentando registrar usuario con email: {}", user.getEmail());
        User saved = userService.register(user);
        log.info("Usuario registrado exitosamente con email: {}", saved.getEmail());
        return ResponseEntity.ok(saved);
    }

    // =========================== LOGIN ==========================

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        log.info("Intentando login para email: {}", request.email());
        String token = userService.login(request.email(), request.password());
        log.info("Login exitoso para email: {}", request.email());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
