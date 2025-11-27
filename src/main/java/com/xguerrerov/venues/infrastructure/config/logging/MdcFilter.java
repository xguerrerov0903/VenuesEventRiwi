package com.xguerrerov.venues.infrastructure.config.logging;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

public class MdcFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain)
            throws IOException, ServletException {

        try {
            // Generar un ID único por request
            String traceId = UUID.randomUUID().toString();

            // Guardarlo en el MDC
            MDC.put("traceId", traceId);

            // Continuar la cadena del filtro
            chain.doFilter(request, response);

        } finally {
            // Limpiar el MDC después del request
            MDC.clear();
        }
    }
}
