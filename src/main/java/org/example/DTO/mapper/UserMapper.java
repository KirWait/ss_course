package org.example.DTO.mapper;

import org.example.DTO.user.UserRequestDto;
import org.example.DTO.user.UserResponseDto;
import org.example.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserResponseDto userEntityToUserResponseDTO(UserEntity userEntity);
    UserEntity userRequestDTOToUserEntity(UserRequestDto requestDto);
}
