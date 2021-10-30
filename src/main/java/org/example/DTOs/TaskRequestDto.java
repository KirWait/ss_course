package org.example.DTOs;

import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;
import org.example.entities.enums.Status;
import org.example.entities.enums.TaskType;

import java.util.List;


public class TaskRequestDto {

    private Long id;

    private Long projectId;

    private String status;

    private String name;

    private String description;

    private Long authorId;

    private Long responsibleId;

    private List<TaskVersionEntity> version;

    private String type;

    public TaskEntity convertToTaskEntity(){
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        taskEntity.setProjectId(projectId);
        taskEntity.setStatus(Status.valueOf(status));
        taskEntity.setName(name);
        taskEntity.setDescription(description);
        taskEntity.setAuthorId(authorId);
        taskEntity.setResponsibleId(responsibleId);
        taskEntity.setVersion(version);
        taskEntity.setType(TaskType.valueOf(type));

        return taskEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<TaskVersionEntity> getVersion() {
        return version;
    }

    public void setVersion(List<TaskVersionEntity> version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
