package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.TaskRequestDto;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;
import org.example.exception.DeletedException;
import org.example.exception.InvalidStatusException;
import org.example.repository.ProjectRepository;
import org.example.repository.TaskRepository;
import org.example.service.MyDateFormat;
import org.example.service.ReleaseService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.translator.TranslationService;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.service.MyDateFormat.formatterWithTime;

/**
 * This is the class that implements business-logic of tasks in this app.
 * @author Kirill Zhdanov
 *
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final EntityManager entityManager;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ReleaseService releaseService;
    private final ProjectRepository projectRepository;
    private final TranslationService translationService;



    public TaskServiceImpl(EntityManager entityManager, TaskRepository taskRepository, UserService userService,
                           ReleaseService releaseService, TranslationService translationService,
                           ProjectRepository projectRepository) {
        this.entityManager = entityManager;
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.releaseService = releaseService;
        this.translationService = translationService;
        this.projectRepository = projectRepository;
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
    public void delete(Long id) throws NotFoundException {
        findById(id);
        taskRepository.deleteById(id);
    }


    /**
     * Changes task status by id
     * @param id Task id
     *
     */
    @Override
    public void changeStatus(Long id) throws NotFoundException {

        TaskEntity task = findById(id);
        Status status = task.getStatus();
        if (status == Status.DONE) {
            throw new InvalidStatusException(translationService.getTranslation("The task has already been done!"));
        }
        if (status == Status.IN_PROGRESS) {
            task.setEndTime(MyDateFormat.formatterWithTime.format(new GregorianCalendar().getTime()));
            task.setStatus(Status.DONE);
        }
        if (status == Status.BACKLOG) {
            task.setStartTime(MyDateFormat.formatterWithTime.format(new GregorianCalendar().getTime()));
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

        return taskRepository.findByNameAndDeleted(name, false)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("No such task with name %s"), name)));
    }

    /**
     * Finds all tasks by project id
     * @param projectId Project id
     *
     */
    @Override
    public List<TaskEntity> findAllByProjectIdAndDeleted(Long projectId, boolean isDeleted) throws NotFoundException {

        return taskRepository.findAllByProjectIdAndDeleted(projectId, isDeleted)
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
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("No such task with id: %d!"), id)));
        if(task.isDeleted()){
            throw new DeletedException(String.format("The task with id %d has already been deleted!", id));
        }
        return task;
    }

    /**
     * Checks if at least one of the tasks of the project with stated id is in progress or backlog
     * @param projectId Project id
     *
     */
    @Override
    public boolean checkForTasksInProgressAndBacklog(Long projectId) throws NotFoundException {

        return findAllByProjectIdAndDeleted(projectId, false)
               .stream().allMatch(task -> task.getStatus() == Status.DONE);
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

        requestDto.setCreationTime(MyDateFormat.formatterWithTime.format(Calendar.getInstance().getTime()));
        UserEntity currentSessionUser = userService.getCurrentSessionUser();
        requestDto.setAuthor(currentSessionUser);
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(String.format("No such project with id: %d", projectId)));
        if (project.isDeleted()){
            throw new DeletedException(String.format("The project with id: %d has already been deleted!", projectId));
        }
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

        List<TaskEntity> result = findAll(false);

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
            result = result.stream()
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
     * @return Returns 2 lists: unfinished and expired tasks
     */
    @Override
    public List<TaskEntity> findUnfinishedTasksByReleaseVersion(Long projectId, String releaseVersion) throws NotFoundException {

        List<TaskEntity> tasksByReleaseVersion = getTasksByReleaseVersion(projectId, releaseVersion);

        return tasksByReleaseVersion.stream().filter(task -> task.getEndTime() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskEntity> findExpiredTasksByReleaseVersion(Long projectId, String releaseVersion) throws NotFoundException {
        List<TaskEntity> tasksWithReleaseVersion = getTasksByReleaseVersion(projectId, releaseVersion);
        return tasksWithReleaseVersion.stream().filter(task -> task.getEndTime() != null)
                .filter(task -> {

                    try {
                        return MyDateFormat.formatterWithoutTime.parse(task.getRelease().getEndTime()).getTime()
                                < MyDateFormat.formatterWithTime.parse(task.getEndTime()).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return false;
                }).collect(Collectors.toList());
    }

    private List<TaskEntity> getTasksByReleaseVersion(Long projectId, String releaseVersion) throws NotFoundException {
        return taskRepository.findAllByProjectIdAndDeleted(projectId, false)
                .orElseThrow(() -> new NotFoundException(String.format(
                        translationService.getTranslation("Project with id: %d have no tasks!"), projectId))).stream()
                .filter(task -> task.getRelease().getVersion().equals(releaseVersion)).collect(Collectors.toList());
    }

    /**
     * Searches for unfinished or expired(ended after release end) tasks of a project with stated id by release version
     * @param spec Specification of TaskEntity using Spring Data JPA
     *
     */
    @Override
    public List<TaskEntity> findAll(Specification<TaskEntity> spec) {

        List<TaskEntity> tasks = taskRepository.findAll(spec);

        return tasks.stream().filter(task -> !task.isDeleted()).collect(Collectors.toList());
    }

    /**
     * Finds all the tasks of all projects
     */
    @Override
    public List<TaskEntity> findAll(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedTaskFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<TaskEntity> tasks = taskRepository.findAll();
        session.disableFilter("deletedTaskFilter");
        return tasks;
    }

    public static long countTaskTime(TaskEntity task) throws ParseException {
        if (task.getStartTime() == null) {
            return 0;
        }
        if (task.getEndTime() == null) {
            return System.currentTimeMillis() - formatterWithTime.parse(task.getStartTime()).getTime();

        }
        return formatterWithTime.parse(task.getEndTime()).getTime() -
                formatterWithTime.parse(task.getStartTime()).getTime();

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
