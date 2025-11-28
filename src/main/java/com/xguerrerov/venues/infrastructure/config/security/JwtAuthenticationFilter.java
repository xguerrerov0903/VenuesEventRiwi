package com.xguerrerov.venues.infrastructure.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // ---------------------------------------------------------------------------------
        // 1. IGNORAR RUTAS /auth*
        // ---------------------------------------------------------------------------------
        if (path.startsWith("/auth")) {
            log.debug("Ruta {} ignorada por JwtAuthenticationFilter (endpoint público)", path);
            filterChain.doFilter(request, response);
            return;
        }

        // ---------------------------------------------------------------------------------
        // 2. EXTRAER TOKEN DEL HEADER
        // ---------------------------------------------------------------------------------
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            log.debug("Solicitud a {} sin token JWT. Continuando sin autenticación.",
                    request.getRequestURI());

            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // ---------------------------------------------------------------------------------
        // 3. EXTRAER USERNAME DEL TOKEN
        // ---------------------------------------------------------------------------------
        String username = null;

        try {
            username = jwtService.extractUsername(token);
            log.debug("Token recibido. Usuario extraído: {}", username);

        } catch (Exception ex) {
            log.warn("Fallo al extraer username del token en {}: {}",
                    request.getRequestURI(), ex.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // ---------------------------------------------------------------------------------
        // 4. VALIDACIÓN DE AUTHENTICACIÓN ACTUAL
        // ---------------------------------------------------------------------------------
        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            log.debug("Cargando detalles del usuario: {}", username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // ---------------------------------------------------------------------------------
            // 5. VALIDAR TOKEN
            // ---------------------------------------------------------------------------------
            if (jwtService.isTokenValid(token, userDetails)) {

                log.info("Token válido para usuario: {}", username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);

            } else {
                log.warn("Token inválido para usuario {}", username);
            }
        }

        // ---------------------------------------------------------------------------------
        // 6. CONTINUAR LA CADENA DE FILTROS
        // ---------------------------------------------------------------------------------
        filterChain.doFilter(request, response);
    }
}
