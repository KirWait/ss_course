package org.example.DTO.version;


import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;

import java.util.Calendar;

public class VersionResponseDto {
    public VersionResponseDto() {
    }

    private TaskEntity task;

    private String version;

    private Calendar endTime;

    private Calendar startTime;

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

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar start_time) {
        this.startTime = start_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
