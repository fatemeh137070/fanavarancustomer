package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.invoice.InvoiceDto;
import com.fanavarancustomer.dal.entity.CustomerService;
import com.fanavarancustomer.dal.entity.Invoice;
import com.fanavarancustomer.dal.repository.customerService.CustomerServiceRepository;
import com.fanavarancustomer.dal.repository.invoice.InvoiceRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import com.fanavarancustomer.service.eum.InvoiceType;
import com.fanavarancustomer.service.invoice.InvoiceManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvoiceManagerImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private CustomerServiceRepository customerServiceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InvoiceManagerImpl invoiceManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // invoiceManager با repository ها و modelMapper مقداردهی می‌شود
    }

    @Test
    void testCreateInvoice_Success() {
        // Arrange
        Long serviceId = 1L;
        CustomerService service = CustomerService.builder().id(serviceId).build();

        InvoiceDto dto = InvoiceDto.builder()
                .amount(BigDecimal.valueOf(500000))
                .issuedDate(LocalDate.now())
                .type(InvoiceType.PURCHASE)
                .customerServiceId(serviceId)
                .build();

        Invoice toSave = new Invoice();
        Invoice saved = Invoice.builder()
                .id(10L)
                .amount(dto.getAmount())
                .issuedDate(dto.getIssuedDate())
                .type(dto.getType())
                .paid(false)
                .customerService(service)
                .build();

        InvoiceDto returnedDto = InvoiceDto.builder()
                .id(10L)
                .amount(dto.getAmount())
                .issuedDate(dto.getIssuedDate())
                .type(dto.getType())
                .paid(false)
                .customerServiceId(serviceId)
                .build();

        when(customerServiceRepository.findById(serviceId))
                .thenReturn(Optional.of(service));
        when(modelMapper.map(dto, Invoice.class))
                .thenReturn(toSave);
        when(invoiceRepository.save(toSave))
                .thenReturn(saved);
        when(modelMapper.map(saved, InvoiceDto.class))
                .thenReturn(returnedDto);

        // Act
        InvoiceDto result = invoiceManager.create(dto);

        // Assert
        assertNotNull(result.getId());
        assertEquals(BigDecimal.valueOf(500000), result.getAmount());
        assertEquals(InvoiceType.PURCHASE, result.getType());
        assertEquals(serviceId, result.getCustomerServiceId());
        verify(invoiceRepository).save(toSave);
        verify(modelMapper).map(dto, Invoice.class);
        verify(modelMapper).map(saved, InvoiceDto.class);
    }

    @Test
    void testCreateInvoice_CustomerServiceNotFound() {
        // Arrange
        when(customerServiceRepository.findById(99L))
                .thenReturn(Optional.empty());
        InvoiceDto dto = InvoiceDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .issuedDate(LocalDate.now())
                .type(InvoiceType.RENEWAL)
                .customerServiceId(99L)
                .build();

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> invoiceManager.create(dto));
        verify(invoiceRepository, never()).save(any());
    }

    @Test
    void testGetById_Success() {
        // Arrange
        Invoice invoice = Invoice.builder()
                .id(5L)
                .amount(BigDecimal.valueOf(200000))
                .issuedDate(LocalDate.now())
                .type(InvoiceType.RENEWAL)
                .paid(true)
                .customerService(CustomerService.builder().id(1L).build())
                .build();
        InvoiceDto dto = InvoiceDto.builder()
                .id(5L)
                .amount(invoice.getAmount())
                .issuedDate(invoice.getIssuedDate())
                .type(invoice.getType())
                .paid(true)
                .customerServiceId(1L)
                .build();

        when(invoiceRepository.findById(5L))
                .thenReturn(Optional.of(invoice));
        when(modelMapper.map(invoice, InvoiceDto.class))
                .thenReturn(dto);

        // Act
        InvoiceDto result = invoiceManager.getById(5L);

        // Assert
        assertEquals(5L, result.getId());
        assertTrue(result.isPaid());
        verify(modelMapper).map(invoice, InvoiceDto.class);
    }

    @Test
    void testGetById_NotFound() {
        when(invoiceRepository.findById(100L))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> invoiceManager.getById(100L));
    }

    @Test
    void testMarkAsPaid_Success() {
        // Arrange
        Invoice invoice = Invoice.builder()
                .id(12L)
                .paid(false)
                .build();
        when(invoiceRepository.findById(12L))
                .thenReturn(Optional.of(invoice));

        // Act
        invoiceManager.markAsPaid(12L);

        // Assert
        assertTrue(invoice.isPaid());
        verify(invoiceRepository).save(invoice);
    }
}