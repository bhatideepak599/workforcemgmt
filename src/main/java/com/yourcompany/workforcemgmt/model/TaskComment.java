package com.yourcompany.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TaskComment model for user comments on tasks
 * 
 * @author Deepak Bhati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskComment {
    private Long id;
    private Long taskId;
    private String comment;
    private String commentedBy;
    private LocalDateTime timestamp;
}
