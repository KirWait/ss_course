package org.example.services.impl;

import javassist.NotFoundException;
import org.example.entities.InvalidStatusException;
import org.example.entities.ProjectEntity;
import org.example.entities.enums.Status;
import org.example.repositories.ProjectRepository;
import org.example.services.ProjectService;
import org.example.services.TaskService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
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
        if (pe.getStatus() == Status.IN_PROGRESS && taskService.checkForTasksInProgressAndBacklog(pe.getProjectId())) {
            pe.setStatus(Status.DONE);
        }
        if (pe.getStatus() == Status.BACKLOG) {
            pe.setStatus(Status.IN_PROGRESS);
        }

        projectRepository.save(pe);
    }

    @Override
    public boolean checkIfProjectInProgress(Long id) throws NotFoundException {

        ProjectEntity project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("No such project with id: "+id+"!"));

        if (project.getStatus() == Status.BACKLOG || project.getStatus() == Status.DONE) throw new InvalidStatusException("The project is in BACKLOG or DONE stage");
        return true;
    }

    @Override
    public ProjectEntity findByProjectName(String name) throws NotFoundException {
        ProjectEntity project = projectRepository.findByProjectName(name);
        if (project == null) throw new NotFoundException("No such project with name: " + name + "!");

        return project;
    }

    @Override
    public List<ProjectEntity> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public ProjectEntity findById(Long id) throws NotFoundException {
        ProjectEntity project = projectRepository.findById(id).orElse(null);
        if (project == null) throw new NotFoundException("No such project with id: "+id +"!");
        return project;
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }


}
