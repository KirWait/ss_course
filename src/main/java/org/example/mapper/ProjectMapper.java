package org.example.mapper;

import org.example.dto.ProjectRequestDto;
import org.example.dto.ProjectResponseDto;
import org.example.entity.ProjectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {
    ProjectResponseDto projectEntityToProjectResponseDto(ProjectEntity project);
    ProjectEntity projectRequestDtoToProjectEntity(ProjectRequestDto projectRequestDto);
}