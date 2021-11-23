package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;

@Schema(description = "DTO for storing json input for further transformation into task entity")
public class TaskRequestDto {

    @Schema(description = "Field that stores author username of the task")
    private String authorUsername;

    @Schema(description = "Field that stores responsible username of the task")
    private String responsibleUsername;

    @Schema(description = "Field that stores id of the task")
    private Long id;

    @Schema(description = "Field that stores project of the task")
    private ProjectEntity project;

    @Schema(description = "Field that stores status of the task")
    private Status status;

    @Schema(description = "Field that stores name of the task")
    private String name;

    @Schema(description = "Field that stores description of the task")
    private String description;

    @Schema(description = "Field that stores author of the task")
    private UserEntity author;

    @Schema(description = "Field that stores responsible of the task")
    private UserEntity responsible;

    @Schema(description = "Field that stores release of the task")
    private ReleaseEntity release;

    @Schema(description = "Field that stores type of the task")
    private Type type;

    @Schema(description = "Field that stores release version of the release of the task")
    private String releaseVersion;

    @Schema(description = "Field that stores release start time of the release of the task")
    private String startTime;

    @Schema(description = "Field that stores release end time of the release of the task")
    private String endTime;

    @Schema(description = "Field that stores release creation time of the release of the task")
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

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
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

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public UserEntity getResponsible() {
        return responsible;
    }

    public void setResponsible(UserEntity responsible) {
        this.responsible = responsible;
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

    @Override
    public String toString() {
        return "authorUsername:" + authorUsername+
                ", responsibleUsername=" + responsibleUsername +
                ", id=" + id +
                ", projectId=" + project +
                ", status=" + status +
                ", name=" + name +
                ", description=" + description +
                ", authorId=" + author +
                ", responsibleId=" + responsible +
                ", type=" + type +
                ", releaseVersion=" + releaseVersion +
                ", startTime=" + startTime +
                ", endTime=" + endTime  +
                ", creationTime=" + creationTime;
    }
}

