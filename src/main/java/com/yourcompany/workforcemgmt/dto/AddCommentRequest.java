package com.yourcompany.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for adding comments to tasks
 * 
 * @author Deepak Bhati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddCommentRequest {
    @NotNull(message = "Task ID is required")
    private Long taskId;
    
    @NotBlank(message = "Comment is required")
    private String comment;
    
    @NotBlank(message = "Commented by is required")
    private String commentedBy;
}
