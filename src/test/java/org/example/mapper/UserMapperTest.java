package org.example.mapper;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.example.constants.Constants.*;
import static org.junit.Assert.assertEquals;

public class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);


    @Test
    public void userEntityToResponseDTO() {
        UserEntity userEntity = USER;

        UserResponseDto dto = mapper.userEntityToUserResponseDTO(userEntity);

        assertEquals(dto.getId(), userEntity.getId());
        assertEquals(dto.getUsername(), userEntity.getUsername());
        assertEquals(dto.getPassword(), userEntity.getPassword());
        assertEquals(dto.getActive(), userEntity.getActive());
        assertEquals(dto.getRoles(), userEntity.getRoles());


    }

    @Test
    public void requestDTOToUserEntity() {
        UserRequestDto requestDto = UserRequestDto.builder()
                .id(USER_ID)
                .active(USER_ACTIVE)
                .password(USER_PASSWORD)
                .roles(USER_ROLES)
                .username(USER_USERNAME)
                .build();

        UserEntity entity = mapper.userRequestDTOToUserEntity(requestDto);
        assertEquals(entity.getId(), requestDto.getId());
        assertEquals(entity.getUsername(), requestDto.getUsername());
        assertEquals(entity.getPassword(), requestDto.getPassword());
        assertEquals(entity.getActive(), requestDto.getActive());
        assertEquals(entity.getRoles(), requestDto.getRoles());


    }


}
