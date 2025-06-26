package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.ticketResponseDto.TicketResponseDto;
import com.fanavarancustomer.api.facade.TicketResponseFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-responses")
//@RequiredArgsConstructor
public class TicketResponseController {

    public TicketResponseController(TicketResponseFacade facade) {
        this.facade = facade;
    }

    private final TicketResponseFacade facade;

    @PostMapping
    public ResponseEntity<TicketResponseDto> respond(@RequestBody TicketResponseDto dto) {
        return ResponseEntity.ok(facade.respondToTicket(dto));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<TicketResponseDto>> getResponses(@PathVariable Long ticketId) {
        return ResponseEntity.ok(facade.getResponsesForTicket(ticketId));
    }
}
