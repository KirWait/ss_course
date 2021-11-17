package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dto.ProjectRequestDto;
import org.example.dto.ProjectResponseDto;
import org.example.entity.ProjectEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-16T21:32:43+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectResponseDto projectEntityToProjectResponseDto(ProjectEntity project) {
        if ( project == null ) {
            return null;
        }

        ProjectResponseDto projectResponseDto = new ProjectResponseDto();

        projectResponseDto.setPaid( project.isPaid() );
        projectResponseDto.setPrice( project.getPrice() );
        projectResponseDto.setId( project.getId() );
        projectResponseDto.setName( project.getName() );
        projectResponseDto.setCustomerId( project.getCustomerId() );
        projectResponseDto.setStatus( project.getStatus() );

        return projectResponseDto;
    }

    @Override
    public ProjectEntity projectRequestDtoToProjectEntity(ProjectRequestDto projectRequestDto) {
        if ( projectRequestDto == null ) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setPaid( projectRequestDto.isPaid() );
        projectEntity.setPrice( projectRequestDto.getPrice() );
        projectEntity.setStatus( projectRequestDto.getStatus() );
        projectEntity.setId( projectRequestDto.getId() );
        projectEntity.setName( projectRequestDto.getName() );
        projectEntity.setCustomerId( projectRequestDto.getCustomerId() );

        return projectEntity;
    }
}
