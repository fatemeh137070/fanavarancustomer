package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.CustomerDto;
import com.fanavarancustomer.dal.entity.Customer;
import com.fanavarancustomer.dal.repository.CustomerRepository;
import com.fanavarancustomer.exception.DuplicateEntityException;
import com.fanavarancustomer.exception.EntityNotFoundException;
import com.fanavarancustomer.service.customer.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer_Success() {
        // Arrange
        CustomerDto dto = CustomerDto.builder()
                .name("Test Customer")
                .corporate(false)
                .nationalId("1234567890")
                .companyRegNo(null)
                .build();

        Customer entity = new Customer();
        Customer savedEntity = new Customer();
        savedEntity.setId(1L);
        savedEntity.setName("Test Customer");
        savedEntity.setCorporate(false);
        savedEntity.setNationalId("1234567890");

        CustomerDto returnedDto = CustomerDto.builder()
                .id(1L)
                .name("Test Customer")
                .corporate(false)
                .nationalId("1234567890")
                .build();

        when(customerRepository.existsByNationalId("1234567890")).thenReturn(false);
        when(customerRepository.existsByCompanyRegNo(null)).thenReturn(false);

        when(modelMapper.map(dto, Customer.class)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, CustomerDto.class)).thenReturn(returnedDto);

        // Act
        CustomerDto result = customerService.registerCustomer(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Customer", result.getName());

        verify(customerRepository).existsByNationalId("1234567890");
        verify(customerRepository).existsByCompanyRegNo(null);
        verify(customerRepository).save(entity);
        verify(modelMapper).map(dto, Customer.class);
        verify(modelMapper).map(savedEntity, CustomerDto.class);
    }

    @Test
    void testRegisterCustomer_DuplicateNationalId() {
        // Arrange
        CustomerDto dto = CustomerDto.builder()
                .name("Test Customer")
                .corporate(false)
                .nationalId("1234567890")
                .build();

        when(customerRepository.existsByNationalId("1234567890")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> customerService.registerCustomer(dto));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testRegisterCustomer_DuplicateCompanyRegNo() {
        // Arrange
        CustomerDto dto = CustomerDto.builder()
                .name("Company A")
                .corporate(true)
                .companyRegNo("CRN123456")
                .build();

        when(customerRepository.existsByNationalId(null)).thenReturn(false);
        when(customerRepository.existsByCompanyRegNo("CRN123456")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> customerService.registerCustomer(dto));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(1L));
        verify(customerRepository).findById(1L);
    }
}