package org.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.enumeration.Status;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;



@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FilterDef(name = "deletedProjectFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedProjectFilter", condition = "deleted = :isDeleted")
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.BACKLOG;

    @Column(name = "price")
    private Long price;

    @Column(name = "paid")
    private boolean isPaid = false;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    @Singular
    @ToString.Exclude
    private List<TaskEntity> tasks;

    @Column(name = "deleted")
    @JsonIgnore
    private boolean deleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProjectEntity that = (ProjectEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
