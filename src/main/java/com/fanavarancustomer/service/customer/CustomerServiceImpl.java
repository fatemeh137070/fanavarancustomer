package com.fanavarancustomer.service.customer;


import com.fanavarancustomer.api.dto.CustomerDto;
import com.fanavarancustomer.dal.entity.Customer;
import com.fanavarancustomer.dal.entity.User;
import com.fanavarancustomer.dal.repository.CustomerRepository;
import com.fanavarancustomer.dal.repository.userRepository.UserRepository;
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
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerDto registerCustomer(CustomerDto dto) {
        if (dto.getNationalId() != null && customerRepository.existsByNationalId(dto.getNationalId())) {
            throw new DuplicateEntityException("Customer with this National ID already exists.");
        }
        if (dto.getCompanyRegNo() != null && customerRepository.existsByCompanyRegNo(dto.getCompanyRegNo())) {
            throw new DuplicateEntityException("Customer with this Company Registration Number already exists.");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));

        Customer entity = modelMapper.map(dto, Customer.class);
        entity.setUser(user);  // ست کردن یوزر روی موجودیت مشتری

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

        // جلوگیری از تغییر شناسه توسط modelMapper
        dto.setId(null); // 👈 خط بسیار مهم برای جلوگیری از overwrite شدن id

        // نگاشت دستی فقط فیلدهای قابل به‌روزرسانی
        customer.setName(dto.getName());
        customer.setCorporate(dto.isCorporate());
        customer.setNationalId(dto.getNationalId());
        customer.setCompanyRegNo(dto.getCompanyRegNo());

        // اگر userId هم نیاز به آپدیت داشت
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUserId()));
            customer.setUser(user);
        }

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
