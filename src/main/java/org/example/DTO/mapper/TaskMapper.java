package org.example.DTO.mapper;

import org.example.DTO.task.TaskRequestDto;
import org.example.DTO.task.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {
    TaskEntity taskRequestDtoToTaskEntity(TaskRequestDto requestDto);
    TaskResponseDto taskEntityToTaskResponseDto(TaskEntity entity);
}
