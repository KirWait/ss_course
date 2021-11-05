package org.example.security.jwt;


import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtFactory {

    public static JwtUser create(UserEntity userEntity){
        JwtUser jwtUser = new JwtUser(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getActive().equals(Active.ACTIVE),
                List.of(userEntity.getRoles().name()).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));

        return jwtUser;
    }
}
