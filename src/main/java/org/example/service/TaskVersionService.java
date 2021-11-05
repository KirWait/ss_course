package org.example.service;

import org.example.dto.version.VersionRequestDto;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;

import java.util.List;

public interface TaskVersionService {
    List<TaskVersionEntity> findAllByTaskOrderById(TaskEntity task);
    void save(TaskVersionEntity version);
    void changeVersionOrThrowException(TaskVersionEntity version, TaskEntity task) throws Exception;

    void setUpRequestDto(VersionRequestDto version, TaskEntity task);
}
