package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.TicketDto;
import com.fanavarancustomer.dal.entity.Customer;
import com.fanavarancustomer.dal.entity.Ticket;
import com.fanavarancustomer.dal.repository.CustomerRepository;
import com.fanavarancustomer.dal.repository.TicketRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import com.fanavarancustomer.service.activityLogService.ActivityLogService;
import com.fanavarancustomer.service.eum.TicketStatus;
import com.fanavarancustomer.service.ticket.TicketManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketManagerImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ActivityLogService activityLogService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TicketManagerImpl ticketManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // ticketManager is initialized with mocks
    }

    @Test
    void testCreate_Success() {
        Long customerId = 1L;
        Customer customer = Customer.builder().id(customerId).build();

        TicketDto dto = TicketDto.builder()
                .subject("خطا در سرور")
                .description("سرور بالا نمی‌آید")
                .customerId(customerId)
                .build();

        Ticket toSave = new Ticket();
        Ticket saved = Ticket.builder()
                .id(10L)
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .customer(customer)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();
        TicketDto returnedDto = TicketDto.builder()
                .id(10L)
                .subject(dto.getSubject())
                .description(dto.getDescription())
                .status(TicketStatus.OPEN)
                .customerId(customerId)
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(modelMapper.map(dto, Ticket.class)).thenReturn(toSave);
        when(ticketRepository.save(toSave)).thenReturn(saved);
        when(modelMapper.map(saved, TicketDto.class)).thenReturn(returnedDto);

        TicketDto result = ticketManager.create(dto);

        assertNotNull(result.getId());
        assertEquals("خطا در سرور", result.getSubject());
        assertEquals(TicketStatus.OPEN, result.getStatus());
        verify(activityLogService).logAction(any());
    }

    @Test
    void testCreate_CustomerNotFound() {
        when(customerRepository.findById(100L)).thenReturn(Optional.empty());

        TicketDto dto = TicketDto.builder()
                .subject("هر موضوعی")
                .description("توضیح تستی")
                .customerId(100L)
                .build();

        assertThrows(EntityNotFoundException.class, () -> ticketManager.create(dto));
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void testGetById_Success() {
        Ticket ticket = Ticket.builder()
                .id(1L)
                .subject("موضوع")
                .description("توضیح")
                .status(TicketStatus.IN_PROGRESS)
                .customer(Customer.builder().id(2L).build())
                .createdAt(LocalDateTime.now())
                .build();
        TicketDto dto = TicketDto.builder()
                .id(1L)
                .subject(ticket.getSubject())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .customerId(2L)
                .build();

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(modelMapper.map(ticket, TicketDto.class)).thenReturn(dto);

        TicketDto result = ticketManager.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals(TicketStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void testGetById_NotFound() {
        when(ticketRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ticketManager.getById(100L));
    }

    @Test
    void testChangeStatus_Success() {
        Ticket ticket = Ticket.builder()
                .id(5L)
                .status(TicketStatus.OPEN)
                .build();

        when(ticketRepository.findById(5L)).thenReturn(Optional.of(ticket));

        ticketManager.changeStatus(5L, "closed");

        assertEquals(TicketStatus.CLOSED, ticket.getStatus());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testChangeStatus_InvalidStatus() {
        Ticket ticket = Ticket.builder().id(6L).status(TicketStatus.OPEN).build();
        when(ticketRepository.findById(6L)).thenReturn(Optional.of(ticket));

        assertThrows(IllegalArgumentException.class, () -> ticketManager.changeStatus(6L, "INVALID"));
    }

    @Test
    void testChangeStatus_TicketNotFound() {
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ticketManager.changeStatus(99L, "closed"));
    }
}
