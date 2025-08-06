package com.yourcompany.workforcemgmt.controller;

import com.yourcompany.workforcemgmt.dto.*;
import com.yourcompany.workforcemgmt.model.Priority;
import com.yourcompany.workforcemgmt.service.TaskManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for task management operations
 * 
 * @author Deepak Bhati
 */
@RestController
@RequestMapping("/api/v1/tasks")
@Validated
@Slf4j
@RequiredArgsConstructor
public class TaskManagementController {

    private final TaskManagementService taskManagementService;

    /**
     * Retrieves a task by its ID
     * 
     * @param id the task identifier
     * @return standardized response containing task details
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<TaskManagementDto>> getTaskById(
            @PathVariable @NotNull Long id) {
        log.info("Fetching task with ID: {}", id);
        TaskManagementDto task = taskManagementService.findTaskById(id);
        BaseResponse<TaskManagementDto> response = new BaseResponse<>(task, "Task retrieved successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Creates new tasks
     * 
     * @param request the task creation request
     * @return standardized response containing created tasks
     */
    @PostMapping
    public ResponseEntity<BaseResponse<List<TaskManagementDto>>> createTasks(
            @RequestBody @Valid TaskCreateRequest request) {
        log.info("Creating {} tasks", request.getRequests().size());
        List<TaskManagementDto> createdTasks = taskManagementService.createTasks(request);
        BaseResponse<List<TaskManagementDto>> response = new BaseResponse<>(
                createdTasks, "Tasks created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates an existing task
     * 
     * @param request the task update request
     * @return standardized response containing updated task
     */
    @PutMapping
    public ResponseEntity<BaseResponse<TaskManagementDto>> updateTask(
            @RequestBody @Valid UpdateTaskRequest request) {
        log.info("Updating task with ID: {}", request.getTaskId());
        TaskManagementDto updatedTask = taskManagementService.updateTask(request);
        BaseResponse<TaskManagementDto> response = new BaseResponse<>(
                updatedTask, "Task updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Assigns tasks by customer reference
     * 
     * @param request the assignment request
     * @return standardized response with success message
     */
    @PostMapping("/assign-by-reference")
    public ResponseEntity<BaseResponse<String>> assignByReference(
            @RequestBody @Valid AssignByReferenceRequest request) {
        log.info("Assigning tasks by reference: {}", request.getCustomerReference());
        String result = taskManagementService.assignByReference(request);
        BaseResponse<String> response = new BaseResponse<>(result, "Tasks assigned successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches tasks by date range
     * 
     * @param request the date range request
     * @return standardized response containing filtered tasks
     */
    @PostMapping("/fetch-by-date")
    public ResponseEntity<BaseResponse<List<TaskManagementDto>>> fetchTasksByDate(
            @RequestBody @Valid TaskFetchByDateRequest request) {
        log.info("Fetching tasks by date range: {} to {}", request.getStartDate(), request.getEndDate());
        List<TaskManagementDto> tasks = taskManagementService.fetchTasksByDate(request);
        BaseResponse<List<TaskManagementDto>> response = new BaseResponse<>(
                tasks, "Tasks retrieved successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Updates task priority
     * 
     * @param request the priority update request
     * @return standardized response containing updated task
     */
    @PutMapping("/priority")
    public ResponseEntity<BaseResponse<TaskManagementDto>> updateTaskPriority(
            @RequestBody @Valid UpdatePriorityRequest request) {
        log.info("Updating priority for task ID: {}", request.getTaskId());
        TaskManagementDto updatedTask = taskManagementService.updateTaskPriority(request);
        BaseResponse<TaskManagementDto> response = new BaseResponse<>(
                updatedTask, "Task priority updated successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Fetches tasks by priority
     * 
     * @param priority the priority level
     * @return standardized response containing tasks with specified priority
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<BaseResponse<List<TaskManagementDto>>> getTasksByPriority(
            @PathVariable Priority priority) {
        log.info("Fetching tasks with priority: {}", priority);
        List<TaskManagementDto> tasks = taskManagementService.getTasksByPriority(priority);
        BaseResponse<List<TaskManagementDto>> response = new BaseResponse<>(
                tasks, "Tasks retrieved successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Adds a comment to a task
     * 
     * @param request the comment request
     * @return standardized response containing updated task with new comment
     */
    @PostMapping("/comment")
    public ResponseEntity<BaseResponse<TaskManagementDto>> addComment(
            @RequestBody @Valid AddCommentRequest request) {
        log.info("Adding comment to task ID: {}", request.getTaskId());
        TaskManagementDto updatedTask = taskManagementService.addComment(request);
        BaseResponse<TaskManagementDto> response = new BaseResponse<>(
                updatedTask, "Comment added successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all tasks without any filters
     * 
     * @return standardized response containing all tasks
     */
    @GetMapping
    public ResponseEntity<BaseResponse<List<TaskManagementDto>>> getAllTasks() {
        log.info("Fetching all tasks");
        List<TaskManagementDto> tasks = taskManagementService.getAllTasks();
        BaseResponse<List<TaskManagementDto>> response = new BaseResponse<>(
                tasks, "All tasks retrieved successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to retrieve the "Smart" daily task view.
     * 
     * This includes:
     * 1. All ACTIVE tasks that started within the specified date range.
     * 2. All ACTIVE tasks that started before the start of the range but are still
     * open.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @return ResponseEntity containing the list of matching tasks
     */
    @GetMapping("/smart-view")
    public ResponseEntity<BaseResponse<List<TaskManagementDto>>> getSmartDailyTaskView(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("Fetching smart daily task view from {} to {}", startDate, endDate);

        List<TaskManagementDto> tasks = taskManagementService.getSmartDailyTaskView(startDate, endDate);

        BaseResponse<List<TaskManagementDto>> response = new BaseResponse<>(
                tasks, "Smart daily task view fetched successfully");

        return ResponseEntity.ok(response);
    }

}
