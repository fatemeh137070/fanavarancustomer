package com.fanavarancustomer.service.ticketResponseService;

import com.fanavarancustomer.api.dto.ticketResponseDto.TicketResponseDto;

import java.util.List;

public interface TicketResponseService {
    TicketResponseDto respondToTicket(TicketResponseDto dto);
    List<TicketResponseDto> getResponsesForTicket(Long ticketId);
}
