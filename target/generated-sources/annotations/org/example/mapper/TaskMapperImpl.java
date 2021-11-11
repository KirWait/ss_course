package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T14:37:00+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskEntity taskRequestDtoToTaskEntity(TaskRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setCreationTime( requestDto.getCreationTime() );
        taskEntity.setStartTime( requestDto.getStartTime() );
        taskEntity.setEndTime( requestDto.getEndTime() );
        taskEntity.setRelease( requestDto.getRelease() );
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

        taskResponseDto.setStartTime( entity.getStartTime() );
        taskResponseDto.setEndTime( entity.getEndTime() );
        taskResponseDto.setCreationTime( entity.getCreationTime() );
        taskResponseDto.setId( entity.getId() );
        taskResponseDto.setProjectId( entity.getProjectId() );
        taskResponseDto.setStatus( entity.getStatus() );
        taskResponseDto.setName( entity.getName() );
        taskResponseDto.setDescription( entity.getDescription() );
        taskResponseDto.setAuthorId( entity.getAuthorId() );
        taskResponseDto.setResponsibleId( entity.getResponsibleId() );
        taskResponseDto.setRelease( entity.getRelease() );
        taskResponseDto.setType( entity.getType() );

        return taskResponseDto;
    }
}
