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
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
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

        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {throw new NotFoundException(String.format("User with '%s' username not found!", username));}

        return user;
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
