package org.example.mapper;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDto userEntityToUserResponseDTO(UserEntity userEntity);
    UserEntity userRequestDTOToUserEntity(UserRequestDto requestDto);
}
