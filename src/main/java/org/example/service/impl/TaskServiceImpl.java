package org.example.service.impl;

import javassist.NotFoundException;
import org.example.dto.task.TaskRequestDto;
import org.example.entity.UserEntity;
import org.example.exception.InvalidStatusException;
import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;
import org.example.enumeration.Status;
import org.example.repository.TaskRepository;
import org.example.service.DateFormatter;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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

            versions.get(versions.size() - 1).setEndTime(DateFormatter.formatter.format(new GregorianCalendar().getTime()));
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

        requestDto.setVersions(List.of(new TaskVersionEntity("1.0", DateFormatter.formatter.format(new GregorianCalendar().getTime()))));

        requestDto.setStatus(Status.BACKLOG);
    }

    @Override
    public List<TaskEntity> searchByFilter(TaskRequestDto filterDto) throws NotFoundException {

        List<TaskEntity> result = taskRepository.findAll();

        if (filterDto.getAuthorUsername() != null) {
            UserEntity author = userService.findByUsername(filterDto.getAuthorUsername());
            result = result.stream().filter(task -> Objects.equals(task.getAuthorId(), author.getId())).collect(Collectors.toList());
        }
        if (filterDto.getResponsibleUsername() != null) {
            UserEntity responsible = userService.findByUsername(filterDto.getResponsibleUsername());
            result = result.stream().filter(task -> Objects.equals(task.getResponsibleId(), responsible.getId())).collect(Collectors.toList());
        }
        if (filterDto.getId() != null){
           result = taskRepository.findAll().stream().filter(task -> task.getId().toString().contains(filterDto.getId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getStatus() != null){
            result = result.stream().filter(task -> task.getStatus() == filterDto.getStatus()).collect(Collectors.toList());
        }
        if (filterDto.getType() != null){
            result = result.stream().filter(task -> task.getType() == filterDto.getType()).collect(Collectors.toList());
        }
        if (filterDto.getAuthorId() != null){
            result = result.stream().filter(task -> task.getAuthorId().toString().contains(filterDto.getAuthorId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getResponsibleId() != null){
            result = result.stream().filter(task -> task.getResponsibleId().toString().contains(filterDto.getResponsibleId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getProjectId() != null){
            result = result.stream().filter(task -> task.getProjectId().toString().contains(filterDto.getProjectId().toString())).collect(Collectors.toList());
        }
        if (filterDto.getName() != null){
            result = result.stream().filter(task -> task.getName().contains(filterDto.getName())).collect(Collectors.toList());
        }
        if (filterDto.getDescription() != null){
            result = result.stream().filter(task -> task.getDescription().contains(filterDto.getDescription())).collect(Collectors.toList());
        }

        if (filterDto.getVersionForSearch() != null){
            result = result.stream().filter(
                    task -> task.getVersions().stream().filter(version -> version.getEndTime() == null).collect(Collectors.toList()) // finds last version of the task (equal to condition endTime != null)
                            .get(0).getVersion().contains(filterDto.getVersionForSearch()) //checks if it contains versionForSearchString
            ).collect(Collectors.toList());
        }

        if (filterDto.getStartTimeForSearch() != null){
            result = result.stream().filter(
                    task -> task.getVersions().stream().filter(version -> version.getEndTime() == null).collect(Collectors.toList())
                            .get(0).getStartTime().contains(filterDto.getStartTimeForSearch())
            ).collect(Collectors.toList());
        }
        if (filterDto.getEndTimeForSearch() != null){
            result = result.stream().filter(
                    task -> task.getVersions().stream().filter(version -> version.getEndTime() == null).collect(Collectors.toList())
                            .get(0).getEndTime().contains(filterDto.getEndTimeForSearch())
            ).collect(Collectors.toList());
        }







        return result;
    }


}
