package org.example.entities;


import org.example.entities.enums.Roles;
import org.example.entities.enums.UserStatus;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {
    public UserEntity(String userName, String password) {

        this.userName = userName;
        this.password = password;
    }

    public UserEntity(String userName) {
        this.userName = userName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    @Enumerated(value = EnumType.STRING)
    private Roles roles;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    public UserEntity() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(user_id, that.user_id) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && roles == that.roles && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, userName, password, roles, status);
    }
}
