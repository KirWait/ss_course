package org.example.mapper;

import org.example.dto.ProjectRequestDto;
import org.example.dto.ProjectResponseDto;
import org.example.entity.ProjectEntity;
import org.example.entity.UserEntity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.Assert.assertEquals;
import static org.example.constants.Constants.*;

public class ProjectMapperTest {
    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);


    @Test
    public void projectEntityToResponseDTO() {
        ProjectEntity project = PROJECT;

        ProjectResponseDto dto = mapper.projectEntityToProjectResponseDto(project);
        System.out.println(dto);
        assertEquals(dto.getId(), project.getId());
        assertEquals(dto.getCustomer(), project.getCustomer());
        assertEquals(dto.getName(), project.getName());
        assertEquals(dto.getStatus(), project.getStatus());


}

    @Test
    public void requestDTOToProjectEntity() {
        ProjectRequestDto requestDto = ProjectRequestDto.builder()
                .id(PROJECT_ID)
                .name(PROJECT_NAME)
                .customer(UserEntity.builder()
                        .id(USER_ID)
                        .build())
                .status(PROJECT_STATUS)
                .build();

        ProjectEntity project = mapper.projectRequestDtoToProjectEntity(requestDto);
        assertEquals(requestDto.getId(), project.getId());
        assertEquals(requestDto.getCustomer(), project.getCustomer());
        assertEquals(requestDto.getName(), project.getName());
        assertEquals(requestDto.getStatus(), project.getStatus());


    }


}
