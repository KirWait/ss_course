package org.example.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.ProjectEntity;
import org.example.entity.TaskEntity;

@Schema(description = "DTO for showing json of release entity")
public class ReleaseResponseDto {

    @JsonIgnore
    @Schema(description = "Field that stores tasks of the release")
    private List<TaskEntity> tasks;

    @Schema(description = "Field that stores version of the release")
    private String version;

    @Schema(description = "Field that stores dead line of the release")
    private String endTime;

    @Schema(description = "Field that stores creation time of the release")
    private String creationTime;

    @Schema(description = "Field that stores id of the release")
    private Long id;

    @Schema(description = "Field that stores project of the release")
    private ProjectEntity project;

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

    @Override
    public String toString() {
        return "ReleaseResponseDto{" +
                ", version='" + version + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + creationTime + '\'' +
                ", id=" + id +
                '}';
    }
}
