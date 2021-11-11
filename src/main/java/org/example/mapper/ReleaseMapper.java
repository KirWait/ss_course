package org.example.mapper;

import org.example.dto.ReleaseRequestDto;
import org.example.dto.ReleaseResponseDto;
import org.example.entity.ReleaseEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ReleaseMapper {
ReleaseEntity releaseRequestDtoToReleaseEntity(ReleaseRequestDto requestDto);
ReleaseResponseDto releaseEntityToReleaseResponseDto(ReleaseEntity versionEntity);

}
