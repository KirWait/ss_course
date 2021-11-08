package org.example.service;

import javassist.NotFoundException;
import org.example.dto.task.TaskRequestDto;
import org.example.entity.TaskEntity;

import java.util.List;


public interface TaskService {

    void save(TaskEntity taskEntity);

    void delete(Long id);

    void changeStatus(Long id) throws NotFoundException;

    TaskEntity findByName(String name) throws NotFoundException;

    List<TaskEntity> findAllByProjectId(Long project_id);

    TaskEntity findById(Long id) throws NotFoundException;

    boolean checkForTasksInProgressAndBacklog(Long projectId);

    void setUpRequestDto(TaskRequestDto requestDto, Long id) throws NotFoundException;

    List<TaskEntity> searchByFilter(TaskRequestDto task) throws NotFoundException;

    List<TaskEntity> findUnfinishedAndExpiredTasksByReleaseVersion(Long projectId, String releaseVersion);
}
