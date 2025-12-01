package com.xguerrerov.venues.infrastructure.adapters.in.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestSecurityController {

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String testUser() {
        log.info("Acceso permitido: ROLE_USER o ROLE_ADMIN");
        return "Acceso permitido: ROLE_USER o ROLE_ADMIN";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdmin() {
        return "Acceso permitido: SOLO ROLE_ADMIN";
    }
}
