package com.yourcompany.workforcemgmt.mapper;

import com.yourcompany.workforcemgmt.dto.TaskActivityDto;
import com.yourcompany.workforcemgmt.dto.TaskCommentDto;
import com.yourcompany.workforcemgmt.dto.TaskManagementDto;
import com.yourcompany.workforcemgmt.model.Task;
import com.yourcompany.workforcemgmt.model.TaskActivity;
import com.yourcompany.workforcemgmt.model.TaskComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between Task entities and DTOs
 * 
 * @author Deepak Bhati
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {
    
    /**
     * Converts Task entity to TaskManagementDto
     * @param task the task entity
     * @return the task DTO
     */
    TaskManagementDto toDto(Task task);
    
    /**
     * Converts TaskManagementDto to Task entity
     * @param dto the task DTO
     * @return the task entity
     */
    Task toEntity(TaskManagementDto dto);
    
    /**
     * Converts TaskActivity entity to TaskActivityDto
     * @param activity the task activity entity
     * @return the task activity DTO
     */
    TaskActivityDto toActivityDto(TaskActivity activity);
    
    /**
     * Converts TaskActivityDto to TaskActivity entity
     * @param dto the task activity DTO
     * @return the task activity entity
     */
    TaskActivity toActivityEntity(TaskActivityDto dto);
    
    /**
     * Converts TaskComment entity to TaskCommentDto
     * @param comment the task comment entity
     * @return the task comment DTO
     */
    TaskCommentDto toCommentDto(TaskComment comment);
    
    /**
     * Converts TaskCommentDto to TaskComment entity
     * @param dto the task comment DTO
     * @return the task comment entity
     */
    TaskComment toCommentEntity(TaskCommentDto dto);
}
