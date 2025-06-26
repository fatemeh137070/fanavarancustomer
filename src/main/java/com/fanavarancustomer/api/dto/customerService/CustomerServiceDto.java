package com.fanavarancustomer.api.dto.customerService;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class CustomerServiceDto {
    private Long id;
    private String serverName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private Long customerId;

    public CustomerServiceDto() {
    }

    public CustomerServiceDto(Long id, String serverName, LocalDate startDate, LocalDate endDate, boolean active, Long customerId) {
        this.id = id;
        this.serverName = serverName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}