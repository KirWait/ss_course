package org.example.services.impl;

import org.example.entities.UserEntity;
import org.example.entities.enums.Roles;
import org.example.entities.enums.UserStatus;
import org.example.repositories.UserRepository;
import org.example.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;



@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserEntity register(UserEntity user) {



        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Roles.ROLE_USER);
        user.setStatus(UserStatus.ACTIVE);

        UserEntity registeredUser = userRepository.save(user);


        return registeredUser;
    }
    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
    userRepository.deleteById(id);
    }
}
