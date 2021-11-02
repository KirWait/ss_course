package org.example.services;

import javassist.NotFoundException;
import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;

import java.util.List;


public interface TaskService {

    void save(TaskEntity taskEntity);
    void delete(Long id);
    void changeStatus(Long id) throws Exception;
    List<TaskEntity> getAll();
    List<TaskEntity> getAllByProjectId(Long project_id);
    TaskEntity findById(Long id) throws NotFoundException;

    boolean checkForTasksInProgressAndBacklog(Long projectId);

    boolean checkVersion(TaskVersionEntity version, List<TaskVersionEntity> versions);
}
