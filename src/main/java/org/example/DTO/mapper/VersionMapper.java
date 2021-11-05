package org.example.DTO.mapper;

import org.example.DTO.version.VersionRequestDto;
import org.example.DTO.version.VersionResponseDto;
import org.example.entity.TaskVersionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface VersionMapper {
TaskVersionEntity taskVersionRequestDtoToTaskVersionEntity(VersionRequestDto requestDto);
VersionResponseDto taskVersionEntityToTaskVersionResponseDto(TaskVersionEntity versionEntity);

}
