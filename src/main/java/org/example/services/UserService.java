package org.example.services;

import org.example.entities.UserEntity;

import java.util.List;

public interface UserService {

    void register(UserEntity user);

    List<UserEntity> getAll();

    UserEntity findByUsername(String username);

    UserEntity findById(Long id);

    void delete(Long id);
}
