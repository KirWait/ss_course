package org.example.service;

import javassist.NotFoundException;
import org.example.dto.TaskRequestDto;
import org.example.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface TaskService {

    void save(TaskEntity taskEntity);

    void delete(Long id) throws NotFoundException;

    void changeStatus(Long id) throws NotFoundException;

    TaskEntity findByName(String name) throws NotFoundException;

    List<TaskEntity> findAllByProjectId(Long project_id) throws NotFoundException;

    TaskEntity findById(Long id) throws NotFoundException;

    boolean checkForTasksInProgressAndBacklog(Long projectId) throws NotFoundException;

    void setUpRequestDto(TaskRequestDto requestDto, Long id) throws NotFoundException;

    List<TaskEntity> searchByFilter(TaskRequestDto task) throws NotFoundException;

    List<TaskEntity> findUnfinishedAndExpiredTasksByReleaseVersion(Long projectId, String releaseVersion) throws NotFoundException;

    List<TaskEntity> findAll(Specification<TaskEntity> spec);

    List<TaskEntity> findAll(boolean isDeleted);
}
