package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.ticketResponseDto.TicketResponseDto;
import com.fanavarancustomer.dal.entity.Ticket;
import com.fanavarancustomer.dal.entity.TicketResponse;
import com.fanavarancustomer.dal.entity.User;
import com.fanavarancustomer.dal.repository.TicketRepository;
import com.fanavarancustomer.dal.repository.TicketResponseRepository;
import com.fanavarancustomer.dal.repository.userRepository.UserRepository;
import com.fanavarancustomer.service.ticketResponseService.TicketResponseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketResponseServiceImplTest {

    @Mock
    private TicketResponseRepository responseRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private TicketResponseServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        service = new TicketResponseServiceImpl(responseRepository, ticketRepository, userRepository, modelMapper);
    }

    @Test
    void testRespondToTicket_success() {
        // Prepare input DTO
        TicketResponseDto dto = TicketResponseDto.builder()
                .message("Test message")
                .ticketId(1L)
                .responderId(2L)
                .build();

        // Prepare entities
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        User user = new User();
        user.setId(2L);

        TicketResponse response = new TicketResponse();
        response.setMessage("Test message");

        // Mocks
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(responseRepository.save(any(TicketResponse.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute
        TicketResponseDto result = service.respondToTicket(dto);

        // Verify
        assertNotNull(result);
        assertEquals("Test message", result.getMessage());
        verify(responseRepository).save(any(TicketResponse.class));
    }

    @Test
    void testGetResponsesForTicket() {
        TicketResponse response = new TicketResponse();
        response.setMessage("Response message");
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        response.setTicket(ticket);
        User user = new User();
        user.setId(2L);
        response.setResponder(user);

        when(responseRepository.findByTicketId(1L)).thenReturn(Collections.singletonList(response));

        List<TicketResponseDto> result = service.getResponsesForTicket(1L);

        assertEquals(1, result.size());
        assertEquals("Response message", result.get(0).getMessage());
        verify(responseRepository).findByTicketId(1L);
    }
}