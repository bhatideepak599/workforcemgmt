package com.yourcompany.workforcemgmt.exception;

import com.yourcompany.workforcemgmt.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application
 * 
 * @author Deepak Bhati
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * Handle resource not found exceptions
     * @param ex the ResourceNotFoundException
     * @return standardized error response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        BaseResponse<Object> response = new BaseResponse<>(ex.getMessage(), "RESOURCE_NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    /**
     * Handle validation exceptions
     * @param ex the MethodArgumentNotValidException
     * @return standardized error response with validation details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        BaseResponse<Object> response = new BaseResponse<>("Validation failed", "VALIDATION_ERROR");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * Handle illegal argument exceptions
     * @param ex the IllegalArgumentException
     * @return standardized error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        log.error("Invalid argument: {}", ex.getMessage());
        BaseResponse<Object> response = new BaseResponse<>(ex.getMessage(), "INVALID_ARGUMENT");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * Handle general exceptions
     * @param ex the Exception
     * @return standardized error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        BaseResponse<Object> response = new BaseResponse<>(
            "An unexpected error occurred", "INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
