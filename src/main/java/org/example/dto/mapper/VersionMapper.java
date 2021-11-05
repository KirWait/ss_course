package org.example.dto.mapper;

import org.example.dto.version.VersionRequestDto;
import org.example.dto.version.VersionResponseDto;
import org.example.entity.TaskVersionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface VersionMapper {
TaskVersionEntity taskVersionRequestDtoToTaskVersionEntity(VersionRequestDto requestDto);
VersionResponseDto taskVersionEntityToTaskVersionResponseDto(TaskVersionEntity versionEntity);

}
