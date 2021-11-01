package org.example.DTOs;


import org.example.entities.TaskVersionEntity;

import java.util.Calendar;
import java.util.Date;

public class VersionResponseDto {


    public VersionResponseDto(TaskVersionEntity version){
        this.id = version.getId();
        this.version = version.getVersion();
        this.taskId = version.getTask().getId();
        this.startTime = version.getStartTime();
        this.endTime = version.getEndTime();
    }

    private Long taskId;
    private String version;
    private Calendar endTime;
    private Calendar startTime;
    private Long id;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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
