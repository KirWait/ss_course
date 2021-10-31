package org.example.services;

import org.example.entities.TaskEntity;

import java.util.List;


public interface TaskService {

    void save(TaskEntity taskEntity);
    void delete(Long id);
    List<TaskEntity> getAll();
    List<TaskEntity> getAllByProjectId(Long project_id);
    TaskEntity findById(Long id);


}
