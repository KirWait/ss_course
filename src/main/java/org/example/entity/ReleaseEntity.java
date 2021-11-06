package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "release")
public class ReleaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "release_id")
    private Long id;

    @Column(name = "creation_time")
    private String creationTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "version")
    private String version;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "release")
    private List<TaskEntity> tasks;

    @Column(name = "project_id")
    private Long projectId;

    public ReleaseEntity(String version, String creationTime) {
        this.version = version;
        this.creationTime = creationTime;
    }


    public ReleaseEntity() {

    }

    @Override
    public String toString() {
        return "ReleaseEntity{" +
                "id=" + id +
                ", startTime=" + creationTime +
                ", endTime=" + endTime +
                ", version='" + version + '\'' +
                '}';
    }



    public void setTasks(List<TaskEntity> tasks) {

        if (tasks != null) {
            tasks.forEach(a -> a.setRelease(this));

        }
        this.tasks = tasks;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String startTime) {
        this.creationTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {this.endTime = endTime;}

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
