package org.example.mapper;

import org.example.dto.ReleaseRequestDto;
import org.example.dto.ReleaseResponseDto;
import org.example.entity.TaskEntity;
import org.example.entity.ReleaseEntity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReleaseMapperTest {

    ReleaseMapper mapper = Mappers.getMapper(ReleaseMapper.class);

    @Test
    public void releaseEntityToResponseDto(){
        ReleaseEntity entity = new ReleaseEntity();
        entity.setVersion("1.0");
        entity.setCreationTime(null);
        entity.setEndTime(null);
        entity.setId(99999L);
        entity.setTasks(List.of(new TaskEntity("HI")));

        ReleaseResponseDto responseDto = mapper.releaseEntityToReleaseResponseDto(entity);

        assertThat(entity.getId()).isEqualTo(responseDto.getId());
        assertThat(entity.getVersion()).isEqualTo(responseDto.getVersion());
        assertThat(entity.getCreationTime()).isEqualTo(responseDto.getCreationTime());
        assertThat(entity.getEndTime()).isEqualTo(responseDto.getEndTime());
        assertThat(entity.getTasks().size()).isEqualTo(responseDto.getTasks().size());
        assertThat(entity.getTasks().get(0).getName()).isEqualTo(responseDto.getTasks().get(0).getName());


    }

    @Test
    public void requestDtoToReleaseEntity(){
        ReleaseRequestDto requestDto = new ReleaseRequestDto();
        requestDto.setVersion("1.0");
        requestDto.setCreationTime(null);
        requestDto.setEndTime(null);
        requestDto.setId(99999L);
        requestDto.setTasks(List.of(new TaskEntity("HI")));

        ReleaseEntity entity = mapper.releaseRequestDtoToReleaseEntity(requestDto);

        assertThat(entity.getId()).isEqualTo(requestDto.getId());
        assertThat(entity.getVersion()).isEqualTo(requestDto.getVersion());
        assertThat(entity.getCreationTime()).isEqualTo(requestDto.getCreationTime());
        assertThat(entity.getEndTime()).isEqualTo(requestDto.getEndTime());
        assertThat(entity.getTasks().size()).isEqualTo(requestDto.getTasks().size());
        assertThat(entity.getTasks().get(0).getName()).isEqualTo(requestDto.getTasks().get(0).getName());


    }

}
