package org.example.dto;

import org.example.enumeration.Status;

public class ProjectResponseDto {

    private Long id;
    private String name;
    private Long customerId;
    private Status status;
    private boolean paid;
    private Long price;

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

    public Long getId() { return id; }

    public void setId(Long project_id) {
        this.id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String project_name) {
        this.name = project_name;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customer_id) {
        this.customerId = customer_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
