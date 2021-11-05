package org.example.dto.mapper;

import org.example.dto.user.UserRequestDto;
import org.example.dto.user.UserResponseDto;
import org.example.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDto userEntityToUserResponseDTO(UserEntity userEntity);
    UserEntity userRequestDTOToUserEntity(UserRequestDto requestDto);
}
