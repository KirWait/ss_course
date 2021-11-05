package org.example.dto.mapper;

import org.example.dto.project.ProjectRequestDto;
import org.example.dto.project.ProjectResponseDto;
import org.example.entity.ProjectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {
    ProjectResponseDto projectEntityToProjectResponseDTO(ProjectEntity project);
    ProjectEntity projectRequestDTOToProjectEntity(ProjectRequestDto projectRequestDto);
}