package org.example.dto.mapper;

import org.example.dto.project.ProjectRequestDto;
import org.example.dto.project.ProjectResponseDto;
import org.example.entity.ProjectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {
    ProjectResponseDto projectEntityToProjectResponseDto(ProjectEntity project);
    ProjectEntity projectRequestDtoToProjectEntity(ProjectRequestDto projectRequestDto);
}