package org.example.service.impl;

import javassist.NotFoundException;
import org.example.DTO.task.TaskRequestDto;
import org.example.entity.UserEntity;
import org.example.exception.InvalidStatusException;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;
import org.example.enumeration.Status;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    
    private final TaskRepository taskRepository;
    private final UserService userService;


    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
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
    public void changeStatus(Long id) throws NotFoundException {
        TaskEntity te = taskRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("No such task with id: %d!", id)));


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

        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("No such task with id: %d!", id)));
    }

    @Override
    public boolean checkForTasksInProgressAndBacklog(Long projectId){

         AtomicBoolean result = new AtomicBoolean(true);

         taskRepository.findAllByProjectId(projectId).forEach(task -> {
             if (task.getStatus() == Status.IN_PROGRESS || task.getStatus() == Status.BACKLOG) { result.set(false);}
         });

         return result.get();
    }

    @Override
    public void setUpRequestDto(TaskRequestDto requestDto, Long id) throws NotFoundException,IllegalArgumentException{

        if (requestDto.getResponsibleId() != null){
            throw new IllegalArgumentException("Responsible id shouldn't be defined manually!");
        }

        if (requestDto.getResponsibleUsername() == null) {
            throw new IllegalArgumentException("Enter the username of the responsible person!");
        }
        else {
            requestDto.setResponsibleId(userService.findByUsername(requestDto.getResponsibleUsername()).getId());
        }

        String currentSessionUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity currentSessionUser = userService.findByUsername(currentSessionUserName);

        requestDto.setAuthorId(currentSessionUser.getId());

        requestDto.setProjectId(id);

        requestDto.setVersions(List.of(new TaskVersionEntity("1.0", Calendar.getInstance())));

        requestDto.setStatus(Status.BACKLOG);
    }


}
