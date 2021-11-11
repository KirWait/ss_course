package org.example.mapper;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(dto.getId()).isEqualTo(userEntity.getId());
        assertThat(dto.getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(dto.getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(dto.getActive()).isEqualTo(userEntity.getActive());
        assertThat(dto.getRoles()).isEqualTo(userEntity.getRoles());


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
        assertThat(entity.getId()).isEqualTo(requestDto.getId());
        assertThat(entity.getUsername()).isEqualTo(requestDto.getUsername());
        assertThat(entity.getPassword()).isEqualTo(requestDto.getPassword());
        assertThat(entity.getActive()).isEqualTo(requestDto.getActive());
        assertThat(entity.getRoles()).isEqualTo(requestDto.getRoles());


    }


}
