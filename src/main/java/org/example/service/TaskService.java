package org.example.service;

import javassist.NotFoundException;
import org.example.dto.TaskRequestDto;
import org.example.dto.TaskResponseDto;
import org.example.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface TaskService {

    void save(TaskEntity taskEntity);

    void delete(Long id) throws NotFoundException;

    void changeStatus(Long id) throws NotFoundException;

    TaskEntity findByName(String name) throws NotFoundException;

    List<TaskEntity> findAllByProjectIdAndDeleted(Long projectId, boolean isDeleted) throws NotFoundException;

    TaskEntity findById(Long id) throws NotFoundException;

    boolean checkForTasksInProgressAndBacklog(Long projectId) throws NotFoundException;

    void setUpRequestDto(TaskRequestDto requestDto, Long id) throws NotFoundException;

    List<TaskEntity> findUnfinishedTasksByReleaseVersion(Long projectId, String releaseVersion) throws NotFoundException;

    List<TaskEntity> findExpiredTasksByReleaseVersion(Long projectId, String releaseVersion) throws NotFoundException;

    Page<TaskResponseDto> findAll(Specification<TaskEntity> spec, int page, int pageSize);

}
