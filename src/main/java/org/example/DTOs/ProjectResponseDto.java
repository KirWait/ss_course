package org.example.DTOs;

import org.example.entities.ProjectEntity;
import org.example.entities.enums.Status;

public class ProjectResponseDto {

    private Long project_id;
    private String project_name;
    private Long customer_id;
    private String status;

    public ProjectResponseDto(ProjectEntity project){
        this.project_id = project.getProject_id();
        this.project_name = project.getProject_name();
        this.customer_id = project.getCustomer_id();
        this.status = project.getStatus().name();
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
