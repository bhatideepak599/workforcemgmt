package com.yourcompany.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for assigning tasks by customer reference
 * 
 * @author Deepak Bhati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AssignByReferenceRequest {
    @NotBlank(message = "Customer reference is required")
    private String customerReference;
    
    @NotNull(message = "New assignee ID is required")
    private Long newAssigneeId;
    
    @NotBlank(message = "New assignee name is required")
    private String newAssigneeName;
    
    @NotBlank(message = "Updated by is required")
    private String updatedBy;
}
