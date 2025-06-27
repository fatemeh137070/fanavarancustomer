package com.fanavarancustomer.service.customerService;

import com.fanavarancustomer.api.dto.customerService.CustomerServiceDto;
import com.fanavarancustomer.dal.entity.Customer;
import com.fanavarancustomer.dal.entity.CustomerService;
import com.fanavarancustomer.dal.repository.CustomerRepository;
import com.fanavarancustomer.dal.repository.customerService.CustomerServiceRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class CustomerServiceManagerImpl implements CustomerServiceManager {

    private final CustomerServiceRepository repository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceManagerImpl(CustomerServiceRepository repository,
                                      CustomerRepository customerRepository,
                                      ModelMapper modelMapper) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerServiceDto create(CustomerServiceDto dto) {

        System.out.println("customerId = " + dto.getCustomerId());
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        CustomerService service = modelMapper.map(dto, CustomerService.class);
        service.setCustomer(customer);
        service.setActive(true);

        CustomerService saved = repository.save(service);

        return modelMapper.map(saved, CustomerServiceDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerServiceDto getById(Long id) {
        CustomerService service = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        return modelMapper.map(service, CustomerServiceDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerServiceDto> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(entity -> modelMapper.map(entity, CustomerServiceDto.class));
    }

    @Override
    public void deactivate(Long id) {
        CustomerService service = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        service.setActive(false);
        repository.save(service);
    }
}