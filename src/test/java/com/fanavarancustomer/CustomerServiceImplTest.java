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
        // customerService به صورت خودکار با customerRepository و modelMapper مقداردهی می‌شود
    }

    @Test
    void testRegisterCustomer_Success() {
        // Arrange
        CustomerDto dto = CustomerDto.builder()
                .name("Test Customer")
                .corporate(false)
                .nationalIdOrCompanyRegNo("1234567890")
                .build();

        Customer entity = new Customer();
        Customer savedEntity = new Customer();
        savedEntity.setId(1L);
        savedEntity.setName("Test Customer");
        savedEntity.setCorporate(false);
        savedEntity.setNationalIdOrCompanyRegNo("1234567890");

        CustomerDto returnedDto = CustomerDto.builder()
                .id(1L)
                .name("Test Customer")
                .corporate(false)
                .nationalIdOrCompanyRegNo("1234567890")
                .build();

        when(customerRepository.existsByNationalIdOrCompanyRegNo(
                dto.getNationalId(), dto.getCompanyRegNo()
        )).thenReturn(false);

        when(modelMapper.map(dto, Customer.class)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, CustomerDto.class)).thenReturn(returnedDto);

        // Act
        CustomerDto result = customerService.registerCustomer(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Customer", result.getName());
        verify(customerRepository).save(entity);
        verify(modelMapper).map(dto, Customer.class);
        verify(modelMapper).map(savedEntity, CustomerDto.class);
    }

    @Test
    void testRegisterCustomer_Duplicate() {
        // Arrange
        CustomerDto dto = CustomerDto.builder()
                .name("Test Customer")
                .corporate(false)
                .nationalIdOrCompanyRegNo("1234567890")
                .build();

        when(customerRepository.existsByNationalIdOrCompanyRegNo(
                dto.getNationalId(), dto.getCompanyRegNo()
        )).thenReturn(true);

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