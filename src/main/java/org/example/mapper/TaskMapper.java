package org.example.mapper;

import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.dto.TaskStatResponseDto;
import org.example.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface TaskMapper {
    TaskEntity taskRequestDtoToTaskEntity(TaskRequestDto requestDto);
    TaskResponseDto taskEntityToTaskResponseDto(TaskEntity entity);

    @Mappings({
    @Mapping(source = "release.version", target = "releaseVersion"),
    @Mapping(source = "responsible.username", target = "responsibleUsername"),
    })
    TaskStatResponseDto taskEntityToTaskStatResponseDto(TaskEntity entity);


}
