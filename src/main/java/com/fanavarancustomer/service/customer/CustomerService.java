package com.fanavarancustomer.service.customer;


import com.fanavarancustomer.api.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto registerCustomer(CustomerDto dto);
    CustomerDto getCustomerById(Long id);
    List<CustomerDto> getAllCustomers();
    CustomerDto updateCustomer(Long id, CustomerDto dto);
    void deleteCustomer(Long id);
}