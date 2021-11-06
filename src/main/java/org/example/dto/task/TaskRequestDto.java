package org.example.dto.task;

import org.example.entity.ReleaseEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;


public class TaskRequestDto {

    private String authorUsername;

    private String responsibleUsername;

    private Long id;

    private Long projectId;

    private Status status;

    private String name;

    private String description;

    private Long authorId;

    private Long responsibleId;

    private ReleaseEntity release;

    private Type type;

    private String releaseVersion;

    private String startTime;

    private String endTime;

    private String creationTime;

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public Long getId() {return id; }

    public void setId(Long id) {this.id = id;}

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

    public ReleaseEntity getRelease() {
        return release;
    }

    public void setRelease(ReleaseEntity release) {
        this.release = release;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getResponsibleUsername() {
        return responsibleUsername;
    }
}
