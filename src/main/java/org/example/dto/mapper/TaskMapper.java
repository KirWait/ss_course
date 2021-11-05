package org.example.dto.mapper;

import org.example.dto.task.TaskRequestDto;
import org.example.dto.task.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {
    TaskEntity taskRequestDtoToTaskEntity(TaskRequestDto requestDto);
    TaskResponseDto taskEntityToTaskResponseDto(TaskEntity entity);
}
