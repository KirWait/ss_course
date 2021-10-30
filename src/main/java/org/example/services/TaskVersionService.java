package org.example.services;

import org.example.entities.TaskVersionEntity;

import java.util.List;

public interface TaskVersionService {
    List<TaskVersionEntity> findByTaskId(Long id);
    void save(TaskVersionEntity version);
}
