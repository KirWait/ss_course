package org.example.entities;

import org.example.DTOs.TaskRequestDto;
import org.example.entities.enums.Status;
import org.example.entities.enums.TaskType;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "tasks")
public class TaskEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long task_id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "responsible_id")
    private Long responsibleId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", nullable = false)
    private List<TaskVersionEntity> version;


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType taskType;




    public TaskRequestDto convertToTaskDto(){
        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setId(task_id);
        taskRequestDto.setProjectId(projectId);
        taskRequestDto.setStatus(status.name());
        taskRequestDto.setName(name);
        taskRequestDto.setDescription(description);
        taskRequestDto.setAuthorId(authorId);
        taskRequestDto.setResponsibleId(responsibleId);
        taskRequestDto.setVersion(version);
        taskRequestDto.setType(taskType.name());

        return taskRequestDto;
    }

    public void setVersion(List<TaskVersionEntity> version) {
        this.version = version;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Long getId() {
        return task_id;
    }

    public void setId(Long id) {
        this.task_id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public List<TaskVersionEntity> getVersionDto() {
        return version;
    }

    public void setVersionDto(List<TaskVersionEntity> version) {
        this.version = version;
    }

    public TaskType getType() {
        return taskType;
    }

    public void setType(TaskType taskType) {
        this.taskType = taskType;
    }
}
