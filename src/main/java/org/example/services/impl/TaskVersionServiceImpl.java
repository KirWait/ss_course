package org.example.services.impl;

import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;
import org.example.entities.enums.Status;
import org.example.repositories.TaskVersionRepository;
import org.example.services.TaskVersionService;
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
    public void changeVersion(TaskVersionEntity version, TaskEntity task) throws Exception {
        List<TaskVersionEntity> versions = taskVersionRepository.findAllByTaskOrderById(task);

        if (task.getStatus() == Status.BACKLOG) throw new Exception("Can't change version of BACKLOG task!");

        TaskVersionEntity lastVersion = versions.get(versions.size() - 1);

        if (lastVersion.getEndTime() == null) lastVersion.setEndTime(Calendar.getInstance());
        version.setTask(task);
        taskVersionRepository.save(version);

    }
}
