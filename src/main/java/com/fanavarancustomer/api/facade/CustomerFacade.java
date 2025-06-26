package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.dto.CustomerDto;
import com.fanavarancustomer.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class CustomerFacade {

    private final CustomerService customerService;

    public CustomerFacade(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDto register(CustomerDto dto) {
        return customerService.registerCustomer(dto);
    }

    public CustomerDto getById(Long id) {
        return customerService.getCustomerById(id);
    }

    public List<CustomerDto> getAll() {
        return customerService.getAllCustomers();
    }

    public CustomerDto update(Long id, CustomerDto dto) {
        return customerService.updateCustomer(id, dto);
    }

    public void delete(Long id) {
        customerService.deleteCustomer(id);
    }
}