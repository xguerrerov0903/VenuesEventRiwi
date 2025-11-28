package com.xguerrerov.venues.infrastructure.adapters.in.web.exception;

import com.xguerrerov.venues.domain.exceptions.BadRequestException;
import com.xguerrerov.venues.domain.exceptions.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ==========================
    // HELPERS
    // ==========================

    private ProblemDetail buildError(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request,
            String errorCode
    ) {

        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle(title);
        pd.setDetail(detail);
        pd.setType(URI.create("/errors/" + errorCode));
        pd.setInstance(URI.create(request.getRequestURI()));

        pd.setProperty("timestamp", LocalDateTime.now().toString());
        pd.setProperty("traceId", MDC.get("traceId"));
        pd.setProperty("errorCode", errorCode);

        return pd;
    }

    // ==========================
    // EXCEPCIONES DE DOMINIO
    // ==========================

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(NotFoundException ex, HttpServletRequest request) {

        log.warn("NOT_FOUND en {}: {}", request.getRequestURI(), ex.getMessage());

        return buildError(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage(),
                request,
                "NOT_FOUND"
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequest(BadRequestException ex, HttpServletRequest request) {

        log.warn("BAD_REQUEST en {}: {}", request.getRequestURI(), ex.getMessage());

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage(),
                request,
                "BAD_REQUEST"
        );
    }

    // ==========================
    // VALIDACIONES @Valid
    // ==========================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .reduce("", (a, b) -> a + b + "; ");

        log.warn("VALIDATION_ERROR en {}: {}", request.getRequestURI(), errors);

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                errors,
                request,
                "VALIDATION_ERROR"
        );
    }

    // ==========================
    // ERRORES JWT
    // ==========================

    @ExceptionHandler({
            BadCredentialsException.class,
            SignatureException.class,
            MalformedJwtException.class,
            UnsupportedJwtException.class
    })
    public ProblemDetail handleJwtInvalid(Exception ex, HttpServletRequest request) {

        log.warn("JWT_INVALID en {}: {}", request.getRequestURI(), ex.getMessage());

        return buildError(
                HttpStatus.UNAUTHORIZED,
                "Invalid Token",
                ex.getMessage(),
                request,
                "JWT_INVALID"
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleJwtExpired(ExpiredJwtException ex, HttpServletRequest request) {

        log.warn("JWT_EXPIRED en {}: {}", request.getRequestURI(), ex.getMessage());

        return buildError(
                HttpStatus.UNAUTHORIZED,
                "Token Expired",
                ex.getMessage(),
                request,
                "JWT_EXPIRED"
        );
    }

    // ==========================
    // ACCESO DENEGADO
    // ==========================

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {

        log.warn("FORBIDDEN en {}: acceso denegado", request.getRequestURI());

        return buildError(
                HttpStatus.FORBIDDEN,
                "Access Denied",
                "You do not have permission to perform this action",
                request,
                "FORBIDDEN"
        );
    }

    // ==========================
    // ERROR GENERICO 500
    // ==========================

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(Exception ex, HttpServletRequest request) {

        log.error("INTERNAL_ERROR en {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Unexpected error occurred",
                request,
                "INTERNAL_ERROR"
        );
    }
}
