package org.example.mapper;

import org.example.dto.ReleaseRequestDto;
import org.example.dto.ReleaseResponseDto;
import org.example.entity.TaskEntity;
import org.example.entity.ReleaseEntity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.example.constants.Constants.*;
import static org.junit.Assert.assertEquals;

public class ReleaseMapperTest {

    ReleaseMapper mapper = Mappers.getMapper(ReleaseMapper.class);

    @Test
    public void releaseEntityToResponseDto(){
        ReleaseEntity entity = RELEASE;

        ReleaseResponseDto responseDto = mapper.releaseEntityToReleaseResponseDto(entity);

        assertEquals(entity.getId(), responseDto.getId());
        assertEquals(entity.getVersion(), responseDto.getVersion());
        assertEquals(entity.getCreationTime(), responseDto.getCreationTime());
        assertEquals(entity.getEndTime() ,responseDto.getEndTime());
        assertEquals(entity.getTasks().size(), responseDto.getTasks().size());

    }

    @Test
    public void requestDtoToReleaseEntity(){
        ReleaseRequestDto requestDto =  ReleaseRequestDto.builder()
                .version(RELEASE_VERSION)
                .id(RELEASE_ID)
                .task(TaskEntity.builder()
                        .name(TASK_NAME)
                        .build())
                .build();

        ReleaseEntity entity = mapper.releaseRequestDtoToReleaseEntity(requestDto);

        assertEquals(entity.getId(), requestDto.getId());
        assertEquals(entity.getVersion(), requestDto.getVersion());
        assertEquals(entity.getCreationTime(), requestDto.getCreationTime());
        assertEquals(entity.getEndTime(), requestDto.getEndTime());
        assertEquals(entity.getTasks().size(), requestDto.getTasks().size());
        assertEquals(entity.getTasks().get(0).getName(),requestDto.getTasks().get(0).getName());

    }

}
