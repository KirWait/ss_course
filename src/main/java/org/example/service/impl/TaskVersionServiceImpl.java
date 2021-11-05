package org.example.service.impl;

import org.example.DTO.version.VersionRequestDto;
import org.example.exception.InvalidVersionException;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;
import org.example.enumeration.Status;
import org.example.repository.TaskVersionRepository;
import org.example.service.TaskVersionService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
@Service
public class TaskVersionServiceImpl implements TaskVersionService {

    private final TaskVersionRepository taskVersionRepository;

    public TaskVersionServiceImpl(TaskVersionRepository taskVersionRepository) {
        this.taskVersionRepository = taskVersionRepository;
    }

    @Override
    public List<TaskVersionEntity> findAllByTaskOrderById(TaskEntity task) {
        return taskVersionRepository.findAllByTaskOrderById(task);
    }

    @Override
    public void save(TaskVersionEntity version) {
        taskVersionRepository.save(version);
    }

    @Override
    public void changeVersionOrThrowException(TaskVersionEntity version, TaskEntity task) {

        if (task.getStatus() == Status.BACKLOG || task.getStatus() == Status.DONE) throw new InvalidVersionException("Can't change version of BACKLOG or DONE task!");

        List<TaskVersionEntity> versions = taskVersionRepository.findAllByTaskOrderById(task);

        TaskVersionEntity lastVersion = versions.get(versions.size() - 1);

        if (lastVersion.getEndTime() == null) lastVersion.setEndTime(Calendar.getInstance());

        version.setTask(task);

        taskVersionRepository.save(version);

    }

    @Override
    public void setUpRequestDto(VersionRequestDto version, TaskEntity task) {

        version.setStartTime(Calendar.getInstance());

        version.setTask(task);
    }
}
