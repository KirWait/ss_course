package org.example.DTOs;

import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;
import java.util.Calendar;


public class VersionRequestDto {

    private String version;
    private Calendar endTime;
    private Calendar startTime;
    private Long id;
    private TaskEntity task;

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

    public TaskVersionEntity convertToTaskVersionEntity(){
        TaskVersionEntity taskVersion = new TaskVersionEntity();

        taskVersion.setVersion(version);
        taskVersion.setStartTime(startTime);
        taskVersion.setEndTime(endTime);
        taskVersion.setTask(task);
        taskVersion.setId(id);

        return taskVersion;

    }
}
