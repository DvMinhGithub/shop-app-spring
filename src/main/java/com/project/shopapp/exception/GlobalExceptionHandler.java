package com.project.shopapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.shopapp.dto.response.ApiResponse;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageUtils messageUtils;

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        log.error("Exception caught", e);
        String baseMessage = messageUtils.getMessage(MessageKeys.ERROR_INTERNAL_SERVER);
        String detailedMessage = e.getMessage();
        ApiResponse<?> response =
                new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), baseMessage + detailedMessage, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Exception caught", e);
        String baseMessage = messageUtils.getMessage(MessageKeys.ERROR_INVALID_REQUEST);
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<?> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), baseMessage + errorMessage, null);
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
