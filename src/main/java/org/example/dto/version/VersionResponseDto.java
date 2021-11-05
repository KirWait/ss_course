package org.example.dto.version;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.entity.TaskEntity;

public class VersionResponseDto {
    public VersionResponseDto() {
    }

    @Override
    public String toString() {
        return "VersionResponseDto{" +
                ", version='" + version + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", id=" + id +
                '}';
    }
    @JsonIgnore
    private TaskEntity task;

    private String version;

    private String endTime;

    private String startTime;

    private Long id;

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String start_time) {
        this.startTime = start_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
