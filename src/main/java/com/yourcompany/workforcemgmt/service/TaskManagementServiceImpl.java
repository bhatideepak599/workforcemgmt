package com.yourcompany.workforcemgmt.service;

import com.yourcompany.workforcemgmt.dto.*;
import com.yourcompany.workforcemgmt.exception.ResourceNotFoundException;
import com.yourcompany.workforcemgmt.mapper.TaskMapper;
import com.yourcompany.workforcemgmt.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Service implementation for task management operations
 * 
 * @author Deepak Bhati
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskManagementServiceImpl implements TaskManagementService {
    
    private final TaskMapper taskMapper;
    
    // In-memory storage
    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final Map<Long, List<TaskActivity>> taskActivities = new ConcurrentHashMap<>();
    private final Map<Long, List<TaskComment>> taskComments = new ConcurrentHashMap<>();
    private final AtomicLong taskIdGenerator = new AtomicLong(1);
    private final AtomicLong activityIdGenerator = new AtomicLong(1);
    private final AtomicLong commentIdGenerator = new AtomicLong(1);
    
    /**
     * Creates new tasks based on the provided request
     * @param request the task creation request containing task details
     * @return list of created task DTOs
     * @throws IllegalArgumentException if request is invalid
     */
    @Override
    public List<TaskManagementDto> createTasks(TaskCreateRequest request) {
        log.info("Creating {} tasks", request.getRequests().size());
        
        List<TaskManagementDto> createdTasks = new ArrayList<>();
        
        for (CreateTaskRequest taskRequest : request.getRequests()) {
            Task task = new Task();
            task.setId(taskIdGenerator.getAndIncrement());
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setStatus(TaskStatus.ACTIVE);
            task.setPriority(taskRequest.getPriority());
            task.setStartDate(taskRequest.getStartDate());
            task.setDueDate(taskRequest.getDueDate());
            task.setAssigneeId(taskRequest.getAssigneeId());
            task.setAssigneeName(taskRequest.getAssigneeName());
            task.setCustomerReference(taskRequest.getCustomerReference());
            task.setCreatedAt(LocalDateTime.now());
            task.setUpdatedAt(LocalDateTime.now());
            task.setCreatedBy(taskRequest.getCreatedBy());
            task.setUpdatedBy(taskRequest.getCreatedBy());
            
            tasks.put(task.getId(), task);
            
            // Add creation activity
            addActivity(task.getId(), "TASK_CREATED", taskRequest.getCreatedBy(), 
                       "Task created with title: " + task.getTitle());
            
            TaskManagementDto dto = taskMapper.toDto(task);
            createdTasks.add(dto);
        }
        
        log.info("Successfully created {} tasks", createdTasks.size());
        return createdTasks;
    }

    /**
     * Updates existing tasks with new information
     * @param request the update request containing task modifications
     * @return updated task DTO
     * @throws ResourceNotFoundException if task not found
     */
    @Override
    public TaskManagementDto updateTask(UpdateTaskRequest request) {
        log.info("Updating task with ID: {}", request.getTaskId());

        Task task = tasks.get(request.getTaskId());
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + request.getTaskId());
        }

        // Update fields if provided
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            TaskStatus oldStatus = task.getStatus();
            task.setStatus(request.getStatus());
            addActivity(task.getId(), "STATUS_CHANGED", request.getUpdatedBy(),
                       "Status changed from " + oldStatus + " to " + request.getStatus());
        }
        if (request.getPriority() != null) {
            Priority oldPriority = task.getPriority();
            task.setPriority(request.getPriority());
            addActivity(task.getId(), "PRIORITY_CHANGED", request.getUpdatedBy(),
                       "Priority changed from " + oldPriority + " to " + request.getPriority());
        }
        if (request.getStartDate() != null) {
            task.setStartDate(request.getStartDate());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        if (request.getAssigneeId() != null) {
            Long oldAssigneeId = task.getAssigneeId();
            task.setAssigneeId(request.getAssigneeId());
            task.setAssigneeName(request.getAssigneeName());
            addActivity(task.getId(), "ASSIGNEE_CHANGED", request.getUpdatedBy(),
                       "Assignee changed from " + oldAssigneeId + " to " + request.getAssigneeId());
        }
        if (request.getCustomerReference() != null) {
            task.setCustomerReference(request.getCustomerReference());
        }

        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(request.getUpdatedBy());

        tasks.put(task.getId(), task);

        log.info("Successfully updated task with ID: {}", task.getId());
        return taskMapper.toDto(task);
    }

    // BUG 1: Task Re-assignment Creates Duplicates
    // BUGGY VERSION - This creates duplicates instead of cancelling old tasks

