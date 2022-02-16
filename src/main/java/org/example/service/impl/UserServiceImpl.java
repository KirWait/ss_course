package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.TaskStatResponseDto;
import org.example.dto.UserStatResponseDto;
import org.example.entity.TaskEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;
import org.example.exception.DeletedException;
import org.example.exception.InvalidAccessException;
import org.example.mapper.TaskMapper;
import org.example.repository.ProjectRepository;
import org.example.repository.TaskRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.example.service.MyDateFormat.formatTaskTime;
import static org.example.service.impl.TaskServiceImpl.countTaskTime;

/**
 * This is the class that implements business-logic of users in this app.
 * @author Kirill Zhdanov
 *
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);
    private final TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
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

    @Override
    public UserStatResponseDto getStatistics(Long projectId, Long userId, Date startTime, Date endTime)
            throws NotFoundException {
        UserEntity currentSessionUser = getCurrentSessionUser();
        Roles role = currentSessionUser.getRoles();
        UserStatResponseDto responseDto = UserStatResponseDto.builder()
                .username(getCurrentSessionUser().getUsername())
                .build();
        if(role == Roles.ROLE_CUSTOMER || role == Roles.ROLE_ADMIN ) {

            if (role == Roles.ROLE_ADMIN || projectRepository.findAllByCustomerIdAndDeleted(currentSessionUser.getId(), false) // checks if this customer is a customer of that project(projectId)
                    .stream().anyMatch(project -> Objects.equals(project.getId(), projectId))) {

                if (role == Roles.ROLE_ADMIN || taskRepository.findAllByProjectIdAndDeleted(projectId, false)
                        .orElse(new ArrayList<>()).stream()
                        .filter(task -> !task.isDeleted())
                        .anyMatch(task -> Objects.equals(task.getResponsible().getId(), userId))) {


                    long startTimeInMillis;
                    long endTimeInMillis;
                    int tasksDone = 0;
                    long taskEndTimeInMillis;
                    long totalTimeWorked = 0;
                    List<TaskStatResponseDto> taskDones = new ArrayList<>();


                    if (startTime != null) {
                        startTimeInMillis = startTime.getTime();
                    } else {
                        startTimeInMillis = 0;
                    }

                    if (endTime != null) {
                        endTimeInMillis = endTime.getTime();
                    } else {
                        endTimeInMillis = System.currentTimeMillis();
                    }

                    for (TaskEntity task : taskRepository.findAllByProjectIdAndDeleted(projectId, false)
                            .orElse(new ArrayList<>())) {
                        if (task.getEndTime() != null){
                            taskEndTimeInMillis = task.getEndTime().getTime();
                            if (taskEndTimeInMillis < endTimeInMillis && taskEndTimeInMillis > startTimeInMillis){
                                tasksDone++;
                                long duration = countTaskTime(task);
                                totalTimeWorked += duration;
                                task.setTimeSpent(formatTaskTime(duration));
                                taskDones.add(taskMapper.taskEntityToTaskStatResponseDto(task));
                            }
                        }
                    }
                    responseDto.setTotalTimeWorked(formatTaskTime(totalTimeWorked));
                    if (tasksDone != 0){
                        responseDto.setAverageTimeSpentOnTask(formatTaskTime(totalTimeWorked / tasksDone));
                    }
                    else {
                        responseDto.setAverageTimeSpentOnTask("00 hrs, 00 min");
                    }
                    responseDto.setTasksDoneCount(tasksDone);
                    responseDto.setTasksDone(taskDones);
                } else
                    throw new NotFoundException(String.format("User with userId: %d is not responsible of any task of the project " +
                            "with projectId: %d", userId, projectId));
            } else
                throw new InvalidAccessException(String.format("Forbidden! You are not the customer of the project " +
                        "with projectId: %d!", projectId));
        }
        else {
            throw new InvalidAccessException("Forbidden! You are not the customer of this projects!");
        }
        return responseDto;
    }
}

