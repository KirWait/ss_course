package org.example.dto.mapper;

import javax.annotation.processing.Generated;
import org.example.dto.version.VersionRequestDto;
import org.example.dto.version.VersionResponseDto;
import org.example.entity.TaskVersionEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-06T01:17:41+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
public class VersionMapperImpl implements VersionMapper {

    @Override
    public TaskVersionEntity taskVersionRequestDtoToTaskVersionEntity(VersionRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        TaskVersionEntity taskVersionEntity = new TaskVersionEntity();

        taskVersionEntity.setTask( requestDto.getTask() );
        taskVersionEntity.setId( requestDto.getId() );
        taskVersionEntity.setStartTime( requestDto.getStartTime() );
        taskVersionEntity.setEndTime( requestDto.getEndTime() );
        taskVersionEntity.setVersion( requestDto.getVersion() );

        return taskVersionEntity;
    }

    @Override
    public VersionResponseDto taskVersionEntityToTaskVersionResponseDto(TaskVersionEntity versionEntity) {
        if ( versionEntity == null ) {
            return null;
        }

        VersionResponseDto versionResponseDto = new VersionResponseDto();

        versionResponseDto.setTask( versionEntity.getTask() );
        versionResponseDto.setVersion( versionEntity.getVersion() );
        versionResponseDto.setEndTime( versionEntity.getEndTime() );
        versionResponseDto.setStartTime( versionEntity.getStartTime() );
        versionResponseDto.setId( versionEntity.getId() );

        return versionResponseDto;
    }
}
