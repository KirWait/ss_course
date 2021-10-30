package org.example.services.impl;

import org.example.entities.TaskEntity;
import org.example.repositories.TaskRepository;
import org.example.services.TaskService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void save(TaskEntity taskEntity) {
        taskRepository.save(taskEntity);
        
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskEntity> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskEntity findById(Long id) {
        return taskRepository.findById(id).orElse(new TaskEntity());
    }

}
