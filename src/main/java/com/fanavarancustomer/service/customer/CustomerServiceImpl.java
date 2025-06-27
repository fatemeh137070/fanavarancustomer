package com.fanavarancustomer.service.customer;


import com.fanavarancustomer.api.dto.CustomerDto;
import com.fanavarancustomer.dal.entity.Customer;
import com.fanavarancustomer.dal.repository.CustomerRepository;
import com.fanavarancustomer.exception.DuplicateEntityException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDto registerCustomer(CustomerDto dto) {
        if (dto.getNationalId() != null && customerRepository.existsByNationalId(dto.getNationalId())) {
            throw new DuplicateEntityException("Customer with this National ID already exists.");
        }
        if (dto.getCompanyRegNo() != null && customerRepository.existsByCompanyRegNo(dto.getCompanyRegNo())) {
            throw new DuplicateEntityException("Customer with this Company Registration Number already exists.");
        }

        Customer entity = modelMapper.map(dto, Customer.class);
        Customer saved = customerRepository.save(entity);
        return modelMapper.map(saved, CustomerDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        // اگر می‌خواهی اعتبارسنجی روی nationalId یا companyRegNo در بروز رسانی هم داشته باشی، باید اضافه کنی

        modelMapper.map(dto, customer);
        Customer updated = customerRepository.save(customer);
        return modelMapper.map(updated, CustomerDto.class);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
