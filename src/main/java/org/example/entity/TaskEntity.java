package org.example.entity;

import org.example.enumeration.Status;
import org.example.enumeration.Type;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "tasks")
public class TaskEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "responsible_id")
    private Long responsibleId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private List<TaskVersionEntity> versions;


    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    public TaskEntity() {
    }

    public TaskEntity(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity entity = (TaskEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(projectId, entity.projectId) && status == entity.status && Objects.equals(name, entity.name) && Objects.equals(description, entity.description) && Objects.equals(authorId, entity.authorId) && Objects.equals(responsibleId, entity.responsibleId) && Objects.equals(versions, entity.versions) && type == entity.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, status, name, description, authorId, responsibleId, versions, type);
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", authorId=" + authorId +
                ", responsibleId=" + responsibleId +
                ", versions=" + versions +
                ", type=" + type +
                '}';
    }

    public void setVersions(List<TaskVersionEntity> versions) {

        if (versions != null) {
            versions.forEach(a -> a.setTask(this));

        }
        this.versions = versions;
    }

    public List<TaskVersionEntity> getVersions() {
        return versions;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
