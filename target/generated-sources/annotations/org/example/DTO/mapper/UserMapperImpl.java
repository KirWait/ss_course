package org.example.DTO.mapper;

import javax.annotation.processing.Generated;
import org.example.DTO.user.UserRequestDto;
import org.example.DTO.user.UserResponseDto;
import org.example.entity.UserEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-05T15:39:15+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto userEntityToUserResponseDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId( userEntity.getId() );
        userResponseDto.setUsername( userEntity.getUsername() );
        userResponseDto.setPassword( userEntity.getPassword() );
        userResponseDto.setRoles( userEntity.getRoles() );
        userResponseDto.setActive( userEntity.getActive() );

        return userResponseDto;
    }

    @Override
    public UserEntity userRequestDTOToUserEntity(UserRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( requestDto.getId() );
        userEntity.setUsername( requestDto.getUsername() );
        userEntity.setPassword( requestDto.getPassword() );
        userEntity.setRoles( requestDto.getRoles() );
        userEntity.setActive( requestDto.getActive() );

        return userEntity;
    }
}
