package org.example.service;

import javassist.NotFoundException;
import org.example.DTO.task.TaskRequestDto;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;

import java.util.List;


public interface TaskService {

    void save(TaskEntity taskEntity);

    void delete(Long id);

    void changeStatus(Long id) throws NotFoundException;

    List<TaskEntity> getAll();

    List<TaskEntity> getAllByProjectId(Long project_id);

    TaskEntity findById(Long id) throws NotFoundException;

    boolean checkForTasksInProgressAndBacklog(Long projectId);

    void setUpRequestDto(TaskRequestDto requestDto, Long id) throws NotFoundException;
}
