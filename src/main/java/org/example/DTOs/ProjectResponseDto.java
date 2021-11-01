package org.example.DTOs;

import org.example.entities.ProjectEntity;

public class ProjectResponseDto {

    private Long projectId;
    private String projectName;
    private Long customerId;
    private String status;

    public ProjectResponseDto(ProjectEntity project){
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.customerId = project.getCustomerId();
        this.status = project.getStatus().name();
    }

    public Long getProjectId() { return projectId; }

    public void setProjectId(Long project_id) {
        this.projectId = project_id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String project_name) {
        this.projectName = project_name;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customer_id) {
        this.customerId = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
