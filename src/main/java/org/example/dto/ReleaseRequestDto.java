package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.ProjectEntity;
import org.example.entity.TaskEntity;
import java.util.List;

@Schema(description = "DTO for storing json input for further transformation into release entity")
public class ReleaseRequestDto {

    @Schema(description = "Field that stores project of the release")
    private ProjectEntity project;

    @Schema(description = "Field that stores version of the release")
    private String version;

    @Schema(description = "Field that stores deadline of the release")
    private String endTime;

    @Schema(description = "Field that stores creation time of the release")
    private String creationTime;

    @Schema(description = "Field that stores id of the release")
    private Long id;

    @Schema(description = "Field that stores tasks of the release")
    private List<TaskEntity> tasks;

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String start_time) {
        this.creationTime = start_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
