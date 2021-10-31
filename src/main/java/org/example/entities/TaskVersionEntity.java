package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "versions")
public class TaskVersionEntity {
    public TaskVersionEntity(String version, Calendar start_time) {
        this.version = version;
        this.start_time = start_time;
    }

    @JsonIgnore
    public static final TaskVersionEntity DEFAULT_VERSION = new TaskVersionEntity("1.0", Calendar.getInstance());

    @Override
    public String toString() {
        return "\nTaskVersionEntity{" +
                "id=" + id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", version='" + version + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_version_id")
    private Long id;

    @Column(name = "start_time")
    private Calendar start_time;

    @JsonIgnore
    @Column(name = "end_time")
    private Date end_time;

    @Column(name = "version")
    private String version;

    public TaskVersionEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getStart_time() {
        return start_time;
    }

    public void setStart_time(Calendar start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
