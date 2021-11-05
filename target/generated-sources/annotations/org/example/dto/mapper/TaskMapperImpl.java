package org.example.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.dto.task.TaskRequestDto;
import org.example.dto.task.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-06T01:17:41+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskEntity taskRequestDtoToTaskEntity(TaskRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        List<TaskVersionEntity> list = requestDto.getVersions();
        if ( list != null ) {
            taskEntity.setVersions( new ArrayList<TaskVersionEntity>( list ) );
        }
        taskEntity.setId( requestDto.getId() );
        taskEntity.setProjectId( requestDto.getProjectId() );
        taskEntity.setStatus( requestDto.getStatus() );
        taskEntity.setName( requestDto.getName() );
        taskEntity.setDescription( requestDto.getDescription() );
        taskEntity.setAuthorId( requestDto.getAuthorId() );
        taskEntity.setResponsibleId( requestDto.getResponsibleId() );
        taskEntity.setType( requestDto.getType() );

        return taskEntity;
    }

    @Override
    public TaskResponseDto taskEntityToTaskResponseDto(TaskEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TaskResponseDto taskResponseDto = new TaskResponseDto();

        taskResponseDto.setId( entity.getId() );
        taskResponseDto.setProjectId( entity.getProjectId() );
        taskResponseDto.setStatus( entity.getStatus() );
        taskResponseDto.setName( entity.getName() );
        taskResponseDto.setDescription( entity.getDescription() );
        taskResponseDto.setAuthorId( entity.getAuthorId() );
        taskResponseDto.setResponsibleId( entity.getResponsibleId() );
        List<TaskVersionEntity> list = entity.getVersions();
        if ( list != null ) {
            taskResponseDto.setVersions( new ArrayList<TaskVersionEntity>( list ) );
        }
        taskResponseDto.setType( entity.getType() );

        return taskResponseDto;
    }
}
