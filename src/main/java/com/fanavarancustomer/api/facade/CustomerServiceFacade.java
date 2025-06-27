package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.dto.customerService.CustomerServiceDto;
import com.fanavarancustomer.service.customerService.CustomerServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Component
//@RequiredArgsConstructor
public class CustomerServiceFacade {
    private final CustomerServiceManager manager;

    public CustomerServiceFacade(CustomerServiceManager manager) {
        this.manager = manager;
    }

    public CustomerServiceDto create(CustomerServiceDto dto) {
        return manager.create(dto);
    }

    public Page<CustomerServiceDto> getAll(Pageable pageable) {
        return manager.getAll(pageable);
    }

    public CustomerServiceDto getById(Long id) {
        return manager.getById(id);
    }

    public void deactivate(Long id) {
        manager.deactivate(id);
    }
}