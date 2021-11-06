package org.example.dto.version;

import org.example.entity.TaskEntity;
import java.util.List;


public class ReleaseRequestDto {

    private Long projectId;

    private String version;

    private String endTime;

    private String creationTime;

    private Long id;

    private List<TaskEntity> tasks;

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
