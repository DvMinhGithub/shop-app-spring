package com.project.shopapp.exception;

import org.springframework.http.HttpStatus;
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
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Exception caught", e);
        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                e.getBindingResult().getFieldError().getDefaultMessage(),
                null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleDataNotFoundException(DataNotFoundException e) {
        log.error("Exception caught", e);
        ApiResponse<?> response = ApiResponse.<String>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidPasswordException(InvalidPasswordException e) {
        log.error("Exception caught", e);
        ApiResponse<?> response = ApiResponse.<String>builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
