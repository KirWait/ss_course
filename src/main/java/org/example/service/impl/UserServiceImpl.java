package org.example.service.impl;

import javassist.NotFoundException;
import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;
import org.example.exception.DeletedException;
import org.example.repository.ProjectRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
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
    private final EntityManager entityManager;
    private final ProjectRepository projectRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           EntityManager entityManager, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
        this.projectRepository = projectRepository;
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
    public List<UserEntity> getAll(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedUserFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<UserEntity> users = userRepository.findAll();
        session.disableFilter("deletedUserFilter");
        return users;
    }

    /**
     * Finds a user by username
     * @param username User username
     *
     */
    @Override
    public UserEntity findByUsername(String username) throws NotFoundException {

        return userRepository.findByUsernameAndDeleted(username, false)
                .orElseThrow(() -> new NotFoundException(String.format("No such user with username: %s", username)));
    }

    /**
     * Finds a user by id
     * @param id User id
     *
     */
    @Override
    public UserEntity findById(Long id) throws NotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No such user with id: %d", id)));
        if (user.isDeleted()){
            throw new DeletedException(String.format("The user with id: %d has already been deleted!", id));
        }
        return user;

    }

    /**
     * Deletes user by id
     * @param id User id
     *
     */
    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        findById(id);
        userRepository.deleteById(id);
        projectRepository.findAllByCustomerIdAndDeleted(id, false)
                .forEach(project -> projectRepository.deleteById(project.getId()));
    }

    @Override
    public UserEntity getCurrentSessionUser() throws NotFoundException {
        return userRepository.findByUsernameAndDeleted(SecurityContextHolder.getContext().getAuthentication().getName(),
                        false).orElseThrow(()-> new NotFoundException("No such user"));
    }
}
