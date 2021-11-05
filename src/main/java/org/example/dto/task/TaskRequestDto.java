package org.example.dto.task;

import com.google.gson.annotations.SerializedName;
import org.example.entity.TaskVersionEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;

import java.util.List;


public class TaskRequestDto {

    public TaskRequestDto() {
    }

    @SerializedName("version")
    private String versionForSearch;

    @SerializedName("startTime")//change!!
    private String startTimeForSearch;

    @SerializedName("endTime")
    private String endTimeForSearch;

    public TaskRequestDto(String versionForSearch) {

        this.versionForSearch = versionForSearch;
    }

    public TaskRequestDto(String startTimeForSearch, String endTimeForSearch) {
        this.startTimeForSearch = startTimeForSearch;
        this.endTimeForSearch = endTimeForSearch;
    }

    public String getVersionForSearch() {
        return versionForSearch;
    }

    public String getStartTimeForSearch() {
        return startTimeForSearch;
    }

    public String getEndTimeForSearch() {
        return endTimeForSearch;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    private String authorUsername;
    private String responsibleUsername;

    private Long id;

    private Long projectId;

    private Status status;

    private String name;

    private String description;

    private Long authorId;

    private Long responsibleId;

    private List<TaskVersionEntity> versions;

    private Type type;

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

    public List<TaskVersionEntity> getVersions() {
        return versions;
    }

    public void setVersions(List<TaskVersionEntity> versions) {
        this.versions = versions;
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
