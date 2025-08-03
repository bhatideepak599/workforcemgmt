package com.yourcompany.workforcemgmt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TaskActivity model for tracking task history
 * 
 * @author Deepak Bhati
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskActivity {
    private Long id;
    private Long taskId;
    private String action;
    private String performedBy;
    private LocalDateTime timestamp;
    private String details;
}
