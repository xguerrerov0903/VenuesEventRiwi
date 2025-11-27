package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import com.xguerrerov.venues.aplication.usecase.UserService;
import com.xguerrerov.venues.domain.model.User;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.AuthRequest;
import com.xguerrerov.venues.infrastructure.adapters.in.web.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // ========================= REGISTER =========================

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User saved = userService.register(user);
        return ResponseEntity.ok(saved);
    }

    // =========================== LOGIN ==========================

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = userService.login(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
