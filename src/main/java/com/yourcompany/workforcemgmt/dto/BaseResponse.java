package com.yourcompany.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * Base response wrapper for all API responses
 * 
 * @author Deepak Bhati
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    private Long timestamp;
    
    /**
     * Constructor for successful responses
     * @param data the response data
     * @param message success message
     */
    public BaseResponse(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * Constructor for error responses
     * @param message error message
     * @param errorCode error code
     */
    public BaseResponse(String message, String errorCode) {
        this.success = false;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
}
