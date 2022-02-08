package org.example.service;

import javassist.NotFoundException;
import org.example.entity.UserEntity;

import java.util.List;

public interface UserService {

    void register(UserEntity user);

    List<UserEntity> getAll(boolean isDeleted);

    UserEntity findByUsername(String username) throws NotFoundException;

    UserEntity findById(Long id) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    UserEntity getCurrentSessionUser() throws NotFoundException;
}
