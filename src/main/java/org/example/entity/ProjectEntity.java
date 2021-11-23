package org.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.enumeration.Status;

import javax.persistence.*;
import java.util.List;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity customer;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "price")
    private Long price;

    @Column(name = "paid")
    private boolean isPaid;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.DETACH)
    List<TaskEntity> tasks;

    public ProjectEntity() {
    }

    public ProjectEntity(Long id, String name, UserEntity customer, Status status, boolean isPaid, Long price) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.isPaid = isPaid;
        this.price = price;
    }

    public ProjectEntity(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public ProjectEntity(String name) {
        this.name = name;
    }

    public ProjectEntity(String name, Long price, boolean IsPaid, Status status) {
        this.name = name;
        this.price = price;
        this.isPaid = IsPaid;
        this.status = status;
    }

    public ProjectEntity(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity project = (ProjectEntity) o;
        return Objects.equals(id, project.id) && Objects.equals(name, project.name) && Objects.equals(customer, project.customer) && status == project.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, customer, status);
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerId=" + customer +
                ", status=" + status +
                '}';
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        this.isPaid = paid;
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

    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customerId) {
        this.customer = customerId;
    }
}
