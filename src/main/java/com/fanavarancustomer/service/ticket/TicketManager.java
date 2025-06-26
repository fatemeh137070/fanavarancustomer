package com.fanavarancustomer.service.ticket;

import com.fanavarancustomer.api.dto.TicketDto;

import java.util.List;

public interface TicketManager {
    TicketDto create(TicketDto dto);
    TicketDto getById(Long id);
    List<TicketDto> getAll();
    void changeStatus(Long id, String status);
}