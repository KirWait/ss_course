package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;

@Schema(description = "DTO for showing json of user entity")
public class UserResponseDto {

    @Schema(description = "Field that stores id of the user")
    private Long id;

    @Schema(description = "Field that stores username of the user")
    private String username;

    @JsonIgnore
    @Schema(description = "Field that stores password of the user")
    private String password;

    @Schema(description = "Field that stores roles of the user")
    private Roles roles;

    @JsonIgnore
    @Schema(description = "Field that stores active status of the user")
    private Active active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setActive(Active active) {
        this.active = active;
    }
}
