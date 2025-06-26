package com.fanavarancustomer.service.customerService;

import com.fanavarancustomer.api.dto.customerService.CustomerServiceDto;

import java.util.List;

public interface CustomerServiceManager {
    CustomerServiceDto create(CustomerServiceDto dto);
    CustomerServiceDto getById(Long id);
    List<CustomerServiceDto> getAll();
    void deactivate(Long id);
}