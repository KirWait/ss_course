package org.example.services.impl;

import javassist.NotFoundException;
import org.example.entities.InvalidStatusException;
import org.example.entities.InvalidVersionException;
import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;
import org.example.entities.enums.Status;
import org.example.repositories.TaskRepository;
import org.example.services.TaskService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
        TaskEntity te = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("No such task with id: "+id+"!"));


        if (te.getStatus() == Status.DONE) {
            throw new InvalidStatusException("The task has already been done!");
        }
        if (te.getStatus() == Status.IN_PROGRESS) {
            te.setStatus(Status.DONE);
            List<TaskVersionEntity> versions = te.getVersions();
            versions.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
            versions.get(versions.size() - 1).setEndTime(Calendar.getInstance());
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
    public TaskEntity findById(Long id) throws NotFoundException {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("No such task with id: "+id+"!"));
    }

    @Override
    public boolean checkForTasksInProgressAndBacklog(Long projectId){
         AtomicBoolean result = new AtomicBoolean(true);
         taskRepository.findAllByProjectId(projectId).forEach(task -> {
             if (task.getStatus() == Status.IN_PROGRESS || task.getStatus() == Status.BACKLOG) result.set(false);
         });
         return result.get();
    }

    @Override
    public boolean checkVersion(TaskVersionEntity version, List<TaskVersionEntity> versions) {

        versions.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        if (versions.get(versions.size() - 1).getVersion() >= version.getVersion()) throw new InvalidVersionException("Can't set version that is less than current version of the task. Current version is: "+versions.get(versions.size() - 1).getVersion() );
        return true;
    }


}
