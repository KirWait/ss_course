package org.example.security.jwt;


import org.example.entities.UserEntity;
import org.example.entities.enums.UserStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtFactory {

    public static JwtUser create(UserEntity userEntity){
        JwtUser jwtUser = new JwtUser(
                userEntity.getUser_id(),
                userEntity.getUserName(),
                userEntity.getPassword(),
                userEntity.getStatus().equals(UserStatus.ACTIVE),
                List.of(userEntity.getRoles().name()).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));

        return jwtUser;
    }
}
