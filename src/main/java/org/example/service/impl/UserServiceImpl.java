package org.example.service.impl;

import javassist.NotFoundException;
import org.example.entity.UserEntity;
import org.example.enumeration.Roles;
import org.example.enumeration.Active;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(UserEntity user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(Roles.ROLE_USER);

        user.setActive(Active.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findByUsername(String username) throws NotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("No such user with username: %s", username)));
    }

    @Override
    public UserEntity findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("No such user with id: %d", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
    userRepository.deleteById(id);
    }
}
