package com.project.shopapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.shopapp.dto.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        log.error("Exception caught", e);
        ApiResponse<?> response = new ApiResponse<>(500, e.getMessage(), null);
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Exception caught", e);
        ApiResponse<?> response =
                new ApiResponse<>(400, e.getBindingResult().getFieldError().getDefaultMessage(), null);
        return ResponseEntity.status(400).body(response);
    }
}
