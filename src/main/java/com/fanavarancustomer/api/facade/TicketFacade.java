package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.dto.TicketDto;
import com.fanavarancustomer.service.ticket.TicketManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class TicketFacade {
    private final TicketManager manager;

    public TicketFacade(TicketManager manager) {
        this.manager = manager;
    }

    public TicketDto create(TicketDto dto) {
        return manager.create(dto);
    }

    public List<TicketDto> getAll() {
        return manager.getAll();
    }

    public TicketDto getById(Long id) {
        return manager.getById(id);
    }

    public void changeStatus(Long id, String status) {
        manager.changeStatus(id, status);
    }
}