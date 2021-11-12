package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.TaskRequestDto;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Type;
import org.example.exception.InvalidStatusException;
import org.example.entity.TaskEntity;
import org.example.enumeration.Status;
import org.example.repository.TaskRepository;
import org.example.service.DateFormatter;
import org.example.service.ReleaseService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is the class that realising business-logic of tasks in this app.
 * @author Kirill Zhdanov
 *
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ReleaseService releaseService;

//    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, ReleaseService releaseService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.releaseService = releaseService;
    }

    /**
     * Finds release by version and project id
     * @param taskEntity Entity of a task
     *
     */
    @Override
    public void save(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
    //    logger.info("Successfully saved task to the database");
    }


    /**
     * Deletes task by id
     * @param id Task id
     *
     */
    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
      //  logger.info(String.format("Successfully deleted task with id: %d from the database", id));
    }

    /**
     * Changes task status by id
     * @param id Task id
     *
     */
    @Override
    public void changeStatus(Long id) throws NotFoundException {

        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No such task with id: %d!", id)));

        Status status = task.getStatus();

        if (status == Status.DONE) {

            throw new InvalidStatusException("The task has already been done!");
        }
        if (status == Status.IN_PROGRESS) {

            task.setEndTime(DateFormatter.formatterWithTime.format(new GregorianCalendar().getTime()));

            task.setStatus(Status.DONE);

        }
        if (status == Status.BACKLOG) {

            task.setStartTime(DateFormatter.formatterWithTime.format(new GregorianCalendar().getTime()));

            task.setStatus(Status.IN_PROGRESS);
        }

        taskRepository.save(task);
       // logger.info(String.format("Successfully changed status of task with id: %d, to %s", id, task.getStatus()));
    }

    /**
     * Finds task by name
     * @param name Task name
     *
     */
    @Override
    public TaskEntity findByName(String name) throws NotFoundException {
      //  logger.info(String.format("Successfully found task with name: %s", name));
        return taskRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("No such task with name %s", name)));
    }

    /**
     * Finds all tasks by project id
     * @param projectId Project id
     *
     */
    @Override
    public List<TaskEntity> findAllByProjectId(Long projectId) throws NotFoundException {
     //   logger.info(String.format("Successfully found tasks of project with id: %d", projectId));
        return taskRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id: %d have no tasks!", projectId)));


    }

    /**
     * Finds task by id
     * @param id Task id
     *
     */
    @Override
    public TaskEntity findById(Long id) throws NotFoundException {
     //   logger.info(String.format("Successfully found task with id: %d", id));
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No such task with id: %d!", id)));
    }

    /**
     * Checks if at least one of the tasks of the project with stated id is in progress or backlog
     * @param projectId Project id
     *
     */
    @Override
    public boolean checkForTasksInProgressAndBacklog(Long projectId) throws NotFoundException {

        //  logger.info("The project with id: %d have tasks with statuses: 'IN_PROGRESS', 'BACKLOG'");
        //    logger.info("The project with id: %d have no tasks with statuses: 'IN_PROGRESS', 'BACKLOG'");
        return taskRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id: %d have no tasks!", projectId)))
                .stream().allMatch(task -> (task.getStatus() == Status.DONE));
    }

    /**
     * Initializing some fields that shouldn't be defined manually(example: creationTime)
     * @param projectId Project id
     * @param requestDto Json from HTTP request that mapped into request dto
     */
    @Override
    public void setUpRequestDto(TaskRequestDto requestDto, Long projectId) throws NotFoundException, IllegalArgumentException {

        checkIfNotNull(requestDto.getCreationTime(), "Creation time shouldn't be defined manually!");
        checkIfNotNull(requestDto.getStartTime(), "Start time shouldn't be defined manually!");
        checkIfNotNull(requestDto.getEndTime(), "End time shouldn't be defined manually!");
        checkIfNull(requestDto.getReleaseVersion(), "Define the release!");
        checkIfNull(requestDto.getName(), "Enter the name of the task!");
        checkIfNull(requestDto.getType(),
                    String.format("Define the type of the task! Possible types: %s", Arrays.toString(Type.values())));

        if (requestDto.getResponsibleUsername() == null) {
            throw new IllegalArgumentException("Enter the username of the responsible person!");
        } else {
            requestDto.setResponsibleId(userService.findByUsername(requestDto.getResponsibleUsername()).getId());
        }

        requestDto.setCreationTime(DateFormatter.formatterWithTime.format(Calendar.getInstance().getTime()));

        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

        requestDto.setAuthorId(currentSessionUser.getId());

        requestDto.setProjectId(projectId);

        requestDto.setStatus(Status.BACKLOG);

        ReleaseEntity currentRelease = releaseService.findByVersionAndProjectId(requestDto.getReleaseVersion(), projectId);

        requestDto.setRelease(currentRelease);

       // logger.info("Successfully set up TaskRequestDto");
    }

    /**
     * Searches for tasks that contains data from the request dto
     * @param filterDto Json from HTTP request that mapped into request dto
     */
    @Override
    public List<TaskEntity> searchByFilter(TaskRequestDto filterDto) throws NotFoundException {

        List<TaskEntity> result = taskRepository.findAll();

        if (filterDto.getAuthorUsername() != null && checkIfEmpty(result)) {
            UserEntity author = userService.findByUsername(filterDto.getAuthorUsername());
            result = result.stream().filter(task -> Objects.equals(task.getAuthorId(), author.getId()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getResponsibleUsername() != null && checkIfEmpty(result)) {
            UserEntity responsible = userService.findByUsername(filterDto.getResponsibleUsername());
            result = result.stream().filter(task -> Objects.equals(task.getResponsibleId(), responsible.getId()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getId() != null && checkIfEmpty(result)) {
            result = taskRepository.findAll().stream()
                    .filter(task -> task.getId().toString().contains(filterDto.getId().toString()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getStatus() != null && checkIfEmpty(result)) {
            result = result.stream().filter(task -> task.getStatus() == filterDto.getStatus())
                    .collect(Collectors.toList());
        }
        if (filterDto.getType() != null && checkIfEmpty(result)) {
            result = result.stream().filter(task -> task.getType() == filterDto.getType())
                    .collect(Collectors.toList());
        }
        if (filterDto.getAuthorId() != null && checkIfEmpty(result)) {
            result = result.stream()
                    .filter(task -> task.getAuthorId().toString().contains(filterDto.getAuthorId().toString()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getResponsibleId() != null && checkIfEmpty(result)) {
            result = result.stream()
                    .filter(task -> task.getResponsibleId().toString().contains(filterDto.getResponsibleId().toString()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getProjectId() != null && checkIfEmpty(result)) {
            result = result.stream()
                    .filter(task -> task.getProjectId().toString().contains(filterDto.getProjectId().toString()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getName() != null && checkIfEmpty(result)) {
            result = result.stream().filter(task -> task.getName().contains(filterDto.getName()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getDescription() != null && checkIfEmpty(result)) {
            result = result.stream().filter(task -> task.getDescription().contains(filterDto.getDescription()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getReleaseVersion() != null && checkIfEmpty(result)) {
            System.out.println("Not null");
            result = result.stream().filter(
                            task -> task.getRelease().getVersion().contains(filterDto.getReleaseVersion()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getStartTime() != null && checkIfEmpty(result)) {
            result = result.stream().filter(
                            task -> task.getStartTime().contains(filterDto.getStartTime()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getCreationTime() != null && checkIfEmpty(result)) {
            result = result.stream().filter(
                            task -> task.getCreationTime().contains(filterDto.getCreationTime()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getEndTime() != null && checkIfEmpty(result)) {
            result = result.stream().filter(
                            task -> task.getEndTime().contains(filterDto.getEndTime()))
                    .collect(Collectors.toList());
        }

  //      logger.info(String.format("Search by filter ended successfully with %d results found", result.size()));
        return result;
    }

    /**
     * Searches for unfinished or expired(ended after release end) tasks of a project with stated id by version
     * @param projectId Project id
     * @param releaseVersion Release version
     *
     */
    @Override
    public List<TaskEntity> findUnfinishedAndExpiredTasksByReleaseVersion(Long projectId, String releaseVersion) throws NotFoundException {

        List<TaskEntity> tasksWithReleaseVersion = taskRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("Project with id: %d have no tasks!", projectId))).stream()
                .filter(task -> task.getRelease().getVersion().equals(releaseVersion)).collect(Collectors.toList());

        List<TaskEntity> unfinishedTasks = tasksWithReleaseVersion.stream().filter(task -> task.getEndTime() == null)
                .collect(Collectors.toList());

        List<TaskEntity> expiredTasks = tasksWithReleaseVersion.stream().filter(task -> task.getEndTime() != null)
                .filter(task -> {

                    try {
                        return DateFormatter.formatterWithoutTime.parse(task.getRelease().getEndTime()).getTime()
                                < DateFormatter.formatterWithTime.parse(task.getEndTime()).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return false;
                }).collect(Collectors.toList());

        //    logger.info(String.format("Search for unfinished and expired tasks for release: %s ended successfully with %d results found",
     //                             releaseVersion, result.size()));

        return Stream.concat(unfinishedTasks.stream(), expiredTasks.stream())
                .collect(Collectors.toList());
    }

    /**
     * Searches for unfinished or expired(ended after release end) tasks of a project with stated id by release version
     * @param spec Specification of TaskEntity using Spring Data JPA
     *
     */
    @Override
    public List<TaskEntity> findAll(Specification<TaskEntity> spec) {
        //   logger.info(String.format("Search by filter ended successfully with %d results found", result.size()));
        return taskRepository.findAll(spec);
    }

    /**
     * Finds all the tasks of all projects
     */
    @Override
    public List<TaskEntity> findAll() {
      //  logger.info("Successfully got all the tasks of all projects");
        return taskRepository.findAll();
    }

    private boolean checkIfEmpty(List<TaskEntity> list) throws NotFoundException {

        if (list.isEmpty()) {
            throw new NotFoundException("No tasks were found with stated filters!");
        }

        return true;
    }

    private void checkIfNull(Object o, String msg) {
        if (o == null) {
            throw new IllegalArgumentException(msg);
        }

    }

    private void checkIfNotNull(Object o, String msg) {
        if (o != null) {
            throw new IllegalArgumentException(msg);
        }
    }
}
