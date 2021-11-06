package org.example.dto.mapper;

import org.example.dto.version.ReleaseRequestDto;
import org.example.dto.version.ReleaseResponseDto;
import org.example.entity.ReleaseEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ReleaseMapper {
ReleaseEntity releaseRequestDtoToReleaseEntity(ReleaseRequestDto requestDto);
ReleaseResponseDto releaseEntityToReleaseResponseDto(ReleaseEntity versionEntity);

}
