package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.customerService.CustomerServiceDto;
import com.fanavarancustomer.dal.entity.Customer;
import com.fanavarancustomer.dal.entity.CustomerService;
import com.fanavarancustomer.dal.repository.CustomerRepository;
import com.fanavarancustomer.dal.repository.customerService.CustomerServiceRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import com.fanavarancustomer.service.customerService.CustomerServiceManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceManagerImplTest {

    @Mock
    private CustomerServiceRepository serviceRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceManagerImpl serviceManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // serviceManager با serviceRepository, customerRepository و modelMapper مقداردهی می‌شود
    }

    @Test
    public void testCreateCustomerService_Success() {
        // Arrange
        Long customerId = 1L;
        Customer customer = Customer.builder()
                .id(customerId)
                .name("Fatemeh")
                .corporate(false)
                .build();

        CustomerServiceDto dto = CustomerServiceDto.builder()
                .serverName("Server-A")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(6))
                .active(true)
                .customerId(customerId)
                .build();

        CustomerService toSaveEntity = new CustomerService();
        CustomerService savedEntity = CustomerService.builder()
                .id(10L)
                .serverName("Server-A")
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .active(true)
                .customer(customer)
                .build();

        CustomerServiceDto returnedDto = CustomerServiceDto.builder()
                .id(10L)
                .serverName("Server-A")
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .active(true)
                .customerId(customerId)
                .build();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customer));
        when(modelMapper.map(dto, CustomerService.class))
                .thenReturn(toSaveEntity);
        when(serviceRepository.save(toSaveEntity))
                .thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, CustomerServiceDto.class))
                .thenReturn(returnedDto);

        // Act
        CustomerServiceDto result = serviceManager.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Server-A", result.getServerName());
        assertEquals(customerId, result.getCustomerId());
        verify(serviceRepository).save(toSaveEntity);
        verify(modelMapper).map(dto, CustomerService.class);
        verify(modelMapper).map(savedEntity, CustomerServiceDto.class);
    }

    @Test
    public void testCreateCustomerService_CustomerNotFound() {
        // Arrange
        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        CustomerServiceDto dto = CustomerServiceDto.builder()
                .serverName("S1")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .customerId(99L)
                .build();

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> serviceManager.create(dto));
        verify(serviceRepository, never()).save(any());
    }

    @Test
    public void testGetById_NotFound() {
        // Arrange
        when(serviceRepository.findById(111L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> serviceManager.getById(111L));
        verify(serviceRepository).findById(111L);
    }

    @Test
    public void testDeactivateCustomerService() {
        // Arrange
        CustomerService cs = CustomerService.builder()
                .id(50L)
                .serverName("Test-Server")
                .active(true)
                .build();

        when(serviceRepository.findById(50L))
                .thenReturn(Optional.of(cs));

        // Act
        serviceManager.deactivate(50L);

        // Assert
        assertFalse(cs.isActive());
        verify(serviceRepository).save(cs);
    }
}