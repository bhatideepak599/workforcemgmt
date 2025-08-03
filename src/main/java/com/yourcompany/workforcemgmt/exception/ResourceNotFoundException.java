package com.yourcompany.workforcemgmt.exception;

/**
 * Exception thrown when a requested resource is not found
 * 
 * @author Deepak Bhati
 */
public class ResourceNotFoundException extends RuntimeException {
    
    /**
     * Constructor with message
     * @param message the error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * @param message the error message
     * @param cause the underlying cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
