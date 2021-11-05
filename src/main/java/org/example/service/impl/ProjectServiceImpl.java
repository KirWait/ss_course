package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.project.ProjectRequestDto;
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
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final UserService userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, TaskService taskService, UserService userService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public void save(ProjectEntity projectEntity) {
        projectRepository.save(projectEntity);

    }

    @Override
    public void changeStatus(Long id) throws InvalidStatusException, NotFoundException  {
        ProjectEntity pe = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("No such projects"));
        if (pe.getStatus() == Status.DONE) {
            throw new InvalidStatusException("The project has already been done!");
        }
        if (pe.getStatus() == Status.IN_PROGRESS && taskService.checkForTasksInProgressAndBacklog(pe.getId())) {
            pe.setStatus(Status.DONE);
        }
        if (pe.getStatus() == Status.BACKLOG) {
            pe.setStatus(Status.IN_PROGRESS);
        }

        projectRepository.save(pe);
    }

    @Override
    public boolean isProjectAvailableToChangeTaskStatus(Long id) throws NotFoundException {

        ProjectEntity project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException( String.format("No such project with id: %d!", id) ));

        if (project.getStatus() == Status.BACKLOG) throw new InvalidStatusException("The project is only in BACKLOG stage");

        return true;
    }

    @Override
    public ProjectEntity findByProjectName(String name) throws NotFoundException {

        ProjectEntity project = projectRepository.findByName(name);

        if (project == null) throw new NotFoundException("No such project with name: " + name + "!");

        return project;
    }

    @Override
    public void setUpRequestDto(ProjectRequestDto requestDto) throws NotFoundException, IllegalArgumentException{

        if (requestDto.getCustomerId() != null) {throw new IllegalArgumentException("Customer id shouldn't be defined manually!");}
        if (requestDto.getCustomerName() == null){

            String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();

            UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

            requestDto.setCustomerId(currentSessionUser.getId());
        }
        else {
            requestDto.setCustomerId(userService.findByUsername(requestDto.getCustomerName()).getId());
        }

        requestDto.setStatus(Status.BACKLOG);
    }

    @Override
    public void ifProjectAvailableToCreateTaskOrThrowException(Status status) {

        if (status == Status.DONE) { throw new InvalidStatusException("The project has already been done!"); }
    }

    @Override
    public void projectChangeStatusOrThrowException(Status status, Long id) throws NotFoundException, InvalidStatusException {

        if ((status == Status.IN_PROGRESS && taskService.checkForTasksInProgressAndBacklog(id))
                || (status == Status.BACKLOG)
                || (status == Status.DONE)) { changeStatus(id);}

        else throw new InvalidStatusException("Can't finish the project: there are unfinished tasks!");
    }

    @Override
    public List<ProjectEntity> getAll() {

        return projectRepository.findAll();
    }

    @Override
    public ProjectEntity findById(Long id) throws NotFoundException{

        ProjectEntity project = projectRepository.findById(id).orElse(null);

        if (project == null) throw new NotFoundException("No such project with id: "+id +"!");

        return project;
    }

    @Override
    public void delete(Long id) {

        projectRepository.deleteById(id);
    }


}
