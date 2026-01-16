package com.flightontime.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.flightontime.exception.dto.ApiError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fields.put(fe.getField(), fe.getDefaultMessage());
        }

        ApiError body = new ApiError(
                LocalDateTime.now(),
                400,
                "Bad Request",
                "Erro de validação",
                req.getRequestURI(),
                fields);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(PredictionServiceUnavailableException.class)
    public ResponseEntity<ApiError> handlePredictionUnavailable(PredictionServiceUnavailableException ex,
            HttpServletRequest req) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                503,
                HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
                ex.getMessage(),
                req.getRequestURI(),
                null);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClient(WebClientResponseException ex, HttpServletRequest req) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                ex.getStatusCode().value(),
                ex.getStatusText(),
                "Erro ao chamar serviço de predição",
                req.getRequestURI(),
                null);
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        ApiError body = new ApiError(
                LocalDateTime.now(),
                500,
                "Internal Server Error",
                "Erro inesperado",
                req.getRequestURI(),
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}