package com.project.shopapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.shopapp.model.dto.response.ApiResponse;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageUtils messageUtils;
    private static final String BASE_MESSAGE = "An error occurred ";

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        log.error("Detailed error message: {}", e.getMessage());
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(BASE_MESSAGE, e);
        String baseMessage = messageUtils.getMessage(MessageKeys.ERROR_INVALID_REQUEST);
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<Void> response =
                new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), baseMessage + errorMessage, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataNotFoundException(DataNotFoundException e) {
        log.error(BASE_MESSAGE, e);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPasswordException(InvalidPasswordException e) {
        log.error(BASE_MESSAGE, e);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = DuplicateEntryException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEntryException(DuplicateEntryException e) {
        log.error(BASE_MESSAGE, e);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(value = ImageUploadException.class)
    public ResponseEntity<ApiResponse<Void>> handleImageUploadException(ImageUploadException e) {
        log.error(BASE_MESSAGE, e);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
