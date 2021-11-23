package org.example.mapper;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.Assert.assertEquals;

public class UserMapperTest {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);


    @Test
    public void userEntityToResponseDTO() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("NAME");
        userEntity.setPassword("password");
        userEntity.setRoles(Roles.ROLE_USER);
        userEntity.setActive(Active.ACTIVE);


        UserResponseDto dto = mapper.userEntityToUserResponseDTO(userEntity);
        assertEquals(dto.getId(), userEntity.getId());
        assertEquals(dto.getUsername(), userEntity.getUsername());
        assertEquals(dto.getPassword(), userEntity.getPassword());
        assertEquals(dto.getActive(), userEntity.getActive());
        assertEquals(dto.getRoles(), userEntity.getRoles());


    }

    @Test
    public void requestDTOToUserEntity() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setId(1L);
        requestDto.setUsername("NAME");
        requestDto.setPassword("password");
        requestDto.setRoles(Roles.ROLE_USER);
        requestDto.setActive(Active.ACTIVE);


        UserEntity entity = mapper.userRequestDTOToUserEntity(requestDto);
        assertEquals(entity.getId(), requestDto.getId());
        assertEquals(entity.getUsername(), requestDto.getUsername());
        assertEquals(entity.getPassword(), requestDto.getPassword());
        assertEquals(entity.getActive(), requestDto.getActive());
        assertEquals(entity.getRoles(), requestDto.getRoles());


    }


}
