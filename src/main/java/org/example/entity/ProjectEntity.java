package org.example.entity;


import org.example.enumeration.Status;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name")
    private String name;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "price")
    private Long price;

    @Column(name = "paid")
    private boolean paid;


    public ProjectEntity() {
    }

    public ProjectEntity(Long id, String name, Long customerId, Status status) {
        this.id = id;
        this.name = name;
        this.customerId = customerId;
        this.status = status;
    }

    public ProjectEntity(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public ProjectEntity(String name) {
        this.name = name;
    }

    public ProjectEntity(String name, Long price, boolean paid, Status status) {
        this.name = name;
        this.price = price;
        this.paid = paid;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity project = (ProjectEntity) o;
        return Objects.equals(id, project.id) && Objects.equals(name, project.name) && Objects.equals(customerId, project.customerId) && status == project.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customerId, status);
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerId=" + customerId +
                ", status=" + status +
                '}';
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long projectId) {
        this.id = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String projectName) {
        this.name = projectName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
