package org.example.dto;

import org.example.enumeration.Status;

public class ProjectRequestDto {

    private String customerName;
    private String name;
    private Long id;
    private Long customerId;
    private Status status;
    private boolean paid;
    private Long price;

    public ProjectRequestDto(String name, Long customerId) {
        this.name=name;
        this.customerId=customerId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
