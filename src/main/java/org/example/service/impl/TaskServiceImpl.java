package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.TaskRequestDto;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Type;
import org.example.exception.InvalidStatusException;
import org.example.entity.TaskEntity;
import org.example.enumeration.Status;
import org.example.repository.ProjectRepository;
import org.example.repository.TaskRepository;
import org.example.service.*;
import org.example.translator.TranslationService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is the class that implements business-logic of tasks in this app.
 * @author Kirill Zhdanov
 *
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ReleaseService releaseService;
    private final ProjectRepository projectService;
    private final TranslationService translationService;



    public TaskServiceImpl(TaskRepository taskRepository, UserService userService,
                           ReleaseService releaseService, TranslationService translationService,
                           ProjectRepository projectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.releaseService = releaseService;
        this.translationService = translationService;
        this.projectService = projectService;
    }

    /**
     * Finds release by version and project id
     * @param taskEntity Entity of a task
     *
     */
    @Override
    public void save(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
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
    }

    /**
     * Changes task status by id
     * @param id Task id
     *
     */
    @Override
    public void changeStatus(Long id) throws NotFoundException {

        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("No such task with id: %d!"), id)));

        Status status = task.getStatus();

        if (status == Status.DONE) {

            throw new InvalidStatusException(translationService.getTranslation("The task has already been done!"));
        }
        if (status == Status.IN_PROGRESS) {

            task.setEndTime(Constants.formatterWithTime.format(new GregorianCalendar().getTime()));

            task.setStatus(Status.DONE);

        }
        if (status == Status.BACKLOG) {

            task.setStartTime(Constants.formatterWithTime.format(new GregorianCalendar().getTime()));

            task.setStatus(Status.IN_PROGRESS);
        }

        taskRepository.save(task);
    }

    /**
     * Finds task by name
     * @param name Task name
     *
     */
    @Override
    public TaskEntity findByName(String name) throws NotFoundException {

        return taskRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("No such task with name %s"), name)));
    }

    /**
     * Finds all tasks by project id
     * @param projectId Project id
     *
     */
    @Override
    public List<TaskEntity> findAllByProjectId(Long projectId) throws NotFoundException {

        return taskRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("Project with id: %d have no tasks!"), projectId)));


    }

    /**
     * Finds task by id
     * @param id Task id
     *
     */
    @Override
    public TaskEntity findById(Long id) throws NotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("No such task with id: %d!"), id)));
    }

    /**
     * Checks if at least one of the tasks of the project with stated id is in progress or backlog
     * @param projectId Project id
     *
     */
    @Override
    public boolean checkForTasksInProgressAndBacklog(Long projectId) throws NotFoundException {

        return taskRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("Project with id: %d have no tasks!"), projectId)))
                .stream().allMatch(task -> (task.getStatus() == Status.DONE));
    }

    /**
     * Initializing some fields that shouldn't be defined manually(example: creationTime)
     * @param projectId Project id
     * @param requestDto Json from HTTP request that mapped into request dto
     */
    @Override
    public void setUpRequestDto(TaskRequestDto requestDto, Long projectId) throws NotFoundException, IllegalArgumentException {

        checkIfNotNull(requestDto.getCreationTime(),
                       translationService.getTranslation("Creation time shouldn't be defined manually!"));
        checkIfNotNull(requestDto.getStartTime(),
                       translationService.getTranslation("Start time shouldn't be defined manually!"));
        checkIfNotNull(requestDto.getEndTime(),
                       translationService.getTranslation("End time shouldn't be defined manually!"));
        checkIfNull(requestDto.getReleaseVersion(),
                    translationService.getTranslation("Define the release!"));
        checkIfNull(requestDto.getName(),
                    translationService.getTranslation("Enter the name of the task!"));
        checkIfNull(requestDto.getType(),
                    String.format(translationService.getTranslation(
                            "Define the type of the task! Possible types: %s"), Arrays.toString(Type.values())));

        if (requestDto.getResponsibleUsername() == null) {
            throw new IllegalArgumentException(
                    translationService.getTranslation("Enter the username of the responsible person!"));
        } else {
            requestDto.setResponsible(userService.findByUsername(requestDto.getResponsibleUsername()));
        }

        requestDto.setCreationTime(Constants.formatterWithTime.format(Calendar.getInstance().getTime()));

        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

        requestDto.setAuthor(currentSessionUser);

        ProjectEntity project = projectService.findById(projectId).orElseThrow(() -> new NotFoundException("No such project with id: %d"));

        requestDto.setProject(project);

        requestDto.setStatus(Status.BACKLOG);

        ReleaseEntity currentRelease = releaseService.findByVersionAndProjectId(requestDto.getReleaseVersion(), projectId);

        requestDto.setRelease(currentRelease);
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
            result = result.stream().filter(task -> Objects.equals(task.getAuthor().getId(), author.getId()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getResponsibleUsername() != null && checkIfEmpty(result)) {
            UserEntity responsible = userService.findByUsername(filterDto.getResponsibleUsername());
            result = result.stream().filter(task -> Objects.equals(task.getResponsible().getId(), responsible.getId()))
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
        if (filterDto.getAuthor() != null && checkIfEmpty(result)) {
            result = result.stream()
                    .filter(task -> task.getAuthor().toString().contains(filterDto.getAuthor().toString()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getResponsible() != null && checkIfEmpty(result)) {
            result = result.stream()
                    .filter(task -> task.getResponsible().toString().contains(filterDto.getResponsible().toString()))
                    .collect(Collectors.toList());
        }
        if (filterDto.getProject() != null && checkIfEmpty(result)) {
            result = result.stream()
                    .filter(task -> task.getProject().toString().contains(filterDto.getProject().toString()))
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
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("Project with id: %d have no tasks!"), projectId))).stream()
                .filter(task -> task.getRelease().getVersion().equals(releaseVersion)).collect(Collectors.toList());

        List<TaskEntity> unfinishedTasks = tasksWithReleaseVersion.stream().filter(task -> task.getEndTime() == null)
                .collect(Collectors.toList());

        List<TaskEntity> expiredTasks = tasksWithReleaseVersion.stream().filter(task -> task.getEndTime() != null)
                .filter(task -> {

                    try {
                        return Constants.formatterWithoutTime.parse(task.getRelease().getEndTime()).getTime()
                                < Constants.formatterWithTime.parse(task.getEndTime()).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return false;
                }).collect(Collectors.toList());

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

        return taskRepository.findAll(spec);
    }

    /**
     * Finds all the tasks of all projects
     */
    @Override
    public List<TaskEntity> findAll() {
        return taskRepository.findAll();
    }

    private boolean checkIfEmpty(List<TaskEntity> list) throws NotFoundException {

        if (list.isEmpty()) {
            throw new NotFoundException(
                    translationService.getTranslation("No tasks were found with stated filters!"));
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
