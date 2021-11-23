package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.UserEntity;
import org.example.enumeration.Status;


@Schema(description = "DTO for storing json input for further transformation into project entity")
public class ProjectRequestDto {

    @Schema(description = "Field for defining customer by login")
    private String customerName;

    @Schema(description = "Field that stores name of the project")
    private String name;

    @Schema(description = "Field that stores id of the project")
    private Long id;

    @Schema(description = "Field that stores customer of the project")
    private UserEntity customer;

    @Schema(description = "Field that stores status of the project")
    private Status status;

    @Schema(description = "Field that stores information is project paid or not")
    private boolean paid;

    @Schema(description = "Field that stores price of the project")
    private Long price;

    public ProjectRequestDto(String name, UserEntity customer) {
        this.name=name;
        this.customer = customer;
    }

    public ProjectRequestDto(String name) {
        this.name=name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ProjectRequestDto() {
    }

    public ProjectRequestDto(String customerName, String name) {
        this.customerName = customerName;
        this.name = name;
    }
}
