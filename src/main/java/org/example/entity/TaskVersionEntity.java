package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "versions")
public class TaskVersionEntity {


    public TaskVersionEntity(String version, Calendar startTime) {
        this.version = version;
        this.startTime = startTime;
    }

    public TaskVersionEntity(Long id, String version, TaskEntity task) {
        this.id = id;
        this.startTime = Calendar.getInstance();
        this.endTime = null;
        this.version = version;
        this.task = task;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_version_id")
    private Long id;

    @JsonIgnore
    @Column(name = "start_time")
    private Calendar startTime;

    @JsonIgnore
    @Column(name = "end_time")
    private Calendar endTime;

    @Column(name = "version")
    private String version;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private TaskEntity task;



    public TaskVersionEntity() {

    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar start_time) {
        this.startTime = start_time;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {this.endTime = endTime;}

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
