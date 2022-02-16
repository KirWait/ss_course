package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;
import org.example.enumeration.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskStatResponseDto {

    private String name;
    private String description;
    @JsonIgnore
    private UserEntity responsible;
    private String responsibleUsername;
    private Type type;
    private Status status;
    private String releaseVersion;
    private String timeSpent;
}
