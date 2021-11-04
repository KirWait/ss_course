package org.example.entities;


import org.example.entities.enums.Status;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public ProjectEntity(String name) {
        this.projectName = name;
    }

    public ProjectEntity() {
    }

    public ProjectEntity(String projectName, Long customerId) {
        this.projectName = projectName;
        this.customerId = customerId;
        this.status = Status.BACKLOG;

    }

    public ProjectEntity(String projectName, Long customerId, Status status) {
        this.projectName = projectName;
        this.customerId = customerId;
        this.status = status;
    }

    public ProjectEntity(Long id, String projectName, Long customerId, Status status) {
        this.projectId = id;
        this.projectName = projectName;
        this.customerId = customerId;
        this.status = status;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity project = (ProjectEntity) o;
        return Objects.equals(projectId, project.projectId) && Objects.equals(projectName, project.projectName) && Objects.equals(customerId, project.customerId) && status == project.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, customerId, status);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
