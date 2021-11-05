package org.example.DTO.mapper;

import javax.annotation.processing.Generated;
import org.example.DTO.project.ProjectRequestDto;
import org.example.DTO.project.ProjectResponseDto;
import org.example.entity.ProjectEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-05T15:39:15+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectResponseDto projectEntityToProjectResponseDTO(ProjectEntity project) {
        if ( project == null ) {
            return null;
        }

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();

        projectResponseDto.setId( project.getId() );
        projectResponseDto.setName( project.getName() );
        projectResponseDto.setCustomerId( project.getCustomerId() );
        projectResponseDto.setStatus( project.getStatus() );

        return projectResponseDto;
    }

    @Override
    public ProjectEntity projectRequestDTOToProjectEntity(ProjectRequestDto projectRequestDto) {
        if ( projectRequestDto == null ) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setStatus( projectRequestDto.getStatus() );
        projectEntity.setId( projectRequestDto.getId() );
        projectEntity.setName( projectRequestDto.getName() );
        projectEntity.setCustomerId( projectRequestDto.getCustomerId() );

        return projectEntity;
    }
}
