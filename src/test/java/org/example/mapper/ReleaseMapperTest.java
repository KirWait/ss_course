package org.example.mapper;

import org.example.dto.ReleaseRequestDto;
import org.example.dto.ReleaseResponseDto;
import org.example.entity.TaskEntity;
import org.example.entity.ReleaseEntity;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import java.util.List;
import static org.junit.Assert.assertEquals;

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

        assertEquals(entity.getId(), responseDto.getId());
        assertEquals(entity.getVersion(), responseDto.getVersion());
        assertEquals(entity.getCreationTime(), responseDto.getCreationTime());
        assertEquals(entity.getEndTime() ,responseDto.getEndTime());
        assertEquals(entity.getTasks().size(), responseDto.getTasks().size());
        assertEquals(entity.getTasks().get(0).getName(), responseDto.getTasks().get(0).getName());


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

        assertEquals(entity.getId(), requestDto.getId());
        assertEquals(entity.getVersion(), requestDto.getVersion());
        assertEquals(entity.getCreationTime(), requestDto.getCreationTime());
        assertEquals(entity.getEndTime(), requestDto.getEndTime());
        assertEquals(entity.getTasks().size(), requestDto.getTasks().size());
        assertEquals(entity.getTasks().get(0).getName(),requestDto.getTasks().get(0).getName());

    }

}
