package com.xguerrerov.venues.Exception;

import com.xguerrerov.venues.Exception.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Not Found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(NotFoundException ex, HttpServletRequest request) {

        ErrorDTO error = ErrorDTO.builder()
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 400 - JSON mal formado
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleInvalidJson(HttpMessageNotReadableException ex,
                                                      HttpServletRequest request) {

        ErrorDTO error = ErrorDTO.builder()
                .error("Bad Request")
                .message("El cuerpo JSON enviado está mal formado o contiene valores inválidos.")
                .path(request.getRequestURI())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 400 - Validaciones @Valid (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException ex,
                                                              HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ErrorDTO error = ErrorDTO.builder()
                .error("Validation Error")
                .message(message)
                .path(request.getRequestURI())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 409 - Conflictos (ej. nombre duplicado)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateResource(DuplicateResourceException ex,
                                                            HttpServletRequest request) {

        ErrorDTO error = ErrorDTO.builder()
                .error("Conflict")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 400 - Argumentos inválidos de negocio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException ex,
                                                          HttpServletRequest request) {

        ErrorDTO error = ErrorDTO.builder()
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // (Opcional) 500 - Cualquier otro error no controlado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex,
                                                           HttpServletRequest request) {

        ErrorDTO error = ErrorDTO.builder()
                .error("Internal Server Error")
                .message("Ocurrió un error inesperado. Inténtalo de nuevo más tarde.")
                .path(request.getRequestURI())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
