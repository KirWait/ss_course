package org.example.mapper;

import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {
    TaskEntity taskRequestDtoToTaskEntity(TaskRequestDto requestDto);
    TaskResponseDto taskEntityToTaskResponseDto(TaskEntity entity);
}
