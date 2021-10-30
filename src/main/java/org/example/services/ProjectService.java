package org.example.services;

import javassist.NotFoundException;
import org.example.entities.ProjectEntity;

import java.util.List;

public interface ProjectService {
    void save(ProjectEntity projectEntity);
    void changeStatus(Long id) throws Exception;
    List<ProjectEntity> getAll();
    ProjectEntity findById(Long id);
    void delete(Long id);

}
