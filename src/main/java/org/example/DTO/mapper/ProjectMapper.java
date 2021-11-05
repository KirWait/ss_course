package org.example.DTO.mapper;

import org.example.DTO.project.ProjectRequestDto;
import org.example.DTO.project.ProjectResponseDto;
import org.example.entity.ProjectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {
    ProjectResponseDto projectEntityToProjectResponseDTO(ProjectEntity project);
    ProjectEntity projectRequestDTOToProjectEntity(ProjectRequestDto projectRequestDto);
}