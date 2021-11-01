package org.example.services.impl;

import javassist.NotFoundException;
import org.example.entities.ProjectEntity;
import org.example.entities.TaskEntity;
import org.example.entities.enums.Status;
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
    public void changeStatus(Long id) throws Exception {
        TaskEntity te = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("No such task"));
        if (te.getStatus() == Status.DONE) {
            throw new Exception("The task has already been done!");
        }
        if (te.getStatus() == Status.IN_PROGRESS) {
            te.setStatus(Status.DONE);
        }
        if (te.getStatus() == Status.BACKLOG) {
            te.setStatus(Status.IN_PROGRESS);
        }

        taskRepository.save(te);
    }

    @Override
    public List<TaskEntity> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<TaskEntity> getAllByProjectId(Long project_id) {
        return taskRepository.findAllByProjectId(project_id);
    }

    @Override
    public TaskEntity findById(Long id) {
        return taskRepository.findById(id).orElse(new TaskEntity());
    }

}
