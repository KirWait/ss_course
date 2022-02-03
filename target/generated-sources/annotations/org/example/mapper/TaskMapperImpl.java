package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-02T17:31:18+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
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
        taskEntity.setProject( requestDto.getProject() );
        taskEntity.setStatus( requestDto.getStatus() );
        taskEntity.setName( requestDto.getName() );
        taskEntity.setDescription( requestDto.getDescription() );
        taskEntity.setAuthor( requestDto.getAuthor() );
        taskEntity.setResponsible( requestDto.getResponsible() );
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
        taskResponseDto.setProject( entity.getProject() );
        taskResponseDto.setStatus( entity.getStatus() );
        taskResponseDto.setName( entity.getName() );
        taskResponseDto.setDescription( entity.getDescription() );
        taskResponseDto.setAuthor( entity.getAuthor() );
        taskResponseDto.setResponsible( entity.getResponsible() );
        taskResponseDto.setRelease( entity.getRelease() );
        taskResponseDto.setType( entity.getType() );

        return taskResponseDto;
    }
}
