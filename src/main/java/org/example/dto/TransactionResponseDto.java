package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for showing json of transaction entity")
public class TransactionResponseDto {

    @Schema(description = "Field that stores amount of payment")
    private Long amount;

    @Schema(description = "Field that stores project id of payment")
    private Long projectId;

    @Schema(description = "Field that stores user id of payment")
    private Long userId;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
