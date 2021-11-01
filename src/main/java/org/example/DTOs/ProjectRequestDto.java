package org.example.DTOs;

import org.example.entities.ProjectEntity;
import org.example.entities.enums.Status;

public class ProjectRequestDto {

    private String name;
    private Long id;
    private Long customerId;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjectEntity convertToProjectEntity(){
        ProjectEntity project = new ProjectEntity();
        project.setProjectId(id);
        project.setProjectName(name);
        project.setCustomerId(customerId);
        project.setStatus(Status.BACKLOG);
        return project;
    }




}
