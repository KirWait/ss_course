package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.ProjectRequestDto;
import org.example.entity.UserEntity;
import org.example.exception.InvalidStatusException;
import org.example.entity.ProjectEntity;
import org.example.enumeration.Status;
import org.example.repository.ProjectRepository;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final UserService userService;

//    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public ProjectServiceImpl(ProjectRepository projectRepository, TaskService taskService, UserService userService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void save(ProjectEntity projectEntity) {
        projectRepository.save(projectEntity);
//        logger.info("Successfully saved project to database");
    }

    @Override
    public void changeStatus(Long id) throws InvalidStatusException, NotFoundException  {

        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No such projects"));

        Status projectEntityStatus = projectEntity.getStatus();

        if (projectEntityStatus == Status.DONE) {
            throw new InvalidStatusException("The project has already been done!");
        }
        if (projectEntityStatus == Status.IN_PROGRESS && taskService.checkForTasksInProgressAndBacklog(projectEntity.getId())) {
            projectEntity.setStatus(Status.DONE);
        }
        if (projectEntityStatus == Status.BACKLOG) {
            projectEntity.setStatus(Status.IN_PROGRESS);
        }

        projectRepository.save(projectEntity);
//        logger.info(String.format("Project with id: %d is available to change task status", id));
    }

    @Override
    public boolean isProjectAvailableToChangeTaskStatus(Long id) throws NotFoundException {

        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException( String.format("No such project with id: %d!", id) ));

        if (project.getStatus() == Status.BACKLOG) {
            throw new InvalidStatusException("The project is only in BACKLOG stage");
        }

        if (project.getStatus() == Status.DONE) {
            throw new InvalidStatusException("Can't manipulate with tasks of the project which has already been done!");
        }
//        logger.info(String.format("Project with id: %d is available to change task status", id));
        return true;
    }

    @Override
    public ProjectEntity findByProjectName(String name) throws NotFoundException {

//        logger.info(String.format("Successfully found project with name: %s", name));
        return projectRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("No such project with name: %s!", name)));
    }

    @Override
    public void setUpRequestDto(ProjectRequestDto requestDto) throws NotFoundException, IllegalArgumentException{

        if (requestDto.getCustomerId() != null) {
            throw new IllegalArgumentException("Customer id shouldn't be defined manually!");
        }

        if (requestDto.getCustomerName() == null){

            String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();

            UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

            requestDto.setCustomerId(currentSessionUser.getId());
        }
        else {
            requestDto.setCustomerId(userService.findByUsername(requestDto.getCustomerName()).getId());
        }

        requestDto.setStatus(Status.BACKLOG);

//        logger.info("Successfully set up ProjectRequestDto");
    }

    @Override
    public void ifProjectAvailableToCreateTaskOrThrowException(Long id) throws NotFoundException {
        Status status = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No such project with id: %d", id))).getStatus();
        if (status == Status.DONE) {
            throw new InvalidStatusException("The project has already been done!");
        }

//        logger.info(String.format("Project with id: %d is available to create task", id));
    }

    @Override
    public void projectChangeStatusOrThrowException(Long id) throws NotFoundException, InvalidStatusException {

        Status status = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No such project with id: %d", id))).getStatus();

        if ((status == Status.IN_PROGRESS && taskService.checkForTasksInProgressAndBacklog(id))
                || (status == Status.BACKLOG)
                || (status == Status.DONE)) {
            changeStatus(id);
        }
        else {
            throw new InvalidStatusException("Can't finish the project: there are unfinished tasks!");
        }

//        logger.info(String.format("Successfully changed status of the project with id: %d, to %s", id, status));
    }

    @Override
    public boolean ifProjectAvailableToCreateReleaseOrThrowException(Long id) throws NotFoundException {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No such project with id: %d", id)));
        if (project.getStatus() != Status.DONE) {
//            logger.info(String.format("Project with id: %d is available to create release", id));
            return true;
        }
        else {
            throw new InvalidStatusException("Can't create release: the project has already been done!");
        }
    }

    @Override
    public List<ProjectEntity> getAll() {
//        logger.info("Successfully got all the projects");
        return projectRepository.findAll();
    }

    @Override
    public ProjectEntity findById(Long id) throws NotFoundException{

//        logger.info(String.format("Successfully found project with id: %d", id));

        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException( String.format("No such project with id: %d!", id) ));
    }

    @Override
    @Transactional
    public void delete(Long id) {

//        logger.info(String.format("Successfully deleted project with id: %d", id));
        projectRepository.deleteById(id);
    }


}
