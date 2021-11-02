package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.DTOs.VersionRequestDto;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "versions")
public class TaskVersionEntity {


    public TaskVersionEntity(Double version, Calendar startTime) {
        this.version = version;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "\nTaskVersionEntity{" +
                "id=" + id +
                ", start_time=" + startTime +
                ", end_time=" + endTime +
                ", version='" + version + '\'' +
                '}';
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
    private Double version;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    public VersionRequestDto convertToTaskVersionDto(){
        VersionRequestDto requestDto = new VersionRequestDto();
        requestDto.setVersion(version);
        requestDto.setStartTime(startTime);
        requestDto.setEndTime(endTime);
        requestDto.setTask(task);
        requestDto.setId(id);

        return requestDto;

    }

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

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }
}
