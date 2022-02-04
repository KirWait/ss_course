package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "releases")
public class ReleaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_time")
    private String creationTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "version")
    private String version;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "release")
    @Singular
    @ToString.Exclude
    private List<TaskEntity> tasks;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @ToString.Exclude
    private ProjectEntity project;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReleaseEntity that = (ReleaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
