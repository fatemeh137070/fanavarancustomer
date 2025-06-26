package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.dto.ticketResponseDto.TicketResponseDto;
import com.fanavarancustomer.service.ticketResponseService.TicketResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class TicketResponseFacade {
    public TicketResponseFacade(TicketResponseService service) {
        this.service = service;
    }

    private final TicketResponseService service;

    public TicketResponseDto respondToTicket(TicketResponseDto dto) {
        return service.respondToTicket(dto);
    }

    public List<TicketResponseDto> getResponsesForTicket(Long ticketId) {
        return service.getResponsesForTicket(ticketId);
    }
}