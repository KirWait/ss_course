package org.example.mapper;


import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.example.constants.Constants.*;
import static org.junit.Assert.assertEquals;

public class TaskMapperTest {

TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void taskEntityToTaskResponseDto(){

        TaskEntity taskEntity = TASK;
        TaskResponseDto responseDto = mapper.taskEntityToTaskResponseDto(taskEntity);

        assertEquals(responseDto.getAuthor().getId(), taskEntity.getAuthor().getId());
        assertEquals(responseDto.getId(), taskEntity.getId());
        assertEquals(responseDto.getResponsible().getId(), taskEntity.getResponsible().getId());
        assertEquals(responseDto.getDescription(), taskEntity.getDescription());
        assertEquals(responseDto.getStatus(), taskEntity.getStatus());
        assertEquals(responseDto.getName(), taskEntity.getName());
        assertEquals(responseDto.getProject().getId(), taskEntity.getProject().getId());
        assertEquals(responseDto.getType(), taskEntity.getType());
        assertEquals(responseDto.getRelease().getVersion(), taskEntity.getRelease().getVersion());
        assertEquals(responseDto.getRelease().getCreationTime(), taskEntity.getRelease().getCreationTime());



    }


    @Test
    public void taskRequestDtoToTaskEntity() {

        TaskRequestDto requestDto = TaskRequestDto.builder()
                .responsible(USER)
                .status(TASK_STATUS)
                .author(USER)
                .id(TASK_ID)
                .creationTime(TASK_CREATION_TIME)
                .name(TASK_NAME)
                .description(TASK_DESCRIPTION)
                .project(PROJECT)
                .release(RELEASE)
                .type(TASK_TYPE)
                .build();

        TaskEntity entity = mapper.taskRequestDtoToTaskEntity(requestDto);

        assertEquals(requestDto.getAuthor(), entity.getAuthor());
        assertEquals(requestDto.getResponsible(), entity.getResponsible());
        assertEquals(requestDto.getDescription(), entity.getDescription());
        assertEquals(requestDto.getId(), entity.getId());
        assertEquals(requestDto.getStatus(), entity.getStatus());
        assertEquals(requestDto.getName(), entity.getName());
        assertEquals(requestDto.getProject(), entity.getProject());
        assertEquals(requestDto.getType(), entity.getType());
        assertEquals(requestDto.getRelease().getVersion(), entity.getRelease().getVersion());
        assertEquals(requestDto.getRelease().getCreationTime(), entity.getRelease().getCreationTime());

    }
}
