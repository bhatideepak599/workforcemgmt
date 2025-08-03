package com.yourcompany.workforcemgmt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for task management operations
 * 
 * @author Deepak Bhati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskManagementDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Long assigneeId;
    private String assigneeName;
    private String customerReference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    
    // For detailed view with history
    private List<TaskActivityDto> activities;
    private List<TaskCommentDto> comments;
}
