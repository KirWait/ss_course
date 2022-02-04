package org.example.service.impl;

import javassist.NotFoundException;
import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * This is the class that implements business-logic of users in this app.
 * @author Kirill Zhdanov
 *
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves user to the database using Spring Data JPA
     * @param user Entity of a user
     *
     */
    @Override
    @Transactional
    public void register(UserEntity user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(Roles.ROLE_CUSTOMER);

        user.setActive(Active.ACTIVE);

        userRepository.save(user);
    }

    /**
     * Gets all the users from the database
     */
    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by username
     * @param username User username
     *
     */
    @Override
    public UserEntity findByUsername(String username) throws NotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("No such user with username: %s", username)));
    }

    /**
     * Finds a user by id
     * @param id User id
     *
     */
    @Override
    public UserEntity findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("No such user with id: %d", id)));
    }

    /**
     * Deletes user by id
     * @param id User id
     *
     */
    @Override
    @Transactional
    public void delete(Long id) {
    userRepository.deleteById(id);
    }

    @Override
    public UserEntity getCurrentSessionUser() throws NotFoundException {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new NotFoundException("No such user"));
    }
}
