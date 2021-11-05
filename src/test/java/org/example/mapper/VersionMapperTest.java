package org.example.mapper;

import org.example.DTO.mapper.VersionMapper;
import org.example.DTO.version.VersionRequestDto;
import org.example.DTO.version.VersionResponseDto;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;


import static org.assertj.core.api.Assertions.assertThat;

public class VersionMapperTest {

    VersionMapper mapper = Mappers.getMapper(VersionMapper.class);

    @Test
    public void versionEntityToResponseDto(){
        TaskVersionEntity entity = new TaskVersionEntity();
        entity.setVersion("1.0");
        entity.setStartTime(null);
        entity.setEndTime(null);
        entity.setId(99999L);
        entity.setTask(new TaskEntity("HI"));

        VersionResponseDto responseDto = mapper.taskVersionEntityToTaskVersionResponseDto(entity);

        assertThat(entity.getId()).isEqualTo(responseDto.getId());
        assertThat(entity.getVersion()).isEqualTo(responseDto.getVersion());
        assertThat(entity.getStartTime()).isEqualTo(responseDto.getStartTime());
        assertThat(entity.getEndTime()).isEqualTo(responseDto.getEndTime());
        assertThat(entity.getTask().getName()).isEqualTo(responseDto.getTask().getName());


    }

    @Test
    public void requestDtoToVersionEntity(){
        VersionRequestDto requestDto = new VersionRequestDto();
        requestDto.setVersion("1.0");
        requestDto.setStartTime(null);
        requestDto.setEndTime(null);
        requestDto.setId(99999L);
        requestDto.setTask(new TaskEntity("HI"));

        TaskVersionEntity entity = mapper.taskVersionRequestDtoToTaskVersionEntity(requestDto);

        assertThat(entity.getId()).isEqualTo(requestDto.getId());
        assertThat(entity.getVersion()).isEqualTo(requestDto.getVersion());
        assertThat(entity.getStartTime()).isEqualTo(requestDto.getStartTime());
        assertThat(entity.getEndTime()).isEqualTo(requestDto.getEndTime());
        assertThat(entity.getTask().getName()).isEqualTo(requestDto.getTask().getName());


    }

}
