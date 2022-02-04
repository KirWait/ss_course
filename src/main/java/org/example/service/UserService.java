package org.example.service;

import javassist.NotFoundException;
import org.example.entity.UserEntity;

import java.util.List;

public interface UserService {

    void register(UserEntity user);

    List<UserEntity> getAll();

    UserEntity findByUsername(String username) throws NotFoundException;

    UserEntity findById(Long id) throws NotFoundException;

    void delete(Long id);

    UserEntity getCurrentSessionUser() throws NotFoundException;
}
