package org.example.services.impl;

import javassist.NotFoundException;
import org.example.entities.ProjectEntity;
import org.example.entities.enums.Status;
import org.example.repositories.ProjectRepository;
import org.example.services.ProjectService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void save(ProjectEntity projectEntity) {
        projectRepository.save(projectEntity);

    }

    @Override
    public void changeStatus(Long id) throws Exception {
        ProjectEntity pe = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("No such projects"));
        if (pe.getStatus() == Status.DONE) {
            throw new Exception("The project has already been done!");
        }
        if (pe.getStatus() == Status.IN_PROGRESS) {
            pe.setStatus(Status.DONE);
        }
        if (pe.getStatus() == Status.BACKLOG) {
            pe.setStatus(Status.IN_PROGRESS);
        }

        projectRepository.save(pe);
    }

    @Override
    public List<ProjectEntity> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public ProjectEntity findById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
