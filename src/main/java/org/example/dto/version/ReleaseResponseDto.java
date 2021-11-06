package org.example.dto.version;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.entity.TaskEntity;

public class ReleaseResponseDto {

    @Override
    public String toString() {
        return "ReleaseResponseDto{" +
                ", version='" + version + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + creationTime + '\'' +
                ", id=" + id +
                '}';
    }
    @JsonIgnore
    private List<TaskEntity> tasks;

    private String version;

    private String endTime;

    private String creationTime;

    private Long id;

    private Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String start_time) {
        this.creationTime = start_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
