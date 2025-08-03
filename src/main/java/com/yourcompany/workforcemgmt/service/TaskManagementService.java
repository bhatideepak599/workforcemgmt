package com.yourcompany.workforcemgmt.service;

import com.yourcompany.workforcemgmt.dto.*;
import com.yourcompany.workforcemgmt.model.Priority;

import java.util.List;

/**
 * Service interface for task management operations
 * 
 * @author Deepak Bhati
 */
public interface TaskManagementService {
    
    /**
     * Creates new tasks based on the provided request
     * @param request the task creation request containing task details
     * @return list of created task DTOs
     * @throws IllegalArgumentException if request is invalid
     */
    List<TaskManagementDto> createTasks(TaskCreateRequest request);
    
    /**
     * Updates existing tasks with new information
     * @param request the update request containing task modifications
     * @return updated task DTO
     * @throws ResourceNotFoundException if task not found
     */
    TaskManagementDto updateTask(UpdateTaskRequest request);
    
    /**
     * Assigns tasks by reference, handling reassignment logic
     * @param request the assignment request with reference details
     * @return success message
     * @throws ResourceNotFoundException if reference not found
     */
    String assignByReference(AssignByReferenceRequest request);
    
    /**
     * Fetches tasks within a specified date range for given assignees
     * @param request the date range and assignee filter request
     * @return list of matching task DTOs (excluding cancelled tasks)
     */
    List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request);
    
    /**
     * Retrieves a single task by its ID with full details including history
     * @param id the task identifier
     * @return task DTO if found
     * @throws ResourceNotFoundException if task not found
     */
    TaskManagementDto findTaskById(Long id);
    
    /**
     * Updates task priority
     * @param request the priority update request
     * @return updated task DTO
     * @throws ResourceNotFoundException if task not found
     */
    TaskManagementDto updateTaskPriority(UpdatePriorityRequest request);
    
    /**
     * Fetches tasks by priority
     * @param priority the priority to filter by
     * @return list of tasks with specified priority
     */
    List<TaskManagementDto> getTasksByPriority(Priority priority);
    
    /**
     * Adds a comment to a task
     * @param request the comment request
     * @return updated task DTO with new comment
     * @throws ResourceNotFoundException if task not found
     */
    TaskManagementDto addComment(AddCommentRequest request);

    /**
     * Retrieves all tasks without any filters
     * @return list of all tasks in the system
     */
    List<TaskManagementDto> getAllTasks();
}
