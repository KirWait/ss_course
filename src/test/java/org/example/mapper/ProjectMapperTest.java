package org.example.mapper;

import org.example.DTO.project.ProjectRequestDto;
import org.example.DTO.project.ProjectResponseDto;
import org.example.DTO.mapper.ProjectMapper;
import org.example.entity.ProjectEntity;
import org.example.enumeration.Status;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectMapperTest {
    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);


    @Test
    public void projectEntityToResponseDTO() {
        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        project.setName("NAME");
        project.setCustomerId(2L);
        project.setStatus(Status.BACKLOG);


        ProjectResponseDto dto = mapper.projectEntityToProjectResponseDTO(project);
        assertThat(dto.getId()).isEqualTo(project.getId());
        assertThat(dto.getCustomerId()).isEqualTo(project.getCustomerId());
        assertThat(dto.getName()).isEqualTo(project.getName());
        assertThat(dto.getStatus()).isEqualTo(project.getStatus());


}

    @Test
    public void requestDTOToProjectEntity() {
        ProjectRequestDto requestDto = new ProjectRequestDto();
        requestDto.setId(1L);
        requestDto.setName("NAME");
        requestDto.setCustomerId(2L);
        requestDto.setStatus(Status.BACKLOG);


        ProjectEntity project = mapper.projectRequestDTOToProjectEntity(requestDto);
        assertThat(requestDto.getId()).isEqualTo(project.getId());
        assertThat(requestDto.getCustomerId()).isEqualTo(project.getCustomerId());
        assertThat(requestDto.getName()).isEqualTo(project.getName());
        assertThat(requestDto.getStatus()).isEqualTo(project.getStatus());


    }


}
