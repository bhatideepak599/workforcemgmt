package com.yourcompany.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO for updating task information
 * 
 * @author Deepak Bhati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateTaskRequest {
    @NotNull(message = "Task ID is required")
    private Long taskId;
    
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Long assigneeId;
    private String assigneeName;
    private String customerReference;
    private String updatedBy;
}
