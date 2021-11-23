package org.example.mapper;

import org.example.dto.ProjectRequestDto;
import org.example.dto.ProjectResponseDto;
import org.example.entity.ProjectEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.Assert.assertEquals;

public class ProjectMapperTest {
    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);


    @Test
    public void projectEntityToResponseDTO() {
        ProjectEntity project = new ProjectEntity();
        project.setId(1L);
        project.setName("NAME");
        project.setCustomer(new UserEntity(2L));
        project.setStatus(Status.BACKLOG);


        ProjectResponseDto dto = mapper.projectEntityToProjectResponseDto(project);
        assertEquals(dto.getId(), project.getId());
        assertEquals(dto.getCustomer(), project.getCustomer());
        assertEquals(dto.getName(), project.getName());
        assertEquals(dto.getStatus(), project.getStatus());


}

    @Test
    public void requestDTOToProjectEntity() {
        ProjectRequestDto requestDto = new ProjectRequestDto();
        requestDto.setId(1L);
        requestDto.setName("NAME");
        requestDto.setCustomer(new UserEntity(2L));
        requestDto.setStatus(Status.BACKLOG);


        ProjectEntity project = mapper.projectRequestDtoToProjectEntity(requestDto);
        assertEquals(requestDto.getId(), project.getId());
        assertEquals(requestDto.getCustomer(), project.getCustomer());
        assertEquals(requestDto.getName(), project.getName());
        assertEquals(requestDto.getStatus(), project.getStatus());


    }


}
