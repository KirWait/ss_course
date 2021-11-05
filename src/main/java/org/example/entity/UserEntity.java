package org.example.entity;


import org.example.enumeration.Roles;
import org.example.enumeration.Active;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {
    public UserEntity(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public UserEntity(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")

    @Enumerated(value = EnumType.STRING)
    private Roles roles;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Active active;

    public UserEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long user_id) {
        this.id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Active getActive() {
        return active;
    }

    public void setActive(Active status) {
        this.active = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && roles == that.roles && active == that.active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles, active);
    }
}