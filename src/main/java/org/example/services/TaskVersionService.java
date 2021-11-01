package org.example.services;

import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;

import java.util.List;

public interface TaskVersionService {
    List<TaskVersionEntity> findAllByTaskOrderById(TaskEntity task);
    void save(TaskVersionEntity version);
    void changeVersion(TaskVersionEntity version, TaskEntity task) throws Exception;
}