/*    @Override
    public String assignByReference(AssignByReferenceRequest request) {
        log.info("Assigning tasks by reference: {}", request.getCustomerReference());

        // Find tasks with the customer reference
        List<Task> existingTasks = tasks.values().stream()
                .filter(task -> request.getCustomerReference().equals(task.getCustomerReference()))
                .filter(task -> task.getStatus() == TaskStatus.ACTIVE)
                .collect(Collectors.toList());

        if (existingTasks.isEmpty()) {
            throw new ResourceNotFoundException("No active tasks found for customer reference: " + request.getCustomerReference());
        }

        // BUGGY: Create new tasks without cancelling old ones
        for (Task existingTask : existingTasks) {
            Task newTask = new Task();
            newTask.setId(taskIdGenerator.getAndIncrement());
            newTask.setTitle(existingTask.getTitle());
            newTask.setDescription(existingTask.getDescription());
            newTask.setStatus(TaskStatus.ACTIVE);
            newTask.setPriority(existingTask.getPriority());
            newTask.setStartDate(existingTask.getStartDate());
            newTask.setDueDate(existingTask.getDueDate());
            newTask.setAssigneeId(request.getNewAssigneeId());
            newTask.setAssigneeName(request.getNewAssigneeName());
            newTask.setCustomerReference(existingTask.getCustomerReference());
            newTask.setCreatedAt(LocalDateTime.now());
            newTask.setUpdatedAt(LocalDateTime.now());
            newTask.setCreatedBy(existingTask.getCreatedBy());
            newTask.setUpdatedBy(request.getUpdatedBy());

            tasks.put(newTask.getId(), newTask);

            addActivity(newTask.getId(), "TASK_REASSIGNED", request.getUpdatedBy(),
                       "Task reassigned from " + existingTask.getAssigneeName() + " to " + request.getNewAssigneeName());
        }

        return "Tasks reassigned successfully";
    }
*/

    // FIXED VERSION - Properly cancels old tasks when reassigning
    /**
     * Assigns tasks by reference, handling reassignment logic
     * @param request the assignment request with reference details
     * @return success message
     * @throws ResourceNotFoundException if reference not found
     */
    @Override
    public String assignByReference(AssignByReferenceRequest request) {
        log.info("Assigning tasks by reference: {}", request.getCustomerReference());

        // Find tasks with the customer reference
        List<Task> existingTasks = tasks.values().stream()
                .filter(task -> request.getCustomerReference().equals(task.getCustomerReference()))
                .filter(task -> task.getStatus() == TaskStatus.ACTIVE)
                .collect(Collectors.toList());

        if (existingTasks.isEmpty()) {
            throw new ResourceNotFoundException("No active tasks found for customer reference: " + request.getCustomerReference());
        }

        // FIXED: Cancel old tasks and create new ones
        for (Task existingTask : existingTasks) {
            // Cancel the old task
            existingTask.setStatus(TaskStatus.CANCELLED);
            existingTask.setUpdatedAt(LocalDateTime.now());
            existingTask.setUpdatedBy(request.getUpdatedBy());
            tasks.put(existingTask.getId(), existingTask);

            addActivity(existingTask.getId(), "TASK_CANCELLED", request.getUpdatedBy(),
                       "Task cancelled due to reassignment");

            // Create new task for new assignee
            Task newTask = new Task();
            newTask.setId(taskIdGenerator.getAndIncrement());
            newTask.setTitle(existingTask.getTitle());
            newTask.setDescription(existingTask.getDescription());
            newTask.setStatus(TaskStatus.ACTIVE);
            newTask.setPriority(existingTask.getPriority());
            newTask.setStartDate(existingTask.getStartDate());
            newTask.setDueDate(existingTask.getDueDate());
            newTask.setAssigneeId(request.getNewAssigneeId());
            newTask.setAssigneeName(request.getNewAssigneeName());
            newTask.setCustomerReference(existingTask.getCustomerReference());
            newTask.setCreatedAt(LocalDateTime.now());
            newTask.setUpdatedAt(LocalDateTime.now());
            newTask.setCreatedBy(existingTask.getCreatedBy());
            newTask.setUpdatedBy(request.getUpdatedBy());

            tasks.put(newTask.getId(), newTask);

            addActivity(newTask.getId(), "TASK_REASSIGNED", request.getUpdatedBy(),
                       "Task reassigned from " + existingTask.getAssigneeName() + " to " + request.getNewAssigneeName());
        }

        return "Tasks reassigned successfully";
    }

    // BUG 2: Cancelled Tasks Clutter the View
    // BUGGY VERSION - Returns all tasks including cancelled ones
    /*
    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
        log.info("Fetching tasks by date range: {} to {}", request.getStartDate(), request.getEndDate());

        List<Task> filteredTasks = tasks.values().stream()
                .filter(task -> {
                    // BUGGY: Include all tasks regardless of status
                    LocalDate taskStart = task.getStartDate();
                    return !taskStart.isBefore(request.getStartDate()) && !taskStart.isAfter(request.getEndDate());
                })
                .filter(task -> {
                    if (request.getAssigneeIds() != null && !request.getAssigneeIds().isEmpty()) {
                        return request.getAssigneeIds().contains(task.getAssigneeId());
                    }
                    return true;
                })
                .collect(Collectors.toList());

        return filteredTasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    */

    // FIXED VERSION - Excludes cancelled tasks and implements smart daily view
    /**
     * Fetches tasks within a specified date range for given assignees
     * @param request the date range and assignee filter request
     * @return list of matching task DTOs (excluding cancelled tasks)
     */

    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request) {
        log.info("Fetching tasks by date range: {} to {}", request.getStartDate(), request.getEndDate());

        List<Task> filteredTasks = tasks.values().stream()
                .filter(task -> {
                    // FIXED: Exclude cancelled tasks
                    if (task.getStatus() == TaskStatus.CANCELLED) {
                        return false;
                    }

                    // Smart daily view: Include tasks that started in range OR are still active from before
                    LocalDate taskStart = task.getStartDate();
                    boolean startedInRange = !taskStart.isBefore(request.getStartDate()) && !taskStart.isAfter(request.getEndDate());
                    boolean startedBeforeButStillActive = taskStart.isBefore(request.getStartDate()) && task.getStatus() == TaskStatus.ACTIVE;

                    return startedInRange || startedBeforeButStillActive;
                })
                .filter(task -> {
                    if (request.getAssigneeIds() != null && !request.getAssigneeIds().isEmpty()) {
                        return request.getAssigneeIds().contains(task.getAssigneeId());
                    }
                    return true;
                })
                .collect(Collectors.toList());

        return filteredTasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieves a single task by its ID with full details including history
     * @param id the task identifier
     * @return task DTO if found
     * @throws ResourceNotFoundException if task not found
     */
    @Override
    public TaskManagementDto findTaskById(Long id) {
        log.info("Fetching task with ID: {}", id);

        Task task = tasks.get(id);
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }

        TaskManagementDto dto = taskMapper.toDto(task);

        // Add activity history
        List<TaskActivity> activities = taskActivities.get(id);
        if (activities != null) {
            dto.setActivities(activities.stream()
                    .map(taskMapper::toActivityDto)
                    .collect(Collectors.toList()));
        }

        // Add comments
        List<TaskComment> comments = taskComments.get(id);
        if (comments != null) {
            dto.setComments(comments.stream()
                    .map(taskMapper::toCommentDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Updates task priority
     * @param request the priority update request
     * @return updated task DTO
     * @throws ResourceNotFoundException if task not found
     */
    @Override
    public TaskManagementDto updateTaskPriority(UpdatePriorityRequest request) {
        log.info("Updating priority for task ID: {}", request.getTaskId());

        Task task = tasks.get(request.getTaskId());
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + request.getTaskId());
        }

        Priority oldPriority = task.getPriority();
        task.setPriority(request.getPriority());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUpdatedBy(request.getUpdatedBy());

        tasks.put(task.getId(), task);

        addActivity(task.getId(), "PRIORITY_CHANGED", request.getUpdatedBy(),
                   "Priority changed from " + oldPriority + " to " + request.getPriority());

        log.info("Successfully updated priority for task ID: {}", task.getId());
        return taskMapper.toDto(task);
    }

    /**
     * Fetches tasks by priority
     * @param priority the priority to filter by
     * @return list of tasks with specified priority
     */
    @Override
    public List<TaskManagementDto> getTasksByPriority(Priority priority) {
        log.info("Fetching tasks with priority: {}", priority);

        List<Task> filteredTasks = tasks.values().stream()
                .filter(task -> task.getPriority() == priority)
                .filter(task -> task.getStatus() != TaskStatus.CANCELLED) // Exclude cancelled tasks
                .collect(Collectors.toList());

        return filteredTasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Adds a comment to a task
     * @param request the comment request
     * @return updated task DTO with new comment
     * @throws ResourceNotFoundException if task not found
     */
    @Override
    public TaskManagementDto addComment(AddCommentRequest request) {
        log.info("Adding comment to task ID: {}", request.getTaskId());

        Task task = tasks.get(request.getTaskId());
        if (task == null) {
            throw new ResourceNotFoundException("Task not found with ID: " + request.getTaskId());
        }

        TaskComment comment = new TaskComment();
        comment.setId(commentIdGenerator.getAndIncrement());
        comment.setTaskId(request.getTaskId());
        comment.setComment(request.getComment());
        comment.setCommentedBy(request.getCommentedBy());
        comment.setTimestamp(LocalDateTime.now());

        taskComments.computeIfAbsent(request.getTaskId(), k -> new ArrayList<>()).add(comment);

        addActivity(request.getTaskId(), "COMMENT_ADDED", request.getCommentedBy(),
                   "Comment added: " + request.getComment());

        log.info("Successfully added comment to task ID: {}", request.getTaskId());
        return findTaskById(request.getTaskId());
    }

    /**
     * Retrieves all tasks without any filters
     * @return list of all tasks in the system
     */
    @Override
    public List<TaskManagementDto> getAllTasks() {
        log.info("Fetching all tasks without filters");

        List<Task> allTasks = new ArrayList<>(tasks.values());

        return allTasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to add activity to task history
     * @param taskId the task ID
     * @param action the action performed
     * @param performedBy who performed the action
     * @param details additional details
     */
    private void addActivity(Long taskId, String action, String performedBy, String details) {
        TaskActivity activity = new TaskActivity();
        activity.setId(activityIdGenerator.getAndIncrement());
        activity.setTaskId(taskId);
        activity.setAction(action);
        activity.setPerformedBy(performedBy);
        activity.setTimestamp(LocalDateTime.now());
        activity.setDetails(details);

        taskActivities.computeIfAbsent(taskId, k -> new ArrayList<>()).add(activity);
    }
}
