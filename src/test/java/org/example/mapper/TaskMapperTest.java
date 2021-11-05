package org.example.mapper;



import org.example.DTO.mapper.TaskMapper;
import org.example.DTO.task.TaskRequestDto;
import org.example.DTO.task.TaskResponseDto;
import org.example.entity.TaskEntity;

import org.example.entity.TaskVersionEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskMapperTest {

TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void taskEntityToTaskResponseDto(){

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setResponsibleId(1L);
        taskEntity.setStatus(Status.BACKLOG);
        taskEntity.setAuthorId(2L);
        taskEntity.setId(3L);
        taskEntity.setName("NAME");
        taskEntity.setDescription("NONE");
        taskEntity.setVersions(List.of(new TaskVersionEntity("2.0", Calendar.getInstance())));
        taskEntity.setType(Type.BUG);
        taskEntity.setProjectId(4L);


            TaskResponseDto responseDto = mapper.taskEntityToTaskResponseDto(taskEntity);

        assertThat(responseDto.getAuthorId()).isEqualTo(taskEntity.getAuthorId());
        assertThat(responseDto.getId()).isEqualTo(taskEntity.getId());
        assertThat(responseDto.getResponsibleId()).isEqualTo(taskEntity.getResponsibleId());
        assertThat(responseDto.getDescription()).isEqualTo(taskEntity.getDescription());
        assertThat(responseDto.getStatus()).isEqualTo(taskEntity.getStatus());
        assertThat(responseDto.getName()).isEqualTo(taskEntity.getName());
        assertThat(responseDto.getProjectId()).isEqualTo(taskEntity.getProjectId());
        assertThat(responseDto.getType()).isEqualTo(taskEntity.getType());
        assertThat(responseDto.getVersions().size()).isEqualTo(taskEntity.getVersions().size());
        assertThat(responseDto.getVersions().get(0).getVersion()).isEqualTo(taskEntity.getVersions().get(0).getVersion());
        assertThat(responseDto.getVersions().get(0).getStartTime()).isEqualTo(taskEntity.getVersions().get(0).getStartTime());



    }


    @Test
    public void taskRequestDtoToTaskEntity() {

        TaskRequestDto requestDto = new TaskRequestDto();
        requestDto.setResponsibleId(1L);
        requestDto.setStatus(Status.BACKLOG);
        requestDto.setAuthorId(2L);
        requestDto.setId(3L);
        requestDto.setName("NAME");
        requestDto.setDescription("NONE");
        requestDto.setVersions(List.of(new TaskVersionEntity("2.0", Calendar.getInstance())));
        requestDto.setType(Type.BUG);
        requestDto.setProjectId(4L);


        TaskEntity entity = mapper.taskRequestDtoToTaskEntity(requestDto);

        assertThat(requestDto.getAuthorId()).isEqualTo(entity.getAuthorId());
        assertThat(requestDto.getId()).isEqualTo(entity.getId());
        assertThat(requestDto.getResponsibleId()).isEqualTo(entity.getResponsibleId());
        assertThat(requestDto.getDescription()).isEqualTo(entity.getDescription());
        assertThat(requestDto.getStatus()).isEqualTo(entity.getStatus());
        assertThat(requestDto.getName()).isEqualTo(entity.getName());
        assertThat(requestDto.getProjectId()).isEqualTo(entity.getProjectId());
        assertThat(requestDto.getType()).isEqualTo(entity.getType());
        assertThat(requestDto.getVersions().size()).isEqualTo(entity.getVersions().size());
        assertThat(requestDto.getVersions().get(0).getVersion()).isEqualTo(entity.getVersions().get(0).getVersion());
        assertThat(requestDto.getVersions().get(0).getStartTime()).isEqualTo(entity.getVersions().get(0).getStartTime());

    }
}
