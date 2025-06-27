package com.fanavarancustomer.service.customerService;

import com.fanavarancustomer.api.dto.customerService.CustomerServiceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CustomerServiceManager {
    CustomerServiceDto create(CustomerServiceDto dto);
    CustomerServiceDto getById(Long id);
    Page<CustomerServiceDto> getAll(Pageable pageable);
    void deactivate(Long id);
}