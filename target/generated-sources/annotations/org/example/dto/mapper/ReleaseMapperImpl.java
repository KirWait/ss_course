package org.example.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.dto.version.ReleaseRequestDto;
import org.example.dto.version.ReleaseResponseDto;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-07T13:59:25+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
public class ReleaseMapperImpl implements ReleaseMapper {

    @Override
    public ReleaseEntity releaseRequestDtoToReleaseEntity(ReleaseRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        ReleaseEntity releaseEntity = new ReleaseEntity();

        List<TaskEntity> list = requestDto.getTasks();
        if ( list != null ) {
            releaseEntity.setTasks( new ArrayList<TaskEntity>( list ) );
        }
        releaseEntity.setProjectId( requestDto.getProjectId() );
        releaseEntity.setId( requestDto.getId() );
        releaseEntity.setCreationTime( requestDto.getCreationTime() );
        releaseEntity.setEndTime( requestDto.getEndTime() );
        releaseEntity.setVersion( requestDto.getVersion() );

        return releaseEntity;
    }

    @Override
    public ReleaseResponseDto releaseEntityToReleaseResponseDto(ReleaseEntity versionEntity) {
        if ( versionEntity == null ) {
            return null;
        }

        ReleaseResponseDto releaseResponseDto = new ReleaseResponseDto();

        releaseResponseDto.setProjectId( versionEntity.getProjectId() );
        List<TaskEntity> list = versionEntity.getTasks();
        if ( list != null ) {
            releaseResponseDto.setTasks( new ArrayList<TaskEntity>( list ) );
        }
        releaseResponseDto.setVersion( versionEntity.getVersion() );
        releaseResponseDto.setEndTime( versionEntity.getEndTime() );
        releaseResponseDto.setCreationTime( versionEntity.getCreationTime() );
        releaseResponseDto.setId( versionEntity.getId() );

        return releaseResponseDto;
    }
}
