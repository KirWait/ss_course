package org.example.services.impl;

import org.example.entities.TaskVersionEntity;
import org.example.repositories.TaskVersionRepository;
import org.example.services.TaskVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskVersionServiceImpl implements TaskVersionService {

    private final TaskVersionRepository taskVersionRepository;

    public TaskVersionServiceImpl(TaskVersionRepository taskVersionRepository) {
        this.taskVersionRepository = taskVersionRepository;
    }

    @Override
    public List<TaskVersionEntity> findByTaskId(Long id) {
        return taskVersionRepository.findAllById(List.of(id));
    }

    @Override
    public void save(TaskVersionEntity version) {
        taskVersionRepository.save(version);
    }
}
