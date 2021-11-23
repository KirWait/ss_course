package org.example.mapper;



import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;
import org.example.service.Constants;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;

public class TaskMapperTest {

TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void taskEntityToTaskResponseDto(){

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setResponsible(new UserEntity(1L));
        taskEntity.setStatus(Status.BACKLOG);
        taskEntity.setAuthor(new UserEntity(2L));
        taskEntity.setId(3L);
        taskEntity.setName("NAME");
        taskEntity.setDescription("NONE");
        taskEntity.setRelease(new ReleaseEntity("2.0", Constants.formatterWithTime.format(new GregorianCalendar().getTime())));
        taskEntity.setType(Type.BUG);
        taskEntity.setProject(new ProjectEntity(4L));

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

        TaskRequestDto requestDto = new TaskRequestDto();
        requestDto.setResponsible(new UserEntity(1L));
        requestDto.setStatus(Status.BACKLOG);
        requestDto.setAuthor(new UserEntity(2L));
        requestDto.setId(3L);
        requestDto.setName("NAME");
        requestDto.setDescription("NONE");
        requestDto.setRelease(new ReleaseEntity("2.0", Constants.formatterWithTime.format(new GregorianCalendar().getTime())));
        requestDto.setType(Type.BUG);
        requestDto.setProject(new ProjectEntity(4L));


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
